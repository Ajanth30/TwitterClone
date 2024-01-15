package com.Myapps.Twitter.Repository;

import com.Myapps.Twitter.Models.Posts.PollOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PollOptionsRepository extends JpaRepository<PollOptions,Integer> {

    @Modifying
    @Query(value = "insert into poll_options_voters (poll_options_option_id,voters_user_id) values (?1,?2)",nativeQuery = true)
    void castVoteForOptions(Integer optionId,Integer voterId);
}
