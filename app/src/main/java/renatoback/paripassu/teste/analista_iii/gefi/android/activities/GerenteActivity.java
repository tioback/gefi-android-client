package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;
import renatoback.paripassu.teste.analista_iii.gefi.android.model.Senha;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.SenhaService;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.TaskResultListener;

public class GerenteActivity extends GefiActivity {

    private static final String TAG = GerenteActivity.class.getSimpleName();

    private TextView textoCodigoUltimaSenhaChamada;
    private TextView textoMensagemUltimaSenhaChamada;

    private SenhaService senhaService;

    private TaskResultListener<Senha> senhaRecebidaListener;
    private TaskResultListener<Void> senhaReinicidaListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerente);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        senhaService = createSenhaService();
        configuraListeners();

        carregaCampos();
    }

    private void configuraListeners() {
        senhaRecebidaListener = new TaskResultListener<Senha>() {
            @Override
            public void onSuccess(Senha senha) {
                exibeSenhaChamada(senha);
            }

            @Override
            public void onError(Exception error) {
                trataErroDesconhecido(TAG, error);
            }
        };

        senhaReinicidaListener = new TaskResultListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                showMessage(R.string.senha_reiniciada);
            }

            @Override
            public void onError(Exception error) {
                trataErroDesconhecido(TAG, error);
            }
        };
    }

    private void carregaCampos() {
        textoCodigoUltimaSenhaChamada = (TextView) findViewById(R.id.tvCodigoUltimaSenhaChamada);
        textoMensagemUltimaSenhaChamada = (TextView) findViewById(R.id.tvMensagemUltimaSenhaChamada);
    }

    public void onClickChamarProximaSenha(View view) {
        chamarProximaSenha();
    }

    private void chamarProximaSenha() {
        senhaService.chamarProximaSenha(senhaRecebidaListener);
    }

    private void exibeSenhaChamada(Senha senhaChamada) {
        textoCodigoUltimaSenhaChamada.setText(senhaChamada.getCodigo());
        textoMensagemUltimaSenhaChamada.setText(R.string.chamando_senha);
        show(textoCodigoUltimaSenhaChamada, textoMensagemUltimaSenhaChamada);
    }

    public void onClickReiniciarSenhaNormal(View view) {
        senhaService.reiniciarSenhaNormal(senhaReinicidaListener);
    }

    public void onClickReiniciarSenhaPreferencial(View view) {
        senhaService.reiniciarSenhaPreferencial(senhaReinicidaListener);
    }
}
