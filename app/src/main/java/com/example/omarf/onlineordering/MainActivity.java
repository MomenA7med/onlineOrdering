package com.example.omarf.onlineordering;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editPhone, EditPass;
    Button sign_up, sign_in;
    TextView textSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////////////////////////////
        // maybe used later
        setContentView(R.layout.activity_main);


        /////////////////////////////////////////
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        textSlogan = findViewById(R.id.txtSlogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/sea.ttf");
        textSlogan.setTypeface(typeface);
        sign_in.setTypeface(typeface2);
        sign_up.setTypeface(typeface2);



        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, sign_up.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, sign_in.class);
                startActivity(intent);

            }
        });


    }
}