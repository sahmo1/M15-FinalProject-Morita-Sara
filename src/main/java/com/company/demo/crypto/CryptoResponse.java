package com.company.demo.crypto;

public class CryptoResponse {

    /*
    *
    *CryptoResponse class should represent only the key-value pairs for Name, Id, and Price in USD
    * */

    //name of a crypto
    private String name;

    //ID
    private String asset_id;

    //Price in USD
    private double price_usd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public double getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(double price_usd) {
        this.price_usd = price_usd;
    }
}




