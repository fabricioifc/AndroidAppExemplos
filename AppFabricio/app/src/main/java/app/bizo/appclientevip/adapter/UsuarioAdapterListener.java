package app.bizo.appclientevip.adapter;

import app.bizo.appclientevip.model.Usuario;

public interface UsuarioAdapterListener {
        void onPositionClicked(int position, Usuario objeto);
        void onDelete(int position);
//        void onLongClicked(int position);
}
