package bg.registryagency;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrraApplication {

    private static final String FILE_PATH = "file:///C:/Users/dimitarpahnev/workspace/Invoices/invoices-brra/invoices-remastered/xmlParser/src/main/resources/static/testxml";

    // @Autowired
    // private BrraCompanyRepository brraCompanyRepository;

    public static void main(String[] args) {
        BrraParser bp = null;
        try {
            bp = new BrraParser(new URI("file:///C:/Users/dimitarpahnev/Downloads/brra.bg/2008"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            bp.parseAll();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // SpringApplication.run(BrraApplication.class, args);
    }

    // @Override
    // public void run(String... args) throws Exception {
    // BrraParser bp = new BrraParser(new URI(FILE_PATH));
    // Map<String, BrraCompany> parseAll = bp.parseAll();
    // System.out.println(parseAll.size());
    // brraCompanyRepository.save(parseAll.values());
    // }

}
