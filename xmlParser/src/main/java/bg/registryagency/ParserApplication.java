package bg.registryagency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.registryagency.model.BrraCompany;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.envelopev2.MessageType;

@SpringBootApplication
public class ParserApplication {

    private static final String OUTPUT_FILE_NAME = "companies.txt";

    public static final String BRRA_LOCATION = "file:///C:/Users/dimitarpahnev/Downloads/brra.bg/2008";

    private String fileLocation;
    private Map<Long, BrraCompany> companies;
    private JAXBContext jaxbContext;

    public ParserApplication(String fileLocation) {

        this.fileLocation = fileLocation;
        companies = new HashMap<>();

        try {
            this.jaxbContext = JAXBContext.newInstance("bg.registryagency.schemas.envelopev2");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        new ParserApplication(BRRA_LOCATION).start();
    }

    public void start() throws Exception {
        long start = System.currentTimeMillis();

        for (File file : getFilesFromDirectory(new URI(fileLocation))) {
            if (file.isFile()) {

                List<BrraCompany> parsedBrraCompanies = parseBrraXMLPage(file);
                addParsedCompanyToCollection(parsedBrraCompanies);
            }
        }

        createLogFile();

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) / 1000.0);

        // SpringApplication.run(ParserApplication.class, args);
    }

    /**
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void createLogFile() throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(OUTPUT_FILE_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        for (BrraCompany currentCompany : companies.values()) {
            System.out.println(currentCompany.toString());
            bufferedWriter.write(currentCompany.toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileOutputStream.close();

        System.out.println("hashmap size : " + companies.size());
    }

    /**
     */
    private void addParsedCompanyToCollection(List<BrraCompany> parsedBrraCompanies) {
        for (BrraCompany currentCompany : parsedBrraCompanies) {
            if (companies.containsKey(currentCompany.getEik())) {
                BrraCompany previousRegistry = companies.get(currentCompany.getEik());
                if (previousRegistry.getDateLastModified().before(currentCompany.getDateLastModified())) {
                    previousRegistry.updateCompanyEntry(currentCompany);
                }
            } else {
                companies.put(currentCompany.getEik(), currentCompany);
            }
        }
    }

    /**
     * Given the URI of a directory get all the files and subdirectories it
     * contains. Files in the subdirectories, as well as subsubdirectories etc.
     * are also included in the resulting list of files. The directory traversal
     * is done recursively.
     *
     * @param directory
     *            the directory which will be scanned for all contained files
     *            and subdirectories
     * @return {@link List} of all the files contained in the specified
     *         directory
     */
    public List<File> getFilesFromDirectory(URI directory) throws Exception {
        File workingDirectory = new File(directory);
        File[] listOfFiles = workingDirectory.listFiles();
        List<File> results = Arrays.asList(listOfFiles);
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                results = Stream.concat(results.stream(), getFilesFromDirectory(file.toURI()).stream())
                        .collect(Collectors.toList());
            }
        }

        return results;
    }

    public List<BrraCompany> parseBrraXMLPage(File file) throws Exception {

        long start = System.currentTimeMillis();

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MessageType messageType = unmarshaller.unmarshal(new StreamSource(file), MessageType.class).getValue();

        Date fileDate = generateDateFromFileName(file);

        long end = System.currentTimeMillis();
        System.out.println("Parsing " + file.getName() + " takes: " + (end - start) / 1000.0);

        List<BrraCompany> companiesFromFile = new ArrayList<>();

        // To avoid memory leak.
        Iterator<DeedType> it = messageType.getBody().getDeeds().getDeed().iterator();
        while (it.hasNext()) {
            companiesFromFile.add(BrraCompany.createInstance(it.next(), fileDate));
            it.remove();
        }

        messageType = null;
        System.gc();

        return companiesFromFile;
    }

    /**
     * @param file
     * @return
     * @throws ParseException
     */
    private Date generateDateFromFileName(File file) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd", Locale.ENGLISH);
        return dateFormat.parse(file.getName());
    }

    public Map<Long, BrraCompany> getParsedCompanies() {
        return Collections.unmodifiableMap(companies);
    }

}
