package com.example.passshare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="passShare_DB";


    public DBHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Enable foreign key support
        db.execSQL("PRAGMA foreign_keys = ON;");

        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "uid INTEGER PRIMARY KEY," +
                "uname TEXT," +
                "upassword TEXT" +
                ")";

        String CREATE_GROUPS_TABLE = "CREATE TABLE groups (" +
                "group_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "group_name TEXT NOT NULL," +
                "admin_uid INTEGER," +
                "FOREIGN KEY (admin_uid) REFERENCES users(uid) ON DELETE CASCADE" +
                ");";

        String CREATE_SERVICES_TABLE = "CREATE TABLE services (" +
                "service_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "service_name TEXT NOT NULL," +
                "service_uname TEXT," +
                "service_password TEXT," +
                "group_id INTEGER," +
                "FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE" +
                ");";

        String CREATE_GROUP_USER_TABLE = "CREATE TABLE group_user (" +
                "group_id INTEGER," +
                "uid INTEGER," +
                "PRIMARY KEY (group_id, uid)," +
                "FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE," +
                "FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE CASCADE" +
                ");";

        // Execute SQL to create tables
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_SERVICES_TABLE);
        db.execSQL(CREATE_GROUP_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS groups");
        db.execSQL("DROP TABLE IF EXISTS services");
        db.execSQL("DROP TABLE IF EXISTS group_user");

        // Recreate tables
        onCreate(db);
    }


    public Boolean addUser(String name,String password) {
        String tableName="users";
        String uname="uname";
        String upassword="upassword";
        //open db for writing
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //pass data as key-value pairs
        values.put(uname,name);
        values.put(upassword,password);
        //pass content values to the table
        long result = db.insert(tableName,null,values);
        // db.close();
        if (result==-1) return false;
        else return true;
    }

    public Boolean checkUserName(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from users where uname = ?",new String[]{name});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    // for group
    public Boolean addGroup(String name,int id) {
        String tableName="groups";
        String group_name="group_name";
        String admin_uid="admin_uid";
        //open db for writing
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //pass data as key-value pairs
        values.put(group_name,name);
        values.put(admin_uid,(int) id);
        //pass content values to the table
        long result = db.insert(tableName,null,values);
        // db.close();
        if (result==-1) return false;
        else return true;
    }

    public Boolean checkGroupNameAndAid(String name,int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from groups where group_name = ? and admin_uid= ?",new String[]{name, String.valueOf(id)});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public int findUidUsingUname(String name) {
        SQLiteDatabase db = this.getReadableDatabase();  // Use getReadableDatabase for read operations
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT uid FROM users WHERE uname = ?", new String[]{name});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();  // Move to the first row in the result set
                return cursor.getInt(0);  // Return the first column value (uid)
            } else {
                return -1;  // Return -1 if no result is found
            }
        } finally {
            if (cursor != null) {
                cursor.close();  // Close the cursor to avoid memory leaks
            }
        }
    }

    public List<String> getGroupNames(int id) {
        List<String> groupNames = new ArrayList<>();  // List to store group names
        SQLiteDatabase db = this.getReadableDatabase();  // Use getReadableDatabase for read operations
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT group_name FROM groups WHERE admin_uid = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {  // Check if there are any results and move to the first row
                do {
                    // Add each group name to the list
                    @SuppressLint("Range") String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
                    groupNames.add(groupName);
                } while (cursor.moveToNext());  // Move to the next row
            }
        } finally {
            if (cursor != null) {
                cursor.close();  // Close the cursor to avoid memory leaks
            }
        }
        return groupNames;  // Return the list of group names


    }

    public int getGroupIdFromName(String groupName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int groupId = -1; // Default to -1 to indicate "not found"
        Cursor cursor = null;

        try {
            // Query to get the group_id where the group_name matches
            cursor = db.rawQuery("SELECT group_id FROM groups WHERE group_name = ?", new String[]{groupName.trim()});

            if (cursor.moveToFirst()) {
                // Get the group_id from the result
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex("group_id"));
                groupId = id;
            } else {
                // Log or show a message if no matching group_name is found
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return groupId;
    }


    //

    // service

    public Boolean addService(String sname, String suname, String spassword, int gid) {
        String tableName = "services";
        String service_name = "service_name";
        String service_uname = "service_uname";
        String service_password = "service_password";
        String group_id = "group_id";  // Use the column name as a string

        // Open db for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Pass data as key-value pairs
        values.put(service_name, sname);
        values.put(service_uname, suname);
        values.put(service_password, spassword);
        values.put(group_id, gid);  // Use the column name here

        // Pass content values to the table
        long result = db.insert(tableName, null, values);

        if (result == -1) return false;
        else return true;
    }


    public List<String> getSerNames(String gname) {
        List<String> serviceNames = new ArrayList<>();  // List to store service names
        SQLiteDatabase db = this.getReadableDatabase();  // Use getReadableDatabase for read operations
        Cursor cursor = null;

        try {
            // First, find the group_id corresponding to the provided group name
            cursor = db.rawQuery("SELECT group_id FROM groups WHERE group_name = ?", new String[]{gname});
            if (cursor.moveToFirst()) {
                 @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));

                // Now query the services table to get the service names for the given group_id
                Cursor serviceCursor = db.rawQuery("SELECT service_name FROM services WHERE group_id = ?", new String[]{String.valueOf(groupId)});

                if (serviceCursor.moveToFirst()) {
                    do {
                        // Get service name from the current row and add it to the list
                        @SuppressLint("Range") String serviceName = serviceCursor.getString(serviceCursor.getColumnIndex("service_name"));
                        serviceNames.add(serviceName);
                    } while (serviceCursor.moveToNext());
                }

                // Close the second cursor
                serviceCursor.close();
            }
        } finally {
            if (cursor != null) {
                cursor.close();  // Close the cursor to avoid memory leaks
            }
        }

        // Return the list of service names
        return serviceNames;
    }

    public List<String> getSerUNames(String gname) {
        List<String> serviceUNames = new ArrayList<>();  // List to store service names
        SQLiteDatabase db = this.getReadableDatabase();  // Use getReadableDatabase for read operations
        Cursor cursor = null;

        try {
            // First, find the group_id corresponding to the provided group name
            cursor = db.rawQuery("SELECT group_id FROM groups WHERE group_name = ?", new String[]{gname});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));

                // Now query the services table to get the service names for the given group_id
                Cursor serviceCursor = db.rawQuery("SELECT service_uname FROM services WHERE group_id = ?", new String[]{String.valueOf(groupId)});

                if (serviceCursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String serviceUName = serviceCursor.getString(serviceCursor.getColumnIndex("service_uname")); // Fix the column name here
                        serviceUNames.add(serviceUName);
                    } while (serviceCursor.moveToNext());
                }


                // Close the second cursor
                serviceCursor.close();
            }
        } finally {
            if (cursor != null) {
                cursor.close();  // Close the cursor to avoid memory leaks
            }
        }

        // Return the list of service names
        return serviceUNames;
    }

    public List<String> getSerPass(String gname) {
        List<String> servicePass = new ArrayList<>();  // List to store service names
        SQLiteDatabase db = this.getReadableDatabase();  // Use getReadableDatabase for read operations
        Cursor cursor = null;

        try {
            // First, find the group_id corresponding to the provided group name
            cursor = db.rawQuery("SELECT group_id FROM groups WHERE group_name = ?", new String[]{gname});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));

                // Now query the services table to get the service names for the given group_id
                Cursor serviceCursor = db.rawQuery("SELECT service_password FROM services WHERE group_id = ?", new String[]{String.valueOf(groupId)});

                if (serviceCursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String servicePassword = serviceCursor.getString(serviceCursor.getColumnIndex("service_password")); // Fix this line
                        servicePass.add(servicePassword);
                    } while (serviceCursor.moveToNext());
                }


                // Close the second cursor
                serviceCursor.close();
            }
        } finally {
            if (cursor != null) {
                cursor.close();  // Close the cursor to avoid memory leaks
            }
        }

        // Return the list of service names
        return servicePass;
    }

    //

    //
    // Function to find service ID based on service name
    public int findSidFromSname(String sname) {
        int serviceId = -1;  // Default value if service not found
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get the service ID based on service name
        String query = "SELECT service_id FROM services WHERE service_name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{sname});

        if (cursor.moveToFirst()) {
            // Get the service_id from the result set
            serviceId = cursor.getInt(cursor.getColumnIndexOrThrow("service_id"));
        }
        cursor.close();
        db.close();

        return serviceId;
    }

    // Function to delete a service based on service ID
    public Boolean delSer(int sid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Deleting the service with the given service_id
        int result = db.delete("services", "service_id = ?", new String[]{String.valueOf(sid)});
        db.close();

        // If result > 0, it means rows were deleted, otherwise no rows were affected
        return result > 0;
    }
    //

    public Boolean loginFun(String name,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from users where uname = ? and upassword = ?",new String[]{name,password});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
}
