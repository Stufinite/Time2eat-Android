package faith.stufinite.time2eat;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by McGodamn on 2016/12/29.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
    private List<TabFragment.Restaurent> userList;
    private Context context;
    public MyAdapter(List<TabFragment.Restaurent> userList, Context context) {
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
        TabFragment.Restaurent user = userList.get(position);

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
        }
    }
}