package com.example.saramin.repository;

import com.example.saramin.entity.model.Bookmark;
import com.example.saramin.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndJobPostId(User user, Long jobPostId);
}
