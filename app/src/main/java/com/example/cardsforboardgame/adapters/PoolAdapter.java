package com.example.cardsforboardgame.adapters;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.R;

import java.util.ArrayList;
import java.util.List;

public class PoolAdapter extends RecyclerView.Adapter<PoolAdapter.PoolViewHolder> {
    private List<Pool> pools;
    private OnPoolClickListener onPoolClickListener;

    public void setOnPoolClickListener(OnPoolClickListener onPoolClickListener) {
        this.onPoolClickListener = onPoolClickListener;
    }

    public interface OnPoolClickListener
    {
        void onPoolClick(int position);
    }

    public void setPools(List<Pool> pools) {
        this.pools = pools;
        notifyDataSetChanged();
    }

    public List<Pool> getPools() {
        return pools;
    }

    public PoolAdapter(List<Pool> pools) {
        this.pools = pools;
    }

    @NonNull
    @Override
    public PoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pool_item, parent, false);
        return new PoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoolViewHolder holder, int position) {
        Pool pool = pools.get(position);
        ImageView image = holder.image;
        TextView title = holder.title;
        TextView description = holder.description;
        image.setImageURI(Uri.parse(pool.getPathToFile()));//java.lang.SecurityException: Permission Denial: opening provider com.google.a
        title.setText(pool.getTitle());
        String s = pool.getDescription();
        description.setText(s);


    }

    @Override
    public int getItemCount() {
        return pools.size();
    }

    class PoolViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;


        public PoolViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewPool);
            title = itemView.findViewById(R.id.titlePoolTV);
            description = itemView.findViewById(R.id.descriptionPoolTV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onPoolClickListener!=null){
                        onPoolClickListener.onPoolClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
