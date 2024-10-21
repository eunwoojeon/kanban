package com.eunwoojeon.kanban.database;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public class Entities {
    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Getter
    @Setter
    @Builder
    @Table(name = "users")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserEntity {
        @Id
        private long id;
        @Column(nullable = false, unique = true)
        private String username;
        @Column(unique = true)
        @Builder.Default
        private String email = "";
        @Column(nullable = false, unique = true)
        private String password;
        @Builder.Default
        private String role = "ROLE_CLIENT";

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        @Builder.Default
        private LocalDateTime createdAt = LocalDateTime.now();
        @Column(name = "updated_at", nullable = false)
        @LastModifiedDate
        @Builder.Default
        private LocalDateTime updatedAt = LocalDateTime.now();
    }

    @Entity
    @EntityListeners(AuditingEntityListener.class)
    @Getter
    @Setter
    @Builder
    @Table(name = "boards")
    public static class BoardEntity {
        @Id
        private long id;
        @Column(nullable = false)
        private String title;
        @Column(columnDefinition = "TEXT")
        private String description;
        @ManyToOne
        @JoinColumn(name = "author_id")
        private UserEntity userJoinColumn;
        @OneToMany(mappedBy = "boardJoinColumn", cascade = CascadeType.ALL)
        private Set<ListEntity> lists;
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
    @IdClass(BoardMemberId.class)
    public static class BoardMemberEntity {
        @Id
        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity userJoinColumn;

        @Id
        @ManyToOne
        @JoinColumn(name = "board_id")
        private BoardEntity boardJoinColumn;

        @Builder.Default
        @Column(columnDefinition = "VARCHAR", nullable = false)
        private Role role = Role.MEMBER;
    }

    enum Role {
        MEMBER, MANAGER;
    }

    public class BoardMemberId implements Serializable {
        @Column(name = "user_id")
        private long userJoinColumn;
        @Column(name = "board_id")
        private long boardJoinColumn;
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
        private BoardEntity boardJoinColumn;

        @Column
        private Integer position;

        @OneToMany(mappedBy = "listJoinColumn", cascade = CascadeType.ALL)
        private Set<CardEntity> cardEntities;
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "cards")
    public static class CardEntity {
        @Id
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(columnDefinition = "TEXT")
        private String description;

        @ManyToOne
        @JoinColumn(name = "list_id")
        private ListEntity listJoinColumn;

        @Column
        private Integer position;

        @OneToMany(mappedBy = "cardJoinColumn", cascade = CascadeType.ALL)
        private Set<CommentEntity> commentEntities;

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
    public static class CommentEntity {
        @Id
        private Long id;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity userJoinColumn;

        @ManyToOne
        @JoinColumn(name = "card_id")
        private CardEntity cardJoinColumn;

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
    public static class ActivityEntity {

        @Id
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity userJoinColumn;

        @ManyToOne
        @JoinColumn(name = "board_id")
        private BoardEntity boardJoinColumn;

        @Column(nullable = false)
        private String action;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    }
}
