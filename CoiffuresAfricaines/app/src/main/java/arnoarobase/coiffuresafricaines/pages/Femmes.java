package arnoarobase.coiffuresafricaines.pages;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.activities.PhotoView;
import arnoarobase.coiffuresafricaines.activities.Results;
import arnoarobase.coiffuresafricaines.adaptors.PhotoListAdater;
import arnoarobase.coiffuresafricaines.calsses.Photo;
import arnoarobase.coiffuresafricaines.db_mangers.PhotosManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Femmes extends Fragment implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private List<Photo> photos;
    private Results activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Results)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.grid_photos, container, false);
        photos = new PhotosManager(activity).getGenre("f",25);

        gridView = view.findViewById(R.id.gridView);
        PhotoListAdater adater = new PhotoListAdater(activity,photos);
        gridView.setAdapter(adater);
        gridView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Photo photo = photos.get(i);
        Intent intent = new Intent(activity, PhotoView.class);
        intent.putExtra("photo_id",photo.getId());
        intent.putExtra("photo_genre",photo.getGenre());
        startActivity(intent);
    }
}
