package com.eunwoojeon.kanban.database;

import jakarta.persistence.*;
import lombok.*;
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
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false)
        private String password;

        @Builder.Default
        private String role = "ROLE_USER";

        @Column(name = "created_at")
        @CreatedDate
        @Builder.Default
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "updated_at")
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
        @GeneratedValue(strategy = GenerationType.AUTO)
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
        @Column(name = "created_at")
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at")
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

        @Column(columnDefinition = "VARCHAR")
        @Embedded
        private Role role;
    }

    @Embeddable
    public class Role {
        private String member;
        private String manager;
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
        @GeneratedValue(strategy = GenerationType.AUTO)
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
        @GeneratedValue(strategy = GenerationType.AUTO)
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
        @Column(name = "created_at")
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at")
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
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(columnDefinition = "TEXT")
        private String content;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity userJoinColumn;

        @ManyToOne
        @JoinColumn(name = "card_id")
        private CardEntity cardJoinColumn;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at")
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        @Builder.Default
        @Column(name = "updated_at")
        private LocalDateTime updatedAt = LocalDateTime.now();
    }

    @Entity
    @Getter
    @Setter
    @Builder
    @Table(name = "activities")
    public static class ActivityEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity userJoinColumn;

        @ManyToOne
        @JoinColumn(name = "board_id")
        private BoardEntity boardJoinColumn;

        @Column
        private String action;

        @CreatedDate
        @Builder.Default
        @Column(name = "created_at")
        private LocalDateTime createdAt = LocalDateTime.now();
    }
}
