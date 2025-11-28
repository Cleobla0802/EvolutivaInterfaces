package es.iescarrillo.interfacesprogress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAcceder = findViewById(R.id.button);

        btnAcceder.setOnClickListener(v -> {
            Intent i = new Intent(this, SecondActivity.class);
            startActivity(i);
        });

    }
}