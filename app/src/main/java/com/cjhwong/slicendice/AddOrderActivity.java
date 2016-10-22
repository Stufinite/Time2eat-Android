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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddOrderActivity extends Activity {
    OrderInfo order;
    private String flavourString;
    private Calendar calendar;
    private Button orderDate, orderTime;
    private EditText orderName, orderPhone, orderAddress, orderNote, orderPieces;
    private CheckBox orderHalfBox, orderPaid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Bundle b = getIntent().getExtras();
        flavourString = b.getString("flavour");
        order = new OrderInfo();

        calendar = Calendar.getInstance();
        final DecimalFormat df = new DecimalFormat("00");

        orderDate = (Button) this.findViewById(R.id.orderDateButton);
        orderDate.setText(String.format("%s/%s/%s", df.format(calendar.get(Calendar.YEAR)), df.format(calendar.get(Calendar.MONTH) + 1), df.format(calendar.get(Calendar.DAY_OF_MONTH))));
        orderDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddOrderActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        orderDate.setText(String.format("%s/%s/%s", df.format(year), df.format(monthOfYear + 1), df.format(dayOfMonth)));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        orderTime = (Button) this.findViewById(R.id.orderTimeButton);
        orderTime.setText(String.format("%s:%s", df.format(calendar.get(Calendar.HOUR_OF_DAY)), df.format(calendar.get(Calendar.MINUTE))));
        orderTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        orderTime.setText(String.format("%s:%s", df.format(hourOfDay), df.format(minute)));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        orderName = (EditText) this.findViewById(R.id.orderNameText);
        orderPhone = (EditText) this.findViewById(R.id.orderPhoneText);
        orderAddress = (EditText) this.findViewById(R.id.orderAddressText);
        orderNote = (EditText) this.findViewById(R.id.orderNoteText);

        orderHalfBox = (CheckBox) this.findViewById(R.id.orderHalfBoxCheck);
        orderHalfBox.setChecked(false);
        orderPaid = (CheckBox) this.findViewById(R.id.orderPaidCheck);
        orderPaid.setChecked(false);

        orderPieces = (EditText) this.findViewById(R.id.orderPieces);
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
            if (p.getFlavour().split("/")[0].equals(p.getFlavour().split("/")[1])) {
                p.setFlavour(p.getFlavour().split("/")[0]);
            }
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
        if (order.getPizzasString().equals("")) {
            Toast.makeText(view.getContext(), "Damn! You need to at least add one flavour", Toast.LENGTH_LONG).show();
        } else {
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
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
