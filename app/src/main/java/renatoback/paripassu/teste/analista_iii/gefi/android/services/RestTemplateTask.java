package renatoback.paripassu.teste.analista_iii.gefi.android.services;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public abstract class RestTemplateTask<Params, Progess, Result> extends SafeAsyncTask<Params, Progess, Result> {

    private ResponseErrorHandler errorHandler;

    public RestTemplateTask(TaskResultListener<Result> listener, ResponseErrorHandler errorHandler) {
        super(listener);
        this.errorHandler = errorHandler;
    }

    protected abstract ResponseEntity<Result> doRequest(RestTemplate template);

    @Override
    protected Result doInBackgroundSafely() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        template.setErrorHandler(errorHandler);
        ResponseEntity<Result> response = doRequest(template);
        switch (response.getStatusCode()) {
            case NOT_FOUND: return null;
            case OK: return response.getBody();
            default: throw new IllegalStateException("Unknown Status Code:" + response.getStatusCode());
        }
    }

}
