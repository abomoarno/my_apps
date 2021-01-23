package wiredstartups.yakjayexpo.entry_exit;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Entry;
import wiredstartups.yakjayexpo.utils.Utils;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.ENTRY_ID_LABEL;

public class Choose_Entry extends Fragment implements AdapterView.OnItemClickListener {

  private EntryExit context;
  private ListView listView;
  private ProgressDialog dialog;
  private List<Entry> entries;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onPrepareOptionsMenu(@NonNull Menu menu) {
    menu.findItem(R.id.action_settings).setVisible(false);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.choose_entry, container, false);
    context = (EntryExit)getActivity();
    listView = view.findViewById(R.id.list_view);
    listView.setOnItemClickListener(this);

    dialog = new ProgressDialog(context);
    dialog.setTitle("Chargement");
    dialog.setMessage("Patientez un instant");
    dialog.setCancelable(false);

    context.toggleFabs(false);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();

    context.setTitre("Choix de la position");
    context.toggleHomeButton(true);

    entries = new ArrayList<>();

    Entry entry = new Entry();
    entry.setEntry_name("Entrée 1");
    entry.setEntry_id(1);
    entries.add(entry);
    Entry entry1 = new Entry();
    entry1.setEntry_name("Entrée 2");
    entry1.setEntry_id(2);
    entries.add(entry1);

    EntryAdapter adapter = new EntryAdapter(context, entries);
    listView.setAdapter(adapter);

    //getEntries();

  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    preferences.edit().putInt(ENTRY_ID_LABEL,entries.get(i).getEntry_id()).apply();
    context.onBackPressed();
  }

  private void getEntries(){

    context.registerReceiver(new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {

        String data = intent.getStringExtra("result");
        dialog.dismiss();

        try{
          JSONArray jsonObject = new JSONArray(data);
          entries = new ArrayList<>();

          for (int position = 0; position<jsonObject.length(); position++){
            JSONObject object = jsonObject.getJSONObject(position);
            Entry entry = new Entry();

            entry.setEntry_id(object.getInt("entry_id"));
            entry.setEntry_name(object.getString("entry_name"));

            entries.add(entry);
          }

          EntryAdapter adapter = new EntryAdapter(context, entries);
          listView.setAdapter(adapter);

        }
        catch (Exception exception){
          exception.printStackTrace();

        }

        finally {
          context.unregisterReceiver(this);
        }

      }
    }, new IntentFilter("get_entries"));

    new Utils.SendToServer(context)
            .execute("https://dirlabasexpo.com/admin/yakjay.php&cmd=entries","get_entries");

  }

}
