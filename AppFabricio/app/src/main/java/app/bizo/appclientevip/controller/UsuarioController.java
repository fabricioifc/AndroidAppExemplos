package app.bizo.appclientevip.controller;

import android.content.Context;
import android.content.SharedPreferences;

import api.PreferenciasUtil;
import app.bizo.appclientevip.model.Usuario;

public class UsuarioController {

    private static final String EMAIL_TESTE = "fabricio.bizotto@gmail.com";
    private static final String SENHA_TESTE = "123456";

    public boolean validarUsuario(String email, String senha) {
        if (email.equalsIgnoreCase(EMAIL_TESTE) && senha.equals(SENHA_TESTE)){
            return true;
        }
        return false;
    }

    public boolean salvar(Context context, Usuario usuario) {
        SharedPreferences.Editor dados = PreferenciasUtil.getSharedPrefEditor(context, PreferenciasUtil.PREF_APP);
//        dados.putInt("usuarioId", usuario.getId());
        dados.putString("usuarioNome", usuario.getNome());
        dados.putString("usuarioEmail", usuario.getEmail());

        PreferenciasUtil.saveData(dados);
        return true;
    }
}
