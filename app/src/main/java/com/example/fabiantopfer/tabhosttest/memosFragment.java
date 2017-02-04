package com.example.fabiantopfer.tabhosttest;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class memosFragment extends Fragment {

    private MediaRecorder mediaRecorder ;
    static private String sep;
    static private String newFolder;
    private  File myNewFolder;
    private String outputFile = null;
    private File memo;
    MediaPlayer mediaPlayer;
    Button playBt;
    Button deleteBt;
    ImageButton mic ;
    int clicks;
    Boolean isPlaying;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_memos, container, false);
        mediaPlayer = new MediaPlayer();

        //NEW DIRECTORY ON SD CARD
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        sep = File.separator;
        newFolder = "MemosDirectory";
        myNewFolder = new File(outputFile + sep + newFolder);
        myNewFolder.mkdir();

        isPlaying =false;

        newFile();

        //BUTTONS
        playBt = (Button)v.findViewById(R.id.playButton);
        playBt.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {


                                       if (mediaPlayer.isPlaying()== false) {


                                           try {
                                               mediaPlayer = new MediaPlayer();
                                               mediaPlayer.setDataSource(outputFile + sep + newFolder + sep + "tempMemo.3gp");
                                               mediaPlayer.prepare();
                                               mediaPlayer.start();
                                               isPlaying = true;

                                           } catch (IOException e) {
                                               e.printStackTrace();
                                               System.out.println("FEHLER");
                                           }
                                       }

                                   }
                               });
        deleteBt = (Button)v.findViewById(R.id.deleteButton);
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newFile();
                memo.delete();
                mic.setClickable(true);
            }});
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

        clicks = 1;
        return v;
    }

    void newFile(){
        memo = new File(outputFile + sep + newFolder + sep + "tempMemo.3gp");
    }
    void resetAudioRecorder(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(memo.getPath());
    }
    void micPressed(){

        //First Time Click on Mic
        if ( clicks == 1){
            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstop));
            resetAudioRecorder();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                System.out.println("Fehler bei prepare");
                e.printStackTrace();
            }
            Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_SHORT).show();
             clicks = 2;
        }

        //Second Time CLick on Mic

        else {
            mic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fbstart));
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            clicks = 1;
            mic.setClickable(false);
        }





    }}


