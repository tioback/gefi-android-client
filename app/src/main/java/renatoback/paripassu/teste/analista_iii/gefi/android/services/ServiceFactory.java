package renatoback.paripassu.teste.analista_iii.gefi.android.services;

public class ServiceFactory {
    public static SenhaService createSenhaService(String enderecoServico) {
        return new SenhaRESTService(enderecoServico);
    }
}
