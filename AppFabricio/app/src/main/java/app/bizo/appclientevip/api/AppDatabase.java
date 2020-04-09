package app.bizo.appclientevip.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import app.bizo.appclientevip.datamodel.UsuarioDataModel;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = AppDatabase.class.getSimpleName();

    private static final String DB_NAME = "appfabriciodb.sqlite";
    private static final int DB_VERSION = 1;

    public AppDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Criar as tabelas aqui
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String tabelaUsuariosSQL = new UsuarioDataModel().gerarTabela();
            db.execSQL(tabelaUsuariosSQL);
            Log.i(TAG, "Tabela Usuario: " + tabelaUsuariosSQL);
        } catch (SQLiteException ex) {
            Log.e(TAG,ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String tabelaUsuariosSQL = new UsuarioDataModel().excluirTabela();
        db.execSQL(tabelaUsuariosSQL);
        onCreate(db);
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
}
