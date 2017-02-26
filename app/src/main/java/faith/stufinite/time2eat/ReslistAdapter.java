package faith.stufinite.time2eat;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/**
 * Created by McGodamn on 2016/12/29.
 */

public class ReslistAdapter extends RecyclerView.Adapter<ReslistAdapter.myViewHolder> {
    private List<RessFragment.Restaurent> userList;
    public static Context context;
    public ReslistAdapter(List<RessFragment.Restaurent> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.
                from(context).
                inflate(R.layout.cardview, parent, false);

        return new myViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        RessFragment.Restaurent user = userList.get(position);
        holder.resPic.setText(Integer.toString(user.getImageResourceId()));
        holder.resName.setText(user.getresName());
        holder.resPoint.setText(user.getresPoint());
        holder.resLove.setText(user.getresLove());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView resPic;
        TextView resName;
        TextView resPoint;
        TextView resLove;
        public myViewHolder(View itemView) {
            super(itemView);
            resPic = (TextView) itemView.findViewById(R.id.resPic);
            resName = (TextView) itemView.findViewById(R.id.resName);
            resPoint = (TextView) itemView.findViewById(R.id.resPoint);
            resLove = (TextView) itemView.findViewById(R.id.resLove);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int id = Integer.valueOf(resPic.getText().toString());
                    RessFragment.ResId = id;
                    TabFragment.ShopPager.setCurrentItem(1);
                }
            });
        }
    }
}