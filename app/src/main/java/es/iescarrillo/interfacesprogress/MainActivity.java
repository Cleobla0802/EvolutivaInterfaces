package es.iescarrillo.interfacesprogress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private Button btnPlay;
    private boolean isPlaying = false;
    private Handler handler = new Handler(Looper.getMainLooper()); // ← CAMBIA ESTO
    private int progresoActual = 0;
    private ImageButton btnSiguiente;
    private ImageButton btnAnterior;
    private Switch switchAleatorio;
    private TextInputEditText etBuscar;
    private CheckBox cbFavorito;
    private RadioGroup rgPlaylists;
    private TextView tvNombreCancion;
    private TextView tvArtista;
    private TextView tvTiempoActual;
    private TextView tvTiempoTotal;
    private LinearProgressIndicator progressCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        inicializarComponentes();

        // Configurar listeners
        configurarListeners();

        // Cargar Fragments
        cargarFragments();
    }

    private void inicializarComponentes() {
        // Botones
        btnPlay = findViewById(R.id.btnPlay);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);

        // Switch
        switchAleatorio = findViewById(R.id.switchAleatorio);

        // Campos de texto
        etBuscar = findViewById(R.id.etBuscar);
        tvNombreCancion = findViewById(R.id.tvNombreCancion);
        tvArtista = findViewById(R.id.tvArtista);
        tvTiempoActual = findViewById(R.id.tvTiempoActual);
        tvTiempoTotal = findViewById(R.id.tvTiempoTotal);

        // CheckBox y RadioGroup
        cbFavorito = findViewById(R.id.cbFavorito);
        rgPlaylists = findViewById(R.id.rgPlaylists);

        // Progress Indicator
        progressCancion = findViewById(R.id.progressCancion);
    }

    private void configurarListeners() {
        // Listener del botón Play/Pause
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    // Pausar música
                    btnPlay.setText("PLAY");
                    isPlaying = false;
                    Toast.makeText(MainActivity.this, "Música pausada", Toast.LENGTH_SHORT).show();
                } else {
                    // Reproducir música
                    btnPlay.setText("PAUSE");
                    isPlaying = true;
                    simularProgreso(); // ← AÑADE ESTA LÍNEA (IMPORTANTE!)
                    Toast.makeText(MainActivity.this, "Reproduciendo música", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener del botón Siguiente
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Siguiente canción", Toast.LENGTH_SHORT).show();
                progresoActual = 0; // ← RESETEAR PROGRESO
                cambiarCancion("Canción Siguiente", "Artista Nuevo");
            }
        });


        // Listener del botón Anterior
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Canción anterior", Toast.LENGTH_SHORT).show();
                progresoActual = 0; // ← RESETEAR PROGRESO
                cambiarCancion("Canción Anterior", "Artista Previo");
            }
        });
        // Listener del Switch
        switchAleatorio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Modo aleatorio activado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Modo aleatorio desactivado", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener del CheckBox
        cbFavorito.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener del RadioGroup
        rgPlaylists.setOnCheckedChangeListener((group, checkedId) -> {
            String playlistSeleccionada = "";

            if (checkedId == R.id.rbPlaylist1) {
                playlistSeleccionada = "Mis Favoritas";
            } else if (checkedId == R.id.rbPlaylist2) {
                playlistSeleccionada = "Para Entrenar";
            } else if (checkedId == R.id.rbPlaylist3) {
                playlistSeleccionada = "Relajante";
            }

            Toast.makeText(MainActivity.this,
                    "Añadido a: " + playlistSeleccionada,
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void cambiarCancion(String nombreCancion, String nombreArtista) {
        // Actualizar información de la canción
        tvNombreCancion.setText(nombreCancion);
        tvArtista.setText(nombreArtista);

        // Reiniciar progreso
        progressCancion.setProgress(0);
        tvTiempoActual.setText("0:00");

        // Si estaba reproduciendo, mantener el estado
        if (isPlaying) {
            btnPlay.setText("PAUSE");
        }
    }

    private void cargarFragments() {
        // Cargar Fragment de Biblioteca
        BibliotecaFragment bibliotecaFragment = new BibliotecaFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.fragmentBiblioteca, bibliotecaFragment);
        transaction1.commit();

        // Cargar Fragment de Explorar
        ExplorarFragment explorarFragment = new ExplorarFragment();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.fragmentExplorar, explorarFragment);
        transaction2.commit();
    }

    // Método para simular el progreso de la canción
    private void simularProgreso() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPlaying && progresoActual < 100) {
                    progresoActual++;
                    progressCancion.setProgress(progresoActual);

                    // Actualizar tiempo (de 0:00 a 3:45)
                    int segundos = (int) (progresoActual * 2.25);
                    int minutos = segundos / 60;
                    int segs = segundos % 60;

                    tvTiempoActual.setText(minutos + ":" + String.format("%02d", segs));

                    // Volver a ejecutar en 1 segundo
                    simularProgreso();
                }
            }
        }, 1000); // 1000 = 1 segundo
    }
}