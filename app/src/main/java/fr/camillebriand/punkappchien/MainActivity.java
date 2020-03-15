package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import fr.camillebriand.punkappchien.net.FetchPunkAPITask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.findViewById(R.id.main_activity_dialog_button).setOnClickListener(this);
		
		new FetchPunkAPITask(this).execute();
	}
	
	@Override
	public void onClick(View v) {
		new FetchPunkAPITask(this).execute();
	}
}
