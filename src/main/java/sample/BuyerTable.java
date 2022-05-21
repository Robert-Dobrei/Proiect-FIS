package sample;

public class BuyerTable {
    private String id, name, price, sname, snumber;

    public BuyerTable(String id, String name, String price, String sname, String snumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sname = sname;
        this.snumber = snumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }
}
