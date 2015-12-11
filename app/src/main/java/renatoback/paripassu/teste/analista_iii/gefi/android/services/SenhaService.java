package renatoback.paripassu.teste.analista_iii.gefi.android.services;

import renatoback.paripassu.teste.analista_iii.gefi.android.model.Senha;

public interface SenhaService {

    void geraNovaSenhaPreferencial(TaskResultListener<Senha> listener);

    void geraNovaSenhaNormal(TaskResultListener<Senha> listener);

    void buscaUltimaSenhaChamada(TaskResultListener<Senha> listener);

    void chamarProximaSenha(TaskResultListener<Senha> listener);

    void reiniciarSenhaNormal(TaskResultListener<Void> listener);

    void reiniciarSenhaPreferencial(TaskResultListener<Void> listener);

}
