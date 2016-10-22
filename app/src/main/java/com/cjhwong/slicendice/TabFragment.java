package com.cjhwong.slicendice;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {
    private final String TAG = "TabFragment";

    private MainActivity mContext;
    private OrderListAdapter adapter;
    private RecyclerView orderCardList;
    private int position;

    public static TabFragment newInstance(String date, String flavour, int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("date", date);
        b.putString("flavour", flavour);

        TabFragment f = new TabFragment();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, position + " - onViewCreated");
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderCardList = (RecyclerView) view.findViewById(R.id.cardList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = (MainActivity) getActivity();

        orderCardList.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager glm = new GridLayoutManager(mContext, 2);
            glm.setOrientation(GridLayoutManager.VERTICAL);
            orderCardList.setLayoutManager(glm);
        } else {
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            orderCardList.setLayoutManager(llm);
        }

        Bundle b = getArguments();
        adapter = new OrderListAdapter(mContext,
                b.getString("date"),
                b.getString("flavour"),
                position
        );
        orderCardList.setAdapter(adapter);
    }

    public void updateAdapter(String date, String flavour) {
        if (adapter != null) {
            Log.i(TAG, "Adapter updated");
            adapter.dateString = date;
            adapter.flavourString = flavour;
            adapter.notifyDataSetChanged();
        } else {
            Log.i(TAG, "Adapter undefined");
        }
    }
}
