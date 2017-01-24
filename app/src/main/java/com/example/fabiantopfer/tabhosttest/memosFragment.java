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
import android.widget.Toast;

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
    SharedPreferences speicher_Memos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_memos, container, false);

        speicher_Memos = this.getActivity().getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);


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

        int size = speicher_Memos.getInt("SizeArrayMemo", 0);
        for(int i =0; i < size;i++)
        {
            keyArrayMemos.add(speicher_Memos.getString("Memo_"+i, null).toString());
            System.out.println(keyArrayMemos.get(i));

        }

        adapterMemos = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, keyArrayMemos );
        adapterMemos.notifyDataSetChanged();
        list.setAdapter(adapterMemos);

        //ContentValues values = new ContentValues(3);
        //values.put(MediaStore.MediaColumns.TITLE, audioFile);

        resetAudioRecorder();
        System.out.println(" Path" + Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + audioFile);



        return v;
    }

    void resetAudioRecorder(){

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + audioFile);

    }
    void micPressed(){

        //First Time Click on Mic
        if ( clicks == 1){

            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstop));
            //TODO: Aufnahmefunktion hier einfügen

            resetAudioRecorder();
            try {

                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                System.out.println("Fehler bei prepare");
                e.printStackTrace();
            }


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
                            boolean keyIsRepeating = false;
                            int size = speicher_Memos.getInt("SizeArrayMemo", 0);


                          for (int i = 0; i < size; i++) {

                                if (audioFile.equals(keyArrayMemos.get(i))) {
                                    Toast.makeText(getActivity(), "Name already taken!", Toast.LENGTH_LONG).show();
                                    keyIsRepeating = true;
                                }

                            }


                            if (keyIsRepeating == false) {
                            refreshListView();
                            keyArrayMemos.add(audioFile);
                            refreshListView();
                            mediaRecorder.reset();
                            mediaRecorder.release();
                            mediaRecorder = null;
                             }
                        }
                    });

            builder1.setNegativeButton(
                    "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mediaRecorder.reset();
                            mediaRecorder.release();
                            mediaRecorder = null;

                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();






            System.out.println("2");
            clicks = 1;
        }





    }

    void refreshListView(){
        SharedPreferences.Editor editor = speicher_Memos.edit();
        for (int i = 0; i <keyArrayMemos.size(); i++){
            editor.putString(("Memo_" + i), keyArrayMemos.get(i));
        }
        editor.putInt("SizeArrayMemo", keyArrayMemos.size());
        editor.commit();
        System.out.println(speicher_Memos.getAll() + " Speicher");
        adapterMemos.notifyDataSetChanged();
    }

    void deleteKeyArrayFromStorage(){
        SharedPreferences.Editor editor = speicher_Memos.edit();

        for (int i = 0; i <keyArrayMemos.size(); i++){
            editor.remove("Memo_" + i);
            editor.commit();
        }

    }

}
