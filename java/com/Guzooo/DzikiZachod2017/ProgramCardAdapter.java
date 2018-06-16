package com.Guzooo.DzikiZachod2017;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgramCardAdapter extends RecyclerView.Adapter<ProgramCardAdapter.ViewHolder>{

   private Cursor cursor;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public ProgramCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.program_card,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textTitle = cardView.findViewById(R.id.textTitle);
        TextView textTime = cardView.findViewById(R.id.textTime);
        if (cursor.moveToPosition(position)) {
            textTitle.setText(cursor.getInt(0));
            int hour = cursor.getInt(1)/60;
            int minute = cursor.getInt(1) % 60;
            String hours = Integer.toString(hour) + ":" + Integer.toString(minute);
            if(minute == 0){
                hours += Integer.toString(0);
            }
            hour = cursor.getInt(2) / 60;
            minute = cursor.getInt(2) % 60;
            if(hour >= 0){
                hours += "-"+ Integer.toString(hour) + ":" + Integer.toString(minute);
                if(minute == 0){
                    hours += Integer.toString(0);
                }
            }
            textTime.setText(hours);
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
