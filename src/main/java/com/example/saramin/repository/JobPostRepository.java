package com.example.saramin.repository;

import com.example.saramin.entity.model.Company;
import com.example.saramin.entity.model.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    Optional<JobPost> findByTitleAndCompany(String title, Company company);

    @Query("SELECT jp FROM JobPost jp WHERE " +
            "(:workplace IS NULL OR " +
            "(:workplace LIKE '%전체' AND jp.workPlace LIKE CONCAT('%', SUBSTRING_INDEX(:workplace, '전체', 1), '%')) OR " +
            "(jp.workPlace LIKE CONCAT('%', :workplace, '%')) OR " +
            "(jp.workPlace LIKE CONCAT('%', REPLACE(:workplace, ' 외', ''), '%'))) AND " +
            "(:careerMin IS NULL OR jp.careerMin <= :careerMin) AND " +
            "(:careerMax IS NULL OR jp.careerMax >= :careerMax) AND " +
            "(:skillStack IS NULL OR jp.skillStack LIKE CONCAT('%', :skillStack, '%')) AND " +
            "(:keyword IS NULL OR " +
            "(:searchBy = 'title' AND jp.title LIKE CONCAT('%', :keyword, '%')) OR " +
            "(:searchBy = 'companyName' AND jp.company.companyName LIKE CONCAT('%', :keyword, '%')))")
    Page<JobPost> findAllByFilters(
            @Param("workplace") String workplace,
            @Param("careerMin") Integer careerMin,
            @Param("careerMax") Integer careerMax,
            @Param("skillStack") String skillStack,
            @Param("searchBy") String searchBy,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
