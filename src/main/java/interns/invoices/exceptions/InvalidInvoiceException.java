package interns.invoices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error
 * in our model. Used to hide the stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values passed for creating invoice")
public class InvalidInvoiceException extends RuntimeException {
    public InvalidInvoiceException() {
    }

    public InvalidInvoiceException(String message) {
        super(message);
    }

    public InvalidInvoiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInvoiceException(Throwable cause) {
        super(cause);
    }
}
