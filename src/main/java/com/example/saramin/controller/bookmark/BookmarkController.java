package com.example.saramin.controller.bookmark;

import com.example.saramin.entity.dto.JobPost.JobPostIdRequest;
import com.example.saramin.service.BookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
@Tag(name = "Bookmarks", description = "북마크 관련 API")
public class BookmarkController implements BookmarkControllerDocs {
    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleBookmark(Authentication authentication, @RequestBody JobPostIdRequest jobPostIdRequest) {
        Map<String, Object> response = bookmarkService.toggleBookmark(authentication, jobPostIdRequest.getJobPostId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{currentPage}")
    public ResponseEntity<Map<String, Object>> getUserBookmarks(Authentication authentication,
                                                                @PathVariable int currentPage) {
        Map<String, Object> response = bookmarkService.getUserBookmarks(authentication, currentPage);
        return ResponseEntity.ok(response);
    }
}
