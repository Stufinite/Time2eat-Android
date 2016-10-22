package com.cjhwong.slicendice;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class PizzaListAdapter extends RecyclerView.Adapter<PizzaListAdapter.FlavourViewHolder> {

    private MainActivity mContext;
    private ArrayList<PizzaInfo> pizzaList;

    public PizzaListAdapter(MainActivity c, ArrayList<PizzaInfo> p) {
        this.mContext = c;
        this.pizzaList = p;
    }

    @Override
    public int getItemCount() {
        return this.pizzaList == null ? 0 : this.pizzaList.size();
    }

    @Override
    public FlavourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View flavourView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.view_pizza_flavour, parent, false);

        return new FlavourViewHolder(flavourView);
    }

    @Override
    public void onBindViewHolder(FlavourViewHolder flavourViewHolder, int i) {
        final int position = i;
        flavourViewHolder.pizzaImg.setImageDrawable(mContext
                .getDrawable(pizzaList.get(i).isHalfPie() ? R.drawable.pizzahalf : R.drawable.pizza));

        flavourViewHolder.pizzaFlavour.
                setText(pizzaList.get(i).getFlavour());
        if (pizzaList.get(i).isMade()) {
            flavourViewHolder.pizzaFlavour.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            flavourViewHolder.pizzaFlavour.setBackgroundColor(0x00000000);
        }
//        flavourViewHolder.pizzaFlavour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pizzaList.get(position).isMade()) {
//                    pizzaList.get(position).setMade(false);
//                    pizzaList.add(0, pizzaList.remove(position));
//                } else {
//                    pizzaList.get(position).setMade(true);
//                    pizzaList.add(pizzaList.remove(position));
//                }
//                notifyDataSetChanged();
//            }
//        });
    }

    public class FlavourViewHolder extends RecyclerView.ViewHolder {
        protected ImageView pizzaImg;
        protected Button pizzaFlavour;

        public FlavourViewHolder(View v) {
            super(v);

            pizzaFlavour = (Button) v.findViewById(R.id.pizzaFlavour);
            pizzaImg = (ImageView) v.findViewById(R.id.pizzaImg);
        }
    }
}

