package com.karake.EReport.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karake.EReport.R;
import com.karake.EReport.helpers.EReportDB_Helper;
import com.karake.EReport.models.Client;
import com.karake.EReport.utils.PrefManager;

public class ClientProfile extends AppCompatActivity {
    TextView name,phone,address,company_name,compant_tin;
    Client client;
    EReportDB_Helper db_helper;
    PrefManager manager;
    LinearLayout lnl_call,lnl_address;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        db_helper = new EReportDB_Helper(this);
        manager = new PrefManager(getApplicationContext());
        client = db_helper.getClientByID(manager.getCurrentClient());
        // toolbar info
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(client.getName());
        toolbar.setSubtitle(client.getCompantName());
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorGray));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        name = findViewById(R.id.tv_name);
        phone = findViewById(R.id.tv_phone);
        address = findViewById(R.id.tv_address);
        compant_tin = findViewById(R.id.tv_company_tin);
        company_name = findViewById(R.id.tv_company_name);

        lnl_call = findViewById(R.id.lnl_phone_call);
        lnl_address = findViewById(R.id.lnl_address);

        name.setText(client.getName());
        phone.setText("+25"+client.getPhone());
        address.setText(client.getAddress());
        company_name.setText(client.getCompantName());
        compant_tin.setText("TIN = " + client.getTin());

        lnl_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent();
            }
        });
    }

    private void callIntent(){
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", client.getPhone(), null));
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(call);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_call){
            callIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}