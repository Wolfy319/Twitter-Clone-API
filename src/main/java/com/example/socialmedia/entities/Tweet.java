package com.example.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class TweetEntity {

    @Id
    @GeneratedValue
    @OneToMany
    @JoinTable(name = "tweet_hashtags")
    private long id;

    @ManyToOne
    @JoinColumn(table = "user_table", name = "username")
    private int author;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    @ManyToOne
    @JoinTable(name = "tweet")
    private int inReplyTo;

    @ManyToOne
    @JoinTable(name = "tweet")
    @OneToMany
    @JoinTable(name = "user_mentions")
    private int repostOf;

}
