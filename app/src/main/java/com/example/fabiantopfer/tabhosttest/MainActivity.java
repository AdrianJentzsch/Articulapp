package com.example.fabiantopfer.tabhosttest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.TabActivity;
import android.widget.TabHost;
import android.content.Intent;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        //TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.addTab(tabHost.newTabSpec("first").setIndicator("Notizen").setContent(new Intent(this  ,FirstActivity.class )));
        tabHost.addTab(tabHost.newTabSpec("second").setIndicator("Memos").setContent(new Intent(this , SecondActivity.class )));
        tabHost.setCurrentTab(0);


    }

    
}
