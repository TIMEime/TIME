package io.github.timeime.time;

public class AccountDB {
    private int id;
    private String account;
    private String password;
    public AccountDB() {}
    public AccountDB(String account,String password) {
        this.account=account;
        this.password=password;
    }
    public void setID(int id) {this.id = id;}
    public int getID() {
        return this.id;
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
}
