package faith.stufinite.time2eat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by McGodamn on 2017/2/19.
 */

public class RessFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private RecyclerView mRecyclerView;
    private int position;
    private MainActivity mContext;
    private ReslistAdapter ResAdapter;
    public static int ResId = 0;
    public static ViewPager resdetailpaper;
    public static JSONArray arr = null;
    public static JSONObject menu = null,resdata = null,payload = null;
    public static RessFragment newInstance(int position) {
        RessFragment f = new RessFragment();
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
        mContext = (MainActivity) getActivity();
        HttpAsyncTask at;
        String url = "http://10.0.2.2:8000/t2e/api/restaurant/list/",menuurl = null;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
        switch (position)
        {
            case 0:
                View v = inflater.inflate(R.layout.recyclerlayout, container, false);
                mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
                try {
                    at = new HttpAsyncTask(url);
                    at.execute();
                    while (!at.check)
                        ;
                    arr = new JSONArray(at.result);
                    Log.d("result",at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                ResAdapter = new ReslistAdapter(getResInfor(arr), mContext);
                mRecyclerView.setAdapter(ResAdapter);
                fl.addView(mRecyclerView);
                break;
            case 1:
                RelativeLayout detaillayout = (RelativeLayout) inflater.inflate(R.layout.resdetail,container,false);
                url = "http://10.0.2.2:8000/t2e/api/restaurant/prof/?res_id=" + (ResId + 1);
                menuurl = "http://10.0.2.2:8000/t2e/api/restaurant/menu/?res_id=" + (ResId + 1);
                try { //取得餐廳資料
                    at = new HttpAsyncTask(url);
                    at.execute();
                    while (!at.check)
                        ;
                    resdata = new JSONObject(at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try { //取得菜單
                    at = new HttpAsyncTask(menuurl);
                    at.execute();
                    while (!at.check)
                        ;
                    menu = new JSONObject(at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView title = (TextView) detaillayout.findViewById(R.id.ResTitle);
                TextView menutextv = (TextView) detaillayout.findViewById(R.id.ResData);
                try {
                    title.setText(resdata.getString("ResName"));
                    StringBuilder sb = new StringBuilder();
                    sb.append("菜單：\n");
                    sb.append(menu.getJSONArray("dish").getJSONObject(0).getString("name"));
//                            .getJSONArray("dish").getJSONObject(0).getString("name"));
                    menutextv.setText(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Button orderbutton = (Button) detaillayout.findViewById(R.id.orderbutton);
                orderbutton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        TabFragment.ShopPager.setCurrentItem(2);
                    }
                });
//                RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.resdetailpaper, container, false);
//                resdetailpaper =  (ViewPager) rl.findViewById(R.id.resdetailpaper);
                //resdetailpaper.setAdapter(new RessFragment.ResdetailPagerAdapter(getActivity().getSupportFragmentManager()));
                fl.addView(detaillayout);
                break;
            case 2:
                Log.d("yeaah","fuck");
                RelativeLayout rl = new RelativeLayout(getActivity());
                TableLayout tl = new TableLayout(getActivity());
                TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
                tl.setLayoutParams(lp);
                int dishn = 0;
                try {
                    dishn = menu.getJSONArray("dish").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                View dishrow;
                for (int i = 0;i < dishn ;i++)
                {
                    dishrow  = inflater.inflate(R.layout.dishtablerow, null,false);
                    TextView tv = (TextView) dishrow.findViewById(R.id.dish);
                    try {
                        tv.setText(menu.getJSONArray("dish").getJSONObject(0).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tl.addView(dishrow);
                }
                rl.addView(tl);
                Button sendButton = new Button(getActivity());
                sendButton.setText("OK");
                sendButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {

                    }
                });
                rl.addView(sendButton);
                fl.addView(rl);
                break;
            case 3:
                break;
            default:
                break;
        }
        return fl;
    }

    private List<RessFragment.Restaurent> getResInfor(JSONArray arr)
    {
        List<RessFragment.Restaurent> ResList = new ArrayList<>();
        try
        {
            ResList.add(new RessFragment.Restaurent(1,arr.getJSONObject(0).getString("ResName"),arr.getJSONObject(0).getString("score"),arr.getJSONObject(0).getString("ResLike")));
            ResList.add(new RessFragment.Restaurent(2,"fuck","0","0"));
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

    public class ResdetailPagerAdapter extends FragmentPagerAdapter {

        public ResdetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            return Resdetail.newInstance(position,ResId);
        }
    }
}
