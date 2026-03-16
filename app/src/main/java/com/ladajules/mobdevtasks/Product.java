package com.ladajules.mobdevtasks;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Serializable, Parcelable {
    String name;
    double price;
    int quantity;
    String description;
    int imageId;

    public Product(String name, double price, int quantity, String description, int imageId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageId = imageId;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        description = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
        dest.writeString(description);
        dest.writeInt(imageId);
    }
}
