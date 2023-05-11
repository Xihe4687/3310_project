package edu.cuhk.csci3310.cublossom;

public class Canteen {
    private String filename;
    private String canteenName;
    private String openingTime;
    private int rating;

    public Canteen(String filename, String canteenName, String openingTime, int rating) {
        this.filename = filename;
        this.canteenName = canteenName;
        this.openingTime = openingTime;
        this.rating = rating;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
