package faith.stufinite.time2eat;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private RecyclerView mRecyclerView;
    private int position;
    private MainActivity mContext;
    private MyAdapter ResAdapter;

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


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.recyclerlayout, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        JSONArray arr = null;
        mContext = (MainActivity) getActivity();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
        View v = inflater.inflate(R.layout.recyclerlayout, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        String url = "http://10.0.2.2:8000/t2e/api/restaurant/list/";
        FrameLayout.LayoutParams ButtonParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        Log.d("position","" + position);
        StringBuilder sb = new StringBuilder();
        switch (position) {
            case 0:
                try {
                    HttpAsyncTask at = new HttpAsyncTask(url);
                    at.execute();
                    while (!at.check)
                        ;
                    arr = new JSONArray(at.result);
                    Log.d("result",at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Button shop = new Button(getActivity());
//                shop.setText("See More");
//                shop.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("餐廳名稱：");
//                    }
//                });
//                fl.addView(shop,ButtonParams);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                ResAdapter = new MyAdapter(getResInfor(arr), mContext);
                break;
            case 1:
//                vv.setText("這是今天抽獎的地方");
                break;
            case 2:
                //vv.setText("這是附近餐廳的地方");
                try {
                    HttpAsyncTask at = new HttpAsyncTask(url);
                    at.execute();
                    while (!at.check)
                        ;
                    arr = new JSONArray(at.result);
                    Log.d("result",at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Button shop = new Button(getActivity());
//                shop.setText("See More");
//                shop.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("餐廳名稱：");
//                    }
//                });
//                fl.addView(shop,ButtonParams);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                ResAdapter = new MyAdapter(getResInfor(arr), mContext);
                break;
            default:
                break;
        }
        mRecyclerView.setAdapter(ResAdapter);
        fl.addView(mRecyclerView);
        return fl;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mContext = (MainActivity) getActivity();
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        Bundle b = getArguments();
//        JSONArray arr = null;
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        FrameLayout fl = new FrameLayout(getActivity());
//        fl.setLayoutParams(params);
//        String url = "http://10.0.2.2:8000/t2e/api/restaurant/list/";
//        FrameLayout.LayoutParams ButtonParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        HttpAsyncTask at = new HttpAsyncTask(url);
//        at.execute();
//        switch (position) {
//            case 0:
//                StringBuilder sb = new StringBuilder();
//                try {
//                    while (!at.check)
//                        ;
//                    at.check = false;
//                    arr = new JSONArray(at.result);
//                    Log.d("result",at.result);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                Button shop = new Button(getActivity());
////                shop.setText("See More");
////                shop.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        StringBuilder sb = new StringBuilder();
////                        sb.append("餐廳名稱：");
////                    }
////                });
////                fl.addView(shop,ButtonParams);
//                ResAdapter = new MyAdapter(getResInfor(arr), mContext);
//                mRecyclerView.setAdapter(ResAdapter);
//                break;
//            case 1:
////                vv.setText("這是今天抽獎的地方");
//                break;
//            case 2:
//                //               vv.setText("這是附近餐廳的地方");
//                break;
//            default:
//                break;
//        }
//    }

    private List<Restaurent> getResInfor(JSONArray arr)
    {
        List<Restaurent> ResList = new ArrayList<>();
        try
        {
            ResList.add(new Restaurent(1,arr.getJSONObject(0).getString("ResName"),arr.getJSONObject(0).getString("score"),arr.getJSONObject(0).getString("ResLike")));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return ResList;
    }

    public class Restaurent
    {
        private int imageResourceId;
        private String resName;
        private String resPoint;
        private String resLove;

        public int getImageResourceId() {
            return imageResourceId;
        }
        public String getresName() {
            return resName;
        }
        public String getresPoint() {
            return resPoint;
        }
        public String getresLove() {
            return resLove;
        }

        public Restaurent(int imageResourceId, String name, String point, String love) {
            this.imageResourceId = imageResourceId;
            this.resName = name;
            this.resPoint = point;
            this.resLove = love;
        }
    }
   }