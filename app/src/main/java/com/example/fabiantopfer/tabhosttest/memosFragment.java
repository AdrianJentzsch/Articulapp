package com.example.fabiantopfer.tabhosttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class memosFragment extends Fragment {
    int clicks;

    ImageButton mic ;
    private ListView list;
    private ArrayAdapter<String> adapterMemos;
    static ArrayList<String> keyArrayMemos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_memos, container, false);


        clicks = 1;
        mic = (ImageButton) v.findViewById(R.id.mic);
        mic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                micPressed();
            }

        });




        list = (ListView) v.findViewById(R.id.listViewMemos);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Intent showNote = new Intent();
              //  showNote.setClass(getContext(), ShowNote.class);
              //  showNote.putExtra("Key",keyArray.get(i));
              //  showNote.putExtra("ArrayPosition",i);
               // startActivityForResult(showNote,2);
            }
        });
        keyArrayMemos = new ArrayList<String>();
        keyArrayMemos.add(0,"Hallo");
        adapterMemos = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, keyArrayMemos );
        adapterMemos.notifyDataSetChanged();
        list.setAdapter(adapterMemos);
        return v;
    }

    void micPressed(){

        //First Time Click on Mic
        if ( clicks == 1){
            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstop));
            //TODO: Aufnahmefunktion hier einfügen




            System.out.println("1");
            clicks = 2;
        }

        //Second Time CLick on Mic

        else {
            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstart));

            //TODO: Speicherfunktion hier einfügen





            System.out.println("2");
            clicks = 1;
        }





    }



}
