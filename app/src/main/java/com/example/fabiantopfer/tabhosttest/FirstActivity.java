package com.example.fabiantopfer.tabhosttest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by fabiantopfer on 18.12.16.
 */
public class FirstActivity extends  Activity{

    private Button addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.first_layout );

        InitializeApp();


    }



    private void InitializeApp(){

        addNote = (Button)findViewById(R.id.add);
        addNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAdd();
            }
        });

        //  String Thekey = getIntent().getExtras().getString("TheKey");
        //  System.out.println("FirstActivity KEy " + Thekey);




    }
    private void startAdd() {
        Intent add = new Intent (FirstActivity.this, add_Notiz.class);
        startActivity(add);
    }
}

