package com.example.inventory;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
  @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	 
	  
	  System.exit(0);
		super.onBackPressed();
	}

static  EditText ui,pass;
    Button b1,b2;
    data log=new data(this);
    SQLiteDatabase db;
    Cursor r,r1;
    Boolean flag=false;
    private ProgressDialog mprogressdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mprogressdialog =new ProgressDialog(Login.this);
        mprogressdialog.setCancelable(true);
        mprogressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogressdialog.setMessage("Login Successful!");
		
		ui=(EditText)findViewById(R.id.userid);
		pass=(EditText)findViewById(R.id.pass);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String u=ui.getText()+"";
				String p=pass.getText()+"";
			    if(u.equals("") | p.equals(""))
			    	Toast.makeText(getBaseContext(),"Fill both fields",0).show();
			    else
			    {
			    	db=log.getReadableDatabase();
			    	r=db.rawQuery("Select * from user",null);
			    	if(r.getCount()>0)
			    	{   while(!r.isLast())
			    		{r.moveToNext();
			    		if(u.equals(r.getString(1)) & p.equals(r.getString(2))) {
			    			r1=db.rawQuery("Select * from curuser",null);
			    			if(r1.getCount()>0)
			    			{log.close();
			    				db=log.getWritableDatabase();
			    				db.execSQL("Delete from curuser");
			    				
			    			}
			    			log.close();
			    			flag=true;
			    			
			    			Intent i=new Intent(Login.this,MainActivity.class); 
			    			startActivityForResult(i,0);
			    			db=log.getWritableDatabase();
			    			db.execSQL("Insert into curuser values('"+r.getString(0)+"','"+r.getString(1)+"','"+r.getString(2)+"','"
			    					+r.getString(3)+"','"+r.getString(4)+"')");
			    			log.close();
			    	        mprogressdialog.show();
			    	        finish(); 
			    			
			    	        
			    			} }
			    	if(flag==false)
			    	{	 Toast.makeText(getBaseContext(),"Invalid username or password",0).show(); } 
			    			
			    				    	}
			    	else Toast.makeText(getBaseContext(),"Invalid username or password",0).show();
			    	
			    }
			}
		});
b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Login.this,Register.class); 
				startActivity(i);
				
			}
		});
		
	}

	

}
