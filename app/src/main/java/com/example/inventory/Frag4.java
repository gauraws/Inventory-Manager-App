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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Frag4 extends  Fragment{
	 Spinner s1;
	 TextView t1,t2,t3,t4;
	 EditText t5,t6;
	 Cursor r,r1;
	 Button b;
	 int count,k;
	 String check;
	 ArrayList<String> al1;
	 ArrayAdapter<String> ad;
	 DatePickerDialog date;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.activity_frag4,container,false);
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
		final Activity act=getActivity();
		final data sell=new data(act);
		s1=(Spinner)act.findViewById(R.id.orderno);
		
		t1=(TextView)act.findViewById(R.id.sno);
		t2=(TextView)act.findViewById(R.id.ccode);
		t3=(TextView)act.findViewById(R.id.icode);
		t4=(TextView)act.findViewById(R.id.qty);
		t5=(EditText)act.findViewById(R.id.rate);
		t6=(EditText)act.findViewById(R.id.date);
		b=(Button)act.findViewById(R.id.button1);
		
		
		Calendar cal=Calendar.getInstance();
		date = new DatePickerDialog(act,new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int y, int m, int d) {
				// TODO Auto-generated method stub
				
				t6.setText(d+"/"+(m+1)+"/"+y);
			}
		}, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));


		t6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				date.show();
			}
		});
		
		SQLiteDatabase db=sell.getReadableDatabase();
		r1=db.rawQuery("Select userid from curuser",null);
		r1.moveToNext();
		sell.close();
		
		 db=sell.getReadableDatabase();
		r=db.rawQuery("Select Count(*) from Sell where userid='"+r1.getString(0)+"'",null);
		r.moveToNext();
		
		 count=Integer.parseInt(r.getString(0));
		count++ ;
		t1.setText(""+count);
		
		al1=new ArrayList<String>();
		al1.add("Select..");
		r=db.rawQuery("Select order_no from Itemorder where userid='"+r1.getString(0)+"'",null);
		if(r.getCount()>0){
		while(!r.isLast())
		{
			r.moveToNext();
			al1.add(r.getString(0));
		}}
		sell.close();
		ad=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,al1);
		s1.setAdapter(ad);
		
		s1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> list, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				check=list.getItemAtPosition(pos)+"";
				if(!check.equals("Select..")) {
				k=Integer.parseInt(list.getItemAtPosition(pos)+"");
			SQLiteDatabase	db=sell.getReadableDatabase();
				r=db.rawQuery("Select * from itemorder where order_no="+k+"" +
						" and userid='"+r1.getString(0)+"'",null);
				r.moveToNext();
				t2.setText(r.getString(1));
				t3.setText(r.getString(2));
				t4.setText(r.getString(3));
				sell.close();}
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
				String a=t5.getText()+"";
				String b=t6.getText()+"";
				String c=t1.getText()+"";
				String d=t2.getText()+"";
				String e=t3.getText()+"";
				String f=t4.getText()+"";
				if(check.equals("Select..") )
				{
					Toast.makeText(act,"Please select an Order no",0).show();
				}
				else	if(a.equals("") | b.equals(""))
				{
					Toast.makeText(act,"Please fill all the fields",0).show();
				}
				else {
					int rate=Integer.parseInt(a);
					int sno=Integer.parseInt(c);
					int qty=Integer.parseInt(f);
					SQLiteDatabase db=sell.getReadableDatabase();
					r=db.rawQuery("Select rate from Item where icode='"+e+"'" +
							" and userid='"+r1.getString(0)+"'",null);
					r.moveToNext();
					if(rate<=(Integer.parseInt(r.getString(0))))
							{
						Toast.makeText(act,"You're not selling the item at profit! The item rate is "+r.getString(0),0).show();
						sell.close();
							}
					else{
						sell.close();
						db=sell.getWritableDatabase();
						db.execSQL("Insert into sell values("+sno+","+k+",'"+d+"','"+e+"'," +
								""+qty+",'"+b+"',"+rate+",'"+r1.getString(0)+"')");
						db.execSQL("Delete from Itemorder where order_no="+k+" " +
								"and userid='"+r1.getString(0)+"'");
						sell.close();
						
						db=sell.getReadableDatabase();
						r=db.rawQuery("Select qty from item where icode='"+e+"' " +
								"and userid='"+r1.getString(0)+"'",null);
						r.moveToNext();
						qty=(Integer.parseInt(r.getString(0)))-qty;
						sell.close();
						db=sell.getWritableDatabase();
												
						db.execSQL("Update Item set qty="+qty+" where icode='"+e+"' " +
								"and userid='"+r1.getString(0)+"'");
						Toast.makeText(act,"Sell log  added succesfully!",0).show();
						sell.close();
						
						
						db=sell.getReadableDatabase();
						r=db.rawQuery("Select Count(*) from Sell where userid='"+r1.getString(0)+"'",null);
						r.moveToNext();
						 count=Integer.parseInt(r.getString(0));
						count++ ;
						t1.setText(count+"");
						
						t2.setText("");
				      	t3.setText("");
				      	t4.setText("");
				      	t5.setText("");
				      	t6.setText("");
				      	al1=new ArrayList<String>();
						al1.add("Select..");
						r=db.rawQuery("Select order_no from Itemorder where userid='"+r1.getString(0)+"'",null);
						if(r.getCount()>0){
						while(!r.isLast())
						{
							r.moveToNext();
							al1.add(r.getString(0));
						}}
						
						ad=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,al1);
						s1.setAdapter(ad);
				      	sell.close();
					}
				}
			}
		});
		
			super.onStart();
		}
		
		
	}
