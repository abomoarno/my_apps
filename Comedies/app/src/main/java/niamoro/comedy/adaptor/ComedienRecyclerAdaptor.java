package niamoro.comedy.adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Utils;
import niamoro.comedy.utilis.WebImageView;

public class ComedienRecyclerAdaptor extends RecyclerView.Adapter<ComedienRecyclerAdaptor.ViewHolder> {
    private List<Comedien> comediens;
    private Context context;
    private final OnItemClickListener listener;

    private boolean compactMode;

    public ComedienRecyclerAdaptor(final Context context, List<Comedien> comediens, OnItemClickListener listener) {
        this.context = context;
        this.comediens = comediens;
        this.listener = listener;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        compactMode = preferences.getBoolean(Utils.COMPACT_MODE,false);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comedien_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(comediens.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return comediens.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        WebImageView image;
        TextView name;
        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.comedien_image);
            name = itemView.findViewById(R.id.name);
        }

        void bind(final Comedien comedien, final OnItemClickListener listener){

            Bitmap bitmap = Utils.loadImageBitmap(context, comedien.getImage());

            if (bitmap != null)
                image.setImageBitmap(bitmap);
            else
                image.setImageUrl2("https://afrimoov.com/comediens/images/" + comedien.getImage());
            String title = comedien.getNom();
            title = title.substring(0,1).toUpperCase() + title.substring(1);
            name.setText(title);

            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
            name.setTypeface(tf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(comedien);
                }
            });

        }
    }

    public interface OnItemClickListener{

        void onItemClick(Comedien comedien);

    }

}
