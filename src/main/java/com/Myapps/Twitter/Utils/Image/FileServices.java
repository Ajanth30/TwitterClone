package com.Myapps.Twitter.Utils.Image;

import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Models.Image.Image;
import com.Myapps.Twitter.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServices {

    private final ImageRepository imageRepo;

    //get the root path
    private final Path FILEPATH = Paths.get(System.getProperty("user.dir"),"Images");

    @Value("${imageUrl}")
    private  String Url;

    public Image UploadImage(MultipartFile file,String prefix) throws IOException {
            //content type in the request header is like image/png
            String extension="."+ Objects.requireNonNull(file.getContentType()).split("/")[1];

            File Directory=new File(String.valueOf(FILEPATH));

            // Create the directory
            File image=File.createTempFile(prefix,extension,Directory);

            //transfer the file in the request
            file.transferTo(image);

            String imageUrl=Url+image.getName();

            Image i=new Image(image.getName(),file.getContentType(),image.getPath(),imageUrl);

            return imageRepo.save(i);


    }

    public boolean deleteImageByImageName(String imageName){
        try{
            File file=new File(String.valueOf(FILEPATH)+"/"+imageName);
             return  file.delete();

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }



    }

    public ApiResponse downloadImage(String imageName){
        try {
            Image image=imageRepo.findImageByImageName(imageName).get();
            String filePath=image.getImagePath();

            byte[] imageBytes= Files.readAllBytes(new File(filePath).toPath());
            return ApiResponse.success(imageBytes);
        }
        catch (Exception e){
            return ApiResponse.failure(e.getMessage());
        }

    }



}
