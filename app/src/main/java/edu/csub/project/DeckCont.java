package edu.csub.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Class controlling the deck list
 */
public class DeckCont extends AppCompatActivity {
    DeckList deckList = DeckList.getInstance();
    private ListView listView;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayAdapter<String> deckAdapter;
    private TextView userName;

    /**
     * Called when view is created, sets up view's text and clickHandler
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_list);
        userName = findViewById(R.id.deck_name);
        userName.setText(deckList.getOwner());
        listView = findViewById(R.id.lvItems);
        names = deckList.getNames();
        ids = deckList.getIds();
        setupLongClickHandler();
        deckAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, names);
        listView.setAdapter(deckAdapter);
        deckAdapter.notifyDataSetChanged();
    }

    /**
     * Called on x button press to go back to main page
     * @param view
     */
    public void onDeckBack(View view) {
        startActivity(new Intent(DeckCont.this, MainActivity.class));
    }

    /**
     * Long click handler to view a specific deck
     */
    private void setupLongClickHandler() {
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String temp = ids.get(position);
            String query = String.format("https://archidekt.com/api/decks/%s/", temp);
            Handler handler = new Handler();
            handler.post(new Runnable() {
                AsyncHttpClient client = new AsyncHttpClient();
                ArrayList<Card> simpleCards = new ArrayList<>();
                /**
                 * New query to get cards of a specific list
                 */
                @Override
                public void run() {
                    client.get(query, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                /**
                                 * Clear cards for new list
                                 */
                                deckList.clearCards();
                                JSONObject inc = response;
                                JSONArray json = inc.getJSONArray("cards");
                                /**
                                 * Loops through JSONArray of cards, creating each one and storing it in an Array
                                 */
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject single = json.getJSONObject(i);
                                    JSONObject step = single.getJSONObject("card");
                                    JSONObject oracleCard = step.getJSONObject("oracleCard");
                                    JSONArray types = oracleCard.getJSONArray("types");
                                    /**
                                     * Fixes card type
                                     */
                                    String typeLine = "";
                                    for (int j = 0; j < types.length(); j++) {
                                        typeLine += (types.getString(j) + " ");
                                    }
                                    String mana = oracleCard.getString("manaCost");
                                    if (mana.equals("")) {
                                        mana = "0";
                                    }
                                    JSONArray temp = oracleCard.getJSONArray("subTypes");
                                    /**
                                     * Gets subtypes
                                     */
                                    String[] subtypes = new String[temp.length()];
                                    for(int k = 0; k < temp.length(); k++){
                                        subtypes[k] = temp.getString(k);
                                    }
                                    /**
                                     * Creates new card and adds it to array
                                     */
                                    Card card = new Card(oracleCard.getString("name"), mana, oracleCard.getString("text"),
                                            typeLine, oracleCard.getString("power"), oracleCard.getString("toughness"),
                                            oracleCard.getInt("cmc"), single.getInt("quantity"), subtypes);
                                    simpleCards.add(card);
                                }
                                /**
                                 * Assigns the card array and active deck name to the decklist and redirects user to this list
                                 */
                                deckList.setList(simpleCards);
                                deckList.setDeckName(inc.getString("name"));
                                startActivity(new Intent(DeckCont.this, DeckView.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            return false;
        });
    }


}
