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
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Register extends Activity {
    EditText t1,t2,t3,t4,t5,t6;
    ImageView i;
    Button b;
    Cursor r;
    SQLiteDatabase db;
    Boolean f=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		t1=(EditText)findViewById(R.id.name);
		t2=(EditText)findViewById(R.id.userid);
		t3=(EditText)findViewById(R.id.pass);
		t4=(EditText)findViewById(R.id.conf);
		t5=(EditText)findViewById(R.id.email);
		t6=(EditText)findViewById(R.id.address);
		b=(Button)findViewById(R.id.button1);
		
		i=(ImageView)findViewById(R.id.imageView1);
		
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
												
						Geocoder gcd=new Geocoder(Register.this,Locale.getDefault());
						try
						{
							List<Address> ad=gcd.getFromLocation(last.getLatitude(),last.getLongitude(),1);
							if(ad.size()>0)
							{
								t6.setText(""+ad.get(0).getAddressLine(0)+"\n"+ad.get(0).getSubLocality()
										+", "+ad.get(0).getLocality()+"\n"+ad.get(0).getCountryName());
								
								 
							}
							
						}
						catch(Exception e)
						{
							Toast.makeText(Register.this,""+e,0).show();
						}
						
					}
				}
			}
		});
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String n=t1.getText()+"";
			String u=t2.getText()+"";
			String p=t3.getText()+"";
			String c=t4.getText()+"";
			String e=t5.getText()+"";
			String a=t6.getText()+"";
			if(u.equals("") | p.equals("")| n.equals("")|c.equals("")|e.equals("")|a.equals(""))
			{
				Toast.makeText(getBaseContext(),"Please Enter all the fields",0).show();
			}
			else if(c.equals(p))
			{
			 	data reg=new data(Register.this);
			 	 db=reg.getReadableDatabase();
				r=db.rawQuery("Select userid from user",null);
				if(r.getCount()>0)
				{  
					while(!r.isLast())
				{
					r.moveToNext();
					if(u.equals(r.getString(0)))
					{
						Toast.makeText(Register.this,"Username already in use",0).show();
						reg.close();
						f=true;
						break;
					}
				}
				if(f==false)
				{
					reg.close();
					db=reg.getWritableDatabase(); 
					db.execSQL("Insert into user values('"+n+"','"+u+"','"+p+"','"+e+"','"+a+"')");
					reg.close();
					Toast.makeText(Register.this,"Registeration complete!",0).show();
					Intent i=new Intent(Register.this,Login.class);
					startActivity(i);
					finish();
				}
				}
				
				else {
					reg.close();
					db=reg.getWritableDatabase(); 
					db.execSQL("Insert into user values('"+n+"','"+u+"','"+p+"','"+e+"','"+a+"')");
					reg.close();
					Toast.makeText(Register.this,"Registeration complete!",0).show();
					Intent i=new Intent(Register.this,Login.class);
					startActivity(i);
					finish();
				}
				
				
			}
			else 
			{
				Toast.makeText(Register.this,"Passwords don't match",0).show();	
			}
			}
		});
	}

	
	

}
