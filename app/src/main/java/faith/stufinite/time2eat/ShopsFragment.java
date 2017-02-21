package faith.stufinite.time2eat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by McGodamn on 2017/2/19.
 */

public class ShopsFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private RecyclerView mRecyclerView;
    private int position;
    private MainActivity mContext;
    private ReslistAdapter ResAdapter;
    public static ShopsFragment newInstance(int position) {
        ShopsFragment f = new ShopsFragment();
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
        JSONArray arr = null;

        View v = inflater.inflate(R.layout.recyclerlayout, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        String url = "http://10.0.2.2:8000/t2e/api/restaurant/list/";
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
        switch (position)
        {
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
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                ResAdapter = new ReslistAdapter(getResInfor(arr), mContext);
                mRecyclerView.setAdapter(ResAdapter);
                fl.addView(mRecyclerView);
                break;
            default:
                TextView t = new TextView(getContext());
                t.setText(Integer.toString(position));
                fl.addView(t);
                break;
        }
        return fl;
    }

    private List<ShopsFragment.Restaurent> getResInfor(JSONArray arr)
    {
        List<ShopsFragment.Restaurent> ResList = new ArrayList<>();
        try
        {
            ResList.add(new ShopsFragment.Restaurent(1,arr.getJSONObject(0).getString("ResName"),arr.getJSONObject(0).getString("score"),arr.getJSONObject(0).getString("ResLike")));
            ResList.add(new ShopsFragment.Restaurent(2,"fuck","0","0"));
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
