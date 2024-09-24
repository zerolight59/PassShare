package com.example.passshare;

public class DBGroup {
    int id;
    String GroupName;

    public DBGroup(int id, String groupName) {
        this.id = id;
        GroupName = groupName;
    }

    public DBGroup(String groupName) {
        GroupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

}
