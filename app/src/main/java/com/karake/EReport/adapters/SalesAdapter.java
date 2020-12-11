package com.karake.EReport.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.karake.EReport.R;
import com.karake.EReport.helpers.EReportDB_Helper;
import com.karake.EReport.models.Product;
import com.karake.EReport.models.Sale;

import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder>
        implements Filterable {

    private Context context;
    private List<Sale> sales;
    private List<Sale> salesListFiltered;
    private SalesAdapterListener listener;
    public static  boolean isFiltered = false;
    EReportDB_Helper db_helper;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView client_id,product_id,quantity,quantity_type,total_amount,paid_amount,balance_amount,txt_status;
        public LinearLayout lnl_row_client,lnl_status;

        public MyViewHolder(View view) {
            super(view);
            db_helper = new EReportDB_Helper(context);
            client_id = view.findViewById(R.id.txt_compny_name);
            product_id = view.findViewById(R.id.txt_company_tin);
//            quantity_type = view.findViewById(R.id.txt_client_name);
            total_amount = view.findViewById(R.id.txt_product_total_amount);
            paid_amount = view.findViewById(R.id.txt_product_paid_amount);
            balance_amount = view.findViewById(R.id.txt_product_rest_amount);
//            quantity = view.findViewById(R.id.txt_client_phone);

            lnl_status = view.findViewById(R.id.lnl_status);
            txt_status = view.findViewById(R.id.tv_status);
            lnl_row_client = view.findViewById(R.id.lnl_row_client);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onClientSelected(salesListFiltered.get(getAdapterPosition()));
                }
            });
            lnl_row_client.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send selected contact in callback
                    listener.onClientSelected(salesListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    public SalesAdapter(Context context, List<Sale> sales, SalesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.sales = sales;
        this.salesListFiltered = sales;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_sales, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Sale sale = salesListFiltered.get(position);
        holder.client_id.setText(String.valueOf(sale.getClient_id()));
        holder.product_id.setText(sale.getProduct_id() +"  "+ String.valueOf(sale.getQuantity() +" Items"));
        holder.paid_amount.setText(String.valueOf(sale.getPrice_paid()+ " Frw"));
        holder.balance_amount.setText(String.valueOf(sale.getPrice_remain()+ " Frw"));
        holder.total_amount.setText(String.valueOf(sale.getCurrent_price_id()+ " Frw"));
        if (sale.getCurrent_price_id() > sale.getPrice_paid()){
            holder.lnl_status.setBackground(context.getResources().getDrawable(R.drawable.status_orange_color));
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorOrange));
            holder.txt_status.setText("Pending");
        }else{
            holder.lnl_status.setBackground(context.getResources().getDrawable(R.drawable.status_navy_color));
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.txt_status.setText("Paid");
            holder.balance_amount.setText("0");
        }

    }

    @Override
    public int getItemCount() {
        return salesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    salesListFiltered = sales;
                } else {
                    List<Sale> filteredList = new ArrayList<>();
                    for (Sale row : sales) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
                    }
                    salesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = salesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                salesListFiltered = (ArrayList<Sale>) filterResults.values;
                isFiltered = true;
                notifyDataSetChanged();
            }
        };
    }

    public interface SalesAdapterListener {
        void onClientSelected(Sale sale);
    }
}