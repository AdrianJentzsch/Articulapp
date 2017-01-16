package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by fabiantopfer on 13.01.17.
 */

public class ShowNote extends Activity {

    private String note;
    private String noteKey;
    private String sharetext;
    private TextView tv_ShowNote;
    private Button delete;
    private Button edit;
    private Button share;
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
        edit = (Button) findViewById(R.id.edit_Note);
        share = (Button) findViewById(R.id.share_Note);


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
                 deleteAlert();
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editNote();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharetext = tv_ShowNote.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharetext);
                sendIntent.setType("text/plain");

                startActivity(Intent.createChooser(sendIntent, "share using"));

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

    private void deleteAlert() {
        // ALERT
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this );
        builder1.setMessage("DELETE");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "SURE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteNote(noteKey, position);
                    }
                });

        builder1.setNegativeButton(
                "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void editNote(){
        Intent editNote = new Intent();
        editNote.setClass(getApplicationContext(), EditNote.class);
        editNote.putExtra("Key",noteKey);
        editNote.putExtra("ArrayPosition",position);
        startActivityForResult(editNote,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = getIntent();
                noteKey = intent.getStringExtra("Key");
                position = intent.getIntExtra("ArrayPosition", 0);
                note = intent.getStringExtra("Note");
                SharedPreferences.Editor editor = speicher.edit();
                editor.putString(noteKey,note);

                Intent back = new Intent(ShowNote.this, MainActivity.class);
                back.putExtra("ThePosition", position);
                back.putExtra("TheKey",noteKey);
                setResult(Activity.DEFAULT_KEYS_SHORTCUT, back);
                finish();


            }
        }


    }

}
