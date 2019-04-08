package com.example.rick.tictactoe.audiomanaging;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rick.tictactoe.utils.AudioFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class addAudioFile extends AppCompatActivity {

    TextView fileName ;
    Button chercherAudio, confirmer;
    AudioFile audio = new AudioFile("Path", "file name");
    private static final int PICK_AUDIO = 100 ;
    private static final int PERMISSION_CODE = 1001 ;

    private static final String TAG = "changer un fich_audio" ;

    private String outputFileDR, current_folder;
    private String folder_excellent = "excellent", folder_good = "good", folder_encouragement = "encouragement",root_folder = "Audio_Files";
    File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_audio_file);
        Log.d(TAG, "on create");

        fileName = findViewById(R.id.file_name);
        chercherAudio = findViewById(R.id.btn_pick_audio);
        confirmer = findViewById(R.id.btn_confirmer);

        chercherAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** ici, il faut chercher le fichier audio:
                 *lister les fichiers audio (mp3, wav, gp3)
                 *verifier son type
                 *charger son emplacement dans une variable audio.aPath / path
                 * charger son nom dans une variable audio.aName
                 **/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{
                        openGallery();
                    }
                }else{
                    openGallery();
                }
            }
        });

        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Au cas de confirmation du fichier audio, on doit :
                 * verifier la présence d'un répértoire "audio files" dans "internal storage",créer le au cas d'absence.
                 * Vérifier si le fichier et à l'interieur du répértoire "audio files",créer le au cas d'absence.
                 * valider l'opération et dériger l'utilisateur vers l'activité "changeAudio".
                 */
                String audio_type = getIntent().getStringExtra("audio_type");
                if(audio_type.equals(folder_excellent)||audio_type.equals(folder_good)||audio_type.equals(folder_encouragement)) {
                    current_folder = audio_type;
                    if(creerAppFolder()) {
                        if(creerRootFolder()){
                            if (creerFolder(current_folder)) {
                                saveFile();
                                saveSharedPreferences();
                            }
                        }
                    }
                    reloadActivity();
                }
            }
        });


    }

    void saveSharedPreferences(){

        SharedPreferences sharedPref = getSharedPreferences(current_folder+"_"+MainActivity.getLangue(),
                Activity.);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(audioFile.getName(),
                Environment.getExternalStorageDirectory() +"/com.example.rick.tictactoe.audiomanaging/"+
                        root_folder+"/"+current_folder+"/"+audioFile.getName());
        editor.commit();
    }

    void saveFile(){
        outputFileDR = Environment.getExternalStorageDirectory() +"/com.example.rick.tictactoe.audiomanaging/"+
                root_folder+"/"+current_folder+"/";
        // the file to be moved or copied
        File sourceLocation = new File ( audioFile.getPath());
        // make sure your target location folder exists!
        File targetLocation = new File (outputFileDR,audioFile.getName());
        // just to take note of the location sources
        Log.v(TAG, "sourceLocation: " + sourceLocation);
        Log.v(TAG, "targetLocation: " + targetLocation);
        try {
                // make sure the target file exists
                if(sourceLocation.exists()){
                    InputStream in = new FileInputStream(audioFile.getPath());
                    OutputStream out = new FileOutputStream(targetLocation);
                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    Log.v(TAG, "Copy file successful.");
                }else{
                    Log.v(TAG, "Copy file failed. Source file missing.");
                }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.v(TAG, "NullPointerException "+e);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "Exception "+e);
        }
    }

    void changeTextView(){
        fileName.setText(audio.getaName());
    }

    boolean creerFolder(String folder_name){

        File folder = new File(Environment.getExternalStorageDirectory() +"/com.example.rick.tictactoe.audiomanaging/"+
                root_folder+ "/"+folder_name);
        boolean success = true;
        if (!folder.exists()) {
            Toast.makeText(this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
            success = folder.mkdir();
        }
        if (success) {
            Toast.makeText(this, "Directory Created", Toast.LENGTH_SHORT).show();
            return success;
        } else {
            Toast.makeText(this, "Failed - Error", Toast.LENGTH_SHORT).show();
            return success;
        }

    }
    boolean creerRootFolder(){

        File folder = new File(Environment.getExternalStorageDirectory() +"/com.example.rick.tictactoe.audiomanaging/"+
                root_folder);
        boolean success = true;
        if (!folder.exists()) {
            Toast.makeText(this, "Root directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
            success = folder.mkdir();
        }
        if (success) {
            Toast.makeText(this, "Directory Created", Toast.LENGTH_SHORT).show();
            return success;
        } else {
            Toast.makeText(this, "Failed - Error", Toast.LENGTH_SHORT).show();
            return success;
        }

    }
    boolean creerAppFolder(){

        File folder = new File(Environment.getExternalStorageDirectory() +"/com.example.rick.tictactoe.audiomanaging");
        boolean success = true;
        if (!folder.exists()) {
            Toast.makeText(this, "App directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
            success = folder.mkdir();
        }
        if (success) {
            Toast.makeText(this, " App directory Created", Toast.LENGTH_SHORT).show();
            return success;
        } else {
            Toast.makeText(this, "can't create e_mobadara folder", Toast.LENGTH_SHORT).show();
            return success;
        }

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_AUDIO);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG, "on acticity result");
        if (requestCode == PICK_AUDIO) {
            Log.d(TAG, "requestcode verefied");
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "resultcode verefied");
                try {
                    Log.d(TAG, ""+data.getData());
                    _getRealPathFromURI(getApplication(), data.getData());
                    audioFile = new File(audio.getaPath());
                    changeTextView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //pick audio file
                    openGallery();
                }else{
                    Toast.makeText(getBaseContext(), "Permission denied ..", Toast.LENGTH_LONG).show();

                }

            }
        }

    }

    private void _getRealPathFromURI(Context context, Uri contentUri) {
        Log.d(TAG, "_getRealPathFromURI");
        String[] proj = { MediaStore.Audio.Media.DATA,  MediaStore.Audio.Media.DISPLAY_NAME };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Cursor cursor = loader.loadInBackground();
        if(cursor.getCount()>0 && cursor != null )
        {
            cursor.moveToFirst();
            Log.d(TAG, "cursor on the first");
            audio.setaPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            Log.d(TAG, audio.getaPath());
            audio.setaName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
            Log.d(TAG, audio.getaName());
            cursor.close();
        }
        else{
            Log.d(TAG, "problem with the cursor");
        }
    }
    void reloadActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("langue",MainActivity.getLangue());
        startActivity(intent);
        finish();
    }
}
