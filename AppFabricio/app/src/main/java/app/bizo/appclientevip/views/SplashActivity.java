package app.bizo.appclientevip.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import api.AppUtil;
import api.PreferenciasUtil;

public class SplashActivity extends AppCompatActivity {

    private boolean loginAutomatico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        restaurarPreferencias();
        inicializarAplicativo();
    }

    private void inicializarAplicativo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tela = new Intent(SplashActivity.this,
                        loginAutomatico ? MainActivity.class : LoginActivity.class);
                startActivity(tela);
                finish();
                return;
            }
        }, AppUtil.TIME_SPLASH);
    }

    private void restaurarPreferencias() {
        this.loginAutomatico = PreferenciasUtil.getBooleanData(getApplicationContext(), "lembrarSenha");
    }
}
