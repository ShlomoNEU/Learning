package com.shne.learning.Activity.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shne.learning.Activity.MainActivity;
import com.shne.learning.Adapters.TransactionRecycler;
import com.shne.learning.Mudole.Transactions;
import com.shne.learning.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionView extends Fragment {


    public static String ARG_FilePath = "FilePath";

    public TransactionView() {
        // Required empty public constructor
    }

    public static TransactionView newInstance(String param1, String param2) {
        TransactionView fragment = new TransactionView();
        Bundle args = new Bundle();
        args.putString("param2",param2);
        args.putString("param1",param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    TextView Fmonth,Tmonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: make View for the fragment
        View v  = inflater.inflate(R.layout.fragment_transaction_view, container, false);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();



        toolbar.setVisibility(View.GONE);
        toolbar = (Toolbar) v.findViewById(R.id.Trans_toolbar);
        toolbar.setCollapsible(true);


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.ListRecycleTrans);
        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.AddFabTrans);
        Fmonth = (TextView) v.findViewById(R.id.From_Month_spinner);
        Tmonth = (TextView) v.findViewById(R.id.To_Month_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.months,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        //TODO:Make load transaction from File to adapter
        TransactionRecycler mAdapter = new TransactionRecycler(testData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());



        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 5){
                            fab.hide();
                        }else if (dy < -5) {
                            fab.show();
                        }
                    }

                }
        );

        final Calendar myCalendar = Calendar.getInstance();


        return v;

    }



    List<Transactions> testData(){
        ArrayList rr = new ArrayList();
        for (int x = 0; x<6;x++){
            Transactions r = new Transactions();
            try {
                r.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("11/2/2017 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            r.setName("Renuar");
            r.setCategory("Clothing");
            r.setAmount(125.02);
            rr.add(r);
        }
        return rr;
    }






}
