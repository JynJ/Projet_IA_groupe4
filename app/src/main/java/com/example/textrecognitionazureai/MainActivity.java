package com.example.textrecognitionazureai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère le bouton par son Id
        Button enterButton = findViewById(R.id.b1); // remplace l'id par l'id réel du bouton

        // On va utiliser un listener pour les événements de clic sur le bouton
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appelle la méthode pour accéder à l'écran d'accueil
                openHomeScreen();
            }
        });
    }

    private void openHomeScreen() {
        // Utilisation de l'intent pour démarrer la nouvelle activité (HomeActivity)
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}