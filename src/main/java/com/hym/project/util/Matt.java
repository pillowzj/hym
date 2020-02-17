package com.hym.project.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class Matt {

    public static void main(String[] args) throws JpegProcessingException, IOException {

//        File img = new File("C:\\Users\\JAVA\\Desktop\\123.jpg");
        File img =  new File("D:\\hym\\uploadPath\\upload\\1581911373833.jpg");
        System.out.println("File Name:" + img.getName());

        getCreateTime(img);

    }

    public static void getCreateTime(File img) throws JpegProcessingException, IOException {
        Metadata metadata = JpegMetadataReader.readMetadata(img);
        System.out.println("Directory Count: "+metadata.getDirectoryCount());
        System.out.println();

        //输出所有附加属性数据
        for (Directory directory : metadata.getDirectories()) {
            System.out.println("******\t" + directory.getName() + "\t******");
            for (Tag tag : directory.getTags()) {
                System.out.println(tag.getTagName() + ":" + tag.getDescription());
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void set_fileInfo(){
         String file_name="D:\\hym\\uploadPath\\upload\\IMG_3386.jpg";
         Date lastmodfiyTimeDate;
         Date CreateTimeDate;

        Path path = Paths.get(file_name);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class,
                LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            //attr.lastModifiedTime();

            lastmodfiyTimeDate=new Date(attr.lastModifiedTime().toMillis());
            CreateTimeDate= new Date(attr.creationTime().toMillis());
            System.out.println("lastModfiedTime	"+lastmodfiyTimeDate);
            System.out.println("creationTime	"+CreateTimeDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
