package com.hu.huattendens;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.preference.EditTextPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dialog_manual extends Dialog {

    static String id,lecture_material,countstr;
    Context context;
    FirebaseDatabase database;
    Button submit;
    EditText stid;
    int count;
    public dialog_manual(Context context, String id,String lecture_material) {
        super(context);
        this.context = context;
        this.id = id;
        this.lecture_material = lecture_material;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_manual);
        submit = findViewById(R.id.submit);
        stid = findViewById(R.id.stid);
        database = FirebaseDatabase.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{

                            count = dataSnapshot.child(lecture_material).child("count")
                                    .getValue(Integer.class);
                            System.out.println(count);

                        }

                        catch (Exception e){
                            count = 0;

                        }
//                        try{
//                            count =Integer.parseInt(countstr);
//                        }
//                        catch (NumberFormatException e){
//                            count =0;
//                        }

                        database.getReference(id).child(lecture_material).child("count")
                                .setValue(++count);
                        database.getReference(id).child(lecture_material).child(String.valueOf(count))
                                .setValue(stid.getText().toString());
                        Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();
                        dialog_manual.this.cancel();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context,"failed please try again",Toast.LENGTH_LONG).show();
                        dialog_manual.this.cancel();

                    }
                });


            }
        });

    }
}
