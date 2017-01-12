package com.example.fabiantopfer.tabhosttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fabiantopfer on 20.12.16.
 */

public class add_Notiz  extends Activity{
    private Button save;
    private EditText etNotiz;
    SharedPreferences speicher;
    ArrayList<String> keyList = new ArrayList<>();
    private String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notiz);

        InitializeApp();

    }

    private void InitializeApp(){

        etNotiz = (EditText) findViewById(R.id.eT_Notiz);
        save = (Button) findViewById(R.id.save);
        speicher = getSharedPreferences("Notizenspeicher",Context.MODE_PRIVATE);




        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveAlert();
            }
        });
    }
    private void saveAlert()
    {
        final EditText edittext = new EditText(this);

        // ALERT
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("SAVE");
        builder1.setCancelable(true);
        builder1.setView(edittext);


        builder1.setPositiveButton(
                "SURE",
                new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {

                         // SPEICHERN UND LISTVIEW
                         key = new String();
                         key = edittext.getText().toString();
                         SharedPreferences.Editor editor = speicher.edit();
                         editor.putString(key, etNotiz.getText().toString());
                         editor.commit();

                         add(key);
                         //keyList.add(key);
                        // System.out.println(key + " Schlüssel");
                         System.out.println(keyList.size());

                         editor.putInt("Status_size",keyList.size());

                         for (int i = 0; i< keyList.size(); i++){
                             //editor.remove("Status_"+i);
                             editor.putString(("Notiz_" + i).toString(), keyList.get(i));
                         }
                         editor.commit();

                         //editor.putStringSet("KeyList", keyList);
                         //editor.commit();
                         dialog.cancel();

                         //Back to MainActivity with Key-Value
                         Intent intent = new Intent(add_Notiz.this, MainActivity.class);
                         intent.putExtra("TheKey", key);
                         setResult(Activity.RESULT_OK, intent);
                         finish();
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


    public ArrayList add ( String string){
        keyList.add(string);
        return keyList;
    }

}
