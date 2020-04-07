package app.bizo.appclientevip.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.bizo.appclientevip.listeners.Contador;

public class MainActivity extends ActivityBase implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnIncrementar, btnDecrementar, btnIrParaCadastroUsuario;
    private TextView txtContador;
    private ProgressBar progressBar;

    private Handler handler;
    private Contador contador;

    public MainActivity(){
        this.contador = new Contador();
        this.handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrementar = (Button) findViewById(R.id.btnIncrementar);
        btnDecrementar = (Button) findViewById(R.id.btnDecrementar);
        btnIrParaCadastroUsuario = (Button) findViewById(R.id.btnIrParaCadastroUsuario);
        txtContador = (TextView) findViewById(R.id.txtContador);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        btnIncrementar.setOnClickListener(this);
        btnDecrementar.setOnClickListener(this);
        btnIrParaCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(MainActivity.this, CadastroUsuarioActivity.class);
                startActivity(tela);
            }
        });
    }

    @Override
    public void onClick(final View v) {
        ajustarProgressBar(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            public void run() {

                contador.resolver((Button) v);
                Log.i(TAG, "onClick: "+ (contador.getContador()));
                txtContador.setText(contador.getContador().toString());
                ajustarProgressBar(View.GONE);
            }
        }, 1000);

    }

    private void ajustarProgressBar(int visivel) {
        progressBar.setVisibility(visivel);
        if (visivel == View.VISIBLE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}
