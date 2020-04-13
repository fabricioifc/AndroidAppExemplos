package app.bizo.appclientevip.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import app.bizo.appclientevip.api.AppDatabase;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioDao extends AppDatabase {

    private static final String TAG = UsuarioDao.class.getName();

    public UsuarioDao(@Nullable Context context) {
        super(context);
    }

    public boolean inserir(String tabela, ContentValues dados) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(tabela, null, dados) != -1;
    }

    public boolean atualizar(String tabela, ContentValues dados, String condicao, String[] condicaoValores) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(tabela, dados, condicao, condicaoValores) == 1;
    }
    public boolean excluir(String tabela, String condicao, String[] condicaoValores) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(tabela, condicao, condicaoValores) == 1;
    }
    public Cursor listar(String tabela) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tabela + " ORDER BY nome ASC", null);
    }

    public Cursor buscarPorId(String tabela, int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tabela + " where id =?", new String[]{String.valueOf(id)});
    }

    public Cursor buscarPorEmail(String tabela, Integer id, String email) {
        SQLiteDatabase db = getReadableDatabase();
        if (id != null){
            return db.rawQuery("SELECT * FROM " + tabela + " where email =? and id !=?", new String[]{email, String.valueOf(id)});
        }
        return db.rawQuery("SELECT * FROM " + tabela + " where email =?", new String[]{email});
    }

    public Cursor validarUsuarioSenha(String tabela, String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + tabela + " where email =? AND senha =? limit 1",
                new String[]{
                        String.valueOf(email),
                        String.valueOf(senha)
                });
    }
}
