package interns.invoices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import interns.invoices.models.Company;
import interns.invoices.models.Invoice;
import interns.invoices.models.Item;
import interns.invoices.repositories.CompanyRepository;
import interns.invoices.repositories.InvoiceRepository;

@SpringBootApplication
public class PaysafeInternsInvoicesApplication {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(PaysafeInternsInvoicesApplication.class, args);
	}

	/**
	 * An automatically invoked method which is called after Spring boot
	 * runs the application. It uses component scan to inject instances
	 * for our repositories which we use to seed our database.
	 * @param invoiceRepository
	 * @param companyRepository
     * @return
     */
	@Bean
    CommandLineRunner runner() {
		return (args) -> {
			{
				Invoice invoice = new Invoice();
				invoice.setInvoiceNumber("0000000001");
				{
					Company sender = new Company();
					sender.setName("STY");
					sender.setEik("458176341");
					sender.setAddress("улица Миджур №10, гр. София");
					sender.setVATRegistered(true);
					sender.setMol("Михаил Стойнов");
					sender = companyRepository.save(sender);
					invoice.setSender(sender);
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
				invoiceRepository.save(invoice);

				System.out.println(invoiceRepository.findOne(1L));
			}
		};
	}
}
