package app.bizo.appclientevip.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioFormActivity extends ActivityBase implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtSenhaConfirmar;
    private CheckBox chkTermosUso;
    private Button btnCadastrar;

    private Usuario usuario;
    private UsuarioController controller;

    public UsuarioFormActivity() {
        controller = new UsuarioController(minhaTela);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciarComponentes();
        iniciarListeners();
    }

    @Override
    public void iniciarComponentes() {
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenhaConfirmar = (EditText) findViewById(R.id.edtSenhaConfirmar);
        chkTermosUso = (CheckBox) findViewById(R.id.chkConfirmarTermos);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarUsuario);

        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null){
            carregarDados(controller.buscarPorId(usuario.getId()));
        }
    }

    private void iniciarListeners() {
        btnCadastrar.setOnClickListener(this);
    }

    private boolean validarTermosUso() {
        if(chkTermosUso.isChecked()){
            chkTermosUso.setError(null);
            chkTermosUso.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            return true;
        } else {
            chkTermosUso.setError(this.getResources().getString(R.string.termos_uso_error));
            chkTermosUso.setTextColor(ContextCompat.getColor(this, R.color.red));
            chkTermosUso.requestFocus();
            Toast.makeText(this, R.string.termos_uso_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void validarTermoUso(View view){
        validarTermosUso();
    }

    private boolean validarFormulario() {
        if (TextUtils.isEmpty(edtNome.getText().toString())){
            edtNome.setError(this.getResources().getString(R.string.txtNomeUsuario));
            edtNome.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError(this.getResources().getString(R.string.txtEmailUsuario));
            edtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSenha.getText().toString())){
            edtSenha.setError(this.getResources().getString(R.string.txtSenhalUsuario));
            edtSenha.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSenhaConfirmar.getText().toString())){
            edtSenhaConfirmar.setError(this.getResources().getString(R.string.txtSenhaConfirmar));
            edtSenhaConfirmar.requestFocus();
            return false;
        }

        if(!validarSenha()){
            edtSenhaConfirmar.setError(this.getResources().getString(R.string.txtSenhasDiferentes));
            edtSenhaConfirmar.requestFocus();
            Toast.makeText(this, R.string.txtSenhasDiferentes, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validarTermosUso()) {
            return false;
        }
        return true;
    }

    private boolean validarSenha() {
        return TextUtils.equals(edtSenha.getText(), edtSenhaConfirmar.getText());
    }

    private void carregarDados(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null){
            edtNome.setText(usuario.getNome());
            edtEmail.setText(usuario.getEmail());
            chkTermosUso.setChecked(usuario.getAceitouTermosUso());
            Toast.makeText(this, usuario.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCadastrarUsuario:
                if (validarFormulario()){
                    if (usuario== null || usuario.getId()==null){
                        usuario = new Usuario();
                        usuario.setId(null);
                    }

                    usuario.setNome(edtNome.getText().toString());
                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());
                    usuario.setAceitouTermosUso(chkTermosUso.isChecked());

                    if (controller.salvar(usuario)) {
                        Intent intent = new Intent(minhaTela, UsuarioListaActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        ((UsuarioListaActivity) tela).adicionarItem(usuario);
                        finish();
//                        Snackbar.make(null, "Cadastro Efetuado com Sucesso!", Snackbar.LENGTH_SHORT).show();
                        return;
                    } else {
                        Snackbar.make(view, "Um erro ocorreu!", Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
