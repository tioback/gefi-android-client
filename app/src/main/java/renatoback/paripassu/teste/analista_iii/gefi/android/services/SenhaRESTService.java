package renatoback.paripassu.teste.analista_iii.gefi.android.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import renatoback.paripassu.teste.analista_iii.gefi.android.exceptions.NenhumaChamada;
import renatoback.paripassu.teste.analista_iii.gefi.android.exceptions.NenhumaSenhaNaFila;
import renatoback.paripassu.teste.analista_iii.gefi.android.model.Senha;

public class SenhaRESTService implements SenhaService {

    private static final String TAG = SenhaRESTService.class.getSimpleName();

    private final String enderecoServico;

    public SenhaRESTService(String enderecoServico) {
        if (enderecoServico == null || enderecoServico.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.enderecoServico = enderecoServico;
    }

    public void geraNovaSenhaPreferencial(TaskResultListener<Senha> listener) {
        geraNovaSenha(enderecoServico + "/senha/P", listener);
    }

    public void geraNovaSenhaNormal(TaskResultListener<Senha> listener) {
        geraNovaSenha(enderecoServico + "/senha/N", listener);
    }

    private void geraNovaSenha(final String url, final TaskResultListener<Senha> listener) {
        new SafeAsyncTask<Void, Void, Senha>(listener) {
            @Override
            protected Senha doInBackgroundSafely() {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return template.postForObject(url, null, Senha.class);
            }
        }.execute();
    }

    public void buscaUltimaSenhaChamada(final TaskResultListener<Senha> listener) throws NenhumaChamada {
        final String url = enderecoServico + "/senha";
        new RestTemplateTask<Void, Void, Senha>(listener, new UltimaSenhaErrorHandler()) {
            protected ResponseEntity<Senha> doRequest(RestTemplate template) {
                return template.getForEntity(url, Senha.class);
            }
        }.execute();
    }

    public void chamarProximaSenha(final TaskResultListener<Senha> listener) throws NenhumaSenhaNaFila {
        final String url = enderecoServico + "/senha";
        new RestTemplateTask<Void, Void, Senha>(listener, new UltimaSenhaErrorHandler()) {
            protected ResponseEntity<Senha> doRequest(RestTemplate template) {
                return template.postForEntity(url, null, Senha.class);
            }
        }.execute();
    }

    public void reiniciarSenhaNormal(TaskResultListener<Void> listener) {
        reiniciarSenha(enderecoServico + "/senha/N", listener);
    }

    public void reiniciarSenhaPreferencial(TaskResultListener<Void> listener) {
        reiniciarSenha(enderecoServico + "/senha/P", listener);
    }

    private void reiniciarSenha(final String url, TaskResultListener<Void> listener) {
        new SafeAsyncTask<Void, Void, Void>(listener) {
            @Override
            protected Void doInBackgroundSafely() {
                RestTemplate template = new RestTemplate();
                template.put(url, null);
                return null;
            }
        }.execute();
    }

//    public void monitorarChamadaDeSenhas(ChamadaDeSenhaListener listener) {
//        try {
//            throw new RuntimeException("Not implemented.");
//        } catch (Exception e) {
//            Log.e(TAG, "Erro ao monitorar senhas.", e);
//        }
//    }

    private class UltimaSenhaErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return !HttpStatus.NOT_FOUND.equals(response.getStatusCode()) && super.hasError(response);
        }
    }
}
