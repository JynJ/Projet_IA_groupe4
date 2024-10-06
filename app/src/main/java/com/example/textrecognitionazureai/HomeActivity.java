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

        // Action lorsque l'on clique sur le bouton pour créer un PDF
        createPdfButton.setOnClickListener(v -> {
            if (capturedImage != null) {
                try {
                    File pdfFile = createPdfFromImage(capturedImage);
                    openPdfFile(pdfFile);  // Ouvrir le fichier PDF après la création
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erreur lors de la création du PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Veuillez capturer une image d'abord", Toast.LENGTH_SHORT).show();
            }
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

    // Méthode pour convertir l'image capturée en PDF
    private File createPdfFromImage(Bitmap bitmap) throws IOException {
        // Convertir l'image Bitmap en byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Créer un fichier PDF dans le répertoire de stockage externe
        File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ImageToPDF.pdf");
        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Ajouter l'image au document PDF
        ImageData imageData = ImageDataFactory.create(byteArray);
        Image pdfImage = new Image(imageData);
        document.add(pdfImage);

        // Fermer le document PDF
        document.close();

        // Notifier que le PDF est créé
        Toast.makeText(this, "PDF créé avec succès : " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        return pdfFile;
    }

    // Méthode pour ouvrir le fichier PDF créé
    private void openPdfFile(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}