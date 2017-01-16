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
 * Created by fabiantopfer on 14.01.17.
 */

public class EditNote extends Activity {

    private String noteKey;
    private String note;
    private int position;
    SharedPreferences speicher;

    private EditText et_editNote;
    private Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.edit_note );

        InititalizeApp();
    }

    void InititalizeApp(){
        speicher = getSharedPreferences("Notizenspeicher", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        noteKey = intent.getStringExtra("Key");
        position = intent.getIntExtra("ArrayPosition", 0);
        note = speicher.getString(noteKey,"Sorry");

        et_editNote = (EditText)findViewById(R.id.et_editNote);
        et_editNote.setText(note);

        apply = (Button) findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
             applyChanges();
            }
        });
    }


   void  applyChanges(){
       final EditText edittext  = new EditText(this);
       edittext.setText(noteKey);
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setMessage("APPlY");
       builder.setCancelable(true);
       builder.setView(edittext);
       builder.setPositiveButton(
               "SURE",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       SharedPreferences.Editor editor = speicher.edit();
                       editor.remove(noteKey);
                       editor.remove("Notiz_"+position);
                       noteKey = edittext.getText().toString();
                       FirstActivity.keyArray.remove(position);
                       FirstActivity.keyArray.add(position,noteKey);
                       editor.putString(noteKey, et_editNote.getText().toString());
                       editor.commit();
                       dialog.cancel();

                       //Back to ShowNote with Key-Value
                       Intent intent = new Intent(EditNote.this, ShowNote.class);
                       intent.putExtra("TheKey", noteKey);
                       intent.putExtra("ThePosition", position);
                       intent.putExtra("Note",et_editNote.getText().toString());
                       setResult(Activity.RESULT_OK, intent);
                       finish();

                    }
               });

       builder.setNegativeButton(
               "NO",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               });

       AlertDialog alert11 = builder.create();
       alert11.show();




    }


}
