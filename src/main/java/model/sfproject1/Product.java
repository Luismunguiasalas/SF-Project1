package model.sfproject1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the class Product can store a list of part objects and provides the necessary
 * properties and methods to manipulate the list and part information
 * RUNTIME ERROR:
 * 1. main () is not found in the class, but will be using JavaFX to extend in the future and error will be gone
 * @author Luis Munguia Salas
 */
public class Product {
    /*
     * properties
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        // add parameter values to properties
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product() {
        this(0,null,0.00,0,0,0);
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * return the name
     */
    public String getName() {
        return name;
    }

    /**
     * return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param part adds the part to the associatedParts list
     */
//    public void addAssociatedPart(ObservableList<Part> part) {
//        this.associatedParts.addAll(part);
//    }
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * @return the part from the associatedParts list
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return the associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}