package com.karake.EReport.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.karake.EReport.R;

public class MainActivity extends AppCompatActivity {
    CardView lnl_client,lnl_purchased,lnl_store,lnl_product;
    TextView count_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        // prepare initialization
        lnl_client = findViewById(R.id.lnl_client);
        lnl_purchased = findViewById(R.id.lnl_sales);
        lnl_product = findViewById(R.id.lnl_product);
        lnl_store = findViewById(R.id.lnl_other);


        lnl_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent client = new Intent(getApplicationContext(),ClientActivity.class);
                startActivity(client);

            }
        });
        lnl_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stock = new Intent(getApplicationContext(),ProductActivity.class);
                startActivity(stock);

            }
        });

        lnl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sales = new Intent(getApplicationContext(),Sales.class);
                startActivity(sales);

            }
        });


    }
}