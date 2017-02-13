package com.shne.learning.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shne.learning.Activity.Fragments.MainFragment;
import com.shne.learning.Activity.Fragments.TransactionView;
import com.shne.learning.Arguments.JsonArguments;
import com.shne.learning.Mudole.CreditCard;
import com.shne.learning.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    MainFragment fragment;
    private ArrayList<CreditCard> cards;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        cards = loadCards();

        FragmentManager fm = getSupportFragmentManager();
        fragment = (MainFragment) fm.findFragmentById(R.id.MainFrame);

        if (fragment == null) {
            fragment = MainFragment.newInstance("", "");
            fm.beginTransaction().add(R.id.MainFrame, fragment).commit();
        }

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.actions_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fragment.SearchInList(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void openTransactionFragment(String FilePath) {

        toolbar.setVisibility(View.GONE);
        TransactionView fragment = new TransactionView();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFrame,fragment).addToBackStack(null).commit();


    }
    public void returnToolbar(){
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

    }
    public ArrayList loadCards() {
        String Filedata = openFile("cardlist.card");
        ArrayList cardList = new ArrayList();
        try {
            JSONArray object = new JSONArray(Filedata);
            for(int i = 0; i < object.length(); i++ ){
                CreditCard creditCard = new CreditCard(this.getApplicationContext());
                creditCard.setName(object.getJSONObject(i).getString(JsonArguments.Arg_Name));
                creditCard.setCardCompany(object.getJSONObject(i).getString(JsonArguments.Arg_Compeny));
                creditCard.setFourDig(object.getJSONObject(i).getString(JsonArguments.Arg_Digets));
                creditCard.setAmount(object.getJSONObject(i).getDouble(JsonArguments.Arg_Amount));
                cardList.add(creditCard);
            }

            return cardList;
        } catch (JSONException e) {
                e.printStackTrace();
        }
        return new ArrayList();
    }

    public void writeFile(String filename, String data) {
        File file = new File(filename);
        FileOutputStream outputStream = null;
        try {
            FileOutputStream fos = openFileOutput(filename,MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String openFile(String filename) {
        try {
            File file = new File(getFilesDir()+filename);
            FileInputStream inputStream = openFileInput(filename);
            byte[] dat = new byte[inputStream.available()];
            inputStream.read(dat);
            String s = new String(dat);
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            writeFile(filename,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public ArrayList<CreditCard> getCards() {
        return cards;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

}



