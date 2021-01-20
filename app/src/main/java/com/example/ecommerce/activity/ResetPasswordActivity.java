package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.prevalent.Prevalent;
import com.example.ecommerce.seller.SellerRegiserationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check= "";
    private TextView pageTitle ,titleQusetions;
    private EditText phoneNumber,question1,question2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getWindow().setStatusBarColor(ContextCompat.getColor(ResetPasswordActivity.this, R.color.colord2));


        check=getIntent().getStringExtra("check");

        pageTitle=findViewById(R.id.page_title);
        titleQusetions=findViewById(R.id.title_questions);
        phoneNumber=findViewById(R.id.find_phone_number);
        question1=findViewById(R.id.qusetion1);
        question2=findViewById(R.id.question2);
        verifyButton=findViewById(R.id.verfiy_btn);





    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);


        if (check.equals("settings")){

            pageTitle.setText("Set Questions");
            titleQusetions.setText("Please Set Answer For The Following Security Questions?");
            verifyButton.setText("Set");

            DisplayPerviousAnswers();
            verifyButton.setOnClickListener(v -> setAnswer());

        }
        if (check.equals("login")){
            phoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });



        }
    }

    private void verifyUser() {
         String phone=phoneNumber.getText().toString();
         String answer1=question1.getText().toString().toLowerCase();
         String answer2=question2.getText().toString().toLowerCase();

         if (!phone.equals("") && !answer1.equals("") &&!answer2.equals("")){
             DatabaseReference ref = FirebaseDatabase.getInstance()
                     .getReference()
                     .child("Users")
                     .child(phone);
             ref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     if (dataSnapshot.exists()){
                         String mphone=dataSnapshot.child("phone").getValue().toString();
                         if (dataSnapshot.hasChild("Security Questions")){
                             String ans1 =dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                             String ans2 =dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                             if(!ans1.equals(answer1)){
                                 Toast.makeText(ResetPasswordActivity.this, "your first answer is wrong", Toast.LENGTH_SHORT).show();
                             }
                             else if(!ans2.equals(answer2)){
                                 Toast.makeText(ResetPasswordActivity.this, "your second answer is wrong", Toast.LENGTH_SHORT).show();
                             }
                             else {
                                 AlertDialog.Builder builder=new AlertDialog.Builder(ResetPasswordActivity.this);
                                 builder.setTitle("New Password ");

                                 final EditText newPassword =new EditText(ResetPasswordActivity.this);
                                 newPassword.setHint("Write New Password here...");
                                 builder.setView(newPassword);

                                 builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         if (!newPassword.getText().toString().equals("")){
                                             ref.child("password").setValue(newPassword.getText().toString())
                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             if (task.isSuccessful()){
                                                                 Toast.makeText(ResetPasswordActivity.this, "Password change successfully", Toast.LENGTH_SHORT).show();
                                                                 Intent intent =new Intent(ResetPasswordActivity.this,LoginActivity.class);
                                                                 startActivity(intent);
                                                             }
                                                         }
                                                     });
                                         }
                                     }
                                 });
                                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                     }
                                 });
                                 builder.show();
                             }
                         }
                         else {
                             Toast.makeText(ResetPasswordActivity.this, "Use Have not set Security Question.", Toast.LENGTH_SHORT).show();
                         }
                     }
                     else {
                         Toast.makeText(ResetPasswordActivity.this, "This phone number no exists", Toast.LENGTH_SHORT).show();
                     }

                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

         }else {
             Toast.makeText(this, "Please complete the Form.", Toast.LENGTH_SHORT).show();
         }




    }

    private void setAnswer(){
        String answer1=question1.getText().toString().toLowerCase();
        String answer2=question2.getText().toString().toLowerCase();

        if (question2.equals("") &&question1.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Please Answer both Questions", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());
            ref.child("Security Questions");

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "you have set security question successfully", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(ResetPasswordActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }

    }

    private void DisplayPerviousAnswers(){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String ans1 =dataSnapshot.child("answer1").getValue().toString();
                    String ans2 =dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}