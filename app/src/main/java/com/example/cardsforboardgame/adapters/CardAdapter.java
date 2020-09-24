package com.example.cardsforboardgame.adapters;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.activities.AddNewPool;
import com.example.cardsforboardgame.activities.PoolViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewViewHolder> {


    public static int checkbox = 0;//это чтобы регулировать видимость чебокса, 0 - выключены, вроде как
    public static int forUpdatePool = 0;//для обновления пула

    private List<Card> cards;
    private OnCardClickListener onCardClickListener;//создаём объект интерфейс
    private OnLongCardClickListener onLongCardClickListener;

    public void setOnLongCardClickListener(OnLongCardClickListener onLongCardClickListener) {
        this.onLongCardClickListener = onLongCardClickListener;
    }
//    private OnButtonClickListener onButtonClickListener;//for btn at the end

    public interface OnCardClickListener {//создаём интерфейс для клика по элементам адаптера

        void onCardClick(int position);
    }

    public interface OnLongCardClickListener {
        void onLongCardClick(int position);
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {//делаем метод для клика по эелементу
        this.onCardClickListener = onCardClickListener;
    }
//    public interface OnButtonClickListener{//for btnAtTheEnd
//        void onBtnClick(int position);
//    }
//    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){//for btnAtTheEnd
//        this.onButtonClickListener = onButtonClickListener;
//    }

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
        final View view;
        if (viewType == R.layout.card_item) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        } else {//добавление кнопки в конец
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_at_the_end_of_recview, parent, false);
        }
        CardViewViewHolder cardViewViewHolder = new CardViewViewHolder(view);

//        if (forUpdatePool == 0) {
//
//        } else {
//            final int[] toggle = {0};
//            view.findViewById(R.id.imageViewCard).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    if (toggle[0] == 0) {
//                        view.setBackgroundResource((R.drawable.thick_border_for_update));
//
//                        toggle[0] = 1;
//                    } else {
//                        view.setBackgroundResource(android.R.color.white);
//                        toggle[0] = 0;
//                    }
//                }
//            });
//        }

        return cardViewViewHolder;

    }


    @Override
    public int getItemViewType(int position) {
        return (position == cards.size()) ? R.layout.btn_at_the_end_of_recview : R.layout.card_item;//добавление кнопки в конец
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        try {
            if (position == cards.size()) {//добавление кнопки в конец
                Button btnAtTheEnd = holder.btnAtTheEnd;

            } else {

                int bg = 0;
                final Card card = cards.get(position);
                final CardView cardView = holder.cardView;
                final ImageView imageView = holder.imageView;
                TextView textView = holder.textView;
                final CheckBox checkBox = holder.checkBox;
                checkBox.setChecked(card.isChecked());//отметка для установки чекбокса
                imageView.setImageURI(Uri.parse(card.getPathToImage()));
                textView.setText(card.getTitle());
                if (checkbox == 0) {
                    checkBox.setVisibility(View.GONE);
                } else {
                    checkBox.setVisibility(View.VISIBLE);
                }

                if (forUpdatePool == 1) {//для добавление в пул того, что уже существует
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (PoolViewActivity.cardsForUpdate.contains(card)) {
                                PoolViewActivity.cardsForUpdate.remove(card);
                                cardView.setBackgroundColor(holder.cardView.getResources().getColor(android.R.color.white));
                            } else {
                                PoolViewActivity.cardsForUpdate.add(card);
                                cardView.setBackgroundResource(R.drawable.thick_border_for_update);

//                                cardView.setBackgroundResource(holder.imageView.getContext().getResources(R.drawable.thick_border_for_update));//как достать контекст


                            }
                        }
                    });
                }

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox.isChecked()) {//этот блок с ифом я нашёл вот в это подворотне shorturl.at/efryH ну, вернее идею в целом как отметить это чекбокс
                            checkBox.setChecked(true);
                            AddNewPool.cards.add(card.getTitle());
                            ;
                        } else {
                            checkBox.setChecked(false);
                            AddNewPool.cards.remove(card.getTitle());
                        }
                    }
                });


            }
            //checkBox.setChecked(true); просто тестировал че делает этот метод. выставляет значение чкбкса на "отмечено"
        } catch (NullPointerException e) {

        }
    }


    public CardAdapter() {
    }

    @Override
    public int getItemCount() {
        return cards.size() + 1;//добавление кнопки в конец
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CheckBox checkBox;
        private Button btnAtTheEnd;//добавление кнопки в конец
        private CardView cardView;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageViewCard);
            textView = itemView.findViewById(R.id.textViewCard);
            checkBox = itemView.findViewById(R.id.checkBox);
            btnAtTheEnd = itemView.findViewById(R.id.endOfRecView);//добавление кнопки в конец

            itemView.setOnClickListener(new View.OnClickListener() {//завершающий штрих для клика по элементу адаптера
                @Override
                public void onClick(View v) {
                    if (onCardClickListener != null) {
                        onCardClickListener.onCardClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {//если что вот так и работает лонг клик
                @Override
                public boolean onLongClick(View v) {
                    if (onLongCardClickListener != null) {
                        onLongCardClickListener.onLongCardClick(getAdapterPosition());
                    }
                    return true;

                }
            });

        }

    }
}
