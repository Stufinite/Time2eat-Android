package faith.stufinite.time2eat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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

/**
 * Created by McGodamn on 2017/2/22.
 */

public class Resdetail extends Fragment{
    private static final String ARG_POSITION = "position";
    private static final String ResID = "choice";
    private int position;
    private int ResId;
    private MainActivity mContext;
    public static JSONArray arr = null,menuarr = null;

    public static TabFragment newInstance(int position,int ResId) {
        TabFragment f = new TabFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putInt(ResID,ResId);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        ResId = getArguments().getInt(ResID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        String url = null,menuurl = null;
        mContext = (MainActivity) getActivity();
        HttpAsyncTask at;
        FrameLayout fl = new FrameLayout(mContext);
        switch (position) {
            case 0:
                RelativeLayout detaillayout = (RelativeLayout) inflater.inflate(R.layout.resdetail,container,false);
                url = "http://10.0.2.2:8000/t2e/api/restaurant/prof/?res_id=" + ResId + 1;
                menuurl = "http://127.0.0.1:8000/t2e/api/restaurant/list?start=1" + ResId + 1;
                try { //取得餐廳資料
                    at = new HttpAsyncTask(url);
                    at.execute();
                    while (!at.check)
                        ;
                    arr = new JSONArray(at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try { //取得菜單
                    at = new HttpAsyncTask(menuurl);
                    at.execute();
                    while (!at.check)
                        ;
                    menuarr = new JSONArray(at.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView title = (TextView) detaillayout.findViewById(R.id.ResTitle);
                TextView menu = (TextView) detaillayout.findViewById(R.id.ResData);
                try {
                    title.setText(arr.getJSONObject(0).getString("Resname"));
                    StringBuilder sb = new StringBuilder();
                    sb.append("菜單：");
                    sb.append(menuarr.getJSONObject(0).getJSONArray("dish").getJSONObject(0).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Button orderbutton = (Button) detaillayout.findViewById(R.id.orderbutton);
                orderbutton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        RessFragment.resdetailpaper.setCurrentItem(1);
                    }
                });
                break;
            case 1:
                RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.pickdish,container,false);
                TableLayout tl = new TableLayout(getActivity());
                TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
                tl.setLayoutParams(lp);
                int dishn = 0;
                try {
                    dishn = menuarr.getJSONObject(0).getJSONArray("dish").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0;i < dishn ;i++)
                {
//                    tl.addView();
                }
                break;
            case 2:
                break;
        }
        return fl;
    }
}
