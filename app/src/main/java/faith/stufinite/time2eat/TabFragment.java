package faith.stufinite.time2eat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private MainActivity mContext;
    public static ViewPager ShopPager;

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

        mContext = (MainActivity) getActivity();
        FrameLayout fl = new FrameLayout(mContext);
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.respage,container,false);
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        FrameLayout fl = new FrameLayout(getActivity());
//        fl.setLayoutParams(params);

//        Log.d("position","" + position);
        switch (position) {
            case 0:
                //ShopPager = (ViewPager) mContext.findViewById(R.id.pager);
                ShopPager = (ViewPager) rl.findViewById(R.id.respager);
                ShopPager.setAdapter(new ShopPagerAdapter(getChildFragmentManager()));
                fl.addView(rl);
                ShopPager.setCurrentItem(0);
                return fl;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        return fl;
    }

    public class ShopPagerAdapter extends FragmentPagerAdapter {

        public ShopPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return ShopsFragment.newInstance(position);
        }
    }
   }