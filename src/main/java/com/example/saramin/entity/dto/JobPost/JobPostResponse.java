package com.example.saramin.entity.dto.JobPost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostResponse {
    private String title;
    private Date deadline;
    private Date postDate;
}
