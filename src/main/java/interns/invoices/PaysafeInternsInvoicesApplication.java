package interns.invoices;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lowagie.text.DocumentException;

@SpringBootApplication
public class PaysafeInternsInvoicesApplication {

    public static void main(String[] args) throws InvalidFormatException, IOException, DocumentException {
        SpringApplication.run(PaysafeInternsInvoicesApplication.class, args);
    }
}
