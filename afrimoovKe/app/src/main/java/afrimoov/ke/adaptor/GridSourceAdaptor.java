package afrimoov.ke.adaptor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import afrimoov.ke.R;
import afrimoov.ke.utilis.Source;
import afrimoov.ke.utilis.Utils;
import afrimoov.ke.utilis.WebImageView;

public class GridSourceAdaptor extends BaseAdapter {

    private List<Source> sources;
    private Activity context;


    public GridSourceAdaptor(List<Source> sources, Activity context) {
        this.sources = sources;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sources.size();
    }

    @Override
    public Source getItem(int position) {
        return sources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Source source = sources.get(position);
        ViewHolder holder;
        View view = convertView;

        if (view == null){
            holder = new ViewHolder();
            view = context.getLayoutInflater().inflate(R.layout.source_grid_view,null,true);
            holder.image = view.findViewById(R.id.replay_image);
            holder.name = view.findViewById(R.id.titre);
            holder.pays = view.findViewById(R.id.pays);
            holder.replays = view.findViewById(R.id.replays);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();

        Bitmap bitmap = Utils.loadImageBitmap(context,source.getImage());

        if (bitmap != null)
            holder.image.setImageBitmap(bitmap);
        else
            holder.image.setImageUrl2("https://afrimoov.com/appsimages/" + source.getImage());

        String title = "Replays "+ source.getNom();
        holder.name.setText(title);
        String pays = source.getPays();
        pays = pays.substring(0,1).toUpperCase() + pays.substring(1);
        holder.pays.setText(pays);
        String nbReplay = (source.getNbr_replays()>0) ? source.getNbr_replays() + " videos" : source.getNbr_replays() + " video";
        holder.replays.setText(nbReplay);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        holder.name.setTypeface(tf);
        holder.replays.setTypeface(tf);
        holder.pays.setTypeface(tf);

        return view;
    }

    private static class ViewHolder{
        WebImageView image;
        TextView name;
        TextView pays;
        TextView replays;
    }
}
