package com.eunwoojeon.kanban.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

public class Entities {
    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Getter
    @Setter
    @Builder
    @Table(name = "users")
    public static class User {
        @Id
        private long id;
        @Column(nullable = false)
        private String username;
        @Column(nullable = false, unique = true)
        private String email;
        @Column(nullable = false)
        private String password;
        @CreatedDate
        private LocalDateTime createdAt;
        @LastModifiedDate
        private LocalDateTime updatedAt;
    }

    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Getter
    @Setter
    @Builder
    @Table(name = "boards")
    public static class Board {
        @Id
        private long id;
        private String username;
        private String email;
        private String password;
        @CreatedDate
        @Builder.Default
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt = LocalDateTime.now();
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "board_members")
    @IdClass(BoardMember.class)
    public static class BoardMember {
        @Id
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Id
        @ManyToOne
        @JoinColumn(name = "board_id")
        private Board board;

        @Column(nullable = false)
        @Builder.Default
        private String role = "member";
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "lists")
    public static class ListEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @ManyToOne
        @JoinColumn(name = "board_id")
        private Board board;

        @Column
        private Integer position;

        @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
        private Set<Card> cards;
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "cards")
    public static class Card {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(columnDefinition = "TEXT")
        private String description;

        @ManyToOne
        @JoinColumn(name = "list_id")
        private ListEntity list;

        @Column
        private Integer position;

        @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
        private Set<Comment> comments;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt = LocalDateTime.now();

        @PreUpdate
        public void preUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
    }

    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Getter
    @Setter
    @Builder
    @Table(name = "comments")
    public static class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "card_id")
        private Card card;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt = LocalDateTime.now();
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "activities")
    public static class Activity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "board_id")
        private Board board;

        @Column(nullable = false)
        private String action;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    }
}
