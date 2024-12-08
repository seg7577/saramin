package com.example.saramin.entity.model;

import com.example.saramin.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private UserRole userRole;

    @ManyToMany(mappedBy = "applicants")
    @Builder.Default
    private List<JobPost> appliedJobPosts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "bookmark",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_post_id")
    )
    @Builder.Default
    private List<JobPost> bookmarkedJobPosts = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}
