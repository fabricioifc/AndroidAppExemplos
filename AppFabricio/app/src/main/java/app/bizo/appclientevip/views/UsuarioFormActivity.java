package app.bizo.appclientevip.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioFormActivity extends ActivityBase {

    private static final String TAG = UsuarioFormActivity.class.getSimpleName();

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtSenhaConfirmar;
    private CheckBox chkTermosUso;
    private Button btnCadastrar;

    private Usuario usuario;
    private UsuarioController controller;

    public UsuarioFormActivity() {
        controller = new UsuarioController(UsuarioFormActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciarComponentes();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Intent tela = new Intent(UsuarioFormActivity.this, UsuarioListaActivity.class);
                        tela.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        finish();
//                        Snackbar.make(null, "Cadastro Efetuado com Sucesso!", Snackbar.LENGTH_SHORT).show();
                        return;
                    } else {
                        Snackbar.make(v, "Um erro ocorreu!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
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

    public void validarTermoUso(View view){
        if (!chkTermosUso.isChecked()){
            chkTermosUso.setTextColor(this.getResources().getColor(R.color.red));
            Toast.makeText(getApplicationContext(), "É necessário ler e confirmar os termos de uso", Toast.LENGTH_LONG).show();
        } else {
            chkTermosUso.setTextColor(this.getResources().getColor(R.color.black));
        }
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
        }
        Toast.makeText(this, usuario.toString(), Toast.LENGTH_SHORT).show();
    }

}
