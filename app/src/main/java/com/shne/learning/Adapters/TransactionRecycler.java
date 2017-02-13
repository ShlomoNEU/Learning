package com.shne.learning.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shne.learning.Mudole.Transactions;
import com.shne.learning.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Shlomo on 11/02/2017.
 */

public class TransactionRecycler extends RecyclerView.Adapter<TransactionRecycler.TransactionHolder> {

    private  List<Transactions> transactionsList;
    public TransactionRecycler(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item,parent,false);
        return new TransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

        String date = simpleDateFormat.format(transactionsList.get(position).getDate());
        holder.Category.setText(transactionsList.get(position).getCategory());
        holder.Name.setText(transactionsList.get(position).getName());
        holder.mDate.setText(date);
        holder.Amount.setText(String.valueOf(transactionsList.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder{
        TextView Name,mDate,Amount,Category;
        public TransactionHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.Trans_TX_NAME);
            mDate = (TextView) itemView.findViewById(R.id.Trans_TX_DATE);
            Amount = (TextView) itemView.findViewById(R.id.Trans_TX_AMOUNT);
            Category = (TextView) itemView.findViewById(R.id.Trans_TX_CATEGORY);


        }
    }

}
