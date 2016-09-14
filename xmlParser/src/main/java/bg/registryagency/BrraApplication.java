package bg.registryagency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.registryagency.repository.BrraCompanyRepository;

@SpringBootApplication
public class BrraApplication implements CommandLineRunner {

    @Autowired
    BrraCompanyRepository brraCompanyRepository;

    public static void main(String[] args) {
        // SpringApplication.run(BrraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // BrraParser brraParser = new BrraParser("");
        // Map<Long, BrraCompany> companies = brraParser.parseAll();
        // brraCompanyRepository.save(companies.values());
    }

}
