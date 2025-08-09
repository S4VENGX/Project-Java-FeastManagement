
import core.*;
import tool.ConsoleInputter;

public class OrderManager {

    static String fmFname = "D:\\FPT UNIVERSITY\\Nam 2\\Semester 3\\LAB211\\FeastMngPrj(J1.L.P0028)\\src\\data\\FeastMenu.csv";
    static String custFname = "D:\\FPT UNIVERSITY\\Nam 2\\Semester 3\\LAB211\\FeastMngPrj(J1.L.P0028)\\src\\data\\Customers.dat";
    static String orderFname = "D:\\FPT UNIVERSITY\\Nam 2\\Semester 3\\LAB211\\FeastMngPrj(J1.L.P0028)\\src\\data\\feast_order_service.dat";

    public static void main(String[] args) {
        FeastMenuList fmList; // Danh sách món ăn
        CustomerList cList; // Danh sách khách hàng
        OrderList oList; // Danh sách order

        // Chuẩn bị ban đầu về menu list
        fmList = new FeastMenuList();
        fmList.readFile(fmFname); // Đọc file để lấy menu list

        // Chuẩn bị danh sách khách hàng
        cList = new CustomerList();
        cList.readFromFile(custFname); // Đọc file -> lấy danh sách customer đã đăng kí

        // Chuẩn bị danh sách Order đã có
        oList = new OrderList(fmList, cList);
        oList.readToFile(orderFname);

        // Code quản lí
        int choice; // Lựa chọn chức năng của chương trình
        boolean changed = false;
        do {
            System.out.println("=============== TRADITIONAL FEAST ORDER MENU ===============");
            choice = ConsoleInputter.intMenu("Register customers", "Update customer information", "Search for customer information by name", "Display feast menus",
                    "Place feast order", "Update order information", "Save date to file", "Display Customer list", "Display Order list", "Quit");
            switch (choice) {
                case 1:
                    cList.addNew();
                    changed = true;
                    break;
                case 2:
                    cList.updateCust();
                    changed = true;
                    break;
                case 3:
                    cList.printByName();
                    break;
                case 4:
                    fmList.printAll();
                    break;
                case 5:
                    oList.placeOrder();
                    changed = true;
                    break;
                case 6:
                    oList.updateOrder();
                    changed = true;
                    break;
                case 7:
                    if (!cList.isEmpty()) {
                        cList.saveFile(custFname);
                    }
                    if (!oList.isEmpty()) {
                        oList.saveToFile(orderFname);
                    }
                    changed = false;
                    break;
                case 8:
                    cList.printAll();
                    break;
                case 9:
                    oList.printAll();
                    break;
                case 10:
                    if (changed) {
                        boolean resp = ConsoleInputter.getBoolean("Data changed! Save or not");
                        if (resp) {
                            if (!cList.isEmpty()) {
                                cList.saveFile(custFname);
                            }
                            if (!oList.isEmpty()) {
                                oList.saveToFile(orderFname);
                            }
                            System.out.println("Good luck!!");
                        }
                    }
                    System.exit(0);
                    break;
            }
        } while (choice >= 0 && choice <= 100);

    }
}
