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
        imageRepository2.delete(image);
    }

    public Image findById(int id) {
        return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        if(image!=null &&screenDimensions.split("X").length == 2){
            int screenArea= Integer.parseInt(""+screenDimensions.charAt(0))*Integer.parseInt(""+screenDimensions.charAt(2));
            if(image.getDimensions().charAt(0)=='0' || image.getDimensions().charAt(2)=='0') return 0;
            int imageArea= Integer.parseInt(""+image.getDimensions().charAt(0))*Integer.parseInt(""+image.getDimensions().charAt(2));
            return screenArea/imageArea;
        }
        return 0;

    }
}
