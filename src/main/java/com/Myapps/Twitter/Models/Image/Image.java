package com.Myapps.Twitter.Models.Image;


import com.Myapps.Twitter.Models.Posts.Posts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;
    private String imageName;
    private String imageType;
    private String imagePath;
    private String imageUrl;


    public Image(String imageName, String imageType, String imagePath, String imageUrl) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
    }

}

