package app.bizo.appclientevip.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private TextView txtRecuperarSenha, txtLerPolitica;
    private CheckBox chkLembrar;
    private Button btnAcessar, btnSejaVip;

    private boolean isValido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initFormulario();
        
        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Ir para tela recuperação senha", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initFormulario() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        txtRecuperarSenha = (TextView) findViewById(R.id.txtRecurarSenha);
        txtLerPolitica = (TextView) findViewById(R.id.txtLerPolitica);
        chkLembrar = (CheckBox) findViewById(R.id.chkLembrar);
        btnAcessar = (Button) findViewById(R.id.btnAcessar);
        btnSejaVip = (Button) findViewById(R.id.btnSejaVip);

        isValido = false;
    }
}
