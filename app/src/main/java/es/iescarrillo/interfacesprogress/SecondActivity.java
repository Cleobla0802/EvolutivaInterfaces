package es.iescarrillo.interfacesprogress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        ImageButton btnPlay1 = findViewById(R.id.btnPlay1);
        ImageButton btnPlay2 = findViewById(R.id.btnPlay2);
        ImageButton btnPlay3 = findViewById(R.id.btnPlay3);

        View.OnClickListener playListener = v -> findViewById(R.id.playerSection).setVisibility(View.VISIBLE);

        btnPlay1.setOnClickListener(playListener);
        btnPlay2.setOnClickListener(playListener);
        btnPlay3.setOnClickListener(playListener);
    }
}