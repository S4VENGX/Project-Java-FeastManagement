package core;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    String orderID; // mã đơn hàng
    String custCode; // Của khách nào
    String feastCode; // Feast menu code
    int numTable; // Số bàn
    Date preferedDate; // ngay to chuc

    public Order() {
        this.orderID = "";
        this.custCode = "";
        this.feastCode = "";
        this.numTable = 0;
        this.preferedDate = null;
    }

    public Order(String orderID, String custCode, String feastCode, int numTable, Date preferedDate) {
        this.orderID = orderID;
        this.custCode = custCode;
        this.feastCode = feastCode;
        this.numTable = numTable;
        this.preferedDate = preferedDate;
    }

    // contructor cho tìm kiếm
    public Order(String orderID) {
        this.orderID = orderID;
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        Order o = (Order)obj;
        return this.orderID.equals(o.orderID);
    }
    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getFeastCode() {
        return feastCode;
    }

    public void setFeastCode(String feastCode) {
        this.feastCode = feastCode;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public Date getPreferedDate() {
        return preferedDate;
    }

    public void setPreferedDate(Date preferedDate) {
        this.preferedDate = preferedDate;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", custCode=" + custCode + ", feastCode=" + feastCode + ", numTable=" + numTable + ", preferedDate=" + preferedDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.orderID);
        hash = 53 * hash + Objects.hashCode(this.feastCode);
        hash = 53 * hash + Objects.hashCode(this.preferedDate);
        return hash;
    }

}
