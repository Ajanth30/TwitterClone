package com.Myapps.Twitter.Repository;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Integer> {

    ApplicationUser findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);

    Optional<ApplicationUser> findByEmailOrPhoneOrUsername(String email,String phone,String username);

    @Modifying
    @Transactional
    @Query(value = "insert into followers (user_id,follower_id) values(?1,?2)",nativeQuery = true)
    void AddFollower(int userId,int followerId);

    @Modifying
    @Transactional
    @Query(value = "insert into following (user_id,following_id) values(?1,?2)",nativeQuery = true)
    void AddFollowing(int userId,int followingId);

    @Query(value = "select follower_id from followers where user_id=?1",nativeQuery = true)
    Set<Integer> getAllFollowers(int user_id);

    @Query(value = "select following_id from following where user_id=?1",nativeQuery = true)
    Set<Integer> getAllFollowings(int user_id);



}
