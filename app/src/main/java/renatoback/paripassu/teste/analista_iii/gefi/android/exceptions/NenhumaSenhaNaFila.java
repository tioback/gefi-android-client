package renatoback.paripassu.teste.analista_iii.gefi.android.exceptions;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;

public class NenhumaSenhaNaFila extends SenhaServiceException {

    @Override
    public int getMessageResourceId() {
        return R.string.nenhuma_senha_na_fila;
    }
}
