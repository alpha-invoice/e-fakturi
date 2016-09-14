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

import bg.registryagency.model.BrraCompany;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.envelopev2.MessageType;

public class BrraParser {

    private static final String OUTPUT_FILE_NAME = "companies.txt";

    public static final String BRRA_LOCATION = "file:///C:/Users/borisrosenov/Downloads/brra.bg/2008";

    private String registryLocation;
    private Map<Long, BrraCompany> companies;
    private JAXBContext jaxbContext;

    public BrraParser(String registryLocation) {

        this.registryLocation = registryLocation;
        companies = new HashMap<>();

        try {
            this.jaxbContext = JAXBContext.newInstance("bg.registryagency.schemas.envelopev2");
        } catch (JAXBException e) {
            System.err.println("Could not load registry xml schema.");
        }

    }

    public Map<Long, BrraCompany> parseAll() throws Exception {
        long start = System.currentTimeMillis();

        for (File file : getFilesFromDirectory(new URI(registryLocation))) {
            if (file.isFile()) {
                List<BrraCompany> parsedBrraCompanies = parseBrraXMLPage(file);
                updateCompanies(parsedBrraCompanies);
            }
        }

        createLogFile();

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) / 1000.0);

        return companies;
    }

    /**
     * Adds the parsedBrraCompanies list to the companies hashmap. Old company
     * entries are updated, whereas new ones are added to the hashmap.
     *
     * @param parsedBrraCompanies list of parsed brra companies which should be
     *            added/updated in the companies hashmap
     */
    private void updateCompanies(List<BrraCompany> parsedBrraCompanies) {
        for (BrraCompany currentCompany : parsedBrraCompanies) {
            if (companies.containsKey(currentCompany.getEik())) {
                BrraCompany previousEntry = companies.get(currentCompany.getEik());
                if (previousEntry.getDateLastModified().before(currentCompany.getDateLastModified())) {
                    previousEntry.updateCompanyData(currentCompany);
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
     * @param directory URI of the directory which will be scanned for all
     *            contained files and subdirectories
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

    public List<BrraCompany> parseBrraXMLPage(File page) throws Exception {

        long start = System.currentTimeMillis();

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MessageType messageType = unmarshaller.unmarshal(new StreamSource(page), MessageType.class).getValue();

        Date fileDate = generateDateFromFileName(page);

        long end = System.currentTimeMillis();
        System.out.println("Parsing " + page.getName() + " takes: " + (end - start) / 1000.0);

        List<BrraCompany> companiesFromFile = new ArrayList<>();

        Iterator<DeedType> deedIterator = messageType.getBody().getDeeds().getDeed().iterator();
        while (deedIterator.hasNext()) {
            companiesFromFile.add(BrraCompany.createInstance(deedIterator.next(), fileDate));
            deedIterator.remove();
        }

        // To avoid memory leak.
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

    /**
     * TODO: Will be deleted in release
     *
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

    public Map<Long, BrraCompany> getParsedCompanies() {
        return Collections.unmodifiableMap(companies);
    }

}
