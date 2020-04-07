package app.bizo.appclientevip.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import api.PreferenciasUtil;
import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class CadastroUsuarioActivity extends ActivityBase {

    private static final String TAG = CadastroUsuarioActivity.class.getSimpleName();

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtSenhaConfirmar;
    private CheckBox chkTermosUso;
    private Button btnCadastrar;

    private Usuario usuario;
    private UsuarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFormulario();

        Log.i(TAG, PreferenciasUtil.getStringData(getApplicationContext(), "usuarioNome"));

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarFormulario()){
                    usuario = new Usuario();
                    usuario.setNome(edtNome.getText().toString());
                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());
                    usuario.setAceitouTermosUso(chkTermosUso.isChecked());
                    usuario.setId(null);

                    controller = new UsuarioController();
                    controller.salvar(getApplicationContext(), usuario);
                }
            }
        });
    }

    private void initFormulario() {
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenhaConfirmar = (EditText) findViewById(R.id.edtSenhaConfirmar);
        chkTermosUso = (CheckBox) findViewById(R.id.chkConfirmarTermos);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarUsuario);
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

}
