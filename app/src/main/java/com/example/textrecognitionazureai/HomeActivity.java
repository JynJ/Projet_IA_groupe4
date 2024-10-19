package com.example.textrecognitionazureai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    // Constantes
    private static final int CAMERA_REQUEST_CODE = 100;

    // Variables globales
    private ImageView imageView;
    private Bitmap capturedImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Lier les composants XML au code Java
        Button cameraButton = findViewById(R.id.btscan);
        Button createPdfButton = findViewById(R.id.btpdf);  // Ajouter un bouton pour créer le PDF
        imageView = findViewById(R.id.imageView);

        // Action lorsque l'on clique sur le bouton pour ouvrir la caméra
        cameraButton.setOnClickListener(v -> {
            // Intent pour ouvrir la caméra
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);  // Lancer l'intention de la caméra

        });


    }

    // Gérer le retour d'image de la caméra
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Récupérer l'image capturée
            capturedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            // Afficher l'image dans l'ImageView
            imageView.setImageBitmap(capturedImage);
        }
    }




}