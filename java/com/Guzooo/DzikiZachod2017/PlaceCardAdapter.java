package com.Guzooo.DzikiZachod2017;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PlaceCardAdapter extends RecyclerView.Adapter<PlaceCardAdapter.ViewHolder> {

    private Cursor cursor;
    private Listener listener;

    public static interface Listener {
        public void onClick(int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public PlaceCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent, false);
        return new ViewHolder(cv);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(PlaceCardAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView textView = cardView.findViewById(R.id.place_card_text);
        ImageView imageView = cardView.findViewById(R.id.place_card_image);

        if (cursor.moveToPosition(position)){
            textView.setText(cursor.getInt(1));
            imageView.setImageResource(cursor.getInt(2));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        if(cursor.moveToPosition(position)){
                            listener.onClick(cursor.getInt(0));
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public PlaceCardAdapter(Cursor cursor, View nullCard){
        CloseCursor();
        this.cursor = cursor;

        if(nullCard != null){
            if(cursor.getCount() == 0){
                nullCard.setVisibility(View.VISIBLE);
            } else {
                nullCard.setVisibility(View.GONE);
            }
        }
    }

    public void CloseCursor(){
        if(cursor != null){
            cursor.close();
        }
    }
}
