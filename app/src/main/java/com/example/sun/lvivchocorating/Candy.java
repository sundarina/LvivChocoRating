package com.example.sun.lvivchocorating;


public class Candy {
    private String name;
    private String imageId;
    private String category;
    private int rating;
    private String description;
    private boolean isFavourite;

    public Candy(String name, String description,  String category, String imageId, boolean isFavourite,  int rating) {
        this.name = name;
        this.imageId = imageId;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavorite(boolean favorite) {
        isFavourite = favorite;
    }

    @Override
    public String toString() {
        return "Candy{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
