package com.Myapps.Twitter.Models.Auth;

import com.Myapps.Twitter.Models.Image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ApplicationUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Integer userId;

  private String firstName;

  private String lastName;

  private String phone;

  @Column(unique = true,
  nullable = false)
  private   String email;

  private Date dateOfBirth;

  @Column(unique = true)
  private String username;

  private String bio;

  private String nickName;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_picture",referencedColumnName = "imageId")
  private Image profileImage;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cover_image",referencedColumnName = "imageId")
  private Image coverImage;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "followers",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "follower_id")}
  )
  @JsonIgnore
  private Set<ApplicationUser> followers=new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "following",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "following_id")}
  )
  @JsonIgnore
  private Set<ApplicationUser> following=new HashSet<>();



//security related
  @JsonIgnore
  private String password;

  @JsonIgnore
  private String verificationCode;


@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
private Set<Role> authorities;


}
