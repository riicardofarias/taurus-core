package br.com.gransistemas.taurus.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException() {

    }

    public DeviceNotFoundException(String message) {
        super(message);
    }
}
