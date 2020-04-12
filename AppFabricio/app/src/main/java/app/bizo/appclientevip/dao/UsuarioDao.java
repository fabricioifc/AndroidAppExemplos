package app.bizo.appclientevip.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import app.bizo.appclientevip.api.AppDatabase;

public class UsuarioDao extends AppDatabase {

    public UsuarioDao(@Nullable Context context) {
        super(context);
    }

    public boolean inserir(String tabela, ContentValues dados) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(tabela, null, dados) != -1;
    }

    public boolean atualizar(String tabela, ContentValues dados, String condicao, String[] condicaoValores) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(tabela, dados, condicao, condicaoValores) == 1;
    }
    public boolean excluir(String tabela, String condicao, String[] condicaoValores) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(tabela, condicao, condicaoValores) == 1;
    }
    public Cursor listar(String tabela) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tabela, null);
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
