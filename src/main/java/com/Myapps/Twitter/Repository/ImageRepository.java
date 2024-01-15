package com.Myapps.Twitter.Repository;

import com.Myapps.Twitter.Models.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {

    Optional<Image>findImageByImageName(String imageName);

}
