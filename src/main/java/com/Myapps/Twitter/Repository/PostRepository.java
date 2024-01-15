package com.Myapps.Twitter.Repository;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer> {

    Optional<Posts> findByAuthor(ApplicationUser user);

}
