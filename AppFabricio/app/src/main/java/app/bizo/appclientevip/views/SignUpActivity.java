package app.bizo.appclientevip.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class SignUpActivity extends ActivityBase implements View.OnClickListener {

    private Usuario usuario;
    private UsuarioController controller;

    private TextInputEditText edtSignUpNome, edtSignUpEmail, edtSignUpSenha, edtSignUpSenhaConfirmar;
    private MaterialCheckBox chkSignUpConfirmarTermos;
    private AppCompatButton btnSignUpCadastrar;
    private Class telaAnterior;

    public SignUpActivity() {
        controller = new UsuarioController(minhaTela);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro");

        iniciarComponentes();
        iniciarListeners();
        carregar();
    }

    private void carregar() {
        telaAnterior = (Class) getIntent().getSerializableExtra("telaAnterior");
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null){
            carregarDados(controller.buscarPorId(usuario.getId()));
        }
    }

    private void iniciarListeners() {
        btnSignUpCadastrar.setOnClickListener(this);
    }

    @Override
    public void iniciarComponentes() {
        edtSignUpNome = (TextInputEditText) findViewById(R.id.edtSignUpNome);
        edtSignUpEmail = (TextInputEditText) findViewById(R.id.edtSignUpEmail);
        edtSignUpSenha = (TextInputEditText) findViewById(R.id.edtSignUpSenha);
        edtSignUpSenhaConfirmar = (TextInputEditText) findViewById(R.id.edtSignUpSenhaConfirmar);
        chkSignUpConfirmarTermos = (MaterialCheckBox) findViewById(R.id.chkSignUpConfirmarTermos);
        btnSignUpCadastrar = (AppCompatButton) findViewById(R.id.btnSignUpCadastrar);
    }

    private void carregarDados(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null){
            edtSignUpNome.setText(usuario.getNome());
            edtSignUpEmail.setText(usuario.getEmail());
            chkSignUpConfirmarTermos.setChecked(usuario.getAceitouTermosUso());
            Toast.makeText(this, usuario.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validarTermosUso() {
        if(chkSignUpConfirmarTermos.isChecked()){
            chkSignUpConfirmarTermos.setError(null);
            chkSignUpConfirmarTermos.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            return true;
        } else {
            chkSignUpConfirmarTermos.setError(this.getResources().getString(R.string.termos_uso_error));
            chkSignUpConfirmarTermos.setTextColor(ContextCompat.getColor(this, R.color.red));
            chkSignUpConfirmarTermos.requestFocus();
            Toast.makeText(this, R.string.termos_uso_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void signUpValidarTermoUso(View view){
        validarTermosUso();
    }

    private boolean validarFormulario() {
        if (TextUtils.isEmpty(edtSignUpNome.getText().toString())){
            edtSignUpNome.setError(this.getResources().getString(R.string.txtNomeUsuario));
            edtSignUpNome.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSignUpEmail.getText().toString())){
            edtSignUpEmail.setError(this.getResources().getString(R.string.txtEmailUsuario));
            edtSignUpEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSignUpSenha.getText().toString())){
            edtSignUpSenha.setError(this.getResources().getString(R.string.txtSenhalUsuario));
            edtSignUpSenha.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSignUpSenhaConfirmar.getText().toString())){
            edtSignUpSenhaConfirmar.setError(this.getResources().getString(R.string.txtSenhaConfirmar));
            edtSignUpSenhaConfirmar.requestFocus();
            return false;
        }

        if(!validarSenha()){
            edtSignUpSenhaConfirmar.setError(this.getResources().getString(R.string.txtSenhasDiferentes));
            edtSignUpSenhaConfirmar.requestFocus();
            Toast.makeText(this, R.string.txtSenhasDiferentes, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validarTermosUso()) {
            return false;
        }
        return true;
    }

    private boolean validarSenha() {
        return TextUtils.equals(edtSignUpSenha.getText(), edtSignUpSenhaConfirmar.getText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUpCadastrar:
                btnSignUpCadastrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                        if (validarFormulario()){
                                if (usuario== null || usuario.getId()==null){
                                    usuario = new Usuario();
                                    usuario.setId(null);
                                }

                                usuario.setNome(edtSignUpNome.getText().toString());
                                usuario.setEmail(edtSignUpEmail.getText().toString());
                                usuario.setSenha(edtSignUpSenha.getText().toString());
                                usuario.setAceitouTermosUso(chkSignUpConfirmarTermos.isChecked());

                                if (controller.salvar(usuario)) {
                                    Log.i(TAG, "onClick: " + SignUpActivity.this.getParent());
                                    Intent tela = new Intent(SignUpActivity.this, telaAnterior);
                                    tela.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    Toast.makeText(SignUpActivity.this, "Cadastro Efetuado com Sucesso!", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }catch (Exception ex) {
                            Snackbar.make(v, ex.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }
}
