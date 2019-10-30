package com.basil.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class BakingModel {
    private long id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private long servings;
    private String image;

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> value) { this.ingredients = value; }

    public List<Step> getSteps() { return steps; }
    public void setSteps(List<Step> value) { this.steps = value; }

    public long getServings() { return servings; }
    public void setServings(long value) { this.servings = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }


}

 class Ingredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() { return quantity; }
    public void setQuantity(double value) { this.quantity = value; }

    public String getMeasure() { return measure; }
    public void setMeasure(String value) { this.measure = value; }

    public String getIngredient() { return ingredient; }
    public void setIngredient(String value) { this.ingredient = value; }

     @Override
     public int describeContents() {
         return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeDouble(this.quantity);
         dest.writeString(this.measure);
         dest.writeString(this.ingredient);
     }

     public Ingredient() {
     }

     protected Ingredient(Parcel in) {
         this.quantity = in.readDouble();
         this.measure = in.readString();
         this.ingredient = in.readString();
     }

     public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
         @Override
         public Ingredient createFromParcel(Parcel source) {
             return new Ingredient(source);
         }

         @Override
         public Ingredient[] newArray(int size) {
             return new Ingredient[size];
         }
     };
 }

class Step implements Parcelable {
    private long id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String value) { this.shortDescription = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getVideourl() { return videoURL; }
    public void setVideourl(String value) { this.videoURL = value; }

    public String getThumbnailurl() { return thumbnailURL; }
    public void setThumbnailurl(String value) { this.thumbnailURL = value; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.id = in.readLong();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}