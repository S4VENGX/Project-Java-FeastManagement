package core;

import static core.CustomerList.custCodePattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import tool.ConsoleInputter;

public class OrderList extends ArrayList<Order> {

    public static final String HEAD_TABLE
            = "-----------------------------------------------------------------------------------\n"
            + "ID               | Event date |Customer ID| Set Menu| Price    | Tables |      Cost\n"
            + "-----------------------------------------------------------------------------------";
    private static FeastMenuList fmList; // Phải có danh sách menu
    private CustomerList cList; // Phải có danh sách khách hàng

    public OrderList(FeastMenuList fmList, CustomerList cList) {
        this.fmList = fmList;
        this.cList = cList;
    }

    // Đặt tiệc
    public void placeOrder() {
        if (fmList.size() == 0) {
            System.out.println("Feast menu list is empty");
            return;
        }

        String orderID = ConsoleInputter.dateKeyGen();
        String custCode = ConsoleInputter.getStr("Cust code", CustomerList.custCodePattern, "Cuscode C or G or K + 4 digits");
        custCode = custCode.toUpperCase();
        //Tìm custCode tồn tại
        int post = this.cList.indexOf(new Customer(custCode));
        if (post < 0) {
            System.out.println("Cust is not exist");
            return;
        }

        String feastMenuCode;
        FeastMenu selectedFm = (FeastMenu) ConsoleInputter.objMenu(this.fmList.toArray());
        feastMenuCode = selectedFm.getFeastCode();
        int numTable;
        numTable = ConsoleInputter.getInt("Number of table", 1, 1000);

        Date preferedDate; // Ngày tổ chức
        boolean dateValid = false;
        do {
            preferedDate = ConsoleInputter.getDate("Date (dd-MM-yyyy)", "dd-MM-yyyy");
            dateValid = (preferedDate).after(new Date());
            if (!dateValid) {
                System.out.println("Prefered date must be after current day!");
            }
        } while (!dateValid);

        // Adding new order to list
        Order order = new Order(orderID, custCode, feastMenuCode, numTable, preferedDate);
        this.add(order);

        // Display message to user after placing successfully
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String head = "--------------------------------------------------\n"
                + "Customer order information [Order ID: " + orderID + "]\n"
                + "--------------------------------------------------";
        String line = "--------------------------------------------------";
        FeastMenu thi = fmList.get(fmList.indexOf(new FeastMenu(feastMenuCode)));

        System.out.println(head);
        System.out.println(String.format("Customer code : %s\nCustomer name : %s\nPhone number  : %s\nEmail         : %s", cList.get(post).getCustCode(), cList.get(post).getCustName(), cList.get(post).getPhone(), cList.get(post).getEmail()));
        System.out.println(line);
        System.out.println(String.format("Code of set menu: %s\nSet menu name   : %s\nEvent date      : %s\nNumber of tables: %s\nPrice           : %,d Vnd\nIngredients:\n%s", thi.getFeastCode(), thi.getName(), ConsoleInputter.dateStr(preferedDate, "dd/MM/yyyy"), order.getNumTable(), thi.getPrice(), thi.getIngredients().replace("#", "\n")));
        System.out.println(String.format(line + "\nTotal cost      : %,d Vnd\n" + line, thi.getPrice() * numTable));

        System.out.println("New order has been added!");
    }

    //Update tiệc
    public void updateOrder() {
        if (this.size() == 0) {
            System.out.println("Empty list");
            return;
        }

        /*--------------------------------------------------------------------------------------------------------*/
        String orderID = ConsoleInputter.getStr("Enter cuscode", "\\d+", "enter again");
        int pos = this.indexOf(new Order(orderID));
        if (pos < 0) {
            System.out.println("This order id does not exist!");
            return;
        }

        FeastMenu selectedFm = (FeastMenu) ConsoleInputter.objMenu(this.fmList.toArray());
        String feastMenuCode = selectedFm.getFeastCode();
        int numOfTable = ConsoleInputter.getInt("Enter number of table", 1, 1000);
        Date preferedDate;
        boolean dateValid = false;
        do {
            preferedDate = ConsoleInputter.getDate("Date (dd-MM-yyyy)", "dd-MM-yyyy");
            dateValid = (preferedDate).after(new Date());
            if (!dateValid) {
                System.out.println("Prefered date must be after current day!");
            }
        } while (!dateValid);

        if (!feastMenuCode.isEmpty()) {
            this.get(pos).setFeastCode(feastMenuCode);
        }
        if (numOfTable != 0) {
            this.get(pos).setNumTable(numOfTable);
        }
        if (preferedDate != null) {
            this.get(pos).setPreferedDate(preferedDate);
        }
        System.out.println("Update order information successfully!");

        // Lấy ra order ở vị trí pos rồi cập nhật các thông tin trong đề
        //Thông báo đã cập nhật
        //do yourself!!
        boolean isContinue = ConsoleInputter.getBoolean("Continue?");
        if (isContinue) {
            updateOrder();
        }
    }

    //Lưu file nhị phân
    public void saveToFile(String fName) {
        if (this.size() == 0) {
            System.out.println("Empty list");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            for (Order order : this) {
                fo.writeObject(order);
            }
            fo.close();
            f.close();
            System.out.println("Order successfully saved!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Đọc file nhị phân
    public void readToFile(String fName) {
        if (this.size() > 0) {
            this.clear();
        }
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }

            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);

            Order order;
//            while ((order = (Order) fo.readObject()) != null) {
//                this.add(order);
//            }

            // Không còn exception out of read file nữa
            while (true) {
                try {
                    order = (Order) fo.readObject();
                    this.add(order);
                } catch (IOException e) {
                    break;
                }
            }
            fi.close();
            fo.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Xuất danh sách order
    public void printAll() {
        if (this.isEmpty()) {
            System.out.println("Order list is empty");
            return;
        }

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        FeastMenu fm;
        this.sort(Comparator.comparing(orderList -> orderList.getPreferedDate()));
        System.out.println(HEAD_TABLE);
        for (Order order : this) {
            //Xuất order, tham khảo format cách xuất trong đề
            fm = fmList.get(fmList.indexOf(new FeastMenu(order.getFeastCode())));
            System.out.println(String.format("%-6s| %s | %-10s| %-8s| %,9d| %7d| %,d", order.getOrderID(), sdf.format(order.getPreferedDate()), order.getCustCode(), order.getFeastCode(), fm.getPrice(), order.getNumTable(), order.getNumTable() * fm.getPrice()));
        }
    }
}// class OrderList
