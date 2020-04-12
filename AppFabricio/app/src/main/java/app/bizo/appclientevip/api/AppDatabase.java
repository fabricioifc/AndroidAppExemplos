package app.bizo.appclientevip.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.bizo.appclientevip.datamodel.DataModelListener;
import app.bizo.appclientevip.datamodel.UsuarioDataModel;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = AppDatabase.class.getSimpleName();

    private static final String DB_NAME = "appfabriciodb.sqlite";
    private static final int DB_VERSION = 1;
    protected Context context;
    private List<DataModelListener> listeners;

    public AppDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        listeners = new ArrayList<>();
        listeners.add(new UsuarioDataModel());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (DataModelListener l: listeners){
                String sql = l.gerarTabela();
                db.execSQL(sql);
                Log.i(TAG, "Tabela: " + sql);    
            }
            
        } catch (SQLiteException ex) {
            Log.e(TAG,ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Atualizando a base de dados da versão " + oldVersion + " para a versão " + newVersion);
        for (DataModelListener l : listeners) {
            String sql = l.excluirTabela();
            db.execSQL(sql);
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onDowngrade: ");
    }
}
