package com.example.saramin.entity.dto.JobPost;


import com.example.saramin.entity.model.JobPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDto {
    private Long id;
    private String title;
    private String skillStack;
    private String workPlace;
    private String career;
    private Integer careerMin;
    private Integer careerMax;
    private String education;
    private Date postDate;
    private Date deadline;
    private Integer viewCount;

    public static JobPostDto toDto(JobPost jobPost) {
        if (jobPost == null) {
            return null;
        }

        return new JobPostDto(
                jobPost.getId(),
                jobPost.getTitle(),
                jobPost.getSkillStack(),
                jobPost.getWorkPlace(),
                jobPost.getCareer(),
                jobPost.getCareerMin(),
                jobPost.getCareerMax(),
                jobPost.getEducation(),
                jobPost.getPostDate(),
                jobPost.getDeadline(),
                jobPost.getViewCount()
        );
    }
}
