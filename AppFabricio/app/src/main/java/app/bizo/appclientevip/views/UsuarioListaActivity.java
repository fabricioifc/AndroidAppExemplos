package app.bizo.appclientevip.views;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import app.bizo.appclientevip.adapter.UsuarioAdapter;
import app.bizo.appclientevip.controller.UsuarioController;

public class UsuarioListaActivity extends ActivityBase {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private UsuarioController controller;

    public UsuarioListaActivity() {
        controller = new UsuarioController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Lista de Usuários");

        init();
        carregarLista();
    }

    private void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        ((UsuarioAdapter) adapter).updateData(controller.listarTodos());
    }

    private void carregarLista() {
        recyclerView = (RecyclerView) findViewById(R.id.usuarioListaView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UsuarioAdapter(this, controller.listarTodos());
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                startActivity(new Intent(UsuarioListaActivity.this, UsuarioFormActivity.class));
//                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
