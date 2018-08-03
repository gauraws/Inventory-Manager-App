package com.example.inventory;


import java.util.Calendar;


import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Frag1 extends Fragment {
    Cursor cur, r1;
    DatePickerDialog date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.activity_frag1, container, false);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        final Activity act = getActivity();
        final data i = new data(act);

        Button b = (Button) act.findViewById(R.id.button1);
        final EditText t1 = (EditText) act.findViewById(R.id.icode);
        final EditText t2 = (EditText) act.findViewById(R.id.iname);
        final EditText t3 = (EditText) act.findViewById(R.id.qty);
        final EditText t4 = (EditText) act.findViewById(R.id.rate);
        final EditText t5 = (EditText) act.findViewById(R.id.date);
        SQLiteDatabase db = i.getReadableDatabase();
        r1 = db.rawQuery("Select userid from curuser", null);
        r1.moveToNext();
        i.close();

        Calendar cal = Calendar.getInstance();
        date = new DatePickerDialog(act, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int y, int m, int d) {
                // TODO Auto-generated method stub

                t5.setText(d + "/" + (m + 1) + "/" + y);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        t5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                date.show();
            }
        });
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String ic = t1.getText() + "";
                String in = t2.getText() + "";
                String qty = t3.getText() + "";
                String rate = t4.getText() + "";
                String d = t5.getText() + "";
                if (ic.equals("") | in.equals("") | qty.equals("") | rate.equals("") | d.equals("")) {
                    Toast.makeText(act, "Please fill all the fields", 0).show();
                } else {
                    int q = Integer.parseInt(t3.getText() + "");
                    float r = Float.parseFloat(t4.getText() + "");

                    SQLiteDatabase db = i.getReadableDatabase();
                    cur = db.rawQuery("Select * from Item where icode='" + ic + "' and userid='" + r1.getString(0) + "'", null);
                    if (cur.getCount() > 0) {
                        i.close();
                        db = i.getWritableDatabase();
                        db.execSQL("Update Item set iname='" + in + "',qty=" + q + ",rate=" + r + ",date='" + d +
                                "' where icode='" + ic + "' and userid='" + r1.getString(0) + "'");
                        Toast.makeText(act, "Item updated successfully!", 0).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                    } else {
                        i.close();
                        db = i.getWritableDatabase();
                        db.execSQL("Insert into Item values('" + ic + "','" + in + "'," + q + "," + r + ",'" + d + "','" + r1.getString(0) + "')");
                        i.close();
                        Toast.makeText(act, "Item added successfully!", 0).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                    }
                }
            }

        });
        super.onStart();
    }

}