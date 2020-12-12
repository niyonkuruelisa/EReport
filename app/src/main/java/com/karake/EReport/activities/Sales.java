package com.karake.EReport.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karake.EReport.R;
import com.karake.EReport.adapters.ProductAdapter;
import com.karake.EReport.adapters.SalesAdapter;
import com.karake.EReport.helpers.EReportDB_Helper;
import com.karake.EReport.models.Client;
import com.karake.EReport.models.Product;
import com.karake.EReport.models.ProductType;
import com.karake.EReport.models.Sale;
import com.karake.EReport.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity implements SalesAdapter.SalesAdapterListener{
    EReportDB_Helper db_helper = new EReportDB_Helper(this);
    Toolbar toolbar;

    //for list Users
    RecyclerView sales_recyclerView;
    SalesAdapter salesAdapter;
    List<Sale> salesList = new ArrayList<>();
    PrefManager manager;
    Client client = new Client();
    Product product = new Product();
    String selectedClientID,selectedProductID;

    FloatingActionButton fab;
    LinearLayout lnl_list,lnl_add_sales;
    EditText ed_client_id,ed_product_id,ed_quantity,ed_price,ed_paid,ed_remain;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        //new PrefManager(getApplicationContext()).setCurrentClient(0);
//        new PrefManager(getApplicationContext()).setCurrentProduct(0);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sales");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGray));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.colorGray), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sales_recyclerView = findViewById(R.id.recyclerviewSales);
        salesAdapter = new SalesAdapter(getApplicationContext(), salesList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        sales_recyclerView.setLayoutManager(mLayoutManager);
        sales_recyclerView.setItemAnimator(new DefaultItemAnimator());
        sales_recyclerView.setAdapter(salesAdapter);

        lnl_list = findViewById(R.id.lnl_list_of_sales_dispatch);
        lnl_add_sales = findViewById(R.id.lnl_add_sales_dsipatch);
        btn_save = findViewById(R.id.btn_save);
        ed_client_id = findViewById(R.id.client_company_name);
        ed_product_id = findViewById(R.id.product_name);
        ed_quantity = findViewById(R.id.product_quantity);
        ed_price = findViewById(R.id.product_price_total);
        ed_paid = findViewById(R.id.product_price_paid);
        ed_remain = findViewById(R.id.product_price_remain);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle("New Sales Dispatch");
                lnl_add_sales.setVisibility(View.VISIBLE);
            }
        });
        //get Sales
        getAllSales();

        if(new PrefManager(getApplicationContext()).getCurrentClient() > 0){

            client  = db_helper.getClientByID(new PrefManager(getApplicationContext()).getCurrentClient());
            ed_client_id.setText(client.getCompantName());

            selectedClientID= String.valueOf(new PrefManager(getApplicationContext()).getCurrentClient());
        }
        if(new PrefManager(getApplicationContext()).getCurrentProduct() > 0){

            product  = db_helper.getProductByID(new PrefManager(getApplicationContext()).getCurrentProduct());
            ed_product_id.setText(product.getName());

            selectedProductID= String.valueOf(new PrefManager(getApplicationContext()).getCurrentProduct());
        }
        ed_client_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoClient = new Intent(getApplicationContext(), SelectClient.class);
                startActivity(gotoClient);
            }
        });
        ed_product_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProduct = new Intent(getApplicationContext(), SelectProduct.class);
                startActivity(gotoProduct);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sale sale = new Sale();
                int total = 0;
                int balance = 0;
                sale.setClient_id(ed_client_id.getText().toString());
                sale.setProduct_id(ed_product_id.getText().toString());
                sale.setQuantity(Integer.parseInt(ed_quantity.getText().toString()));
                total+=(Integer.parseInt(ed_quantity.getText().toString()))*(Integer.parseInt(ed_price.getText().toString()));
                sale.setCurrent_price_id(total);
                balance+=(total - Integer.parseInt(ed_paid.getText().toString()));

                sale.setPrice_paid(Integer.parseInt(ed_paid.getText().toString()));
                sale.setPrice_remain(balance);

                if(db_helper.createSale(sale)){
                    // SweetAlert
                    Toast.makeText(getApplicationContext(), "Product Sold successful", Toast.LENGTH_SHORT).show();
                    Intent sold = new Intent(getApplicationContext(),Sales.class);
                    Product product = db_helper.getProductByID(Integer.parseInt(ed_product_id.getText().toString()));

                    product.setQuantity(product.getQuantity()  - Integer.parseInt(ed_quantity.getText().toString()));
                    db_helper.updateProduct(product);

                    startActivity(sold);
                    getAllSales();
                    emptyAllEditField();
                    finish();
                    lnl_add_sales.setVisibility(View.GONE);

                }else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void getAllSales() {
        salesList.clear();
        salesList.addAll(db_helper.getAllSales());
        salesAdapter.notifyDataSetChanged();
    }

    private void emptyAllEditField() {
        ed_client_id.setText("");
        ed_product_id.setText("");
        ed_quantity.setText("");
        ed_price.setText("");
        ed_paid.setText("");
        ed_remain.setText("");
    }



    @Override
    public void onClientSelected(Sale sale) {
        Intent intent = new Intent(getApplicationContext(),ProductDetails.class);
        new PrefManager(getApplicationContext()).setCurrentSales(sale.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllSales();
    }
}