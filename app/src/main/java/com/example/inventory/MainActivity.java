package com.example.inventory;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {


    SQLiteDatabase db;
    data main = new data(this);
    Cursor r;
    private ProgressDialog mprogressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = main.getReadableDatabase();
        r = db.rawQuery("select * from curuser", null);
        if (r.getCount() == 0) {
            main.close();
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mprogressdialog = new ProgressDialog(this);
        mprogressdialog.setCancelable(true);
        mprogressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogressdialog.setMessage("Logout Successful!");

        ActionBar action = getActionBar();
        ActionBar.Tab t1 = action.newTab();
        t1.setText("Item");
        //t1.setIcon(drawable.ic_menu_info_details);
        t1.setTabListener(new TabListener() {

            @Override
            public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                Frag1 f = new Frag1();
                arg1.replace(R.id.frame, f);
            }

            @Override
            public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }
        });
        ActionBar.Tab t2 = action.newTab();
        t2.setText("Customer");
        //t2.setIcon(drawable.btn_star);
        t2.setTabListener(new TabListener() {

            @Override
            public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                Frag2 f = new Frag2();
                arg1.replace(R.id.frame, f);
            }

            @Override
            public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }
        });

        ActionBar.Tab t3 = action.newTab();
        t3.setText("Item Order");
        //t3.setIcon(drawable.ic_dialog_email);
        t3.setTabListener(new TabListener() {

            @Override
            public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                Frag3 f = new Frag3();
                arg1.replace(R.id.frame, f);
            }

            @Override
            public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }
        });

        ActionBar.Tab t4 = action.newTab();
        t4.setText("Sell");
       // t4.setIcon(drawable.ic_menu_agenda);
        t4.setTabListener(new TabListener() {

            @Override
            public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                Frag4 f = new Frag4();
                arg1.replace(R.id.frame, f);
            }

            @Override
            public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }
        });

        ActionBar.Tab t5 = action.newTab();
        t5.setText("Report");
       // t5.setIcon(drawable.ic_media_ff);
        t5.setTabListener(new TabListener() {

            @Override
            public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                Frag5 f = new Frag5();
                arg1.replace(R.id.frame, f);
            }

            @Override
            public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }
        });
        action.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        action.addTab(t1);
        action.addTab(t2);
        action.addTab(t3);
        action.addTab(t4);
        action.addTab(t5);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (resultCode) {
            case 1:
                setResult(1);
                finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem mi = menu.add(0, 0, 0, "Home");
        MenuItem mi1 = menu.add(0, 1, 1, "My Customers");
        MenuItem mi2 = menu.add(0, 2, 2, "My Items");
        MenuItem mi3 = menu.add(0, 3, 3, "Orders");
        MenuItem mi4 = menu.add(0, 4, 4, "Sell Log");
        MenuItem mi5 = menu.add(0, 5, 5, "My Account");
        MenuItem mi6 = menu.add(0, 6, 6, "Logout");


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 0:
                startActivityForResult(new Intent(MainActivity.this, MainActivity.class), 0);
                break;

            case 1:
                startActivityForResult(new Intent(MainActivity.this, Customers.class), 0);
                break;

            case 2:
                startActivityForResult(new Intent(MainActivity.this, Items.class), 0);
                break;

            case 3:
                startActivityForResult(new Intent(MainActivity.this, Orders.class), 0);
                break;

            case 4:
                startActivityForResult(new Intent(MainActivity.this, Sell.class), 0);
                break;
            case 5:
                startActivityForResult(new Intent(MainActivity.this, Account.class), 0);
                break;
            default: {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Logging out!");
                adb.setMessage("Are you sure, you want to log out?");
                adb.setCancelable(false);
                adb.setPositiveButton("Yes", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(getBaseContext(), Login.class);

                        startActivity(i);
                        db = main.getWritableDatabase();
                        db.execSQL("Delete from curuser");
                        main.close();
                        mprogressdialog.show();
                        finish();
                    }
                });
                adb.setNegativeButton("Cancel", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.cancel();
                    }
                });
                AlertDialog ad = adb.create();
                ad.show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

}
