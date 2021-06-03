package edu.csub.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Class controlling the detailed card list for a deck
 */
public class DeckView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DeckList deckList = DeckList.getInstance();
    private ListView listView;
    private ArrayList<Card> simpleCards;
    private ArrayList<Card> sorted;
    private ArrayList<Card> held = new ArrayList<>();;
    private ArrayAdapter<Card> cardAdapter;
    private TextView deckName;
    private Spinner filters;
    private FragmentManager fragmentManager;
    private Fragment cardFragment;

    /**
     * Called when view is created, creates and initializes variables and objects
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_list);
        String test[];
        deckName = findViewById(R.id.deck_name);
        deckName.setText(deckList.getDeckName());
        listView = findViewById(R.id.lvItems);
        simpleCards = deckList.getList();
        sorted = new ArrayList<>();

        /**
         * For dropdown filter
         */
        filters = (Spinner) findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filters,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filters.setAdapter(adapter);
        filters.setOnItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        setupLongClickHandler();

        /**
         * Sorts the cards into ordered by type
         */
        for(Card card : simpleCards){
            test = card.getType().split(" ");
            card.setSort(test[0]);
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Creature")){
                sorted.add(card);
            }
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Sorcery")){
                sorted.add(card);
            }
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Instant")){
                sorted.add(card);
            }
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Enchantment")){
                sorted.add(card);
            }
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Artifact")){
                sorted.add(card);
            }
        }
        for(Card card :simpleCards){
            if (card.getSort().equals("Land")){
                sorted.add(card);
            }
        }
        /**
         * Second list to preserve unfiltered list
         */
        for(Card card: sorted){
            held.add(card);
        };

        cardAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, held);
        listView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();
    }

    /**
     * Long click handler to view specific card
     */
    private void setupLongClickHandler(){
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Card temp = sorted.get(position);
            cardFragment = CardFragment.newInstance(temp);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dFragment, cardFragment);
            fragmentTransaction.commit();
            return false;
        });
    }

    /**
     * Helper function for filtering, called when the item from the spinner object is selected
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sorted.clear();
        String sel = parent.getItemAtPosition(position).toString();
        /**
         * Repopulates 'sorted' from 'held' to provide unfiltered list
         */
        if(sel.equals("No Filter")){
            for(Card card: held){
                sorted.add(card);
            }
            cardAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, sorted);
        } else {
            /**
             * Repopulates 'sorted' from 'held' based on filter criteria
             */
            for (Card card : held) {
                if (card.getType().contains(sel)) {
                    sorted.add(card);
                }
            }
            cardAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, sorted);
        }
        listView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();
    }

    /**
     * Called when nothing is selected by filter (unused)
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        cardAdapter = new ArrayAdapter<>(this, R.layout.list_item_layout, sorted);
        listView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();
    }

    /**
     * Closes card fragment
     * @param view
     */
    public void onFragBack(View view) {
        fragmentManager.beginTransaction().remove(cardFragment).commit();
    }

    /**
     * Closes card list and to go back to decklist
     * @param view
     */
    public void onDeckBack(View view) {
        startActivity(new Intent(DeckView.this, DeckCont.class));
    }
}
