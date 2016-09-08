package interns.invoices;

import interns.invoices.models.Company;
import interns.invoices.models.Invoice;
import interns.invoices.models.Item;
import interns.invoices.models.User;
import interns.invoices.repositories.CompanyRepository;
import interns.invoices.repositories.InvoiceRepository;
import interns.invoices.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PaysafeInternsInvoicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaysafeInternsInvoicesApplication.class, args);
    }

    /**
     * An automatically invoked method which is called after Spring boot
     * runs the application. It uses component scan to inject instances
     * for our repositories which we use to seed our database.
     *
     * @param invoiceRepository
     * @param companyRepository
     * @return
     */
    @Bean
    CommandLineRunner runner(InvoiceRepository invoiceRepository,
                             CompanyRepository companyRepository,
                             UserRepository userRepository) {
        return (args) -> {
            {
                Invoice invoice = new Invoice();
                invoice.setInvoiceNumber("0000000001");
                {
                    List<Item> itemList = new ArrayList<>();
                    {
                        Item computer = new Item();
                        computer.setDescription("Компютър");
                        computer.setPriceWithoutVAT(1500.0);
                        computer.setQuantity(1);
                        itemList.add(computer);
                    }
                    {
                        Item monitor = new Item();
                        monitor.setDescription("Монитор");
                        monitor.setPriceWithoutVAT(2200.0);
                        monitor.setQuantity(2);
                        itemList.add(monitor);
                    }
                    invoice.setItems(itemList);
                }
                {
                    Company recipient = new Company();
                    recipient.setName("Тест фирма");
                    recipient.setEik("123456789");
                    recipient.setAddress("тест адрес");
                    recipient.setVATRegistered(false);
                    recipient.setMol("Богомил Димтров");
                    recipient = companyRepository.save(recipient);
                    invoice.setRecipient(recipient);
                }

                Company sender = new Company();
                sender.setName("STY");
                sender.setEik("458176341");
                sender.setAddress("улица Миджур №10, гр. София");
                sender.setVATRegistered(true);
                sender.setMol("Михаил Стойнов");
                {
                    Set<Invoice> issuedInvoices = new HashSet<>();
                    issuedInvoices.add(invoice);
                    sender.setIssuedInvoices(issuedInvoices);
                }
                invoice.setSender(sender);

                User myUser = new User();
                myUser.setEmail("user@example.com");
                {
                    Set<Company> myCompanies = new HashSet<>();
                    myCompanies.add(sender);
                    myUser.setMyCompanies(myCompanies);
                }
                sender.setOwner(myUser);
                userRepository.save(myUser);

                System.out.println(userRepository.findOne(1L));
            }
        };
    }
}
