package com.Myapps.Twitter.Models.Posts;


import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PollOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionId;

    @Column(nullable = false)
    private String option;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "option_votes",joinColumns = {@JoinColumn(name = "option_id")},
    inverseJoinColumns = {@JoinColumn(name = "voter_id")})
    private Set<ApplicationUser> voters=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "poll_id",referencedColumnName = "pollId")
    @JsonIgnore
    private Poll poll;


}
