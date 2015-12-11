package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;
import renatoback.paripassu.teste.analista_iii.gefi.android.model.Senha;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.SenhaService;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.TaskResultListener;

public class MonitoramentoActivity extends GefiActivity {

    private static final String TAG = MonitoramentoActivity.class.getSimpleName();

    private TextView textoMensagemSenhaUsuario;
    private TextView textoCodigoSenhaUsuario;
    private Space divisor;
    private Button botaoUltimaSenhaChamada;
    private TextView textoMonitoramentoAutomatico;
    private TextView textoMensagemUltimaSenhaChamada;
    private TextView textoCodigoUltimaSenhaChamada;

    private SenhaService senhaService;
    
    private TaskResultListener<Senha> listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoramento);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        senhaService = createSenhaService();
        configuraListener();
        carregaCampos();
        carregaSenhaUsuario();
        iniciaMonitoramento();
    }

    private void configuraListener() {
        listener = new TaskResultListener<Senha>() {
            @Override
            public void onSuccess(Senha senha) {
                trataUltimaSenhaChamada(senha);
            }

            @Override
            public void onError(Exception error) {
                trataErroDesconhecido(TAG, error);
            }
        };
    }

    private void trataUltimaSenhaChamada(Senha senha) {
        if (senha == null) {
            trataSenhaNaoChamada();
            return;
        }

        String codigo = senha.getCodigo();
        if (mesmaSenhaDeAntes(codigo)) {
            pisca(textoCodigoUltimaSenhaChamada);
            return;
        }

        textoCodigoUltimaSenhaChamada.setText(codigo);
        textoMensagemUltimaSenhaChamada.setText(R.string.ultima_senha_chamada);
        show(textoCodigoUltimaSenhaChamada);
    }

    private boolean mesmaSenhaDeAntes(String codigo) {
        return codigo.equals(textoCodigoUltimaSenhaChamada.getText().toString());
    }

    private void trataSenhaNaoChamada() {
        if (continuaSemSenhaChamada()) {
            pisca(textoMensagemUltimaSenhaChamada);
        } else {
            textoMensagemUltimaSenhaChamada.setText(R.string.nenhuma_senha_chamada_ate_momento);
            hide(textoCodigoUltimaSenhaChamada);
        }
    }

    private boolean continuaSemSenhaChamada() {
        return !textoCodigoUltimaSenhaChamada.isShown();
    }


    private void carregaCampos() {
        textoMensagemSenhaUsuario = (TextView) findViewById(R.id.tvMensagemSenhaUsuario);
        textoCodigoSenhaUsuario = (TextView) findViewById(R.id.tvCodigoSenhaUsuario);
        divisor = (Space) findViewById(R.id.spDivisor);
        botaoUltimaSenhaChamada = (Button) findViewById(R.id.btAtualizar);
        textoMonitoramentoAutomatico = (TextView) findViewById(R.id.tvMonitoramentoAutomatico);
        textoMensagemUltimaSenhaChamada = (TextView) findViewById(R.id.tvMensagemUltimaSenhaChamada);
        textoCodigoUltimaSenhaChamada = (TextView) findViewById(R.id.tvCodigoUltimaSenhaChamada);
    }

    private void carregaSenhaUsuario() {
        String senhaUsuario = loadStringConfig(R.string.config_senha_usuario);
        if (senhaUsuario != null && !senhaUsuario.isEmpty()) {
            textoCodigoSenhaUsuario.setText(senhaUsuario);
            show(textoMensagemSenhaUsuario, textoCodigoSenhaUsuario, divisor);
            return;
        }

        hide(textoMensagemSenhaUsuario, textoCodigoSenhaUsuario, divisor);
    }

    private void iniciaMonitoramento() {
        textoMensagemUltimaSenhaChamada.setText(R.string.nenhuma_senha_chamada_ate_momento);

        boolean isMonitoramentoAutomatico = loadBooleanConfig(R.string.config_monitoramento_automatico);
        if (isMonitoramentoAutomatico) {
            configuraMonitoramentoAutomatico();
        } else {
            configuraMonitoramentoManual();
        }
    }

    private void configuraMonitoramentoManual() {
        hide(textoMonitoramentoAutomatico);
        show(botaoUltimaSenhaChamada);
    }

    private void configuraMonitoramentoAutomatico() {
        show(textoMonitoramentoAutomatico);
        hide(botaoUltimaSenhaChamada);

//        senhaService.monitorarChamadaDeSenhas(new ChamadaDeSenhaListener() {
//            @Override
//            public void onSenhaChamada(Senha senha) {
//                showMessage(senha.getCodigo());
//            }
//        });
    }

    public void onClickBuscarUltimaSenhaChamada(View view) {
        buscarUltimaSenhaChamada();
    }

    private void buscarUltimaSenhaChamada() {
        senhaService.buscaUltimaSenhaChamada(listener);
    }
}
