package sample;

public class ModelTable {

    private String name,price,desc,phone,sname;

    public ModelTable(String name, String price, String desc, String sname, String phone) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.phone = phone;
        this.sname = sname;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
