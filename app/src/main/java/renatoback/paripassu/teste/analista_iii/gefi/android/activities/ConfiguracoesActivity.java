package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;

public class ConfiguracoesActivity extends GefiActivity {

    private EditText campoEndereco;
    private TextView textoModoMonitoramento;
    private Switch switchModoMonitoramento;
    private CheckBox checkApagarSenhaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        carregaCampos();
        initEndereco();
        initModoMonitoramento();
    }

    private void carregaCampos() {
        switchModoMonitoramento = (Switch) findViewById(R.id.swModoMonitoramento);
        textoModoMonitoramento = (TextView) findViewById(R.id.tvModoMonitoramento);
        campoEndereco = (EditText) findViewById(R.id.etEnderecoServicoSenhas);
        checkApagarSenhaAtual = (CheckBox) findViewById(R.id.cbApagarSenhaAtualUsuario);
    }

    private void initModoMonitoramento() {
        boolean isMonitoramentoAutomatico = loadBooleanConfig(R.string.config_monitoramento_automatico);
        switchModoMonitoramento.setChecked(isMonitoramentoAutomatico);
        switchModoMonitoramento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                atualizaTextoModoMonitoramento(isChecked);
            }
        });
        atualizaTextoModoMonitoramento(isMonitoramentoAutomatico);
    }

    private void atualizaTextoModoMonitoramento(boolean isMonitoramentoAutomatico) {
        textoModoMonitoramento.setText(isMonitoramentoAutomatico ? R.string.automatico : R.string.manual);
    }

    private void initEndereco() {
        campoEndereco.setText(loadEnderecoServicoSenhas());
    }

    public void onClickSalvar(View view) {
        String endereco = campoEndereco.getText().toString();
        if (endereco.trim().isEmpty()) {
            showMessage(R.string.endereco_invalido);
            return;
        }

        if (checkApagarSenhaAtual.isChecked()) {
            saveSenhaUsuario(null);
        }
        saveStringConfig(R.string.config_endereco_servico_senhas, endereco);
        saveBooleanConfig(R.string.config_monitoramento_automatico, switchModoMonitoramento.isChecked());
        showMessage(R.string.configuracoes_salvas);
    }
}
