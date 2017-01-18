package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class noteFragment extends Fragment {

    private Button addNote;
    private ListView list;
    private ArrayAdapter <String> adapter;
    static ArrayList<String> keyArray;
    SharedPreferences speicher;
    Toolbar toolbar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_note, container, false);

      /* toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/


       list = (ListView) v.findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showNote = new Intent();
                showNote.setClass(getContext(), ShowNote.class);
                showNote.putExtra("Key",keyArray.get(i));
                showNote.putExtra("ArrayPosition",i);
                startActivityForResult(showNote,2);
            }
        });
        addNote = (Button)v.findViewById(R.id.add);
        addNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAdd();
            }

        });

        speicher = this.getActivity().getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);
        keyArray = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, keyArray );
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);



        int size = speicher.getInt("SizeArray", 0);
        for(int i =0; i < size;i++)
        {
            keyArray.add(speicher.getString("Notiz_"+i, null).toString());
            System.out.println(keyArray.get(i));

        }
        return v;
      }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


     }


    private void startAdd() {
        Intent add = new Intent (getActivity(), add_Notiz.class);
        startActivityForResult(add,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

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
