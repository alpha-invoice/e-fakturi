package bg.registryagency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import bg.registryagency.exception.InvalidDeedException;
import bg.registryagency.model.BrraCompany;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.envelopev2.MessageType;

/**
 * BrraParser is class for parsing the open source version of the Brra registry.
 * The registry contains XML files which contain company data for many of the
 * registered Bulgarian companies. When given the location of the registry this
 * class can parse all the XML files inside and retrieve the most up-to-date
 * information about each company.
 */
public class BrraParser {

    private static final String BRRA_SCHEMA = "bg.registryagency.schemas.envelopev2";

    private static final String OUTPUT_FILE_NAME = "companies.txt";

    private long logTimer;

    private URI registryLocation;

    // we identify each {@link BrraCompany} by its EIK (UIC)
    private Map<String, BrraCompany> companies;

    private JAXBContext jaxbContext;

    /**
     * Constructor for the BrraParser. Initiliazes the location of the BRRA
     * registry where the XML files we need to parse are located.
     *
     * @param registryLocation
     *            the URI of the directory where the Brra registry is located.
     */
    public BrraParser(URI registryLocation) {
        this.registryLocation = registryLocation;
        this.companies = new HashMap<>();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    createLogFile();
                } catch (IOException e) {
                    System.err.println("Logging into file was interrupter with exception.");
                    e.printStackTrace();
                }
            };
        });
    }

    /**
     * Parses all the XML pages in the directory specified by registryLocation
     * and combines all company information into a map.
     *
     * @return a map containing the most up-to-date company data for each
     *         {@link BrraCompany}
     * @throws JAXBException
     *             when there is a problem with initializing JAXB or an XML page
     *             from the registry location is corrupted
     */
    public Map<String, BrraCompany> parseAll() throws JAXBException {

        initializeBrraJaxbContext();

        for (File file : getFilesFromDirectory(registryLocation)) {
            if (file.isFile()) {
                List<BrraCompany> parsedBrraCompanies = parseBrraXMLPage(file);
                updateCompanies(parsedBrraCompanies);
            }
        }

        return companies;
    }

    /**
     * Initialize the Brra JAXB context from the Brra XML schema. N.B:
     * Initializing the context for the first time is a heavy operation
     *
     * @throws JAXBException
     */
    public void initializeBrraJaxbContext() throws JAXBException {
        if (this.jaxbContext == null) {
            this.jaxbContext = JAXBContext.newInstance(BRRA_SCHEMA);
        }
    }

    /**
     * Adds the parsedBrraCompanies list to the companies hashmap. Old company
     * entries are updated, whereas new ones are added to the hashmap.
     *
     * @param parsedBrraCompanies
     *            list of parsed brra companies which should be added/updated in
     *            the companies hashmap
     */
    private void updateCompanies(List<BrraCompany> parsedBrraCompanies) {

        for (BrraCompany currentCompany : parsedBrraCompanies) {
            if (companies.containsKey(currentCompany.getEik())) {
                BrraCompany previousEntry = companies.get(currentCompany.getEik());
                if (previousEntry.getDateLastModified().isBefore(currentCompany.getDateLastModified())) {
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
     * @param directory
     *            URI of the directory which will be scanned for all contained
     *            files and subdirectories
     * @return {@link List} of all the files contained in the specified
     *         directory
     */
    private List<File> getFilesFromDirectory(URI directory) {
        File workingDirectory = new File(directory);

        if (workingDirectory.isFile()) {
            return Arrays.asList(workingDirectory);
        }

        File[] listOfFiles = workingDirectory.listFiles();
        List<File> results = Arrays.asList(listOfFiles);

        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                results = Stream.concat(results.stream(), getFilesFromDirectory(file.toURI()).stream())
                        .collect(Collectors.toList());
            }
        }

        results.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));

        return results;
    }

    /**
     * Parse a single Brra XML page and return a list of parsed companies from
     * it
     *
     * @param page
     *            the page to be parsed
     * @return list of parsed companies from the specified Brra page
     * @throws JAXBException
     */
    private List<BrraCompany> parseBrraXMLPage(File page) throws JAXBException {

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MessageType messageType = unmarshaller.unmarshal(new StreamSource(page), MessageType.class).getValue();

        List<BrraCompany> companiesFromFile = new ArrayList<>();

        LocalDate fileDate = generateDateFromBrraFileName(page.getName());
        if (fileDate == null) {
            System.err.println("Could not parse file " + page.getName());
            return companiesFromFile;
        }
        Iterator<DeedType> deedIterator = messageType.getBody().getDeeds().getDeed().iterator();
        while (deedIterator.hasNext()) {
            try {
                BrraCompany toAdd = BrraCompany.createInstance(deedIterator.next(), fileDate);
                companiesFromFile.add(toAdd);
            } catch (InvalidDeedException e) {
                System.err.println(e.getMessage());
            } finally {
                deedIterator.remove();
            }
        }

        if (System.currentTimeMillis() - logTimer > 5000) {
            System.out.println("Currently prossessing file : " + page.getName());
            logTimer = System.currentTimeMillis();
        }

        return companiesFromFile;
    }

    /**
     * Generate a date from the file name of a single Brra XML page. Example
     * Brra file name: 20080703.xml. Date from file name: 03 July 2008
     *
     * @param fileName
     *            the Brra XML file from which a date will be extracted
     * @return {@link Date} object containing year, month and day
     * @throws ParseException
     */
    private LocalDate generateDateFromBrraFileName(String fileName) {
        try {
            String fileNameWithoutExtension = fileName.substring(0, fileName.indexOf('.'));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(fileNameWithoutExtension, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Could not extract date from " + fileName);
        }

        return null;
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
            bufferedWriter.write(currentCompany.toString());
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        fileOutputStream.close();

        System.out.println("hashmap size : " + companies.size());
    }

}
