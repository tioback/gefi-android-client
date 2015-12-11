package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;
import renatoback.paripassu.teste.analista_iii.gefi.android.model.Senha;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.SenhaService;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.TaskResultListener;

public class ClienteActivity extends GefiActivity {

    private static final String TAG = ClienteActivity.class.getSimpleName();

    private SenhaService senhaService;
    private TaskResultListener<Senha> senhaRecebidaListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setSenhaGeradaListener();
        setSenhaService();
    }

    private void setSenhaGeradaListener() {
        senhaRecebidaListener = new TaskResultListener<Senha>() {
            @Override
            public void onSuccess(Senha senha) {
                showMessage("Senha gerada: " + senha.getCodigo());
                saveStringConfig(R.string.config_senha_usuario, senha.getCodigo());
                startActivity(new Intent(getApplicationContext(), MonitoramentoActivity.class));
            }

            @Override
            public void onError(Exception error) {
                trataErroDesconhecido(TAG, error);
            }
        };
    }

    private void setSenhaService() {
        this.senhaService = createSenhaService();
    }

    private interface SenhaGenerator {
        void generate();
    }

    private void gerarSenha(SenhaGenerator generator) {
        generator.generate();
    }

    public void gerarSenhaPreferencial(View view) {
        gerarSenha(new SenhaGenerator() {
            public void generate() {
                senhaService.geraNovaSenhaPreferencial(senhaRecebidaListener);
            }
        });
    }

    public void gerarSenhaNormal(View view) {
        gerarSenha(new SenhaGenerator() {
            public void generate() {
                senhaService.geraNovaSenhaNormal(senhaRecebidaListener);
            }
        });
    }
}
