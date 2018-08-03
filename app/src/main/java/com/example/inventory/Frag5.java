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
import android.widget.TextView;

public class Frag5 extends Fragment{

	TextView t1,t2;
Cursor r,r1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_frag5,container,false);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
	final Activity act=getActivity();
		t1=(TextView)act.findViewById(R.id.pending);
		t2=(TextView)act.findViewById(R.id.stock);
		data rep=new data(act);
		SQLiteDatabase db=rep.getReadableDatabase();
		r1=db.rawQuery("Select userid from curuser",null);
		r1.moveToNext();
		r=db.rawQuery("Select count(*) from Itemorder " +
				"where userid='"+r1.getString(0)+"'",null);
		r.moveToNext();
		if(Integer.parseInt(r.getString(0))==0)
			t1.setText("You have 0 orders!");
		else
		t1.setText(r.getString(0)+" item orders are still pending!");
		r=db.rawQuery("Select count(*) from Item where qty>0 " +
				"and userid='"+r1.getString(0)+"'",null);
		r.moveToNext();
		t2.setText(r.getString(0)+" item/s are available in stock");
		super.onStart();
	}
}