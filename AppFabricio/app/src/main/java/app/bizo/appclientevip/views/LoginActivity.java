package app.bizo.appclientevip.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.bizo.appclientevip.api.PreferenciasUtil;
import app.bizo.appclientevip.controller.UsuarioController;

public class LoginActivity extends ActivityBase {

    private EditText edtEmail, edtSenha;
    private TextView txtRecuperarSenha, txtLerPolitica;
    private CheckBox chkLembrar;
    private Button btnAcessar, btnSejaVip;

    private boolean isValido = false, isLembrarSenha = true;

    private UsuarioController controller;

    public LoginActivity() {
        controller = new UsuarioController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initFormulario();

        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "Ir para tela recuperação senha", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), RecuperarSenhaActivity.class));

            }
        });

        txtLerPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "Ir para tela para ler política", Toast.LENGTH_LONG).show();
                new MaterialAlertDialogBuilder(LoginActivity.this, R.style.AppTheme)
                        .setTitle("Termos de Uso")
                        .setMessage(R.string.termos_uso)
                        .setPositiveButton("Fechar", null)
                        .show();
            }
        });



        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarFormulario() == true) {
                    if (validarDados() == true) {
                        PreferenciasUtil.saveData(getApplicationContext(), "lembrarSenha", isLembrarSenha);
                        startActivity(new Intent(view.getContext(), MainActivity.class));
                        finish();
                        return;
                    }

                }
            }
        });
    }

    @Override
    public void iniciarComponentes() {

    }

    private boolean validarFormulario() {
        isValido = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("*");
            edtEmail.requestFocus();
            isValido = false;
        }

        if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtSenha.setError("*");
            edtSenha.requestFocus();
            isValido = false;
        }

        return isValido;
    }

    private boolean validarDados() {
        isValido = true;

        if (!controller.validarUsuario(edtEmail.getText().toString(), edtSenha.getText().toString())) {
            isValido = false;
            edtEmail.requestFocus();
            Toast.makeText(this, "Usuário e/ou Senha Inválido!", Toast.LENGTH_SHORT).show();
        }

        return isValido;
    }

    private void initFormulario() {
        edtEmail = (EditText) findViewById(R.id.edtEmailLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenhaLogin);
        txtRecuperarSenha = (TextView) findViewById(R.id.txtRecurarSenhaLogin);
        txtLerPolitica = (TextView) findViewById(R.id.txtLerPoliticaLogin);
        chkLembrar = (CheckBox) findViewById(R.id.chkLembrarLogin);
        btnAcessar = (Button) findViewById(R.id.btnAcessarLogin);
        btnSejaVip = (Button) findViewById(R.id.btnSejaVipLogin);

        isValido = false;
    }

    public void LembrarSenha(View view) {
        isLembrarSenha = chkLembrar.isChecked();
    }
}
