package se.academy.domain;

public class SubOrder {
    private int suborderID;
    private int orderID;
    private int productID;
    private double price;
    private int quantity;
    private double cost;

    public SubOrder(int suborderID, int orderID, int productID, double price, int quantity, double cost) {
        this.suborderID = suborderID;
        this.orderID = orderID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getSuborderID() {
        return suborderID;
    }

    public void setSuborderID(int suborderID) {
        this.suborderID = suborderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
