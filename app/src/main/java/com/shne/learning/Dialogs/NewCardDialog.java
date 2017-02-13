package com.shne.learning.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shne.learning.Adapters.CreditCardRecyler;
import com.shne.learning.Mudole.CreditCard;
import com.shne.learning.R;

/**
 * Created by Shlomo on 06/02/2017.
 */

public class NewCardDialog extends Dialog implements View.OnClickListener {


    private CreditCard card;
    private CreditCardRecyler mAdapter;
    private View.OnClickListener OkListener,CancelListener;
    private EditText Name,Code,Type;


    public CreditCardRecyler getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(CreditCardRecyler mAdapter) {
        this.mAdapter = mAdapter;
    }

    public NewCardDialog(Context context, CreditCard e,CreditCardRecyler adapter) {
        super(context);
        this.card = e;
        this.mAdapter = adapter;
    }

    public NewCardDialog(Context context, int themeResId, CreditCard e) {
        super(context, themeResId);
    }

    protected NewCardDialog(Context context, boolean cancelable, OnCancelListener cancelListener, CreditCard e) {
        super(context, cancelable, cancelListener);
        this.card = e;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button Cancel,Enable;

        setContentView(R.layout.dialog_add_card);
        Name = (EditText) findViewById(R.id.dialog_ET_Name);
        Code = (EditText) findViewById(R.id.dialog_ET_Code);
        Type = (EditText) findViewById(R.id.dialog_ET_Type);

        Cancel = (Button) findViewById(R.id.dialog_BTN_cancel);
        Enable = (Button) findViewById(R.id.dialog_BTN_add);

        if(CancelListener == null) {
            Cancel.setOnClickListener(this);
        }else{
            Cancel.setOnClickListener(CancelListener);
        }
        if(OkListener == null) {
            Enable.setOnClickListener(this);
        }else{
            Enable.setOnClickListener(OkListener);
        }



    }


    @Override
    public void onClick(View v) {

        if (R.id.dialog_BTN_cancel == v.getId()){
            dismiss();
        }else {
            card.setFourDig(Code.getText().toString());
            card.setCardCompany(Type.getText().toString());
            card.setName(Name.getText().toString());
            card.setAmount(0.0);
            if (card.isAddAble()){
                mAdapter.addCard(card);

                dismiss();
            }
            else
                Toast.makeText(getContext(),"Missing Info",Toast.LENGTH_SHORT).show();
        }
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        CancelListener = cancelListener;
    }

    public  void saveCardtoFile(){

    }
    public void setOkListener(View.OnClickListener okListener) {
        OkListener = okListener;
    }


}
