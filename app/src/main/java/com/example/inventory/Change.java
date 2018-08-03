package com.example.inventory;

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
import android.widget.Toast;

public class Change extends Activity {
Button b;
EditText e1,e2,e3;
data ch=new data(this);
SQLiteDatabase db;
Cursor r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		b=(Button)findViewById(R.id.button1);
		e1=(EditText)findViewById(R.id.old);
		e2=(EditText)findViewById(R.id.newpass);
		e3=(EditText)findViewById(R.id.conf);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String o=e1.getText()+"";
				String n=e2.getText()+"";
				String c=e3.getText()+"";
				if(o.equals("")|n.equals("")|c.equals(""))
				{
					Toast.makeText(Change.this,"Do not leave any field blank",0).show();
				}
				else {
					db=ch.getReadableDatabase();
					r=db.rawQuery("Select userid,pass from curuser",null);
					r.moveToNext();
					String u=r.getString(0);
					
					if(o.equals(r.getString(1)))
					{
						ch.close();
						if(n.equals(c))
						{
							db=ch.getWritableDatabase();
							db.execSQL("Update curuser set pass='"+n+"'");
							db.execSQL("Update user set pass='"+n+"' where userid='"+u+"'");
							ch.close();
							Toast.makeText(Change.this,"Password Changed!",0).show();
							startActivity(new Intent(Change.this,Account.class));
							finish();
						}
						else{
							Toast.makeText(Change.this,"New Passwords don't match! Please check",0).show();
						}
					}
					else{
						Toast.makeText(Change.this,"Old Password Entered Wrong!",0).show();
					}
				}
			}
		});
	}

	

}
