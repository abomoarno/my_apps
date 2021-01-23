package wiredstartups.yakjayexpo.rooms;

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
import wiredstartups.yakjayexpo.utils.Room;
import wiredstartups.yakjayexpo.utils.Utils;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.ROOM_ID_LABEL;

public class Choose_Room extends Fragment implements AdapterView.OnItemClickListener {

    private Rooms context;
    private ListView listView;
    private ProgressDialog dialog;
    private List<Room> rooms;

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
        View view = inflater.inflate(R.layout.choose_room, container, false);
        context = (Rooms) getActivity();
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

        context.setTitre("Choix de la Salle");
        context.toggleHomeButton(true);

        rooms = new ArrayList<>();

        rooms.add(new Room(1,"SALE 1"));
        rooms.add(new Room(2,"SALE DEUX"));
        rooms.add(new Room(3,"SALE DES FETES"));

        RoomAdapter adapter = new RoomAdapter(context, rooms);
        listView.setAdapter(adapter);

        //getEntries();


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        preferences.edit().putInt(ROOM_ID_LABEL, rooms.get(i).getRoom_id()).apply();
        context.onBackPressed();
    }

    private void getEntrieRooms(){

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String data = intent.getStringExtra("result");
                dialog.dismiss();

                try{
                    JSONArray jsonObject = new JSONArray(data);
                    rooms = new ArrayList<>();

                    for (int position = 0; position<jsonObject.length(); position++){
                        JSONObject object = jsonObject.getJSONObject(position);
                        Room room = new Room();

                        room.setRoom_id(object.getInt("entry_id"));
                        room.setRoom_name(object.getString("entry_name"));

                        rooms.add(room);
                    }

                    RoomAdapter adapter = new RoomAdapter(context, rooms);
                    listView.setAdapter(adapter);

                }
                catch (Exception exception){
                    exception.printStackTrace();

                }

                finally {
                    context.unregisterReceiver(this);
                }

            }
        }, new IntentFilter("get_rooms"));

        new Utils.SendToServer(context)
                .execute("https://dirlabasexpo.com/admin/yakjay.php&cmd=rooms","get_rooms");

    }

}
