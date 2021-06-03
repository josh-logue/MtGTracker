package edu.csub.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment cardFragment;
    private DeckList deckList = DeckList.getInstance();
    String pQuery;
    private int site;
    Button button1, button2;

    /**
     * Sets up main activity when it is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * Helper function for clickHandler
     * @param view
     */
    public void onArchClick(View view){
        site = 0;
        onSearchClick(view);
    }

    /**
     * Helper function for clickHandler
     * @param view
     */
    public void onScryClick(View view){
        site = 1;
        onSearchClick(view);
    }

    /**
     * Click handler for the main pages search button
     * @param view
     */
    public void onSearchClick(View view) {
        /**
         * This is the user input
         */
        button1 = findViewById(R.id.searchButton);
        button2 = findViewById(R.id.searchButton2);
        EditText rawText = findViewById(R.id.deck_link);
        String query;
        pQuery = rawText.getText().toString();
        if (site == 0){
            rawText = findViewById(R.id.deck_link);
            pQuery = rawText.getText().toString();
            /**
             * User input appended to query
             */
            if (pQuery != "@string/arch"){
                query = String.format("https://archidekt.com/api/decks/cards/?owner=%s&ownerexact=true/", pQuery);
            }
            else {
                query = "NULL";
            }
        } else {
            query = String.format("https://api.scryfall.com/cards/search?q=%s", pQuery);
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);

        }
        if (query != "NULL") {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                AsyncHttpClient client = new AsyncHttpClient();
                Card card = new Card();
                @Override
                public void run() {
                    Log.d("START", query);
                    client.get(query, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                JSONObject inc = response;
                                /**
                                 * Check for deck or card JSON
                                 * In case of card
                                 */
                                if(inc.has("data")) {
                                    JSONArray arr = inc.getJSONArray("data");
                                    JSONObject json = arr.getJSONObject(0);
                                    /**
                                     * Set attributes of card to display
                                     */
                                    card.setAll(json.getString("name"), json.getString("mana_cost"),
                                            json.getString("oracle_text"), json.getString("type_line"),
                                            json.getString("power"), json.getString("toughness"),
                                            json.getInt("cmc"), 1);
                                    cardFragment = CardFragment.newInstance(card);
                                    /**
                                     *  Call fragment manager to display fragment
                                     */
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment, cardFragment);
                                    fragmentTransaction.commit();
                                /**
                                * In case of deck
                                */
                                } else {
                                    JSONArray arr = inc.getJSONArray("results");
                                    JSONObject temp;
                                    ArrayList<String> deckNames = new ArrayList<>();
                                    ArrayList<String> deckIds = new ArrayList<>();
                                    /**
                                     *  Fill array with user's decks
                                      */
                                    for(int i = 0; i < arr.length(); i++){
                                        temp = arr.getJSONObject(i);
                                        deckNames.add(temp.getString("name"));
                                        deckIds.add(String.format("%s", temp.getInt("id")));
                                    }
                                    /**
                                     * Assign arrays to decklist
                                     */
                                    deckList.setNames(deckNames);
                                    deckList.setIds(deckIds);
                                    deckList.setOwner(pQuery);
                                    startActivity(new Intent(MainActivity.this, DeckCont.class));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * Closes open card fragment
     * @param view
     */
    public void onFragBack(View view) {
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction().remove(cardFragment).commit();
    }
}