package com.example.inventory;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Sell extends Activity {
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode)
		{
		case 1:
			setResult(1);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	ListView l;
	  TextView t1,t2;
	  Button b;
	  ArrayList<String> al1,al2;
	  ArrayAdapter<String> ad;
	  data cus=new data(this);
	  SQLiteDatabase db;
	  Cursor r,r1,cur;
	  String k;
	  private ProgressDialog mprogressdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sell);

		mprogressdialog =new ProgressDialog(this);
        mprogressdialog.setCancelable(true);
        mprogressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogressdialog.setMessage("Logout Successful!");
		
		l=(ListView)findViewById(R.id.listView1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		b=(Button)findViewById(R.id.button1);
		b.setEnabled(false);
		al1=new ArrayList<String>();
		al2=new ArrayList<String>();
		al1.add("Item Code\t\tDate ");
		al2.add(" ");
		db=cus.getReadableDatabase();
		cur=db.rawQuery("Select userid from curuser",null);
		cur.moveToNext();
		r=db.rawQuery("Select * from Sell where userid='"+cur.getString(0)+"'",null);
		if(r.getCount()>0)
		{
			while(!r.isLast())
		{
			r.moveToNext();
			al2.add(r.getString(0));
			al1.add(r.getString(3)+"\t\t\t"+r.getString(5));
		} }
		cus.close();
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al1);
		l.setAdapter(ad);
		
		l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				 k=al2.get(pos);
				 if(k.equals(" "))
				{b.setEnabled(false);
				 t2.setText("");
				}
				 else {
					 b.setEnabled(true);
				db=cus.getReadableDatabase();
				r=db.rawQuery("Select * from Sell where sno="+k+" " +
						"and userid='"+cur.getString(0)+"'",null);
				r.moveToNext();
			float rate=Float.parseFloat(r.getString(6));
			r1=db.rawQuery("Select rate from item where icode='"+r.getString(3)+"'" +
					" and userid='"+cur.getString(0)+"'",null);
			r1.moveToNext();
			float or=Float.parseFloat(r1.getString(0));
			or=((rate-or)/rate)*100;
			t2.setText("S.No: "+r.getString(0)+"\nOrder No: "+r.getString(1)+"\nCustomer Code: "+r.getString(2)+"\nItem Code: "
					+r.getString(3)+"\nQuantity: "+r.getString(4)+"\nDate: "+r.getString(5)+"\nSelling Rate: "+r.getString(6)+"\nProfit: "+or+" %");			
				cus.close(); }
				// TODO Auto-generated method stub
				
			}
		});
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 AlertDialog.Builder adb=new AlertDialog.Builder(Sell.this);
					adb.setTitle("Deleting Entry!");
					adb.setMessage("Are you sure, you want to delete?");
				adb.setCancelable(false);
					adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							db=cus.getWritableDatabase();
							db.execSQL("Delete from Sell where sno="+k+" " +
									"and userid='"+cur.getString(0)+"'");
							cus.close();
							Toast.makeText(getBaseContext(),"Entry deleted successfully",0).show();
							startActivity(new Intent(Sell.this,Sell.class));
							finish();
						}
					});
					adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.cancel();
						}
					});
						AlertDialog ad=adb.create();
						ad.show();
				
						
				
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuItem mi=menu.add(0,0,0,"Home");
        MenuItem mi1=menu.add(0,1,1,"My Customers");
        MenuItem mi2=menu.add(0,2,2,"My Items");
        MenuItem mi3=menu.add(0,3,3,"Orders");
        MenuItem mi4=menu.add(0,4,4,"Sell Log");
        MenuItem mi5=menu.add(0,5,5,"My Account");
        MenuItem mi6=menu.add(0,6,6,"Logout");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0: startActivityForResult(new Intent(Sell.this,MainActivity.class),0);break;
		
		case 1: startActivityForResult(new Intent(Sell.this,Customers.class),0);break;
		
		case 2: startActivityForResult(new Intent(Sell.this,Items.class),0);break;
		
		case 3: startActivityForResult(new Intent(Sell.this,Orders.class),0);break;
		
		case 4: startActivityForResult(new Intent(Sell.this,Sell.class),0);break;
		case 5: startActivityForResult(new Intent(Sell.this,Account.class),0);break;
		default: {  AlertDialog.Builder adb=new AlertDialog.Builder(Sell.this);
		adb.setTitle("Logging out!");
		adb.setMessage("Are you sure, you want to log out?");
	adb.setCancelable(false);
		adb.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),Login.class);
				
				startActivity(i);
				db=cus.getWritableDatabase();
				db.execSQL("Delete from curuser");
				cus.close();
				mprogressdialog.show();
				finish();
			}
		});
			adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.cancel();
				}
			});
			AlertDialog ad=adb.create();
			ad.show();}
		}
		 
		
		return super.onOptionsItemSelected(item);
	}

	
}
