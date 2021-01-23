package wiredstartups.yakjayexpo.entry_exit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Entry;

public class EntryAdapter extends ArrayAdapter<Entry> {

    private List<Entry> entries;
    private Context context;

    public EntryAdapter(Context context, List<Entry> entries){
        super(context, R.layout.entry_item, entries);

        this.entries = entries;
        this.context = context;
    }

    class EntryHolder{
        TextView name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view = convertView;
       EntryHolder holder;

       if (view == null){
           view = LayoutInflater.from(context).inflate(R.layout.entry_item,null, false);
           holder = new EntryHolder();
           holder.name = view.findViewById(R.id.entry_name);

           view.setTag(holder);
       }
       else {
           holder = (EntryHolder)view.getTag();
       }

       holder.name.setText(entries.get(position).getEntry_name());

       return view;
    }
}
