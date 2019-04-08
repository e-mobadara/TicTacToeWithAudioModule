package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rick.tictactoe.utils.AudioFile;
import com.example.rick.tictactoe.audiomanaging.MainActivity;
import com.example.rick.tictactoe.audiomanaging.R;
import com.example.rick.tictactoe.utils.RecyclerItemClickListener;
import com.example.rick.tictactoe.audiomanaging.addAudioFile;
import com.example.rick.tictactoe.Adapter.myAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ExcellentActivity extends Fragment {

    private  static final String TAG="ExcellentActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<AudioFile> itemsData  = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton fab;
    private String current_folder = "excellent";
    private int _position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_excellent, container, false);

        fab = view.findViewById(R.id.excellent_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add", Snackbar.LENGTH_LONG)
                        .setAction("Add audio file", null).show();
                /**
                 * ici il faut appaler l'activité qui va ajouter un nouveau fichier audio
                 */
                Intent intent = new Intent(getContext(),addAudioFile.class);
                intent.putExtra("audio_type","excellent");
                startActivity(intent);
                getActivity().finish();
            }
        });


        // 1. get a reference to recyclerView
        recyclerView =  view.findViewById(R.id.excellent_my_recycler_view);
        // this is data fr  o recycler view
        /**
         * you need to add audio files here.
         */


        getSharedPreferencesData();

        /**
         * you can always add some defaults audio files here
         */

        /*
        AudioFile i = new AudioFile("winAudioFile","fileName") ;
        AudioFile j = new AudioFile("winAudioFile2","fileName2") ;
        itemsData.add(i);
        itemsData.add(j);
        */

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        myAdapter mAdapter = new myAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView = view.findViewById(R.id.excellent_my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new myAdapter(itemsData);
        mRecyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        Log.d(TAG, "RecyclerItemClickListener");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.d(TAG, " onLongItemClick");
                        _position = position;
                        /**
                         * ici on va impléménter le code de suppression d'un fichier audio séléctionné
                         */
                        AlertDialog ad = new AlertDialog.Builder(getContext())
                                .create();
                        ad.setCancelable(true);
                        ad.setTitle("Delete audio file");
                        ad.setMessage("Are you sure");
                        ad.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                AudioFile i  = itemsData.get(_position);
                                Log.d(TAG, " deleting audio file.");
                                File fileToDelete = new File (itemsData.get(_position).getaPath());
                                Log.d(TAG, itemsData.get(_position).getaPath());
                                fileToDelete.delete();
                                SharedPreferences preferences = getActivity().getSharedPreferences(
                                        current_folder+"_"+MainActivity.getLangue(), Activity.MODE_PRIVATE);
                                preferences.edit().remove(itemsData.get(_position).getaName()).commit();
                                reloadActivity();
                                dialog.dismiss();
                            }
                        });
                        ad.show();
                    }

                }));


        return view;
    }

    void getSharedPreferencesData(){

        SharedPreferences sharedPref = getActivity().getSharedPreferences(current_folder+"_"+MainActivity.getLangue(),
                Activity.MODE_PRIVATE);
        Map<String, ?> audio_files = sharedPref.getAll();
        /**
         * here we need to put all data in itemsData attribut
         */
        itemsData = getFilesFromInternalStorage(audio_files);
    }

    private List<AudioFile> getFilesFromInternalStorage(Map<String,?> audio_files) {
        /**
         *
         */
        Iterator mymapIterator = audio_files.keySet().iterator();
        List<AudioFile> afl = new ArrayList<>();
        AudioFile af ;
        while(mymapIterator.hasNext()){
            String key=(String)mymapIterator.next();
            String path = (String)audio_files.get(key);
            Log.d(TAG, ""+path);
            af = _getRealPathFromURI(path);
            if(af!=null){afl.add(af);}
        }

        return afl;
    }
    private AudioFile _getRealPathFromURI(String _path) {
        Log.d(TAG, "_getRealPathFromURI");
        AudioFile audio = new AudioFile();
        File f = new File(_path);
        audio.setaName(f.getName());
        Log.d(TAG, audio.getaName());
        audio.setaPath(_path);
        Log.d(TAG, audio.getaPath());
        return audio;
    }

    void reloadActivity(){
        Intent intent = new Intent(getContext(),MainActivity.class);
        intent.putExtra("langue",MainActivity.getLangue());
        startActivity(intent);
        getActivity().finish();
    }
}
