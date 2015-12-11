package renatoback.paripassu.teste.analista_iii.gefi.android.exceptions;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;

public class NenhumaChamada extends SenhaServiceException {
    @Override
    public int getMessageResourceId() {
        return R.string.nenhuma_senha_chamada_ate_momento;
    }
}
