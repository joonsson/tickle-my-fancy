package se.academy.domain;

public class Order {
    private int orderID;
    private int customerID;
    private double cost;
    private int quantity;

    public Order(int orderID, int customerID, double cost, int quantity) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.cost = cost;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
