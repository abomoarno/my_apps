package afrimoov.tg.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import afrimoov.tg.R;
import afrimoov.tg.activities.Accueil;
import afrimoov.tg.activities.Chargement;
import afrimoov.tg.utilis.Live_Tv;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;
import afrimoov.tg.utilis.WebImageView;

public class TvRecyclerAdaptor extends RecyclerView.Adapter<TvRecyclerAdaptor.ViewHolder> {

    private List<Live_Tv> tvs;
    private Context context;
    private final OnItemClickListener listener;

    public TvRecyclerAdaptor(Context context, List<Live_Tv> tvs, OnItemClickListener listener) {
        this.context = context;
        this.tvs = tvs;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.bind(tvs.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        WebImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.tv_image);
        }

        void bind(final Live_Tv tv, final OnItemClickListener listener){
            Bitmap bitmap = Utils.loadImageBitmap(context,tv.getImage());
            if (bitmap != null)
                image.setImageBitmap(bitmap);
            else
                image.setImageUrl2("https://afrimoov.com/appsimages/" + tv.getImage());

            //Picasso.get().load("https://www.afrimoov.com/crtvnews/images/" + tv.getImage())
                    //.into(image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tv);
                }
            });
        }
    }

    public interface OnItemClickListener{

        void onItemClick(Live_Tv tv);

    }

}
