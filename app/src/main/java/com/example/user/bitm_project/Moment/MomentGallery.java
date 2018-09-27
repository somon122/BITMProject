package com.example.user.bitm_project.Moment;

public class MomentGallery {

    String id;
    String dateTime;
    String momentDetails;
    String imageView;

    public MomentGallery() {
    }

    public MomentGallery(String id, String dateTime, String momentDetails, String imageView) {
        this.id = id;
        this.dateTime = dateTime;
        this.momentDetails = momentDetails;
        this.imageView = imageView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMomentDetails() {
        return momentDetails;
    }

    public void setMomentDetails(String momentDetails) {
        this.momentDetails = momentDetails;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
