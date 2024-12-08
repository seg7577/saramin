package com.example.saramin.service;

import com.example.saramin.customException.CustomExceptions;
import com.example.saramin.entity.dto.JobPost.JobPostDto;
import com.example.saramin.entity.dto.JobPost.JobPostUpdateForm;
import com.example.saramin.entity.dto.JobPost.JobsRequestForm;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.repository.JobPostRepository;
import com.example.saramin.util.CareerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jobPostRepository;

    public Map<String, Object> getJobPosts(JobsRequestForm requestForm) {
        if (requestForm.getCurrentPage() == null || requestForm.getCurrentPage() <= 0) {
            throw new CustomExceptions.BadRequestException("페이지 번호가 잘못되었습니다");
        }

        int pageSize = 20;
        Sort sort;

        if ("alphabetical".equals(requestForm.getSortBy())) {
            sort = Sort.by(Sort.Direction.ASC, "title");
        } else if ("newest".equals(requestForm.getSortBy())) {
            sort = Sort.by(Sort.Direction.DESC, "postDate");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "postDate");
        }

        PageRequest pageRequest = PageRequest.of(requestForm.getCurrentPage() - 1, pageSize, sort);

        Integer userCareer = requestForm.getCareer();

        try {
            Page<JobPost> jobPostPage = jobPostRepository.findAllByFilters(
                    requestForm.getWorkplace(),
                    userCareer,
                    userCareer,
                    requestForm.getSkillStack(),
                    requestForm.getSearchBy(),
                    requestForm.getKeyword(),
                    pageRequest
            );

            List<Map<String, Object>> jobPostResponses = jobPostPage.getContent().stream()
                    .map(jobPost -> {
                        Map<String, Object> jobPostResponse = new LinkedHashMap<>();
                        jobPostResponse.put("id", jobPost.getId());
                        jobPostResponse.put("title", jobPost.getTitle());
                        jobPostResponse.put("deadline", jobPost.getDeadline());
                        jobPostResponse.put("postDate", jobPost.getPostDate());
                        return jobPostResponse;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "200");
            response.put("message", "채용 공고 조회 성공");
            response.put("currentPage", jobPostPage.getNumber() + 1);
            response.put("totalPages", jobPostPage.getTotalPages());
            response.put("totalElements", jobPostPage.getTotalElements());
            response.put("jobPosts", jobPostResponses);

            return response;

        } catch (IllegalArgumentException e) {
            throw new CustomExceptions.BadRequestException("잘못된 요청입니다: " + e.getMessage());
        }
    }

    public Map<String, Object> getJobPostDetail(Long id) throws CustomExceptions.BadRequestException {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(() ->
                new CustomExceptions.BadRequestException("존재하지 않는 채용 공고입니다."));

        jobPost.setViewCount(jobPost.getViewCount() + 1);
        jobPostRepository.save(jobPost);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "채용 공고 조회 성공");
        response.put("jobPost", JobPostDto.toDto(jobPost));

        return response;
    }

    public Map<String, Object> updateJobPost(Long id, JobPostUpdateForm updateForm) throws CustomExceptions.BadRequestException {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(() ->
                new CustomExceptions.BadRequestException("존재하지 않는 채용 공고입니다."));

        try {
            jobPost.setTitle(updateForm.getTitle());
            jobPost.setSkillStack(updateForm.getSkillStack());
            jobPost.setWorkPlace(updateForm.getWorkPlace());
            jobPost.setCareer(updateForm.getCareer());

            Integer careerMin = CareerConverter.extractMinCareer(updateForm.getCareer());
            Integer careerMax = CareerConverter.extractMaxCareer(updateForm.getCareer());

            jobPost.setCareerMin(careerMin);
            jobPost.setCareerMax(careerMax);

            jobPost.setEducation(updateForm.getEducation());
            jobPost.setDeadline(updateForm.getDeadline());

            jobPostRepository.save(jobPost);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "200");
            response.put("message", "채용 공고 수정 성공");
            response.put("jobPost", JobPostDto.toDto(jobPost));

            return response;
        } catch (Exception e) {
            throw new CustomExceptions.BadRequestException("채용 공고 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteJobPost(Long id) {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(() ->
                new CustomExceptions.BadRequestException("존재하지 않는 채용 공고입니다."));

        jobPostRepository.deleteById(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "채용 공고 삭제 성공");
        return response;
    }
}