package edu.csub.project;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Controls the card fragments, displaying relevant info to the user
 */
public class CardFragment extends Fragment {
    Card card;
    TextView cardName;
    TextView cardMana;
    TextView typeLine;
    TextView power;
    TextView toughness;
    TextView oracle;
    ImageView image;

    /**
     * Default constructor
     */
    public CardFragment(){

    }

    /**
     * Creates a single instance of the fragment
     * @param card Which card to represent
     * @return the fragment
     */
    public static CardFragment newInstance(Card card){
        CardFragment cardFragment = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable("card", card);
        cardFragment.setArguments(args);

        return cardFragment;
    }

    public void onFragBack(View view){

    }

    /**
     * Called after creation, needed for fragment display
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    /**
     * Called after activity creation, sets all features of the fragment to represent
     * the given card
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        card = (Card) getArguments().getSerializable("card");
        cardName = getView().findViewById(R.id.cardName);
        cardMana = getView().findViewById(R.id.cardMana);
        typeLine = getView().findViewById(R.id.typeLine);
        power = getView().findViewById(R.id.power);
        toughness = getView().findViewById(R.id.toughness);
        oracle = getView().findViewById(R.id.oracle);
        cardName.setText(card.getName());
        cardMana.setText(card.getMana());
        try {
            typeLine.setText(String.format("%s - %s", card.getType(), card.getSubtypes()));
        } catch (NullPointerException e){
            typeLine.setText(String.format("%s", card.getType()));
            e.printStackTrace();
        }
        power.setText(String.format("%s / ", card.getPower()));
        toughness.setText(card.getTough());
        oracle.setText(card.getOracle());
    }

}
