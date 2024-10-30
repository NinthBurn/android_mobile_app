package com.example.techware.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ComputerComponent implements Parcelable {
    private int id;
    private String name;
    private String manufacturer;
    private String category;
    private float price;
    private int quantity;
    private LocalDate releaseDate;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);


    public ComputerComponent(int id, String name, String manufacturer, String category, float price, int quantity, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
    }

    public ComputerComponent(String name, String manufacturer, String category, float price, int quantity, LocalDate releaseDate) {
        this.id = -1;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
    }

    protected ComputerComponent(Parcel in) {
        id = in.readInt();
        name = in.readString();
        manufacturer = in.readString();
        category = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
        releaseDate = LocalDate.parse(in.readString());
    }

    public static final Creator<ComputerComponent> CREATOR = new Creator<ComputerComponent>() {
        @Override
        public ComputerComponent createFromParcel(Parcel in) {
            return new ComputerComponent(in);
        }

        @Override
        public ComputerComponent[] newArray(int size) {
            return new ComputerComponent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(manufacturer);
        dest.writeString(category);
        dest.writeFloat(price);
        dest.writeInt(quantity);
        dest.writeString(releaseDate.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputerComponent component = (ComputerComponent) o;
        return id == component.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ComputerComponent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", releaseDate=" + releaseDate +
                '}';
    }

}
