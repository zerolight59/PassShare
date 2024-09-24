package com.example.passshare;

public class DBUsers {
    int uid;
    String uname;
    String upassword;

    public DBUsers( String uname, String upassword) {
        this.uname = uname;
        this.upassword = upassword;
    }
    @Override
    public String toString() {
        return "DBUsers{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upassword='" + upassword + '\'' +
                '}';
    }


    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return this.upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }
}
