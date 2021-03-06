package app.bizo.appclientevip.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.bizo.appclientevip.api.PreferenciasUtil;
import app.bizo.appclientevip.listeners.Contador;

public class MainActivity extends ActivityBase implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnIncrementar, btnDecrementar, btnIrParaUsuarios;
    private TextView txtContador, txtOlaMundo;
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
        btnIrParaUsuarios = (Button) findViewById(R.id.btnIrParaUsuarios);
        txtContador = (TextView) findViewById(R.id.txtContador);
        txtOlaMundo = (TextView) findViewById(R.id.olamundo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        btnIncrementar.setOnClickListener(this);
        btnDecrementar.setOnClickListener(this);
        btnIrParaUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(MainActivity.this, UsuarioListaActivity.class);
                startActivity(tela);
//                finish();
            }
        });

        txtOlaMundo.setText(PreferenciasUtil.getStringData(this, "usuario_email"));
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

    @Override
    public void iniciarComponentes() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenu:
                logout();
                Intent tela = new Intent(minhaTela, LoginActivity.class);
                startActivity(tela);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        PreferenciasUtil.saveData(getApplicationContext(), PreferenciasUtil.PREF_LOGIN_LEMBRAR_SENHA, null);
        PreferenciasUtil.saveData(getApplicationContext(), PreferenciasUtil.PREF_LOGIN_USUARIO_EMAIL, null);
        PreferenciasUtil.saveData(getApplicationContext(), PreferenciasUtil.PREF_LOGIN_USUARIO_ID, null);
    }

}
