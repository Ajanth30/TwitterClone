package com.Myapps.Twitter.Repository;

import com.Myapps.Twitter.Models.Posts.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll,Integer> {

}
