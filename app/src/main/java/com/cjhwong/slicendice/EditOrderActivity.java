package com.cjhwong.slicendice;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditOrderActivity extends Activity {
    private int position;
    private OrderInfo order;
    private String flavourString;
    private Calendar calendar;
    private Button orderDate, orderTime;
    private EditText orderName, orderPhone, orderAddress, orderNote, orderPieces;
    private CheckBox orderHalfBox, orderPaid;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Bundle b = getIntent().getExtras();
        flavourString = b.getString("flavour");
        order = b.getParcelable("order");
        position = b.getInt("position", 0);

        calendar = Calendar.getInstance();
        final DecimalFormat df = new DecimalFormat("00");

        orderDate = (Button) this.findViewById(R.id.orderDateButton);
        orderDate.setText(order.getDate());
        orderDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditOrderActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        orderDate.setText(String.format("%s/%s/%s", df.format(year), df.format(monthOfYear), df.format(dayOfMonth)));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        orderTime = (Button) this.findViewById(R.id.orderTimeButton);
        orderTime.setText(order.getTime());
        orderTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EditOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        orderTime.setText(String.format("%s:%s", df.format(hourOfDay), df.format(minute)));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        orderName = (EditText) this.findViewById(R.id.orderNameText);
        orderName.setText(order.getName());
        orderPhone = (EditText) this.findViewById(R.id.orderPhoneText);
        orderPhone.setText(order.getPhone());
        orderAddress = (EditText) this.findViewById(R.id.orderAddressText);
        orderAddress.setText(order.getAddress());
        orderNote = (EditText) this.findViewById(R.id.orderNoteText);
        orderNote.setText(order.getNote());

        orderHalfBox = (CheckBox) this.findViewById(R.id.orderHalfBoxCheck);
        orderHalfBox.setChecked(order.isHalfBox());
        orderPaid = (CheckBox) this.findViewById(R.id.orderPaidCheck);
        orderPaid.setChecked(order.isPaid());

        orderPieces = (EditText) this.findViewById(R.id.orderPieces);
        orderPieces.setText(String.valueOf(order.getPieces()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Sender) {
        super.onActivityResult(requestCode, resultCode, Sender);

        if (resultCode == RESULT_OK) {
            Bundle b = Sender.getExtras();
            order.setPizzasString(b.getString("flavours"));

            /* Convert pizza string to pizza object */
            ArrayList<PizzaInfo> pizzas = new ArrayList<>();
            for (String f : order.getPizzasString().split(",")) {
                pizzas.add(getPizzaObject(f));
            }

            order.setPizzas(pizzas);
        }
    }

    private PizzaInfo getPizzaObject(String s) {
        PizzaInfo p = new PizzaInfo();
        p.setFlavour(s.replace(",", "").replace("(", "").replace(")", ""));
        if (s.contains("/")) {
            p.setHalfPie(false);
        } else {
            p.setHalfPie(true);
        }
        return p;
    }

    public void onAddFlavour(View view) {
        Intent intent = new Intent(this, FlavourPickerActivity.class);
        intent.putExtra("flavour", order.getPizzasString());
        intent.putExtra("flavourString", flavourString);
        startActivityForResult(intent, Constants.ORDER_FLAVOUR_ADDED_RESULT);
    }

    public void onCancel(View view) {
        Intent i = new Intent();
        i.putExtra("order", order);
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void onSubmit(View view) {
        order.setDate(orderDate.getText().toString());
        order.setTime(orderTime.getText().toString());

        order.setName(orderName.getText().toString());
        order.setPhone(orderPhone.getText().toString());
        order.setAddress(orderAddress.getText().toString());
        order.setNote(orderNote.getText().toString());

        order.setHalfBox(orderHalfBox.isChecked());
        order.setPaid(orderPaid.isChecked());

        order.setPieces(Integer.parseInt(orderPieces.getText().toString()));
        order.setAmount(order.getPizzas().size());

        Intent i = new Intent();
        i.putExtra("order", order);
        i.putExtra("position", position);
        setResult(RESULT_OK, i);
        EditOrderActivity.this.finish();
    }
}
