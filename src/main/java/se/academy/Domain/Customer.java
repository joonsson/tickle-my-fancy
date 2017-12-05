package se.academy.Domain;

import javax.validation.constraints.*;

public class Customer {


    @Size(min=1, max=50)
    private String email;
    @Size(min=5, max=15)
    private String password;
    @Size(min=5, max=20)
    private String firstname;
    @Size(min=5, max=20)
    private String lastname;
    @Size(min=5, max=50)
    private String address;
    @Digits(integer = 5, fraction = 0)
    private String zip;
    @Size(min=3, max=50)
    private String city;
    @Size(min=7, max=10)
    private String phone;

    public Customer(){
    }

    public Customer(String email, String password, String firstname, String lastname, String address, String zip, String city, String phone) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
