//設定與取得資料存放的帳號與資料的名稱內容的類別
package io.github.timeime.time;

public class DB {
    private int id;
    private String data;
    private String name;
    private String account;
    public DB(){}
    public DB( String name,String data,String account) {
        this.data = data;
        this.name=name;
        this.account=account;
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
    public String getData() {return this.data;}
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
}
