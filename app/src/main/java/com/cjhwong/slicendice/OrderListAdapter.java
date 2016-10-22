package com.cjhwong.slicendice;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private final MainActivity mContext;
    protected String dateString, flavourString;
    private int tabPosition;

    private boolean made;


    protected OrderListAdapter(MainActivity c, String d, String f, int p) {
        this.mContext = c;
        this.dateString = d;
        this.flavourString = f;
        this.tabPosition = p;
    }

    @Override
    public int getItemCount() {
        return mContext.getOrderList().size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.
                from(mContext).
                inflate(R.layout.view_order_info, parent, false);

        return new OrderViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder orderViewHolder, int i) {
        final int position = i;
        final OrderInfo order = mContext.getOrderList().get(i);

        if (!(order.getDate().equals(dateString))) {
            Log.i("OrderListAdapter", "Date does not match");
            return;
        }

        made = true;
        for (PizzaInfo p : order.getPizzas()) {
            if (!p.isMade()) {
                made = false;
            }
        }

        orderViewHolder.time.setBackgroundColor(mContext.getResources().getColor(order.getAddress().equals("") ? R.color.colorBlack : R.color.colorDelivery));
        orderViewHolder.orderTime.setText(order.getTime());
        orderViewHolder.orderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditOrderActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("order", mContext.getOrderList().get(position));
                intent.putExtra("flavour", flavourString);
                mContext.startActivityForResult(intent, Constants.ORDER_EDITED_RESULT);
            }
        });

        String boxType;
        if (order.isHalfBox() && order.getAddress() != null && !order.getAddress().equals("")) {
            boxType = "DH";
        } else if (order.getAddress() != null && !order.getAddress().equals("")) {
            boxType = "D";
        } else if (order.isHalfBox()) {
            boxType = "H";
        } else {
            boxType = "W";
        }
        orderViewHolder.orderAmount.setText(String.format("%dP/%d%s", order.getPieces(), order.getPizzas().size(), boxType));

        orderViewHolder.orderInfo.setText(String.format("%s%s%s", order.isPaid() ? "Paid" : "", order.isPaid() && order.isTaken() ? "/" : "", order.isTaken() ? "Taken" : ""));

        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        orderViewHolder.orderFlavour.setHasFixedSize(true);
        orderViewHolder.orderFlavour.setLayoutManager(llm);
        orderViewHolder.orderFlavour.setAdapter(new PizzaListAdapter(mContext, order.getPizzas()));


        orderViewHolder.orderName.setText(order.getName());
        orderViewHolder.orderPhone.setText(order.getPhone());
        orderViewHolder.orderPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + order.getPhone()));
                mContext.startActivity(callIntent);
            }
        });
        orderViewHolder.orderAddress.setText(order.getAddress());
        orderViewHolder.orderAddress.setVisibility(order.getAddress().equals("") ? View.GONE : View.VISIBLE);
        orderViewHolder.orderAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + order.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mContext.startActivity(mapIntent);
            }
        });
        orderViewHolder.orderNote.setText(order.getNote());
        orderViewHolder.orderNote.setVisibility(order.getNote().equals("") ? View.GONE : View.VISIBLE);


        orderViewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mContext.removeOrder(position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        orderViewHolder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!made) {
                    Toast.makeText(mContext, "You still have some pizza to make", Toast.LENGTH_SHORT).show();
                } else {
                    mContext.setOrderTaken(position);
                }
            }
        });

        ViewGroup.LayoutParams visibleLP, goneLP;

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            visibleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ((LinearLayout.LayoutParams) visibleLP).setMargins(5, 5, 5, 5);
            goneLP = new GridLayout.LayoutParams(GridLayout.spec(0, 0), GridLayout.spec(0, 0));
            ((GridLayout.LayoutParams) goneLP).width = 0;
        } else {
            visibleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ((LinearLayout.LayoutParams) visibleLP).setMargins(5, 5, 5, 5);
            goneLP = new LinearLayout.LayoutParams(0, 0);
            ((LinearLayout.LayoutParams) goneLP).setMargins(0, 0, 0, 0);
        }

        if (tabPosition == 0) {
            if (order.getDate().equals(dateString) && !order.isTaken() && !made) {
                orderViewHolder.cardView.setLayoutParams(visibleLP);
                orderViewHolder.orderFlavour.setVisibility(View.VISIBLE);
            } else {
                orderViewHolder.cardView.setLayoutParams(goneLP);
            }
        } else if (tabPosition == 1) {
            if (order.getDate().equals(dateString) && !order.isTaken() && made) {
                orderViewHolder.cardView.setLayoutParams(visibleLP);
                orderViewHolder.orderFlavour.setVisibility(View.VISIBLE);
            } else {
                orderViewHolder.cardView.setLayoutParams(goneLP);
            }
        } else if (tabPosition == 2) {
            if (order.getDate().equals(dateString) && order.isTaken() && made) {
                orderViewHolder.cardView.setLayoutParams(visibleLP);
                orderViewHolder.orderFlavour.setVisibility(View.GONE);
            } else {
                orderViewHolder.cardView.setLayoutParams(goneLP);
            }
        }
        Log.i("OLAdapter", "" + tabPosition);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected RelativeLayout container, time, info;
        protected LinearLayout contact, contents, note, operation;

        protected RecyclerView orderFlavour;
        protected TextView orderTime, orderAmount, orderInfo, orderName, orderPhone, orderAddress, orderNote;
        protected Button removeButton, doneButton;


        public OrderViewHolder(View v) {
            super(v);

            container = (RelativeLayout) v.findViewById(R.id.orderContainer);
            time = (RelativeLayout) v.findViewById(R.id.orderTime);
            info = (RelativeLayout) v.findViewById(R.id.orderInfo);
            note = (LinearLayout) v.findViewById(R.id.orderNote);
            operation = (LinearLayout) v.findViewById(R.id.orderOperation);

            cardView = (CardView) v.findViewById(R.id.card_view);
            orderFlavour = (RecyclerView) v.findViewById(R.id.orderFlavour);
            contact = (LinearLayout) v.findViewById((R.id.orderContact));
            contents = (LinearLayout) v.findViewById((R.id.orderContents));
            orderTime = (TextView) v.findViewById(R.id.time);
            orderAmount = (TextView) v.findViewById(R.id.amount);
            orderInfo = (TextView) v.findViewById(R.id.info);
            orderName = (TextView) v.findViewById(R.id.name);
            orderPhone = (TextView) v.findViewById(R.id.phone);
            orderAddress = (TextView) v.findViewById(R.id.address);
            orderNote = (TextView) v.findViewById(R.id.note);

            removeButton = (Button) v.findViewById(R.id.removeCardButton);
            doneButton = (Button) v.findViewById(R.id.doneOrderButton);
        }
    }
}