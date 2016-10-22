package com.cjhwong.slicendice;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private String dateString, flavourString;
    private OrderPagerAdapter fragmentAdapter;
    private SortedList<OrderInfo> orderList = new SortedList<>(OrderInfo.class, new SortedList.Callback<OrderInfo>() {

        @Override
        public int compare(OrderInfo lhs, OrderInfo rhs) {
            int year, month, day, hour, minute;
            year = Integer.parseInt(lhs.getDate().split("/")[0]);
            month = Integer.parseInt(lhs.getDate().split("/")[1]);
            day = Integer.parseInt(lhs.getDate().split("/")[2]);
            hour = Integer.parseInt(lhs.getTime().split(":")[0]);
            minute = Integer.parseInt(lhs.getTime().split(":")[1]);
            GregorianCalendar tl = new GregorianCalendar(year, month, day, hour, minute);

            year = Integer.parseInt(rhs.getDate().split("/")[0]);
            month = Integer.parseInt(rhs.getDate().split("/")[1]);
            day = Integer.parseInt(rhs.getDate().split("/")[2]);
            hour = Integer.parseInt(rhs.getTime().split(":")[0]);
            minute = Integer.parseInt(rhs.getTime().split(":")[1]);
            GregorianCalendar tr = new GregorianCalendar(year, month, day, hour, minute);

            return tl.compareTo(tr);
        }

        @Override
        public void onInserted(int position, int count) {
            refreshOrderList();
        }

        @Override
        public void onRemoved(int position, int count) {
            refreshOrderList();
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            refreshOrderList();
        }

        @Override
        public void onChanged(int position, int count) {
            refreshOrderList();
        }

        @Override
        public boolean areContentsTheSame(OrderInfo oldItem, OrderInfo newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(OrderInfo item1, OrderInfo item2) {
            return false;
        }
    });

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /* Set today to the displaying date */
        DecimalFormat df = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();
        dateString = String.format("%s/%s/%s",
                df.format(cal.get(Calendar.YEAR)),
                df.format(cal.get(Calendar.MONTH) + 1),
                df.format(cal.get(Calendar.DAY_OF_MONTH)));

        /* Initialize Toolbar and ViewPager */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Order List");
        }

        String[] tabName = {"Todo", "Made", "Taken"};
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        for (String name : tabName) {
            // Add tab title
            tabLayout.addTab(tabLayout.newTab().setText(name));
        }

        fragmentAdapter = new OrderPagerAdapter(getSupportFragmentManager(), tabName);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);

        /* Define floating action button behavior */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddOrderActivity.class);
                i.putExtra("flavour", flavourString);
                startActivityForResult(i, Constants.ORDER_ADDED_RESULT);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
               onAppDataLoad();
            }
        }, 0, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        onAppDataSave();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_choose_date) {
        if (id == R.id.action_choose_date) {
            onChooseDate();
            return true;
        } else if (id == R.id.action_set_flavours) {
            onChooseFlavour();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent Sender) {
        super.onActivityResult(requestCode, resultCode, Sender);

        if (resultCode == RESULT_CANCELED) {
            Log.i(TAG, "Result Canceled");
        } else {
            Bundle b = new Bundle();
            if (Sender != null) {
                b = Sender.getExtras();
            }
            switch (requestCode) {
                case Constants.ORDER_ADDED_RESULT:
//                    orderList.add((OrderInfo) b.getParcelable("order"));
                    postOrder((OrderInfo) b.getParcelable("order"));
                    break;
                case Constants.ORDER_EDITED_RESULT:
//                    orderList.updateItemAt(b.getInt("position"), (OrderInfo) b.getParcelable("order"));
                    putOrder((OrderInfo) b.getParcelable("order"));
                    break;
                case Constants.FLAVOUR_EDITED_RESULT:
                    flavourString = b.getString("flavours");
                    onAppDataSave();
                    break;
            }
        }
    }

    private void onAppDataLoad() {
        Log.i(TAG, "Data loading");
        SharedPreferences settings = getSharedPreferences("DATA", 0);

        flavourString = !settings.getString("flavour", "").equals("") ? settings.getString("flavour", "") : "H,DC,MC,P,Resv,Resv,Resv,Resv,Resv";
        new Thread(new Runnable() {
            public void run() {
                try {
                    URI address = new URI("http", null, "104.155.236.60", 8080, "/api/orders", "date=" + dateString, "anchor");
                    Log.i(TAG, address.toString());
                    loadJson(getOrder(address));
//                    Toast.makeText(mContext, "Order added!", Toast.LENGTH_SHORT).show();
//                                RingtoneManager
//                                        .getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                                        .play();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "JSON load error");
                }
            }
        }).start();
    }

    protected void onAppDataSave() {
        SharedPreferences settings = getSharedPreferences("DATA", 0);
        settings.edit()
                .clear()
                .putString("flavour", flavourString)
                .apply();
    }

    private void loadJson(JSONArray c) {
        final JSONArray content = c;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (content.length() == orderList.size()) {
                    return;
                }
                orderList.clear();
                for (int i = 0; i < content.length(); i++) {
                    try {
                        JSONObject orderObj = (JSONObject) content.get(i);
                        OrderInfo o = new OrderInfo();
                        o.setId(orderObj.getString("_id"));
                        o.setDate(orderObj.getString("date"));
                        o.setTime(orderObj.getString("time"));
                        o.setPieces(orderObj.getInt("pieces"));
                        o.setName(orderObj.getString("name"));
                        o.setPhone(orderObj.getString("phone"));
                        o.setAddress(orderObj.getString("address"));
                        o.setNote(orderObj.getString("note"));
                        o.setHalfBox(orderObj.getBoolean("halfBox"));
                        o.setPaid(orderObj.getBoolean("paid"));
                        o.setTaken(orderObj.getBoolean("taken"));
                        o.setPizzasString(orderObj.getString("pizzaString"));
                        ArrayList<PizzaInfo> pizzaArray = new ArrayList<>();
                        try {
                            JSONArray pizzaJsonArray = new JSONArray(orderObj.getString("pizzas"));
                            for (int j = 0; j < pizzaJsonArray.length(); j++) {
                                JSONObject pizzaObj = pizzaJsonArray.getJSONObject(j);
                                PizzaInfo p = new PizzaInfo();
                                p.setFlavour(pizzaObj.getString("flavour"));
                                p.setHalfPie(pizzaObj.getBoolean("half"));
                                p.setMade(pizzaObj.getBoolean("made"));
                                pizzaArray.add(p);
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "pizzaObj create error");
                        }
                        o.setPizzas(pizzaArray);

                        orderList.add(o);
                    } catch (Exception e) {
                        Log.i(TAG, "orderObj create error");
                        onAppDataLoad();
                    }
                }
            }
        });
    }

    private JSONArray getOrder(URI url) {

        InputStream is = null;
        String result = "";
        JSONArray jsonArray = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"), 9999999);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    private void postOrder(OrderInfo o) {
        final OrderInfo order = o;

        new Thread(new Runnable() {
            public void run() {
                try {
                    URI url = new URI("http", null, "104.155.236.60", 8080, "/api/orders", "id=10", "anchor");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(13);
                    nameValuePairs.add(new BasicNameValuePair("date", order.getDate()));
                    nameValuePairs.add(new BasicNameValuePair("time", order.getTime()));
                    nameValuePairs.add(new BasicNameValuePair("pizzaString", order.getPizzasString()));
                    nameValuePairs.add(new BasicNameValuePair("pieces", String.valueOf(order.getPieces())));
                    nameValuePairs.add(new BasicNameValuePair("name", order.getName()));
                    nameValuePairs.add(new BasicNameValuePair("phone", order.getPhone()));
                    nameValuePairs.add(new BasicNameValuePair("address", order.getAddress()));
                    nameValuePairs.add(new BasicNameValuePair("note", order.getNote()));
                    nameValuePairs.add(new BasicNameValuePair("halfBox", String.valueOf(order.isHalfBox())));
                    nameValuePairs.add(new BasicNameValuePair("paid", String.valueOf(order.isPaid())));
                    nameValuePairs.add(new BasicNameValuePair("taken", String.valueOf(order.isTaken())));
                    nameValuePairs.add(new BasicNameValuePair("done", String.valueOf(false)));
                    try {
                        JSONArray pizzas = new JSONArray();
                        ArrayList<PizzaInfo> pizzasList = order.getPizzas();
                        for (PizzaInfo pizza : pizzasList) {
                            JSONObject pizzaObj = new JSONObject();
                            pizzaObj.put("flavour", pizza.getFlavour());
                            pizzaObj.put("half", pizza.isHalfPie());
                            pizzaObj.put("made", pizza.isMade());
                            pizzas.put(pizzaObj);
                        }
                        nameValuePairs.add(new BasicNameValuePair("pizzas", pizzas.toString()));
                    } catch (Exception e) {
                    }
                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(url);
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.i(TAG, "Order post error");
                }
            }
        }).start();
    }

    protected void putOrder(OrderInfo o) {
        final String id = o.getId();
        final OrderInfo order = o;

        new Thread(new Runnable() {
            public void run() {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(13);
                nameValuePairs.add(new BasicNameValuePair("date", order.getDate()));
                nameValuePairs.add(new BasicNameValuePair("time", order.getTime()));
                nameValuePairs.add(new BasicNameValuePair("pizzaString", order.getPizzasString()));
                nameValuePairs.add(new BasicNameValuePair("pieces", String.valueOf(order.getPieces())));
                nameValuePairs.add(new BasicNameValuePair("name", order.getName()));
                nameValuePairs.add(new BasicNameValuePair("phone", order.getPhone()));
                nameValuePairs.add(new BasicNameValuePair("address", order.getAddress()));
                nameValuePairs.add(new BasicNameValuePair("note", order.getNote()));
                nameValuePairs.add(new BasicNameValuePair("halfBox", String.valueOf(order.isHalfBox())));
                nameValuePairs.add(new BasicNameValuePair("paid", String.valueOf(order.isPaid())));
                nameValuePairs.add(new BasicNameValuePair("taken", String.valueOf(order.isTaken())));
                try {
                    JSONArray pizzas = new JSONArray();
                    ArrayList<PizzaInfo> pizzasList = order.getPizzas();
                    for (PizzaInfo pizza : pizzasList) {
                        JSONObject pizzaObj = new JSONObject();
                        pizzaObj.put("flavour", pizza.getFlavour());
                        pizzaObj.put("half", pizza.isHalfPie());
                        pizzaObj.put("made", pizza.isMade());
                        pizzas.put(pizzaObj);
                    }
                    nameValuePairs.add(new BasicNameValuePair("pizzas", pizzas.toString()));
                } catch (Exception e) {
                }

                try {
                    URI url = new URI("http", null, "104.155.236.60", 8080, "/api/orders" + "/" + id, "id=10", "anchor");
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPut httpput = new HttpPut(url);
                    httpput.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httpput);
                } catch (Exception e) {
                    Log.i(TAG, "Order delete error");
                }
            }
        }).start();
    }

    private void deleteOrder(String id) {
        final String Id = id;
        new Thread(new Runnable() {
            public void run() {
                try {
                    URI url = new URI("http", null, "104.155.236.60", 8080, "/api/orders" + "/" + Id, "id=10", "anchor");
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpDelete httpdelete = new HttpDelete(url);
                    HttpResponse response = httpclient.execute(httpdelete);
                } catch (Exception e) {
                    Log.i(TAG, "Order delete error");
                }
            }
        }).start();
    }

    protected void refreshOrderList() {
        for (int i = 0; i < fragmentAdapter.getCount(); i++) {
            fragmentAdapter.getItem(i).updateAdapter(dateString, flavourString);
        }
    }

    protected SortedList<OrderInfo> getOrderList() {
        return orderList;
    }

    protected void removeOrder(int position) {
        if (orderList.size() < position || orderList.size() == 0) {
            return;
        }
        deleteOrder(orderList.get(position).getId());
    }

    protected void setOrderTaken(int position) {
        orderList.get(position).setTaken(true);
        onAppDataSave();
    }

    private void onChooseDate() {
        final DecimalFormat df = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateString = String.format("%s/%s/%s", df.format(year), df.format(monthOfYear + 1), df.format(dayOfMonth));
                refreshOrderList();
            }
        };
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(this, datePicker,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void onChooseFlavour() {
        Intent intent = new Intent(this, SetFlavoursActivity.class);
        intent.putExtra("flavourString", flavourString);
        startActivityForResult(intent, Constants.FLAVOUR_EDITED_RESULT);
    }

    public class OrderPagerAdapter extends FragmentPagerAdapter {

        private List<TabFragment> mFragments;
        private String[] mTitles;

        public OrderPagerAdapter(android.support.v4.app.FragmentManager fm, String[] titles) {
            super(fm);
            mTitles = titles;
            mFragments = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
                // Initialize tab fragment
                mFragments.add(TabFragment.newInstance(dateString, flavourString, i));
            }
        }

        @Override
        public TabFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
