package com.company.message;

import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    private String itemName;
    private String itemLocation;

    public Task(String itemName, String itemLocation) {
        this.itemName = itemName;
        this.itemLocation = itemLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    @Override
    public String toString() {
        return "Task{" +
                "itemName='" + itemName + '\'' +
                ", itemLocation='" + itemLocation + '\'' +
                '}';
    }
}
