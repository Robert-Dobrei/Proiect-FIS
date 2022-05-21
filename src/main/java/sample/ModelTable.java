package sample;

public class ModelTable {

    private String name,price,desc,phone;

    public ModelTable(String name, String price, String desc, String phone) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.phone= phone;
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

   /* public String getSellerName() {
        return sellername;
    }

    public void setSellerName(String sellername) {
        this.sellername = sellername;
    }
*/
}
