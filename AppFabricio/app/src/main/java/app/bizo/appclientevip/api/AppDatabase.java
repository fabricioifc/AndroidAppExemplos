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
    protected Context context;

    public AppDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
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


}
