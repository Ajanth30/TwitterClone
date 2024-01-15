package com.Myapps.Twitter.Models.RequestModels;

import com.Myapps.Twitter.Enum.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
public class ImageUpload {

    private ImageType type;
    private MultipartFile file;

}
