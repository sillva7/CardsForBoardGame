package com.example.cardsforboardgame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewViewHolder> {

    public static int checkbox = 0;//это чтобы регулировать видимость чебокса

    private List<Card> cards;
    private OnCardClickListener onCardClickListener;//создаём объект интерфейс

    public interface OnCardClickListener {//создаём интерфейс для клика по элементам адаптера

        void onCardClick(int position);
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {//делаем метод для клика по эелементу
        this.onCardClickListener = onCardClickListener;
    }

    public List<Card> getCards() {

        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    public void addAllCards(List<Card> cards) {
        this.cards = cards;
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
        CheckBox checkBox = holder.checkBox;
        imageView.setImageBitmap(card.getBitmap());
        textView.setText(card.getTitle());
        if (checkbox == 0) {
            checkBox.setVisibility(View.GONE);
        } else {
            checkBox.setVisibility(View.VISIBLE);
        }
        checkBox.setChecked(true);
    }

    public CardAdapter() {
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CheckBox checkBox;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCard);
            textView = itemView.findViewById(R.id.textViewCard);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {//завершающий штрих для клика по элементу адаптера
                @Override
                public void onClick(View v) {
                    if (onCardClickListener != null) {
                        onCardClickListener.onCardClick(getAdapterPosition());
                    }
                }
            });

        }

    }
}
