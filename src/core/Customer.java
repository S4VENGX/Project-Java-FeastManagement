package core;

import java.io.Serializable;
import java.util.Comparator;

public class Customer implements Serializable {

    String custCode;
    String custName;
    String phone;
    String email;

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    //constructor - ctors
    public Customer() {
        this.custCode = "";
        this.custName = "";
        this.phone = "";
        this.email = "";
    }

    public Customer(String custCode, String custName, String phone, String email) {
        this.custCode = custCode;
        this.custName = custName;
        this.phone = phone;
        this.email = email;
    }

    //ctor cho tac vu tim kiem trong nhom
    public Customer(String custCode) {
        this.custCode = custCode;
    }
    //cơ chế đọ 2 cust bằng nhau để phục vụ việc tìm kiếm
    //override hành vi equals

    @Override
    public boolean equals(Object obj) {
        Customer cust = (Customer) obj;
        return this.custCode.equals(cust.custCode);
    }
}// class Customer
