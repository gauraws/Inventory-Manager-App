package com.example.inventory;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Frag3 extends Fragment {
    Spinner s1, s2;
    ArrayList<String> al1, al2;
    ArrayAdapter<String> ad1, ad2;
    SQLiteDatabase db;
    String c, i;
    int count, check, temp = 0;
    Cursor r, r1, r2, cur;
    DatePickerDialog date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.activity_frag3, container, false);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        final Activity act = getActivity();
        final data io = new data(act);

        Button b = (Button) act.findViewById(R.id.button1);
        final TextView t1 = (TextView) act.findViewById(R.id.orderno);
        final EditText t2 = (EditText) act.findViewById(R.id.qty);
        final EditText t3 = (EditText) act.findViewById(R.id.date);

        db = io.getReadableDatabase();
        cur = db.rawQuery("Select userid from curuser", null);
        cur.moveToNext();
        io.close();

        Calendar cal = Calendar.getInstance();
        date = new DatePickerDialog(act, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int y, int m, int d) {
                // TODO Auto-generated method stub

                t3.setText(d + "/" + (m + 1) + "/" + y);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        t3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                date.show();
            }
        });


        s1 = (Spinner) act.findViewById(R.id.ccode);
        s2 = (Spinner) act.findViewById(R.id.icode);
        al1 = new ArrayList<String>();
        al2 = new ArrayList<String>();

        al1.add("Select..");
        al2.add("Select..");

        db = io.getReadableDatabase();
        r = db.rawQuery("Select order_no from Itemorder where userid='" + cur.getString(0) + "'", null);
        if (r.getCount() > 0) {
            while (!r.isLast()) {
                r.moveToNext();

                count = Integer.parseInt(r.getString(0));
                if (count == (temp + 1)) {
                    temp = count;
                } else {
                    count = temp + 1;
                    break;
                }
            }
            t1.setText(count + "");
        } else t1.setText("1");

        r1 = db.rawQuery("Select ccode from Customer where userid='" + cur.getString(0) + "'", null);
        if (r1.getCount() > 0) {
            while (!r1.isLast()) {
                r1.moveToNext();
                al1.add(r1.getString(0));

            }
        }
        r2 = db.rawQuery("Select icode from Item where userid='" + cur.getString(0) + "'", null);
        if (r2.getCount() > 0) {
            while (!r2.isLast()) {
                r2.moveToNext();
                al2.add(r2.getString(0));

            }
        }
        io.close();
        ad1 = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, al1);
        ad2 = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, al2);
        s1.setAdapter(ad1);
        s2.setAdapter(ad2);
        s1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> list, View arg1, int pos,
                                       long arg3) {
                // TODO Auto-generated method stub
                c = list.getItemAtPosition(pos) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        s2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> list, View arg1, int pos,
                                       long arg3) {
                // TODO Auto-generated method stub
                i = list.getItemAtPosition(pos) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (c.equals("Select..")) {
                    Toast.makeText(act, "Please select a ccode", 0).show();
                } else if (i.equals("Select..")) {
                    Toast.makeText(act, "Please select a icode", 0).show();
                } else {

                    int o = Integer.parseInt(t1.getText() + "");
                    String d = t3.getText() + "";
                    int q = Integer.parseInt(t2.getText() + "");
                    db = io.getReadableDatabase();
                    r = db.rawQuery("Select qty from Item where icode='" + i + "'" +
                            " and userid='" + cur.getString(0) + "'", null);
                    r.moveToNext();
                    check = Integer.parseInt(r.getString(0));
                    io.close();
                    if (q <= check) {
                        db = io.getWritableDatabase();
                        db.execSQL("Insert into Itemorder values(" + o + ",'" + c + "','" + i + "'," + q + ",'" + d +
                                "','" + cur.getString(0) + "')");
                        io.close();
                        Toast.makeText(act, "Order added succesfully!", 0).show();

                        db = io.getReadableDatabase();
                        r = db.rawQuery("Select count(*) from Itemorder where userid='" + cur.getString(0) + "'", null);
                        r.moveToNext();
                        count = Integer.parseInt(r.getString(0));
                        count++;
                        io.close();

                        t1.setText(count + "");
                        t2.setText("");
                        t3.setText("");
                        ad1.notifyDataSetChanged();
                        ad2.notifyDataSetChanged();
                        s1.setAdapter(ad1);
                        s2.setAdapter(ad2);
                    } else
                        Toast.makeText(act, "Quantity is more than stock! You have " + check + " units", 0).show();
                }
            }
        });
        super.onStart();
    }


}
