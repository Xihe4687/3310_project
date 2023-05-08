package edu.cuhk.csci3310.cublossom;

public class Flower {
    private String filename;
    private String flowerName;
    private String genus;
    private int richness;

    public Flower(String filename, String flowerName, String genus, int richness) {
        this.filename = filename;
        this.flowerName = flowerName;
        this.genus = genus;
        this.richness = richness;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public int getRichness() {
        return richness;
    }

    public void setRichness(int richness) {
        this.richness = richness;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
