package model.sfproject1;


/**
 * abstract class Part provides all the necessary properties and methods needed
 * to represent a part and manipulate part information
 * @author Luis Munguia Salas
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * returns the id of a part instance
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of a part instance
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the name of a part instance
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of a part instance
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the price of a part instance
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price of a part instance
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * return stock quantity of a part instance
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets the stock quantity of a part instance
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * returns the minimum quantity of the part instance
     * @return the min
     */
    public int getMin() {
        return this.min;
    }

    /**
     * sets the minimum quantity of the part instance
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * returns the max quantity of the part instance
     * @return the max
     */
    public int getMax() {
        return this.max;
    }

    /**
     * sets the max quantity of the part instance
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}

