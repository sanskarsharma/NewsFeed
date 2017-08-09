package com.dev.sanskar.newscgpioneer;

/**
 * Created by Sanskar on 1/16/2017.
 */
public class Blog {

    public String title1;
    public String desc1;
    public String image;



    public Blog() {


    }

    public Blog(String title1, String desc1, String image) {

        this.title1 = title1;
        this.desc1 = desc1;
    }

    public String getTitle() {
        return title1;
    }

    public void setTitle(String title1) {
        this.title1 = title1;
    }

    public String getDesc() {
        return desc1;
    }

    public void setDesc(String desc1) {
        this.desc1 = desc1;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
