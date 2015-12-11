package renatoback.paripassu.teste.analista_iii.gefi.android.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import renatoback.paripassu.teste.analista_iii.gefi.android.R;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.SenhaService;
import renatoback.paripassu.teste.analista_iii.gefi.android.services.ServiceFactory;

public abstract class GefiActivity extends AppCompatActivity {

    protected void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showMessage(int resourceId) {
        Toast.makeText(getApplicationContext(), resourceId, Toast.LENGTH_SHORT).show();
    }

    protected void saveStringConfig(int resourceId, String value) {
        getGefiPreferences().edit().putString(getString(resourceId), value).apply();
    }

    protected void saveBooleanConfig(int resourceId, boolean value) {
        getGefiPreferences().edit().putBoolean(getString(resourceId), value).apply();
    }

    protected String loadStringConfig(int resourceId) {
        return getGefiPreferences().getString(getString(resourceId), "");
    }

    protected boolean loadBooleanConfig(int resourceId) {
        return getGefiPreferences().getBoolean(getString(resourceId), false);
    }

    private SharedPreferences getGefiPreferences() {
        return getSharedPreferences(GefiActivity.class.getSimpleName(), MODE_PRIVATE);
    }

    protected String loadEnderecoServicoSenhas() {
        return loadStringConfig(R.string.config_endereco_servico_senhas);
    }

    protected void saveSenhaUsuario(String senha) {
        saveStringConfig(R.string.config_senha_usuario, senha);
    }

    protected SenhaService createSenhaService() {
        return ServiceFactory.createSenhaService(loadEnderecoServicoSenhas());
    }

    protected void show(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void hide(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    protected void pisca(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        view.startAnimation(anim);
    }

    protected void trataErroDesconhecido(String TAG, Exception e) {
        showMessage(R.string.ocorreu_erro_contate_suporte);
        Log.e(TAG, getString(R.string.ocorreu_erro_contate_suporte), e);
    }

}
