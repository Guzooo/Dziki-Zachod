package com.Guzooo.DzikiZachod2017;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BiletyCardAdapter extends RecyclerView.Adapter<BiletyCardAdapter.ViewHolder> {

    private String[] titles;
    private String[] textsFirst;
    private String[] textsSecond;
    private String[] textsLitle;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public BiletyCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.bilety_card, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView title = cardView.findViewById(R.id.text_title);
        TextView textFirst = cardView.findViewById(R.id.text_normal_price);
        TextView textSecond = cardView.findViewById(R.id.text_reduced_price);
        TextView textLitle = cardView.findViewById(R.id.bilety_card_dodatkowe_info);

        setTexts(titles, title, position);
        setTexts(textsFirst, textFirst, position);
        setTexts(textsSecond, textSecond, position);
        setTexts(textsLitle, textLitle, position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public BiletyCardAdapter (String[] titles, String[] textsFirst, String[] textsSecond, String[] textsLitle){
        this.titles = titles;
        this.textsFirst = textsFirst;
        this.textsSecond = textsSecond;
        this.textsLitle = textsLitle;
    }

    private void setTexts (String[] tabela, TextView textView, int position){
        if(tabela[position].equals("")){
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(tabela[position]);
        }
    }
}
