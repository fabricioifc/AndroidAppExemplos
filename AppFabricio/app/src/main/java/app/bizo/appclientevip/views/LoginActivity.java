package app.bizo.appclientevip.views;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import app.bizo.appclientevip.api.PreferenciasUtil;
import app.bizo.appclientevip.controller.UsuarioController;

public class LoginActivity extends ActivityBase {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private TextInputEditText edtEmail, edtSenha;
    private TextView txtRecuperarSenha, txtLerPolitica, btnLoginSignUp;
    private CheckBox chkLembrar;
    private Button btnAcessar;
    private ImageView imgLogo;

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
                        PreferenciasUtil.saveData(getApplicationContext(), PreferenciasUtil.PREF_LOGIN_LEMBRAR_SENHA, isLembrarSenha);
                        startActivity(new Intent(view.getContext(), MainActivity.class));
                        finish();
                        return;
                    }

                }
            }
        });

        btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tela = new Intent(view.getContext(), SignUpActivity.class);
                tela.putExtra("telaAnterior", LoginActivity.this.getClass());
                startActivity(tela);
//                finish();
                return;
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
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmailLogin);
        edtSenha = (TextInputEditText) findViewById(R.id.edtSenhaLogin);
        txtRecuperarSenha = (TextView) findViewById(R.id.txtRecurarSenhaLogin);
        txtLerPolitica = (TextView) findViewById(R.id.txtLerPoliticaLogin);
        chkLembrar = (CheckBox) findViewById(R.id.chkLembrarLogin);
        btnAcessar = (Button) findViewById(R.id.btnSignIn);
        btnLoginSignUp = (TextView) findViewById(R.id.btnLoginSignUp);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);

        Picasso.get().setLoggingEnabled(true);
        Picasso.get()
                .load(R.mipmap.logo)
//                .load("https://pngimage.net/wp-content/uploads/2018/06/sample-logo-png-transparent-background-4.png")
                .into(imgLogo);

        isValido = false;
    }

    public void LembrarSenha(View view) {
        isLembrarSenha = chkLembrar.isChecked();
    }

    public void validarTermoUso(View view) {
    }
}
