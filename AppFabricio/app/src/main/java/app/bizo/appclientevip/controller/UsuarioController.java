package app.bizo.appclientevip.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.bizo.appclientevip.api.AppDatabase;
import app.bizo.appclientevip.api.PreferenciasUtil;
import app.bizo.appclientevip.dao.UsuarioDao;
import app.bizo.appclientevip.datamodel.UsuarioDataModel;
import app.bizo.appclientevip.model.Usuario;
import app.bizo.appclientevip.views.UsuarioListaActivity;

public class UsuarioController  {

//    private static final String EMAIL_TESTE = "fabricio.bizotto@gmail.com";
//    private static final String SENHA_TESTE = "123456";

    private UsuarioDao dao;
    private UsuarioDataModel dataModel;
    private Context context;

    public UsuarioController(@Nullable Context context) {
        this.context = context;
        dao = new UsuarioDao(context);
    }

    public boolean validarUsuario(String email, String senha) {
        Cursor usuario =  dao.validarUsuarioSenha(UsuarioDataModel.TABELA, email, senha);

        if (usuario.moveToFirst()) {
            Integer idUsuario = usuario.getInt(usuario.getColumnIndexOrThrow(UsuarioDataModel.ID));
            String emailUsuario = usuario.getString(usuario.getColumnIndexOrThrow(UsuarioDataModel.EMAIL));
            PreferenciasUtil.saveData(context, PreferenciasUtil.PREF_LOGIN_USUARIO_ID, idUsuario);
            PreferenciasUtil.saveData(context, PreferenciasUtil.PREF_LOGIN_USUARIO_EMAIL, emailUsuario);
            return true;
        }

        return false;
    }

    public boolean salvar(Usuario usuario) throws SQLException {
        ContentValues dados = new ContentValues();
        dados.put(UsuarioDataModel.ID, usuario.getId());
        dados.put(UsuarioDataModel.NOME, usuario.getNome());
        dados.put(UsuarioDataModel.EMAIL, usuario.getEmail());
        dados.put(UsuarioDataModel.SENHA, usuario.getSenha());
        dados.put(UsuarioDataModel.ACEITOU_TERMOS_USO, usuario.getAceitouTermosUso());
        if (usuario.getId() == null) {
            return dao.inserir(UsuarioDataModel.TABELA, dados);
//            usuario.setId(dao.getUltimoID(UsuarioDataModel.TABELA));
        } else {
            return dao.atualizar(UsuarioDataModel.TABELA,
                    dados,
                    UsuarioDataModel.ID + "=?",
                    new String[]{String.valueOf(usuario.getId())});
        }


    }

    public boolean remover(int usuarioId) throws SQLException {
        return dao.excluir(UsuarioDataModel.TABELA,
                UsuarioDataModel.ID + "=?",
                new String[]{String.valueOf(usuarioId)});
    }

    public List<Usuario> listarTodos() {
        Cursor cursor = dao.listar(UsuarioDataModel.TABELA);
        List<Usuario> lista = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                lista.add(new Usuario(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) != 0
                ));
            } while (cursor.moveToNext());
        }

        return lista;
    }

    public Usuario buscarPorId(int id) {
        Cursor cursor = dao.buscarPorId(UsuarioDataModel.TABELA, id);
        Usuario usuario = null;

        if (cursor.moveToFirst()){
            usuario = getUsuario(cursor);
        }

        return usuario;
    }

    public Usuario buscarPorEmail(Integer id, String email) {
        Cursor cursor = dao.buscarPorEmail(UsuarioDataModel.TABELA, id, email);
        Usuario usuario = null;

        if (cursor.moveToFirst()){
            usuario = getUsuario(cursor);
        }

        return usuario;
    }

    private Usuario getUsuario(Cursor cursor) {
        return new Usuario(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getInt(4) != 0);
    }

    private int getUltimoID(){
        return dao.getUltimoID(UsuarioDataModel.TABELA);
    }

//    public boolean salvar(Context context, Usuario usuario) {
//        SharedPreferences.Editor dados = PreferenciasUtil.getSharedPrefEditor(context, PreferenciasUtil.PREF_APP);
////        dados.putInt("usuarioId", usuario.getId());
//        dados.putString("usuarioNome", usuario.getNome());
//        dados.putString("usuarioEmail", usuario.getEmail());
//
//        PreferenciasUtil.saveData(dados);
//        return true;
//    }
}
