package bg.registryagency;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.registryagency.model.BrraCompany;
import bg.registryagency.repository.BrraCompanyRepository;

@SpringBootApplication
public class BrraApplication implements CommandLineRunner {

    private static final String FILE_PATH = "file:///C:/Users/dimitarpahnev/workspace/Invoices/invoices-brra/invoices-remastered/xmlParser/src/main/resources/static/testxml";

    @Autowired
    private BrraCompanyRepository brraCompanyRepository;

    public static void main(String[] args) {
        SpringApplication.run(BrraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BrraParser bp = new BrraParser(new URI(FILE_PATH));
        Map<String, BrraCompany> parseAll = bp.parseAll();
        System.out.println(parseAll.size());
        brraCompanyRepository.save(parseAll.values());
    }

}
