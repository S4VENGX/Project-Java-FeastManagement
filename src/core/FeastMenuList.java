package core;

import java.util.ArrayList;
import java.io.FileReader; // Read 1 ký tự
import java.io.BufferedReader; // Đọc theo dòng
import java.util.Collections;
import java.util.*;

public class FeastMenuList extends ArrayList<FeastMenu> {

    public static final String HEAD_MENU
            = "---------------------------------------------------------------\n"
            + "List of Set Menus for ordering party:\n"
            + "---------------------------------------------------------------";
    public static final String LINE = "---------------------------------------------------------------";

    //Read các feast menu
    public void readFile(String fName) {
        //Mở file để đọc
        try {
            FileReader fr = new FileReader(fName);
            BufferedReader bf = new BufferedReader(fr);
            String line; // đọc dòng chữ
            bf.readLine(); // Bỏ dòng đầu
            
            while ((line = bf.readLine()) != null) {
                //Xử lí line, cắt ra theo dấu phẩy ,
                String[] part = line.split(",");
                if (part.length == 4) {
                    String feastCode = part[0].trim();
                    String Name = part[1].trim();
                    int Price = Integer.parseInt(part[2].trim());
                    String Ingredients = part[3].trim().substring(1, part[3].length() - 1); // Cắt 2 dấu nháy ở đầu và cuối

                    FeastMenu fm = new FeastMenu(feastCode, Name, Price, Ingredients);
                    this.add(fm);
                }
            }
            
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Liệt kê toàn bộ feast menu
    public void printAll() {
        if (this.isEmpty()) {
            System.out.println("Cannot read data from feastMenu.csv. Pleasse check it.");
            return;
        }
        
        System.out.println(HEAD_MENU);
        Collections.sort(this, Comparator.comparing(FeastMenu::getPrice));
        for (FeastMenu thi : this) {
            //Xuất feast menu, xem lại đề để tham khảo format xuất dữ liệu
            System.out.println(String.format("Code       :%s\nName       :%s\nPrice      : %,d Vnd\nIngredients:\n%s", thi.getFeastCode(), thi.getName(), thi.getPrice(), thi.getIngredients().replace("#", "\n")));
            System.out.println(LINE);
        }
    }
}
