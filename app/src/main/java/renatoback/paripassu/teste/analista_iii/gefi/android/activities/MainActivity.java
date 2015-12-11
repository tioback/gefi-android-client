package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;

public class MainActivity extends GefiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean configuracoesValidas() {
        String enderecoServicoSenhas = loadEnderecoServicoSenhas();
        if (enderecoServicoSenhas == null || enderecoServicoSenhas.trim().isEmpty()) {
            showMessage(R.string.endereco_servico_senhas_nao_configurado);
            return false;
        }

        return true;
    }

    public void abrirAcompanhamentoDaChamadaDeSenhas(View view) {
        if (configuracoesValidas()) {
            startActivity(new Intent(getApplicationContext(), MonitoramentoActivity.class));
        }
    }

    public void abrirAcessoDoCliente(View view) {
        if (configuracoesValidas()) {
            startActivity(new Intent(getApplicationContext(), ClienteActivity.class));
        }
    }

    public void abrirAcessoDoGerente(View view) {
        if (configuracoesValidas()) {
            startActivity(new Intent(getApplicationContext(), GerenteActivity.class));
        }
    }

    public void abrirConfiguracoes(View view) {
        startActivity(new Intent(getApplicationContext(), ConfiguracoesActivity.class));
    }
}
