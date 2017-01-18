package com.example.fabiantopfer.tabhosttest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fabiantopfer on 18.12.16.
 */
public class FirstActivity extends  AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.first_layout );


         if (savedInstanceState == null) {
            Fragment fragmentDemo = (Fragment)
                    getSupportFragmentManager().findFragmentById(R.id.noteFragment);
        }
     }



}

