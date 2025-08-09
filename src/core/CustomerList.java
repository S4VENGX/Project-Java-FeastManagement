package core;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import tool.ConsoleInputter;

public class CustomerList extends ArrayList<Customer> {

    public static final String custCodePattern = "^[CGK]\\d{4}$";
    public static final String namePattern = "^[a-zA-Z1-9 ]{2,25}$";
    public static final String phonePattern = "^\\d{9}|\\d{11}$";
    public static final String emailPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+.[a-zA-Z0-9_-]+$";
    public static final String HEAD_TABLE = "--------------------------------------------------------------\n"
            + "Code     | Customer Name       | Phone        | Email\n"
            + "--------------------------------------------------------------";

    //Thêm cust data lấy từ bàn phím
    public void addNew() {
        //Nhập 4 fields
        System.out.println("Enter date of new customer.");
        int pos;
        String newCode;
        do {
            newCode = ConsoleInputter.getStr("New code", custCodePattern, "C/G/K and 4 digits");
            newCode = newCode.toUpperCase();
            pos = this.indexOf(new Customer(newCode));
            if (pos >= 0) {
                System.out.println("This code is duplicated");
            }
        } while (pos >= 0);
        //Nhập tên, phone, mail
        String name = ConsoleInputter.getStr("New name", namePattern, "name must be length from 2 to 25 characters");
        String phone = ConsoleInputter.getStr("New phone", phonePattern, "Phone number include 9 - 11 digits");
        String mail = ConsoleInputter.getStr("New email", emailPattern, "email must be like this" + emailPattern);

        //Tạo mới 1 cust với 4 fields đã có
        Customer newCust = new Customer(newCode, name, phone, mail);
        //Thêm cust mới vào danh sách
        this.add(newCust);
        //Thông báo ra màn
        System.out.println("New customer has been added!");

        boolean isContinue = ConsoleInputter.getBoolean("Continue?");
        if (isContinue) {
            addNew();
        }
    }

    //Tìm cust theo tên
    //Xuất danh sách cust với tên nhập từ bàn phím
    public void printByName() {
        if (this.size() == 0) {
            System.out.println("Empty list");
            return;
        }

        this.sort(Comparator.comparing(cust -> cust.getCustName().substring(cust.getCustName().lastIndexOf(" ") + 1)));
        //User nhập tên cần tìm, ko kiểm tra -> String name
        String name = ConsoleInputter.getStr("Searched customer name").trim().toUpperCase();
        //Duyệt danh sách, nếu có cust trùng tên thì xuất cust đó
        boolean existed = false;
        System.out.println(HEAD_TABLE);
        for (Customer cust : this) {
            String lastName = "", firstName = "";
            if (cust.getCustName().contains(" ")) {
                lastName = cust.getCustName().substring(cust.getCustName().lastIndexOf(" ") + 1);
                firstName = cust.getCustName().substring(0, cust.getCustName().lastIndexOf(" "));
                System.out.println(String.format("%-9s| %-20s| %-13s| %s", cust.getCustCode(), lastName + ", " + firstName, cust.getPhone(), cust.getEmail()));
            } else {
                //Xuất cust : tham khảo cách xuất trong đề theo đúng format
                System.out.println(String.format("%-9s| %-20s| %-13s| %s", cust.getCustCode(), cust.getCustName(), cust.getPhone(), cust.getEmail()));
            }
            existed = true;
        }
        if (!existed) {
            System.out.println("No one matches the search criteria.");
        }
    }

    //Update cust với data nhập từ bàn phím
    public void updateCust() {
        if (this.size() == 0) {
            System.out.println("Empty list");
            return;
        }

        //Nhập custCode
        String custcode = ConsoleInputter.getStr("Enter cuscode", custCodePattern, "C/G/K and 4 digits");
        int pos = -1;

        pos = this.indexOf(new Customer(custcode));
        if (pos >= 0) {
            String newName = ConsoleInputter.getStr("Enter customer name", "^([a-zA-Z ]{2,25})?$", "name must be length from 2 to 25 characters");
            String newPhone = ConsoleInputter.getStr("New phone", "(^\\d{9}|\\d{11})?$", "Phone number include 9 - 11 digits");
            String newEmail = ConsoleInputter.getStr("New email", "^([a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+.[a-zA-Z0-9_-]+)?$", "email must be like this" + emailPattern);
            if (!newName.isEmpty()) {
                this.get(pos).setCustName(newName);
            }
            if (!newPhone.isEmpty()) {
                this.get(pos).setPhone(newPhone);
            }
            if (!newEmail.isEmpty()) {
                this.get(pos).setEmail(newEmail);
            }
            System.out.println("Update successfully!");
        } else {
            System.out.println("This customer code does not exist");
        }

        boolean isContinue = ConsoleInputter.getBoolean("Continue?");
        if (isContinue) {
            updateCust();
        }
    }

    // Lưu danh sách cust lên file nhị phân
    public void saveFile(String fName) {
        if (this.size() == 0) {
            System.out.println("Empty list");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            for (Customer customer : this) {
                fo.writeObject(customer);
            }
            fo.close();
            f.close();
            System.out.println("Customer information was successfully saved!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Đọc danh sách cust từ file nhị phân
    public void readFromFile(String fName) {
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

            Customer customer;
//            while ((customer = (Customer) fo.readObject()) != null) {
//                    this.add(customer);
//            }

            // Không còn exception out of read file nữa
            while (true) {
                try {
                    customer = (Customer) fo.readObject();
                    this.add(customer);
                } catch (EOFException e) {
                    break;
                }
            }
            fi.close();
            fo.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Xuất danh sách cust
    public void printAll() {
        if (this.size() == 0) {
            System.out.println("Customer list is list");
            return;
        }
        this.sort(Comparator.comparing(cust -> cust.getCustName().substring(cust.getCustName().lastIndexOf(" ") + 1)));

        System.out.println(HEAD_TABLE);
        for (Customer cust : this) {
            String lastName = "", firstName = "";
            if (cust.getCustName().contains(" ")) {
                lastName = cust.getCustName().substring(cust.getCustName().lastIndexOf(" ") + 1);
                firstName = cust.getCustName().substring(0, cust.getCustName().lastIndexOf(" "));
                System.out.println(String.format("%-9s| %-20s| %-13s| %s", cust.getCustCode(), lastName + ", " + firstName, cust.getPhone(), cust.getEmail()));
            } else {
                //Xuất cust : tham khảo cách xuất trong đề theo đúng format
                System.out.println(String.format("%-9s| %-20s| %-13s| %s", cust.getCustCode(), cust.getCustName(), cust.getPhone(), cust.getEmail()));
            }
        }
    }
}
