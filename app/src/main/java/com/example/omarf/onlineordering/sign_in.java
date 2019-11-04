package com.example.omarf.onlineordering;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omarf.onlineordering.Common.Common;
import com.google.android.gms.signin.SignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_in extends AppCompatActivity {
    Button sign_in;
    EditText editPhone, EditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        View backgroundimage = findViewById(R.id.background_2);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(140);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/sea.ttf");
        sign_in= findViewById(R.id.sign_in2);
        sign_in.setTypeface(typeface2);
        EditPass = findViewById(R.id.editPass);
        editPhone = findViewById(R.id.editPhone);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ProgressDialog progressDialog = new ProgressDialog(sign_in.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

               table_user.addValueEventListener(new ValueEventListener() {


                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       ////check existence of the user in the database
                       if (dataSnapshot.child(editPhone.getText().toString()).exists()) {

                           progressDialog.dismiss();
                           User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                           user.setPhone(editPhone.getText().toString());
                           if (user.getPassword().equals(EditPass.getText().toString())) {
                               Intent intent = new Intent(sign_in.this, home_1.class);
                               Common.user = user;
                               startActivity(intent);
                               finish();
                           } else {
                               Toast.makeText(sign_in.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                           }
                       }

                        else
                       {
                           Toast.makeText(sign_in.this, "User does not exist", Toast.LENGTH_SHORT).show();
                       }


                   }
                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

            }
        });
    }
}
