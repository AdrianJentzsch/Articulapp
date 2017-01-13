package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fabiantopfer on 13.01.17.
 */

public class ShowNote extends Activity {

    private String note;
    private String noteKey;
    private TextView tv_ShowNote;
    private Button delete;
    int position;
    SharedPreferences speicher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.show_note );
        InitializeApp();

    }

    void InitializeApp(){
        speicher = getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);

        tv_ShowNote = (TextView) findViewById(R.id.tv_ShowNote);
        delete  = (Button) findViewById(R.id.delete_Note);


        Intent intent = getIntent();
        noteKey = intent.getStringExtra("Key");
        position = intent.getIntExtra("ArrayPosition", 0);
        note = speicher.getString(noteKey,"Sorry");
        tv_ShowNote.setText(note);

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteNote(noteKey, position);
            }
        });
    }



    void deleteNote(String key, int index)
    {
        SharedPreferences.Editor editor = speicher.edit();
        editor.remove(noteKey);
        editor.remove("Notiz_" + position);
        System.out.println(position);
        editor.commit();

       // FirstActivity.keyArray.remove(position);
        Intent back = new Intent(ShowNote.this, MainActivity.class);
        back.putExtra("ThePosition", index);
        setResult(Activity.RESULT_OK, back);
        finish();
    }
}
