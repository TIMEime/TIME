package io.github.timeime.time;

public class DB {
    private int id;
    private String data;
    private String name;
    private String account;
    private String password;
    private String email;
    public DB() {}
    public DB( String name,String data,String account) {
        this.data = data;
        this.name=name;
        this.account=account;
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
    public void setAccount(String account) {
        this.account = account;
    }
    public String getAccount() {
        return this.account;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
}
