package com.karake.EReport.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karake.EReport.R;
import com.karake.EReport.helpers.EReportDB_Helper;
import com.karake.EReport.models.Client;
import com.karake.EReport.models.Product;
import com.karake.EReport.utils.PrefManager;

public class ProductDetails extends AppCompatActivity {
    TextView name,qty,price,email;
    Product product;
    EReportDB_Helper db_helper;
    PrefManager manager;
    LinearLayout lnl_call,lnl_address,lnl_status;
    TextView txt_status;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        toolbar = findViewById(R.id.toolbar);
        db_helper = new EReportDB_Helper(this);
        manager = new PrefManager(getApplicationContext());
        product = db_helper.getProductByID(manager.getCurrentProduct());
        toolbar.setTitle(product.getName());
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorOrange));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.colorOrange), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db_helper = new EReportDB_Helper(this);
        manager = new PrefManager(getApplicationContext());
        product = db_helper.getProductByID(manager.getCurrentProduct());

        name = findViewById(R.id.tv_name);
        qty = findViewById(R.id.tv_quantity);
        price = findViewById(R.id.tv_price);
        lnl_call = findViewById(R.id.lnl_phone_call);
        lnl_address = findViewById(R.id.lnl_address);
        lnl_status = findViewById(R.id.lnl_status);
        txt_status = findViewById(R.id.tv_status);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        qty.setText(String.valueOf(product.getQuantity()));

        productStatus();
    }

    private void productStatus() {
        if (product.getQuantity() >= 35000){
            lnl_status.setBackground(getResources().getDrawable(R.drawable.status_navy_color));
            txt_status.setTextColor(getResources().getColor(R.color.colorNavy));
            txt_status.setText("Full Stock");
        }else if (product.getQuantity() < 35000 && product.getQuantity() >= 15000){
            lnl_status.setBackground(getResources().getDrawable(R.drawable.status_orange_color));
            txt_status.setTextColor(getResources().getColor(R.color.colorOrange));
            txt_status.setText("Lower Stock");
        }else if (product.getQuantity() < 15000 && product.getQuantity() >= 1000){
            lnl_status.setBackground(getResources().getDrawable(R.drawable.status_red_color));
            txt_status.setTextColor(getResources().getColor(R.color.colorRed));
            txt_status.setText("Critical Low Stock");
        }
    }
}