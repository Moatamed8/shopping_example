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
import com.example.ecommerce.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {
    private Button loginSellerBtn ;
    private EditText emailInput,passwordInput;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(SellerLoginActivity.this, R.color.colord2));

        emailInput=findViewById(R.id.seller_login_email);
        passwordInput=findViewById(R.id.seller_login_password);
        loginSellerBtn=findViewById(R.id.seller_login_btn);
        loadingBar = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        loginSellerBtn.setOnClickListener(v -> loginSeller());



    }

    private void loginSeller() {
        final String email =emailInput.getText().toString();
        final String password =passwordInput.getText().toString();

        if (!email.equals("")&&!password.equals(""))
        {
            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent in =new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                finish();
                            }
                        }
                    });

    }
        else {
            Toast.makeText(this, "Please complete the login form", Toast.LENGTH_SHORT).show();

        }
    }
}