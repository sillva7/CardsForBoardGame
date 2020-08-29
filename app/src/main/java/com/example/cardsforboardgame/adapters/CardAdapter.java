package com.example.cardsforboardgame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewViewHolder> {

    private List<Card> cards;

    public List<Card> getCards() {
        cards = new ArrayList<>();
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }
    public void addAllCards(List<Card> cards){
        this.cards=cards;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new CardViewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        Card card = cards.get(position);
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;
        imageView.setImageBitmap(card.getBitmap());
        textView.setText(card.getTitle());
    }

    public CardAdapter() {
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCard);
            textView = itemView.findViewById(R.id.textViewCard);

        }
    }
}
