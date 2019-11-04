package com.example.omarf.onlineordering.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.omarf.onlineordering.Order;
import com.example.omarf.onlineordering.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cartName, txt_price;
    public ImageView imgCartCount;

    public void setTxt_cartName(TextView txt_cartName) {
        this.txt_cartName = txt_cartName;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cartName = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_Price);
        imgCartCount = itemView.findViewById(R.id.cart_item_count);

    }

    @Override
    public void onClick(View view) {

    }
}
    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        private List<Order> orderList;
        private Context context;


        public CartAdapter(List<Order> orderList, Context context) {
            this.orderList = orderList;
            this.context = context;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
            return new CartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

            TextDrawable drawable = TextDrawable.builder().buildRound("" + orderList.get(position).getQuantity(), Color.RED);
            holder.imgCartCount.setImageDrawable(drawable);
            Locale locale = new Locale("en", "US");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            int price = (Integer.parseInt(orderList.get(position).getPrice())) * (Integer.parseInt(orderList.get(position).getQuantity()));
            holder.txt_price.setText(numberFormat.format(price));
            holder.txt_cartName.setText(orderList.get(position).getProductName());


        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }
    }
