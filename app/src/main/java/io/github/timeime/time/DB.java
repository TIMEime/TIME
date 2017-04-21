package io.github.timeime.time;

public class DB {
    private int id;
    private String name;
    public DB() {
    }

    public DB(int id, String productname) {
        this.id = id;
        this.name = productname;
    }

    public DB(String productname) {
        this.name = productname;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void setProductName(String productname) {
        this.name = productname;
    }

    public String getProductName() {
        return this.name;
    }

}
