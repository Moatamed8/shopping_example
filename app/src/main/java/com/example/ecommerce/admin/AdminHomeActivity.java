package com.example.ecommerce.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;
import com.example.ecommerce.activity.HomeActivity;
import com.example.ecommerce.activity.MainActivity;
import com.example.ecommerce.seller.SellerLoginActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button logOutBtn,checkOrderBtn,maintainProductsBtn,checkApproveProductBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHomeActivity.this, R.color.colord2));


        logOutBtn=findViewById(R.id.logout_admin_Btn);
        checkOrderBtn=findViewById(R.id.check_order_Btn);
        maintainProductsBtn=findViewById(R.id.maintain_Btn);
        checkApproveProductBtn=findViewById(R.id.check_approve_order_btn);

        checkApproveProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this, CheckNewProductActivity.class);
                startActivity(intent);
            }
        });




        logOutBtn.setOnClickListener(v -> {
            Intent intent=new Intent(AdminHomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        });
        checkOrderBtn.setOnClickListener(v -> {
            Intent intent=new Intent(AdminHomeActivity.this, AdminNewOrderActivity.class);
            startActivity(intent);
        });

        maintainProductsBtn.setOnClickListener(v -> {
            Intent intent=new Intent(AdminHomeActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("Admin","Admin");
            startActivity(intent);


        });
    }
}