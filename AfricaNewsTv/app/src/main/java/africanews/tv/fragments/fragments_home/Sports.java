package africanews.tv.fragments.fragments_home;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import africanews.tv.R;
import africanews.tv.activities.Home;
import africanews.tv.adapters.TvListAdapter;
import africanews.tv.entities.TvChannel;
import africanews.tv.utils.Utils;

import static africanews.tv.utils.Utils.SERVER_RESULT;
import static africanews.tv.utils.Utils.decodeTvFromObject;

public class Sports extends Fragment implements AdapterView.OnItemClickListener {
    private Home mContext;
    private List<TvChannel> tvs;

    private ListView listView;
    private TvListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_tvs_view, container, false);

        mContext = (Home) getActivity();

        listView = view.findViewById(R.id.list_tvs);

        tvs = new ArrayList<>();

        adapter = new TvListAdapter(mContext, tvs);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        getTvs();

        return view;
    }

    private void getTvs(){

        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setMessage("Patientez un instant ...");
        dialog.setTitle("Chargement");

        dialog.show();

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String data = intent.getStringExtra(SERVER_RESULT);

                Log.e("SERVER RESPONSE",data);

                try {
                    JSONArray array = new JSONArray(data);
                    tvs = new ArrayList<>();

                    for (int position=0;position<array.length();position++){
                        JSONObject object = array.getJSONObject(position);

                        tvs.add(decodeTvFromObject(object, mContext));
                    }

                    adapter.notifyDataSetChanged();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    dialog.dismiss();
                    context.unregisterReceiver(this);
                }

            }
        }, new IntentFilter("get_list_tvs"));

        new Utils.SendToServer(mContext).execute(
                getString(R.string.tvs_remote_link) + "sports",
                "get_list_tvs"
        );

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TvChannel tv = tvs.get(i);

    }
}
