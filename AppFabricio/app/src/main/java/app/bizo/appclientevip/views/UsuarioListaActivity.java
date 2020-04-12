package app.bizo.appclientevip.views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

import app.bizo.appclientevip.adapter.UsuarioAdapter;
import app.bizo.appclientevip.adapter.UsuarioAdapterListener;
import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioListaActivity extends ActivityBase {
    
    private RecyclerView recyclerView;
    private UsuarioAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Usuario> usuarios;

    private UsuarioController controller;

    public UsuarioListaActivity() {
        controller = new UsuarioController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Usuários");

        iniciarComponentes();
        carregarLista();
    }

    @Override
    public void iniciarComponentes() {
        usuarios = controller.listarTodos();
        recyclerView = (RecyclerView) findViewById(R.id.usuarioListaView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UsuarioAdapter(this, usuarios, new UsuarioAdapterListener() {
            @Override
            public void onPositionClicked(int position, Usuario objeto) {
                Intent tela = new Intent(UsuarioListaActivity.this, SignUpActivity.class);
                tela.putExtra("telaAnterior", UsuarioListaActivity.this.getClass());
//                usuarioForm.putExtra("usuario", usuarios.get(position));
                tela.putExtra("usuario", objeto);
                startActivity(tela);
            }

            @Override
            public void onDelete(int position) {
                Log.i(TAG, "onDelete: " + usuarios.get(position));
                if (controller.remover(usuarios.get(position).getId())){
                    adapter.removeItem(position);
                } else {
                    Toast.makeText(UsuarioListaActivity.this, "Erro ao Remover Usuário!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Usuario deletedModel = usuarios.get(position);

                int pos = viewHolder.getAdapterPosition();
                Log.i(TAG, "onSwiped: " + usuarios.get(pos));
                if (controller.remover(usuarios.get(pos).getId())){
                    adapter.removeItem(pos);
                } else {
                    Toast.makeText(UsuarioListaActivity.this, "Erro ao Remover Usuário!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarLista();
    }

    private void carregarLista() {
        this.usuarios = controller.listarTodos();
        ((UsuarioAdapter) adapter).updateData(usuarios);
    }

    public void adicionarItem(Usuario model) {
        adapter.addItem(model);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usuario_lista_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.usuarioListaMenuAdd:
                Intent tela = new Intent(UsuarioListaActivity.this, SignUpActivity.class);
                tela.putExtra("telaAnterior", minhaTela.getClass());
                startActivity(tela);
//                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
