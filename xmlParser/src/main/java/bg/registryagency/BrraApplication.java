package bg.registryagency;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.registryagency.model.BrraCompany;
import bg.registryagency.repository.BrraCompanyRepository;

@SpringBootApplication
public class BrraApplication implements CommandLineRunner {

    private static final int BATCH_SIZE = 1000;

    private static final String FILE_PATH = "file:///C:/Users/borisrosenov/Downloads/brra.bg";

    @Autowired
    private BrraCompanyRepository brraCompanyRepository;

    public static void main(String[] args) {
        SpringApplication.run(BrraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BrraParser bp = new BrraParser(new URI(FILE_PATH));
        Map<String, BrraCompany> companies = bp.parseAll();
        System.out.println(companies.size());

        List<BrraCompany> all = new ArrayList<BrraCompany>(companies.values());
        for (int i = 0; i < Math.ceil(all.size() * 1.0 / BATCH_SIZE); i++) {
            List<BrraCompany> batch = null;
            if (all.size() - i * BATCH_SIZE < BATCH_SIZE) {
                batch = all.subList(i * BATCH_SIZE, all.size());
            } else {
                batch = all.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
            }

            brraCompanyRepository.save(batch);
            System.out.println("Saved " + batch.size());
        }
        // BrraCompany b = new BrraCompany();
        // b.setAddress(
        // "asds1sssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasds1sssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssss");
        // b.setEik(
        // "12412412");
        // b.setMol(
        // "dasdasdas dasd as da asdsa ");
        // b.setName(
        // "asds1sssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasds1sssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssssasdssssssssssssssss");
        // brraCompanyRepository.save(b);
    }

}
