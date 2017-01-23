package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class memosFragment extends Fragment {
    int clicks;

    ImageButton mic ;
    private ListView list;
    private ArrayAdapter<String> adapterMemos;
    static ArrayList<String> keyArrayMemos;
    private static String audioFile;
    private MediaRecorder mediaRecorder ;


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
                String current = null;
                try {
                    current = new java.io.File( "." ).getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Current dir:"+current);
                String currentDir = System.getProperty("user.dir");
                System.out.println("Current dir using System:" +currentDir);
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



            mediaRecorder = new MediaRecorder();
            //ContentValues values = new ContentValues(3);
            //values.put(MediaStore.MediaColumns.TITLE, audioFile);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/Documents");


            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                System.out.println("Fehler bei prepare");
                e.printStackTrace();
            }
            mediaRecorder.start();


            System.out.println("1");
            clicks = 2;
        }

        //Second Time CLick on Mic

        else {
            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstart));

            //TODO: Speicherfunktion hier einfügen

            mediaRecorder.stop();
            final EditText edittext  = new EditText(getActivity()); ;
            // ALERT
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("SAVE");
            builder1.setCancelable(true);
            builder1.setView(edittext);
            builder1.setPositiveButton(
                    "SURE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            audioFile = edittext.getText().toString();

                            mediaRecorder.reset();
                            mediaRecorder.release();
                        }
                    });

            builder1.setNegativeButton(
                    "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mediaRecorder.reset();
                            mediaRecorder.release();
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();






            System.out.println("2");
            clicks = 1;
        }





    }



}
