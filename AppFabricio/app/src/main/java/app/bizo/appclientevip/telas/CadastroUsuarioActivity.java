package app.bizo.appclientevip.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtSenhaConfirmar;
    private CheckBox chkTermosUso;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializar();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarFormulario()){

                }
            }
        });
    }

    private void inicializar() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
