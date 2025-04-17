package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MSE412_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExplicit = findViewById(R.id.btnExplicit);
        Button btnImplicit = findViewById(R.id.btnImplicit);
        Button btnImage = findViewById(R.id.btnImage);

        // Request permission at runtime if not already granted
        if (checkSelfPermission("com.example.myapp.MSE412") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"com.example.myapp.MSE412"}, REQUEST_MSE412_PERMISSION);
        }

        btnExplicit.setOnClickListener(v -> {
            if (checkSelfPermission("com.example.myapp.MSE412") == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission required to access this feature", Toast.LENGTH_SHORT).show();
            }
        });

        btnImplicit.setOnClickListener(v -> {
            if (checkSelfPermission("com.example.myapp.MSE412") == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setAction("com.example.myapp.SECOND_ACTIVITY");
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission required to access this feature", Toast.LENGTH_SHORT).show();
            }
        });

        btnImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_MSE412_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

