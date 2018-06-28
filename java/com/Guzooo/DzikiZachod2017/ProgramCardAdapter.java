package com.Guzooo.DzikiZachod2017;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgramCardAdapter extends RecyclerView.Adapter<ProgramCardAdapter.ViewHolder>{

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
    public ProgramCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.program_card, parent, false);
        return new ViewHolder(cv);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView textTitle = cardView.findViewById(R.id.textTitle);
        TextView textTime = cardView.findViewById(R.id.textTime);
        TextView textDay = cardView.findViewById(R.id.textDay);
        if (cursor.moveToPosition(position)) {
            if(cursor.getColumnCount() == 5){
                textDay.setText(cursor.getInt(4));
            }
            textTitle.setText(cursor.getInt(1));
            int hour = cursor.getInt(2)/60;
            int minute = cursor.getInt(2) % 60;
            String hours = Integer.toString(hour) + ":" + Integer.toString(minute);
            if(minute == 0){
                hours += Integer.toString(0);
            }
            hour = cursor.getInt(3) / 60;
            minute = cursor.getInt(3) % 60;
            if(hour >= 0){
                hours += "-"+ Integer.toString(hour) + ":" + Integer.toString(minute);
                if(minute == 0){
                    hours += Integer.toString(0);
                }
            }
            textTime.setText(hours);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if(cursor.moveToPosition(position)) {
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

    public ProgramCardAdapter(Cursor cursor){
        CloseCursor();
        this.cursor = cursor;
    }

    public void CloseCursor(){
        if(this.cursor != null){
            this.cursor.close();
        }
    }
}
