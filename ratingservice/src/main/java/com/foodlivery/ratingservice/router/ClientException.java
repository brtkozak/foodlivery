package com.foodlivery.ratingservice.router;
import org.springframework.web.reactive.function.client.ClientResponse;

public class ClientException extends Exception {

    private String message;
    private int status;

    public ClientException(String message, ClientResponse clientResponse) {
        this.message = message;
        if (clientResponse != null)
            this.status = clientResponse.statusCode().value();
        else this.status = 404;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
