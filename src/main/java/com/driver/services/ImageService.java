package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    private BlogRepository blogRepository;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image=new Image(description,dimensions);
        image.setBlog(blog);
        List<Image> res=blog.getImageList();
        if(res==null){
            res=new ArrayList<>();
        }
        res.add(image);
        blog.setImageList(res);
        imageRepository2.save(image);
        blogRepository.save(blog);
        return image;

    }

    public void deleteImage(Image image){
        imageRepository2.deleteById(image.getId());
    }

    public Image findById(int id) {
        return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        if(image!=null &&screenDimensions.split("X").length == 2){
            Integer screenArea= Integer.parseInt(screenDimensions.split("X")[0])*Integer.parseInt(screenDimensions.split("X")[1]);
            Integer imageArea= Integer.parseInt(image.getDimensions().split("X")[0])*Integer.parseInt(image.getDimensions().split("X")[1]);
            return screenArea/imageArea;
        }
        return 0;

    }
}
