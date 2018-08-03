package com.example.inventory;

import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends Activity {

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

	
	Button b1,b2,b3,b4;
 TextView t1;
 ImageView i;
  EditText e1,e2,e3;
  data ac=new data(this);
  SQLiteDatabase db;
  private ProgressDialog pd1,pd2;
  Cursor r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		 pd1 =new ProgressDialog(this);
		 pd1.setCancelable(true);
		 pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pd1.setMessage("Account Deleted!");
	     
		 pd2 =new ProgressDialog(this);
		 pd2.setCancelable(true);
		 pd2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pd2.setMessage("Logout Successful!");
		 
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);
		i=(ImageView)findViewById(R.id.imageView1);
		i.setEnabled(false);
		
		e1=(EditText)findViewById(R.id.name);
		t1=(TextView)findViewById(R.id.userid);
		e2=(EditText)findViewById(R.id.email);
		e3=(EditText)findViewById(R.id.add);
		e1.setEnabled(false);
		e2.setEnabled(false);
		e3.setEnabled(false);
		b2.setEnabled(false);
		
		db=ac.getReadableDatabase();
		r=db.rawQuery("Select * from curuser",null);
		r.moveToNext();
		e1.setText(r.getString(0));
		t1.setText(r.getString(1));
		e2.setText(r.getString(3));
		e3.setText(r.getString(4));
		ac.close();
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				e1.setEnabled(true);
				i.setEnabled(true);
				e2.setEnabled(true);
				e3.setEnabled(true);
				b2.setEnabled(true);
			}
		});
		
b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String n= e1.getText()+"";
				String e= e2.getText()+"";
				String u= t1.getText()+"";
				String a= e3.getText()+"";
				if(n.equals("")|e.equals("")|u.equals(""))
				{
					Toast.makeText(Account.this,"Do not leave any field blank",0).show();
				}
				else {	db=ac.getWritableDatabase();
				db.execSQL("Update user set Name='"+n+"',email='"+e+"',ad='"+a+"' where userid='"+u+"'");
				db.execSQL("Update curuser set Name='"+n+"',email='"+e+"',ad='"+a+"' where userid='"+u+"'");
				ac.close();
				Toast.makeText(Account.this,"Account udated successfully!",0).show();
				startActivity(new Intent(Account.this,Account.class));
				finish(); }
			}
		});
           b3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent(Account.this,Change.class));
	}
});
           b4.setOnClickListener(new OnClickListener() {
        		
        		@Override
        		public void onClick(View arg0) {
        			// TODO Auto-generated method stub
        			AlertDialog.Builder adb=new AlertDialog.Builder(Account.this);
        			adb.setTitle("Deleting Account!!");
        			adb.setMessage("Are you sure, you want to delete your account?");
        		adb.setCancelable(false);
        			adb.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
        				
        				@Override
        				public void onClick(DialogInterface arg0, int arg1) {
        					// TODO Auto-generated method stub
        					String u= t1.getText()+"";
        					db=ac.getWritableDatabase();
        				   db.execSQL("Delete from user where userid='"+u+"'");
        				   db.execSQL("Delete from curuser where userid='"+u+"'");
        				   db.execSQL("Delete from Item where userid='"+u+"'");
        				   db.execSQL("Delete from Customer where userid='"+u+"'");
        				   db.execSQL("Delete from Itemorder where userid='"+u+"'");
        				   db.execSQL("Delete from Sell where userid='"+u+"'");
        					Intent i=new Intent(getBaseContext(),Login.class);
        					
        					startActivity(i);
        					pd1.show();
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
           
           i.setOnClickListener(new OnClickListener() {
   			
   			@Override
   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				LocationManager lm=(LocationManager)getSystemService(LOCATION_SERVICE);
   				Criteria c=new Criteria();
   				c.setAccuracy(Criteria.ACCURACY_MEDIUM);
   				c.setBearingAccuracy(Criteria.ACCURACY_MEDIUM);
   				c.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
   				c.setCostAllowed(false);
   				String provider=lm.getBestProvider(c, true);
   				LocationProvider lprovide=lm.getProvider(LocationManager.GPS_PROVIDER);
   				if(lprovide!=null)
   				{
   					Location last=lm.getLastKnownLocation(provider);
   					if(last!=null)
   					{
   												
   						Geocoder gcd=new Geocoder(Account.this,Locale.getDefault());
   						try
   						{
   							List<Address> ad=gcd.getFromLocation(last.getLatitude(),last.getLongitude(),1);
   							if(ad.size()>0)
   							{
   								e3.setText(""+ad.get(0).getAddressLine(0)+"\n"+ad.get(0).getSubLocality()
   										+", "+ad.get(0).getLocality()+"\n"+ad.get(0).getCountryName());
   								
   								 
   							}
   							
   						}
   						catch(Exception e)
   						{
   							Toast.makeText(Account.this,""+e,0).show();
   						}
   						
   					}
   				}
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
		case 0: startActivityForResult(new Intent(Account.this,MainActivity.class),0);break;
		
		case 1: startActivityForResult(new Intent(Account.this,Customers.class),0);break;
		
		case 2: startActivityForResult(new Intent(Account.this,Items.class),0);break;
		
case 3: startActivityForResult(new Intent(Account.this,Orders.class),0);break;
		
		case 4: startActivityForResult(new Intent(Account.this,Sell.class),0);break;
		
		case 5: startActivityForResult(new Intent(Account.this,Account.class),0);break;
		
		default: {  AlertDialog.Builder adb=new AlertDialog.Builder(Account.this);
		adb.setTitle("Logging out!");
		adb.setMessage("Are you sure, you want to log out?");
	adb.setCancelable(false);
		adb.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),Login.class);
					
				startActivity(i);
					db=ac.getWritableDatabase();
    				db.execSQL("Delete from curuser");
    				ac.close();
    			
				pd2.show();
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
