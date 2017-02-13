package com.shne.learning.Activity.Fragments;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shne.learning.Activity.MainActivity;
import com.shne.learning.Adapters.CreditCardRecyler;
import com.shne.learning.Arguments.JsonArguments;
import com.shne.learning.Dialogs.NewCardDialog;
import com.shne.learning.Mudole.CreditCard;
import com.shne.learning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment  {



    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //All the Setting Before Loading View
        }


    }

    private RecyclerView recyclerView;
    private CreditCardRecyler mAdapter;
    private Paint p = new Paint();
    private FloatingActionButton AddCardBtn;
    private EditText Type,Name,Code;
    private JSONArray cardJson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.ListRecycle);
        AddCardBtn = (FloatingActionButton) v.findViewById(R.id.AddFab);
        ((MainActivity)getActivity()).getToolbar().setVisibility(View.VISIBLE);

        AddCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCardDialog cardDialog = new NewCardDialog(getContext(),new CreditCard(getActivity().getApplicationContext()),mAdapter);
                cardDialog.show();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new CreditCardRecyler(getContext(),((MainActivity)getActivity()).getCards());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                                             @Override
                                             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                 super.onScrolled(recyclerView, dx, dy);
                                                 if (dy > 5){
                                                     AddCardBtn.hide();
                                                 }else if (dy < -5) {
                                                     AddCardBtn.show();
                                                 }
                                             }

                                         }
        );

        mAdapter.setActivity((MainActivity) getActivity());
        return v;
    }


    void initSwipes(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                final CreditCard e = mAdapter.getCardList().get(position);

                if(direction == ItemTouchHelper.LEFT){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mAdapter.removeCard(position);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mAdapter.removeCard(position);
                            mAdapter.addCard(e,position);
                        }
                    });

                    builder.setMessage(R.string.delete);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_remove_circle_outline_black_24dp);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void SearchInList(String SearchTerm){
        mAdapter.getFilter().filter(SearchTerm);
    }


    /**
     * Filling False Data
     * For Testing ONLY
     * @return List<CreditCard>
     */
    List<CreditCard> CardMaker(){
        ArrayList<CreditCard> cards = new ArrayList<>();
        for (int x =0;x<6;x++){
            cards.add(new CreditCard(getActivity().getApplicationContext()));
        }
        return cards;
    }

    public Bitmap LoadImageFromWebOperations(String url) {
        try {
            return BitmapFactory.decodeStream((InputStream)new URL(url.replace(" ","")).getContent());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        saveListToFile();
    }
    void saveListToFile(){

        JSONArray array = new JSONArray();
        int x = 0;
        for (CreditCard card:mAdapter.getList()){
            JSONObject object = new JSONObject();
            try {
                object.put(JsonArguments.Arg_Name,card.getName());
                object.put(JsonArguments.Arg_Amount,card.getAmount());
                object.put(JsonArguments.Arg_Digets,card.getFourDig());
                object.put(JsonArguments.Arg_Compeny,card.getCardCompany());
                array.put(x,object);
                x++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ((MainActivity)getActivity()).writeFile("cardlist.card",array.toString());
    }
}

