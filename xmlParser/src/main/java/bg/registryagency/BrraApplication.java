package bg.registryagency;

import java.net.URI;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.registryagency.repository.BrraCompanyRepository;

@SpringBootApplication
public class BrraApplication implements CommandLineRunner {

    @Autowired
    BrraCompanyRepository brraCompanyRepository;

    public static void main(String[] args) throws JAXBException, Exception {
        BrraParser bp = new BrraParser(new URI("file:///C:/Users/dimitarpahnev/Downloads/brra.bg"));
        System.out.println(bp.parseAll().size());
        // SpringApplication.run(BrraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // BrraParser brraParser = new BrraParser("");
        // Map<String, BrraCompany> companies = brraParser.parseAll();
        // brraCompanyRepository.save(companies.values());
    }

}
