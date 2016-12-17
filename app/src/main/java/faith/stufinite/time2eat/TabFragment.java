package faith.stufinite.time2eat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    public static TabFragment newInstance(int position) {
        TabFragment f = new TabFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        JSONArray arr = null;
        InputStream is = null;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
        TextView v = new TextView(getActivity());
        v.setLayoutParams(params);
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
//        v.setBackgroundResource(R.color.windowBackground);
//        v.setText("Tab " + (position + 1));
        String url = "http://10.0.2.2:8000/t2e/api/restaurant/list/";
        HttpAsyncTask at = new HttpAsyncTask(url);
        at.execute();
        switch (position) {
            case 0:
                StringBuilder sb = new StringBuilder();
                try {
                    while (!at.check)
                        ;
                    at.check = false;
                    arr = new JSONArray(at.result);
                    Log.d("result",at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sb.append("餐廳名稱：");
                    sb.append(arr.getJSONObject(0).getString("ResName"));
                    sb.append("\n分數：");
                    sb.append(arr.getJSONObject(0).getString("score"));
                    sb.append("\n喜歡人數：");
                    sb.append(arr.getJSONObject(0).getString("ResLike"));
                    sb.append("\n照片：\n");
                    sb.append(arr.getJSONObject(0).getString("avatar"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                v.setText(sb.toString());
                break;
            case 1:
                v.setText("這是今天抽獎的地方");
                break;
            case 2:
                v.setText("這是附近餐廳的地方");
                break;
            default:
                break;
        }
        fl.addView(v);
        return fl;
    }
   }