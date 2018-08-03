package com.example.inventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class data extends SQLiteOpenHelper {

    public data(Context context) {
        super(context, "Inventory", null, 5);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        arg0.execSQL("Create table user(Name text,userid text Primary Key,pass text,email text," +
                "ad text)");

        arg0.execSQL("Create table curuser(Name text,userid text Primary Key,pass text," +
                "email text,ad text)");

        arg0.execSQL("Create table Item(icode varchar(10) not null,iname text,qty Numeric," +
                "rate Float,date Date,userid text not null,Primary Key(icode, userid))");

        arg0.execSQL("Create table Customer(ccode varchar(10) not null,cname text,ad Text," +
                "phone Numeric,userid text not null,Primary Key(ccode, userid))");

        arg0.execSQL("Create table Itemorder(order_no Numeric,ccode varchar(10)," +
                "icode varchar(10),qty Numeric,date Date,userid text not null," +
                "Primary Key(order_no, userid))");

        arg0.execSQL("Create table Sell(sno Numberic,order_no Numeric,ccode varchar(10)," +
                "icode varchar(10),qty Numeric,date Date,rate Float," +
                "userid text not null,Primary Key(sno, userid))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

        arg0.execSQL("drop table Item");
        arg0.execSQL("drop table Customer");
        arg0.execSQL("drop table Itemorder");
        arg0.execSQL("drop table Sell");
        arg0.execSQL("drop table user");
        arg0.execSQL("drop table curuser");
        onCreate(arg0);
    }

}
