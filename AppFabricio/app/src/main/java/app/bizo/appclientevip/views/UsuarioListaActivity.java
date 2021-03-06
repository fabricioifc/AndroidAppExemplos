package app.bizo.appclientevip.views;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.bizo.appclientevip.adapter.UsuarioAdapter;
import app.bizo.appclientevip.adapter.UsuarioAdapterListener;
import app.bizo.appclientevip.controller.UsuarioController;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioListaActivity extends ActivityBase {
    
    private RecyclerView recyclerView;
    private UsuarioAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    private UsuarioController controller;

    public UsuarioListaActivity() {
        controller = new UsuarioController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_usuario_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Usuários");

        iniciarComponentes();
    }

    @Override
    public void iniciarComponentes() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.usuarioLisatSwipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.updateData(controller.listarTodos());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(
                R.color.bg,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView = (RecyclerView) findViewById(R.id.usuarioListaView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UsuarioAdapter(this, controller.listarTodos(), new UsuarioAdapterListener() {
            @Override
            public void onPositionClicked(int position, Usuario objeto) {
                Intent tela = new Intent(UsuarioListaActivity.this, SignUpActivity.class);
                tela.putExtra("telaAnterior", UsuarioListaActivity.this.getClass());
//                usuarioForm.putExtra("usuario", usuarios.get(position));
                tela.putExtra("usuario", objeto);
                startActivity(tela);
            }

            @Override
            public void onDelete(final int position) {
                Log.i(TAG, "onDelete: " + position);
                excluirUsuario(position);
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
                Log.i(TAG, "onSwiped: " + position);
                excluirUsuario(position);
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

    }

    private void excluirUsuario(final int position) {
        try {
            final Usuario objeto = adapter.getByPosition(position);
            new MaterialAlertDialogBuilder(UsuarioListaActivity.this, R.style.ThemeOverlay_MaterialComponents_Dark)
                    .setTitle("Excluir Usuário")
                    .setMessage("Tem certeza que deseja remover o usuário " + objeto.getNome() +"?")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (controller.remover(objeto.getId())){
                                adapter.removeItem(position);
                            } else {
                                Toast.makeText(UsuarioListaActivity.this, "Erro ao Remover Usuário!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .show();

        }catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        Boolean atualizarLista = getIntent().getBooleanExtra("atualizar_lista", false);
        Log.i(TAG, "onResume: " + atualizarLista);

        if (atualizarLista) {
            ((UsuarioAdapter) adapter).updateData(controller.listarTodos());
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
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
