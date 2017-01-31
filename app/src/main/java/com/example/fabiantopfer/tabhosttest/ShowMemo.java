package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by fabiantopfer on 23.01.17.
 */

public class ShowMemo extends Activity{

    private FloatingActionButton playBtn;
    private MediaPlayer mediaPlayer;
    String memoKey;
    String sep;
    String newFolder;
    File file;
    String path;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.show_memo );
        InitializeApp();
    }



    void InitializeApp() {

        Intent intent = getIntent();
        memoKey = intent.getStringExtra("KeyMemo");
        sep = File.separator;
        newFolder = "MemosDirectory";
        path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + sep + newFolder + sep + memoKey+".3gp" ;
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + sep + newFolder + sep + memoKey+".3gp");
        uri  = Uri.parse(path);




      /*  mediaPlayer = new MediaPlayer();
       try {
           mediaPlayer.setDataSource(path);
           mediaPlayer.prepare();

       }
       catch (IOException e)
       {

       }*/
        playBtn = (FloatingActionButton) findViewById(R.id.playButton);
        playBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                play();
            }

        });
    }

    void play() {

     /*   MediaPlayer mediaPlayer = new MediaPlayer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("testMemo.3gp");
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                System.out.println("File nicht verfügbar");
                try {
                    fis.close();
                } catch (IOException ignore) {
                }
            }

        }
*/




       mediaPlayer = new MediaPlayer();

        System.out.println(path + " PATH");

      // mediaPlayer = MediaPlayer.create(this,uri);
        try {
             mediaPlayer.setDataSource("testMemo.3gp");
             mediaPlayer.prepare();
             mediaPlayer.start();

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
