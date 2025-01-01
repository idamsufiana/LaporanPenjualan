package com.aegis.laporan.penjualan.exception;

public class ResourceNotFoundException  extends RuntimeException{
    private static final long serialVersionUID = 8720716765345933158L;

    private final String msg;

    public static ResourceForbiddenException create(String msg) {
        return new ResourceForbiddenException(msg);
    }

    public ResourceNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
