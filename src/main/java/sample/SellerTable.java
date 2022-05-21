package sample;

public class SellerTable {
    private String id, name, bname,bnumber;

    public SellerTable(String id,String name,String bname, String bnumber) {
        this.id = id;
        this.name=name;
        this.bname=bname;
        this.bnumber=bnumber;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBnumber() {
        return bnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber;
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
}
