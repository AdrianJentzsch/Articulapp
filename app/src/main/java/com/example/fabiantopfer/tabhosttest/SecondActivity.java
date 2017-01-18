package com.example.fabiantopfer.tabhosttest;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
/**
 * Created by fabiantopfer on 18.12.16.
 */
public class SecondActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.second_layout );
        if (savedInstanceState == null) {
            Fragment fragmentDemo = (Fragment)getSupportFragmentManager().findFragmentById(R.id.memosFragment);
        }
    }
}