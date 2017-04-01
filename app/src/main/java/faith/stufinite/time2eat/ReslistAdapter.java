package faith.stufinite.time2eat;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public void onBindViewHolder(final myViewHolder holder, int position) {
        RessFragment.Restaurent user = userList.get(position);
        //holder.resPic.setText(Integer.toString(user.getImageResourceId()));
        Log.d("fuck", user.getImageResource());
//        new AsyncTask<String, Void, Bitmap>()
//        {
//            @Override
//            protected Bitmap doInBackground(String... params) {
//                String url = params[0];
//                return getBitmapFromURL(url);
//            }
//            @Override
//            protected void onPostExecute(Bitmap result)
//            {
//                holder.resPic.setImageBitmap (result);
//                super.onPostExecute(result);
//            }
//        }.execute(user.getImageResource());
        holder.resId.setText(user.getresId());
        holder.resName.setText(user.getresName());
        holder.resPoint.setText(user.getresPoint());
        holder.resLove.setText(user.getresLove());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        ImageView resPic;
        TextView resId;
        TextView resName;
        TextView resPoint;
        TextView resLove;
        public myViewHolder(View itemView) {
            super(itemView);
            resId = (TextView)itemView.findViewById(R.id.resId);
            resPic = (ImageView) itemView.findViewById(R.id.resPic);
            resName = (TextView) itemView.findViewById(R.id.resName);
            resPoint = (TextView) itemView.findViewById(R.id.resPoint);
            resLove = (TextView) itemView.findViewById(R.id.resLove);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int id = Integer.valueOf(resId.getText().toString());
                    RessFragment.ResId = id;
                    TabFragment.ShopPager.setCurrentItem(1);
                }
            });
        }
    }

    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}