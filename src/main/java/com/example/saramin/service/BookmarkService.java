package com.example.saramin.service;

import com.example.saramin.customException.CustomExceptions;
import com.example.saramin.entity.model.Bookmark;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.entity.model.User;
import com.example.saramin.repository.BookmarkRepository;
import com.example.saramin.repository.JobPostRepository;
import com.example.saramin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;
    private final BookmarkRepository bookmarkRepository;

    public Map<String, Object> toggleBookmark(Authentication authentication, Long jobPostId) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomExceptions.UnauthorizedException("사용자를 찾을 수 없습니다."));

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndJobPostId(user, jobPostId);

        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new CustomExceptions.BadRequestException("채용 공고를 찾을 수 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            
            response.put("status", "200");
            response.put("message", "북마크 제거 성공");
            response.put("title", jobPost.getTitle());
        } else {
            Bookmark newBookmark = Bookmark.builder()
                    .user(user)
                    .jobPost(jobPost)
                    .bookmarkedAt(LocalDateTime.now())
                    .build();
            bookmarkRepository.save(newBookmark);

            response.put("status", "200");
            response.put("message", "북마크 추가 성공");
            response.put("title", jobPost.getTitle());
        }

        return response;
    }

    public Map<String, Object> getUserBookmarks(Authentication authentication, int currentPage) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomExceptions.UnauthorizedException("사용자를 찾을 수 없습니다."));

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);

        bookmarks.sort((bookmark1, bookmark2) -> bookmark2.getBookmarkedAt().compareTo(bookmark1.getBookmarkedAt()));

        int pageSize = 5;
        int start = (currentPage-1) * pageSize;
        int end = Math.min(start + pageSize, bookmarks.size());
        List<Bookmark> pagedBookmarks = bookmarks.subList(start, end);

        int totalPages = (int) Math.ceil((double) bookmarks.size() / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "사용자의 북마크된 채용 공고 목록입니다.");
        response.put("user", user.getEmail());
        response.put("totalPages", totalPages);
        response.put("currentPage", currentPage);

        List<Map<String, Object>> jobPostInfos = pagedBookmarks.stream()
                .map(bookmark -> {
                    Map<String, Object> jobPostInfo = new LinkedHashMap<>();
                    jobPostInfo.put("title", bookmark.getJobPost().getTitle());
                    jobPostInfo.put("id", bookmark.getJobPost().getId().toString());
                    jobPostInfo.put("bookmarkedAt", bookmark.getBookmarkedAt());
                    return jobPostInfo;
                })
                .collect(Collectors.toList());

        response.put("bookmarkedJobPosts", jobPostInfos);

        return response;
    }
}
