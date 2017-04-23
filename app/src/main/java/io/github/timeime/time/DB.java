package io.github.timeime.time;

public class DB {
    private int id;
    private String data;
    private String name;
    public DB() {
    }

    public DB( String data,String name) {
        this.data = data;
        this.name=name;
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
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
