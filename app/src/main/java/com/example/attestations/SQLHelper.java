package com.example.attestations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {
    public SQLHelper (Context context){
        super(context,"PassMGRDB", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " +
                "Users (id integer primary key autoincrement, " +
                "Login text, Password text);");
        db.execSQL("create table " +
                "PaswordRecords (id integer primary key autoincrement,UserId integer, Service text,Login text, Password text, Note text);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists Users");
        db.execSQL("drop table if exists PaswordRecords");
        onCreate(db);
    }
    private Cursor getUsersTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from Users",null);
    }
    public User getUser(String login){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where Login = '"+login+"'",null);
        User user = new User();
        user.setId(-1);
        if(cursor != null){
            while (cursor.moveToNext()){
                user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            }
        }
        return user;
    }
    private Cursor getPasswordRecordsTable(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from PaswordRecords where UserId = "+userId,null);
    }

    public void insertUser(String login,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into Users (Login, Password) values ('"+ login +"', '"+pass+"')");
    }
    public void insertPasswordRecord(int userId,String service,String login,String pass, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into PaswordRecords (UserId,Service,Login, Password, Note) values ("+userId+", '"+ service +"', '"+login+"', '"+pass+"', '"+note+"')");
    }
    public ArrayList<User> getUsersList(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = getUsersTable();
        if(cursor != null){
            while (cursor.moveToNext()){
                users.add(new User(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2)));
            }
        }
        return users;
    }

    public ArrayList<PasswordRecord> getPasswordRecordsList(int userId){
        ArrayList<PasswordRecord> passwordRecords = new ArrayList<>();
        Cursor cursor = getPasswordRecordsTable(userId);
        if(cursor != null){
            while (cursor.moveToNext()){
                passwordRecords.add(new PasswordRecord(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
        }
        return passwordRecords;
    }
}