package com.example.ecommerce.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerce.Model.AdminOrders;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminNewOrderActivity.this,R.color.colord2));

        orderRef= FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList=findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders>options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderRef,AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder>adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder, int i, @NonNull AdminOrders adminOrders) {
                        adminOrdersViewHolder.userName.setText("Name: "+adminOrders.getName());
                        adminOrdersViewHolder.userPhoneNumber.setText("Phone: "+adminOrders.getPhone());
                        adminOrdersViewHolder.userShippingAddress.setText(" Shipping Address: " + adminOrders.getAddress() + "  " + adminOrders.getCity());
                        adminOrdersViewHolder.userDateTime.setText("Order at: " + adminOrders.getDate() + "  " + adminOrders.getTime());
                        adminOrdersViewHolder.userTotalPrice.setText("Total Amount: "+adminOrders.getTotalAmount());

                        adminOrdersViewHolder.showOrderBtn.setOnClickListener(v -> {
                            String uID = getRef(i).getKey();

                            Intent intent =new Intent(AdminNewOrderActivity.this, AdminUserProductsActivity.class);
                            intent.putExtra("uid",uID);
                            startActivity(intent);
                        });
                        adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                    AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrderActivity.this);
                                    builder.setTitle("Have you shipped order product ?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (i==0){
                                                String uID = getRef(i).getKey();
                                                RemoveOrder(uID);

                                            }if (i==1){

                                            }
                                        }
                                    });
                                    builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return new AdminOrdersViewHolder(view);                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView userName,userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrderBtn;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);

            showOrderBtn=itemView.findViewById(R.id.show_all_products);


        }
    }
    private void RemoveOrder(String uID) {

        orderRef.child(uID).removeValue();
    }
}