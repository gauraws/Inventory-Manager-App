package com.example.inventory;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Frag2 extends Fragment {
    Cursor cur, r1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.activity_frag2, container, false);
    }

    public void onStart() {
        // TODO Auto-generated method stub
        final Activity act = getActivity();
        final data c = new data(act);

        Button b = (Button) act.findViewById(R.id.button1);
        final EditText t1 = (EditText) act.findViewById(R.id.ccode);
        final EditText t2 = (EditText) act.findViewById(R.id.cname);
        final EditText t3 = (EditText) act.findViewById(R.id.add);
        final EditText t4 = (EditText) act.findViewById(R.id.phone);

        SQLiteDatabase db = c.getReadableDatabase();
        r1 = db.rawQuery("Select userid from curuser", null);
        r1.moveToNext();
        c.close();
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String ic = t1.getText() + "";
                String in = t2.getText() + "";
                String q = t3.getText() + "";
                String ph = t4.getText() + "";
                if (ic.equals("") | in.equals("") | q.equals("") | ph.equals("")) {
                    Toast.makeText(act, "Please fill all the fields", 0).show();
                } else {
                    long r = Long.parseLong(t4.getText() + "");

                    SQLiteDatabase db = c.getReadableDatabase();
                    cur = db.rawQuery("Select * from Customer where ccode='" + ic + "' and userid='" + r1.getString(0) + "'", null);
                    if (cur.getCount() > 0) {
                        db.close();
                        db = c.getWritableDatabase();
                        db.execSQL("Update Customer set cname='" + in + "',ad='" + q + "',phone=" + r +
                                " where ccode='" + ic + "' and userid='" + r1.getString(0) + "'");
                        Toast.makeText(act, "Item updated successfully!", 0).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                    } else {
                        db = c.getWritableDatabase();
                        db.execSQL("Insert into Customer values('" + ic + "','" + in + "','" + q + "'," + r + ",'" + r1.getString(0) + "')");
                        c.close();
                        Toast.makeText(act, "Customer added succefully!", 0).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                    }
                }
            }
        });
        super.onStart();
    }
}
