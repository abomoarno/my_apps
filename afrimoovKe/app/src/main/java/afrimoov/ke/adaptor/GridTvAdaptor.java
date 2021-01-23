package afrimoov.ke.adaptor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import afrimoov.ke.R;
import afrimoov.ke.utilis.Live_Tv;
import afrimoov.ke.utilis.Utils;
import afrimoov.ke.utilis.WebImageView;

public class GridTvAdaptor extends BaseAdapter {

    private List<Live_Tv> tvs;
    private Activity context;


    public GridTvAdaptor(List<Live_Tv> tvs, Activity context) {
        this.tvs = tvs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tvs.size();
    }

    @Override
    public Live_Tv getItem(int position) {
        return tvs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Live_Tv tv = tvs.get(position);
        ViewHolder holder;
        View view = convertView;

        if (view == null){
            holder = new ViewHolder();
            view = context.getLayoutInflater().inflate(R.layout.tv_grid_view,null,true);
            holder.image = view.findViewById(R.id.tv_image);

            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();
        Bitmap bitmap = Utils.loadImageBitmap(context,tv.getImage());

        if (bitmap != null)
            holder.image.setImageBitmap(bitmap);
        else
            holder.image.setImageUrl2("https://afrimoov.com/appsimages/" + tv.getImage());


        return view;
    }

    private static class ViewHolder{
        WebImageView image;
    }
}
