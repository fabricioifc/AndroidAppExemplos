package app.bizo.appclientevip.listeners;

import android.widget.Button;

import app.bizo.appclientevip.views.R;

public class Contador implements ContadorListener {

    private Integer contador = 0;

    @Override
    public void incrementar() {
        this.contador++;
    }

    @Override
    public void decrementar() {
        this.contador--;
    }

    @Override
    public void resolver(Button botao) {
        switch (botao.getId()) {
            case R.id.btnIncrementar:
                incrementar();
                break;
            case R.id.btnDecrementar:
                decrementar();
                break;
            default:
                return;
        }
    }

    public Integer getContador() {
        return this.contador;
    }
}
