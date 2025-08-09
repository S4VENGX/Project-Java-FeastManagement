package core;

import java.util.Objects;

public class FeastMenu {
    String feastCode;
    String Name;
    int Price;
    String Ingredients;
    
    //ctor

    public FeastMenu(String feastCode, String Name, int Price, String Ingredients) {
        this.feastCode = feastCode;
        this.Name = Name;
        this.Price = Price;
        this.Ingredients = Ingredients;
    }
    
    // cotr 1 tham số để tìm feast menu
    public FeastMenu(String feastCode) {
        this.feastCode = feastCode;
    }

    //Override equals

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeastMenu other = (FeastMenu) obj;
        return Objects.equals(this.feastCode, other.feastCode);
    }

    
    
    
    public String getFeastCode() {
        return feastCode;
    }

    public void setFeastCode(String feastCode) {
        this.feastCode = feastCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
    }

    @Override
    public String toString() {
        return String.format("FeastCode: %s, FeastName: %s, FeastPrice: %d", feastCode, Name, Price);
    }
    
}// class FeastMenu
