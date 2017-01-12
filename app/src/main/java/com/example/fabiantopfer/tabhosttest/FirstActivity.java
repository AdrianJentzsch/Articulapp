package com.example.fabiantopfer.tabhosttest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fabiantopfer on 18.12.16.
 */
public class FirstActivity extends  Activity{

    private Button addNote;
    private ListView list;
    private ArrayAdapter <String> adapter;
    ArrayList<String> keyArray;
    SharedPreferences speicher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.first_layout );
        InitializeApp();
    }
    private void InitializeApp(){

        speicher = getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);
        System.out.println(speicher.getAll() + " Das ist der Speicher");
        keyArray = new ArrayList<String>();

        //keys.clear();
        int size = speicher.getInt("SizeArray", 0);
        for(int i =0; i < size;i++)
        {
            keyArray.add(speicher.getString("Notiz_"+i, null).toString());
        }

        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, keyArray );
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
    }
    private void startAdd() {
        Intent add = new Intent (FirstActivity.this, add_Notiz.class);
        startActivityForResult(add,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                refreshListView(data);
            }
        }
    }
    void refreshListView(Intent data){
        SharedPreferences.Editor editor = speicher.edit();
        //WENN LÖSCHEN DANN MUSS DAS IN onActivityResult!!!!
        keyArray.add(data.getStringExtra("TheKey").toString());

        for (int i = 0; i < keyArray.size(); i++){
            editor.remove("Notiz_"+i);
            editor.putString(("Notiz_" + i), keyArray.get(i));
         }
        editor.putInt("SizeArray", keyArray.size());
        editor.commit();
        adapter.notifyDataSetChanged();
     }

}

