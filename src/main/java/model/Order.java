package model;

public class Order {
    private String firstname;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String firstname, String lastName, String address, String metroStation, String rentTime, String phone, String deliveryDate, String[] color, String comment) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.rentTime = rentTime;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.color = color;
        this.comment = comment;
    }




}
