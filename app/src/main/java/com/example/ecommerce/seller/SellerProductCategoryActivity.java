package com.example.ecommerce.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import com.example.ecommerce.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportsTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        getWindow().setStatusBarColor(ContextCompat.getColor(SellerProductCategoryActivity.this,R.color.colord2));




        tShirts = (ImageView) findViewById(R.id.t_shirts);
        sportsTShirts = (ImageView) findViewById(R.id.sports_t_shirts);
        femaleDresses = (ImageView) findViewById(R.id.female_dresses);
        sweathers = (ImageView) findViewById(R.id.sweathers);

        glasses = (ImageView) findViewById(R.id.glasses);
        hatsCaps = (ImageView) findViewById(R.id.hats_caps);
        walletsBagsPurses = (ImageView) findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView) findViewById(R.id.shoes);

        headPhonesHandFree = (ImageView) findViewById(R.id.headphones_handfree);
        Laptops = (ImageView) findViewById(R.id.laptop_pc);
        watches = (ImageView) findViewById(R.id.watches);
        mobilePhones = (ImageView) findViewById(R.id.mobilephones);

        tShirts.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });


        sportsTShirts.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Sports tShirts");
            startActivity(intent);
        });


        femaleDresses.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Female Dresses");
            startActivity(intent);
        });


        sweathers.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Sweathers");
            startActivity(intent);
        });


        glasses.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Glasses");
            startActivity(intent);
        });


        hatsCaps.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Hats Caps");
            startActivity(intent);
        });



        walletsBagsPurses.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Wallets Bags Purses");
            startActivity(intent);
        });


        shoes.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Shoes");
            startActivity(intent);
        });



        headPhonesHandFree.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "HeadPhones HandFree");
            startActivity(intent);
        });


        Laptops.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Laptops");
            startActivity(intent);
        });


        watches.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Watches");
            startActivity(intent);
        });


        mobilePhones.setOnClickListener(view -> {
            Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Mobile Phones");
            startActivity(intent);
        });
    }
}
