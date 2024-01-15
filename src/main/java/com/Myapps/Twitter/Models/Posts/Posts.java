package com.Myapps.Twitter.Models.Posts;

import com.Myapps.Twitter.Enum.Audience;
import com.Myapps.Twitter.Enum.ReplyRestriction;
import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    @Column(length = 256,nullable = false)
    private String content;

    private Date postDate;

    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "user_id")
    private ApplicationUser author;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Image> images=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_likes",
            joinColumns={@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<ApplicationUser> likes=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_reply",
    joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "reply_id")})
    @JsonIgnore
    private Set<Posts> replies=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_reposts",
    joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<ApplicationUser> reposts=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_bookmarks",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<ApplicationUser> bookmarks=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_views",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<ApplicationUser> views=new HashSet<>();

    private  boolean scheduled;

    private Date scheduledDate;

    @Enumerated(EnumType.ORDINAL)
    private Audience audience;

    @Enumerated(EnumType.ORDINAL)
    private ReplyRestriction restriction;

    @OneToOne(mappedBy = "posts",cascade = CascadeType.ALL)
    private Poll poll;










}
