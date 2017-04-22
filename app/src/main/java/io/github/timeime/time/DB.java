package io.github.timeime.time;

public class DB {
    private int id;
    private String data;

    public DB() {
    }

    public DB(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public DB(String data) {
        this.data = data;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
