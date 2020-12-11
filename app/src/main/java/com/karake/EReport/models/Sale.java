package com.karake.EReport.models;

public class Sale {
    private int  id;
    private String  client_id;
    private String  product_id;
    private int  quantity;
    private int  quantity_type;
    private int  price_paid;
    private int  price_remain;
    private int  current_price_id;
    private String  status;
    private String  created_at;
    private String  updated_at;

    public Sale() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity_type() {
        return quantity_type;
    }

    public void setQuantity_type(int quantity_type) {
        this.quantity_type = quantity_type;
    }

    public int getPrice_paid() {
        return price_paid;
    }

    public void setPrice_paid(int price_paid) {
        this.price_paid = price_paid;
    }

    public int getPrice_remain() {
        return price_remain;
    }

    public void setPrice_remain(int price_remain) {
        this.price_remain = price_remain;
    }

    public int getCurrent_price_id() {
        return current_price_id;
    }

    public void setCurrent_price_id(int current_price_id) {
        this.current_price_id = current_price_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
