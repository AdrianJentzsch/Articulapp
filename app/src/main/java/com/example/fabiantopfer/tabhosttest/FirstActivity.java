package com.example.fabiantopfer.tabhosttest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiantopfer on 18.12.16.
 */
public class FirstActivity extends  Activity{

    private Button addNote;
    private ListView list;
    private ArrayAdapter <String> adapter;
    final List<String> keyList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.first_layout );

        InitializeApp();


    }



    private void InitializeApp(){

        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,keyList );
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
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
        startActivityForResult(add,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){

                System.out.println(data.getStringExtra("TheKey").toString() + " ListView");
                refreshListView(data);

            }
        }
    }


    void refreshListView(Intent data){
        keyList.add(data.getStringExtra("TheKey").toString());
        adapter.notifyDataSetChanged();
        list.invalidateViews();
    }

}

