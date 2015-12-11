package renatoback.paripassu.teste.analista_iii.gefi.android.exceptions;

public abstract class SenhaServiceException extends RuntimeException {

    public SenhaServiceException() {
    }

    public SenhaServiceException(String detailMessage) {
        super(detailMessage);
    }

    public abstract int getMessageResourceId();
}
