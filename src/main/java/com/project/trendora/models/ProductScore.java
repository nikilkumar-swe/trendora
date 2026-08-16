package com.project.trendora.models;

public class ProductScore {

    private Product product;
    private int score;

    public ProductScore(Product product, int score) {
        this.product = product;
        this.score = score;
    }

    public Product getProduct() {
        return product;
    }

    public int getScore() {
        return score;
    }
}
