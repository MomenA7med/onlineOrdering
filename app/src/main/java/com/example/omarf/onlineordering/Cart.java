package com.example.omarf.onlineordering;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarf.onlineordering.Common.Common;
import com.example.omarf.onlineordering.Database.Database;
import com.example.omarf.onlineordering.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference requests;

    TextView textTotalPrice;
    Button buttonPlace;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        requests= firebaseDatabase.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice = findViewById(R.id.total);
        buttonPlace = findViewById(R.id.btnPlaceOrder);

        buttonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
                alertDialog.setTitle("One more step!");
                alertDialog.setMessage("Enter your address: ");

                final EditText editText = new EditText(Cart.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(layoutParams);
                alertDialog.setView(editText);
                alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Request request = new Request(Common.user.getPhone(), Common.user.getName(), editText.getText().toString(), textTotalPrice.getText().toString(),cart);

                        //add to firebase
                        requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                        //delete cart
                        new Database(getBaseContext()).deleteCart();
                        Toast.makeText(Cart.this,"Order Placed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }



        });

        loadFood();

    }

    private void loadFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        int total=0;
        for (Order order:cart){
            total+=(Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en", "US");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            textTotalPrice.setText(numberFormat.format(total));
        }

    }
}
