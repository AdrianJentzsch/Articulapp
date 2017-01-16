package com.example.fabiantopfer.tabhosttest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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

    private Button addNote;
    private ListView list;
    private ArrayAdapter <String> adapter;
    static ArrayList<String> keyArray;
    SharedPreferences speicher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.first_layout );

        InitializeApp();
    }
    private void InitializeApp(){

        //LISTVIEW AND ONCLICKADAPTER
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent showNote = new Intent();
              showNote.setClass(getApplicationContext(), ShowNote.class);
              showNote.putExtra("Key",keyArray.get(i));
              showNote.putExtra("ArrayPosition",i);
              startActivityForResult(showNote,2);
          }
      });

        speicher = getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);
        keyArray = new ArrayList<String>();

        int size = speicher.getInt("SizeArray", 0);
        for(int i =0; i < size;i++)
        {
            keyArray.add(speicher.getString("Notiz_"+i, null).toString());
            System.out.println(keyArray.get(i));

        }
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

        //NEW NOTE
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                deleteKeyArrayFromStorage();
                keyArray.add(data.getStringExtra("TheKey").toString());
                refreshListView(data);
            }
        }

        //SHOW NOTE -> NOTE DELETED
        if (requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                deleteKeyArrayFromStorage();
                keyArray.remove(data.getIntExtra("ThePosition",0));
                refreshListView(data);
            }
        }

        //SHOW NOTE -> NOTE EDITED
        if (requestCode == 2){
            if(resultCode == Activity.DEFAULT_KEYS_SHORTCUT){
                deleteKeyArrayFromStorage();
                refreshListView(data);
            }
        }


    }
    void refreshListView(Intent data){
        SharedPreferences.Editor editor = speicher.edit();
        for (int i = 0; i <keyArray.size(); i++){
            editor.putString(("Notiz_" + i), keyArray.get(i));
         }
        editor.putInt("SizeArray", keyArray.size());
       // editor.clear();
         editor.commit();
        System.out.println(speicher.getAll() + " Speicher");
        adapter.notifyDataSetChanged();
     }

    void deleteKeyArrayFromStorage(){
        SharedPreferences.Editor editor = speicher.edit();

        for (int i = 0; i <keyArray.size(); i++){
            editor.remove("Notiz_" + i);
            editor.commit();
        }

    }

}

