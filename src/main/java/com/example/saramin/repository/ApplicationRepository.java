package com.example.saramin.repository;

import com.example.saramin.entity.model.Application;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByUserAndJobPost(User user, JobPost jobPost);

    Collection<Application> findByUser(User user);
}
