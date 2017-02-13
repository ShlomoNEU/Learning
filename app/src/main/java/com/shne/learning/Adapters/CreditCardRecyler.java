package com.shne.learning.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shne.learning.Activity.MainActivity;
import com.shne.learning.Mudole.CreditCard;
import com.shne.learning.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shlomo on 14/01/2017.
 */

public class CreditCardRecyler extends RecyclerView.Adapter<CreditCardRecyler.MyViewHolder> implements Filterable{


    Context context;
    private List<CreditCard> CardList;
    private List<CreditCard> OrignalCard;
    private MainActivity activity;


    public CreditCardRecyler(Context context){
        this.context = context;
        CardList = new ArrayList<>();
        OrignalCard = new ArrayList<>();
    }

    public CreditCardRecyler(Context context, List<CreditCard> CardList) {
        this.CardList = CardList;
        this.OrignalCard = new ArrayList<>();
        this.OrignalCard.addAll(CardList);
        this.context = context;
    }

    public List<CreditCard> getCardList() {
        return CardList;
    }

    public void setCardList(List<CreditCard> cardList) {
        CardList = cardList;
    }

    public void addCard(CreditCard e) {
        CardList.add(e);
        OrignalCard.add(e);
        notifyItemInserted(CardList.size());
    }

    public void removeCard(int position) {
        CardList.remove(position);
        OrignalCard.remove(position);
        notifyItemRemoved(position);

    }

    public void addCard(CreditCard e,int index){
        CardList.add(index, e);
        OrignalCard.add(index, e);
        notifyItemChanged(index);
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView FourDig,CardCompany,Name,Amount;

        public MyViewHolder(View itemView) {
            super(itemView);
            FourDig = (TextView) itemView.findViewById(R.id.Card_fourdigits);
            CardCompany = (TextView) itemView.findViewById(R.id.Card_card_compeny);
            Name = (TextView) itemView.findViewById(R.id.Card_Title);
            Amount = (TextView) itemView.findViewById(R.id.Card_amount);
            final MyViewHolder viewHolder = this;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.openTransactionFragment(CardList.get(viewHolder.getAdapterPosition()).getName()+".card");
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int loc = viewHolder.getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            removeCard(loc);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    builder.setMessage(R.string.delete);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return false;
                }
            });

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item,parent,false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.FourDig.setText(CardList.get(position).getFourDig());
        holder.CardCompany.setText(CardList.get(position).getCardCompany());
        holder.Name.setText(CardList.get(position).getName());
        holder.Amount.setText(String.valueOf(CardList.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }



    @Override
    public Filter getFilter() {
        return new CardFilter(this, CardList);
    }



    private static class CardFilter extends Filter {

        private final CreditCardRecyler adapter;

        private final List<CreditCard> originalList;

        private final List<CreditCard> filteredList;

        private CardFilter(CreditCardRecyler adapter, List<CreditCard> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(adapter.OrignalCard);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final CreditCard Card : originalList) {
                    if (Card.getName().toLowerCase().contains(filterPattern) ||
                            Card.getCardCompany().toLowerCase().contains(filterPattern) ||
                            Card.getFourDig().toLowerCase().contains(filterPattern)||
                            String.valueOf(Card.getAmount()).toLowerCase().contains(filterPattern)) {
                        filteredList.add(Card);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.CardList.clear();
            adapter.CardList.addAll((ArrayList<CreditCard>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    public List<CreditCard> getList(){
        return CardList;
    }

}
