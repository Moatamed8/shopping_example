package com.example.ecommerce.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SellerRegiserationActivity extends AppCompatActivity {

    private Button sellerLogin ,sellerRegBtn;
    private EditText nameInput,phoneInput,emailInput,passwordInput,addressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_regiseration);
        getWindow().setStatusBarColor(ContextCompat.getColor(SellerRegiserationActivity.this, R.color.colord2));

        mAuth=FirebaseAuth.getInstance();

        sellerLogin=findViewById(R.id.already_have_account_btn);
        sellerRegBtn=findViewById(R.id.seller_register_btn);
        nameInput=findViewById(R.id.seller_name);
        phoneInput=findViewById(R.id.seller_phone);
        emailInput=findViewById(R.id.seller_email);
        addressInput=findViewById(R.id.seller_address);
        passwordInput=findViewById(R.id.seller_password);
        loadingBar = new ProgressDialog(this);

        sellerLogin.setOnClickListener(v -> {
            Intent in =new Intent(SellerRegiserationActivity.this,SellerLoginActivity.class);
            startActivity(in);
        });
        sellerRegBtn.setOnClickListener(v -> registerSeller());



    }

    private void registerSeller() {

        String name =nameInput.getText().toString();
        String email =emailInput.getText().toString();
        String phone =phoneInput.getText().toString();
        String password =passwordInput.getText().toString();
        String address =addressInput.getText().toString();

        if (!name.equals("")&&!email.equals("")&&!phone.equals("")&&!password.equals("")&&!address.equals(""))
        {
            loadingBar.setTitle("Create Seller Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            final DatabaseReference rootRef;
                            rootRef= FirebaseDatabase.getInstance().getReference();

                            String sid=mAuth.getCurrentUser().getUid();
                            HashMap<String ,Object> sellerMap=new HashMap<>();
                            sellerMap.put("sid",sid);
                            sellerMap.put("phone",phone);
                            sellerMap.put("email",email);
                            sellerMap.put("address",address);
                            sellerMap.put("name",name);

                            rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            loadingBar.dismiss();
                                            Toast.makeText(SellerRegiserationActivity.this, "You are Register Successfully", Toast.LENGTH_SHORT).show();
                                            Intent in =new Intent(SellerRegiserationActivity.this, SellerHomeActivity.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(in);
                                            finish();
                                        }
                                    });
                        }
                    });
        }
        else {
            Toast.makeText(this, "Please complete the Registration form", Toast.LENGTH_SHORT).show();

        }

    }
}