package com.example.sun.lvivchocorating;


public class Candy {
    private String name;
    private int imageId;
    private String category;
    private int rating;
    private String description;
    private boolean isFavorite;

    public Candy(String name, String description,  String category, int imageId, boolean isFavorite,  int rating) {
        this.name = name;
        this.imageId = imageId;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public Candy(String name, String description,  String category, int imageId,  int rating) {
        this.name = name;
        this.imageId = imageId;
        this.category = category;
        this.rating = rating;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Candy{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
