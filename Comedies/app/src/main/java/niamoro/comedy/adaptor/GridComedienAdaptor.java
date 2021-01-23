package niamoro.comedy.adaptor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Utils;
import niamoro.comedy.utilis.WebImageView;

public class GridComedienAdaptor extends BaseAdapter {

    private List<Comedien> comediens;
    private Activity context;


    public GridComedienAdaptor(List<Comedien> comediens, Activity context) {
        this.comediens = comediens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comediens.size();
    }

    @Override
    public Comedien getItem(int position) {
        return comediens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comedien comedien = comediens.get(position);
        ViewHolder holder;
        View view = convertView;

        if (view == null){
            holder = new ViewHolder();
            view = context.getLayoutInflater().inflate(R.layout.comedien_grid_view,null,true);
            holder.image = view.findViewById(R.id.comedien_image);
            holder.name = view.findViewById(R.id.name);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();

        Bitmap bitmap = Utils.loadImageBitmap(context, comedien.getImage());

        if (bitmap != null)
            holder.image.setImageBitmap(bitmap);
        else
            holder.image.setImageUrl2("https://afrimoov.com/comediens/images/" + comedien.getImage());

        holder.name.setText(comedien.getNom());
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        holder.name.setTypeface(tf);
        return view;
    }
    private static class ViewHolder{
        WebImageView image;
        TextView name;
    }
}
