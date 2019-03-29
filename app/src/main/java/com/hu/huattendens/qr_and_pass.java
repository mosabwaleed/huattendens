package com.hu.huattendens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class qr_and_pass extends AppCompatActivity {
    String TAG = "GenerateQRCode";
    EditText edtValue;
    ImageView qrImage;
    Button generate,manual;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    TextView pass;
    FirebaseDatabase database;
    SharedPreference sharedPreference;
    ArrayList<info>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_and_pass);
        edtValue = findViewById(R.id.lecture);
        generate = findViewById(R.id.generate);
        qrImage = findViewById(R.id.imageView2);
        pass = findViewById(R.id.pass);
        database = FirebaseDatabase.getInstance();
        sharedPreference = new SharedPreference();
        arrayList=new ArrayList<>();
        arrayList=sharedPreference.getFavorites(qr_and_pass.this);
        manual = findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_manual dialog_manual = new dialog_manual(qr_and_pass.this,arrayList.get(0).getId(),intent("name")+"_"+edtValue.getText().toString().trim());
                dialog_manual.show();
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValue = GetPassword();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    edtValue.setError("Required");
                }
                pass.setText(inputValue);
            }
        });
        }
        public String intent (String key){
        return getIntent().getStringExtra(key);
        }
    public String GetPassword(){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < 3; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        database.getReference(arrayList.get(0).getId()).child(intent("name")+"_"+edtValue.getText().toString().trim()).child("pass_lecture")
                .setValue(stringBuilder.toString());

        return stringBuilder.toString();
    }
    }

