package renatoback.paripassu.teste.analista_iii.gefi.android.services;

import android.os.AsyncTask;

public abstract class SafeAsyncTask<Params, Progess, Result> extends AsyncTask<Params, Progess, Result> {

    private Exception error;

    private TaskResultListener<Result> listener;

    public SafeAsyncTask(TaskResultListener<Result> listener) {
        this.listener = listener;
    }

    protected abstract Result doInBackgroundSafely();

    @SafeVarargs
    @Override
    protected final Result doInBackground(Params... params) {
        try {
            return doInBackgroundSafely();
        } catch (Exception e) {
            error = e;
            return null;
        }
    }

    @Override
    protected final void onPostExecute(Result result) {
        if (error == null) {
            listener.onSuccess(result);
        } else {
            listener.onError(error);
        }
    }
}
