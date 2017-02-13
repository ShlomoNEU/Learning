package com.shne.learning.Mudole;

import android.content.Context;

import com.shne.learning.Arguments.JsonArguments;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shlomo on 04/02/2017.
 */

public class CreditCard {

    private String FourDig;
    private String CardCompany;
    private String Name;
    private Double Amount;
    Context context;

    public CreditCard(Context context) {
        this.context = context;
    }

    private void crateCard(String fourDig, String cardCompany, String name){
        FourDig = fourDig;
        CardCompany = cardCompany;
        Name = name;
        this.Amount = 0.0;
    }

    public String getFourDig() {
        return FourDig;
    }

    public void setFourDig(String fourDig) {
        FourDig = fourDig;
    }

    public String getCardCompany() {
        return CardCompany;
    }

    public void setCardCompany(String cardCompany) {
        CardCompany = cardCompany;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    /**
     *Cheaks all data of card if it can be saved
     * @return boolean true if the card as the basics data
     */
    public boolean isAddAble(){

        if(Name.isEmpty())
            return false;
        else if (CardCompany.isEmpty())
            return false;
        else if (FourDig.isEmpty())
            return false;
        return true;
    }

    public static JSONObject toJson(CreditCard e){
        JSONObject object = new JSONObject();

        String FourDig = e.getFourDig();
        String CardCompany = e.getCardCompany();
        String Name = e.getName();
        Double Amount = e.getAmount();

        try {
            object.put(JsonArguments.Arg_Name,Name);
            object.put(JsonArguments.Arg_Compeny,CardCompany);
            object.put(JsonArguments.Arg_Digets,FourDig);
            object.put(JsonArguments.Arg_Amount,Amount);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return object;
    }



}
