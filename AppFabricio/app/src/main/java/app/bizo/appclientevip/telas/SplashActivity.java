package app.bizo.appclientevip.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import api.AppUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        inicializarAplicativo();
    }

    private void inicializarAplicativo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tela = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(tela);
                finish();
                return;
            }
        }, AppUtil.TIME_SPLASH);
    }
}
