package com.project.employees;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private Button openCsv;
    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int PICK_FILE_REQUEST_CODE = 1001;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForPermissions();

        openCsv = findViewById(R.id.btn_open);
        openCvsFile();
    }

    private void checkForPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            //Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_DOCUMENTS},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Uri selectedFileUri = data.getData();
                    if (selectedFileUri != null) {
                        File selectedFile = new File(selectedFileUri.getPath());
                        ResultFragment fragment = new ResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("file_path", selectedFile.getAbsolutePath()); // Пример за предаване на текст, използвайте подходящия тип данни
                        fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                    }
                }

            } catch (FileUriExposedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void openCvsFile() {
        openCsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*"); // Всички файлови типове

                startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
}