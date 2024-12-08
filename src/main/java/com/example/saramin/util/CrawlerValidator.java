package com.example.saramin.util;

import com.example.saramin.entity.model.Company;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.repository.JobPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrawlerValidator {
    private final JobPostRepository jobPostRepository;

    public boolean isJobPostValid(String title, Company company) {
        return title != null && !title.trim().isEmpty() && company != null;
    }

    public boolean isJobPostUnique(String title, Company company) {
        Optional<JobPost> existingJobPost = jobPostRepository.findByTitleAndCompany(title, company);
        return existingJobPost.isEmpty();
    }

    public boolean isAnyFieldNull(String companyName, String title, String workPlace, String career, String education,
                                  Date deadlineDate, Date postDate) {
        return companyName == null || title == null || workPlace == null || career == null ||
                education == null || deadlineDate == null || postDate == null;
    }
}
