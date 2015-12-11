package renatoback.paripassu.teste.analista_iii.gefi.android.services;

public interface TaskResultListener<T> {
    void onSuccess(T result);

    void onError(Exception error);
}
