package services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import com.lowagie.text.pdf.BaseFont;

import interns.invoices.models.Company;
import interns.invoices.models.Invoice;
import interns.invoices.models.Item;

public class CreatePDFService {

    private static final String DEFAULT_FONT_FAMILY = "Arial";
    private static final String DEFAULT_TEMPLATE_PATH = "defaultTemplate.docx";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    private static HashSet<String> incompatibleFonts = new HashSet<String>();
    // Populates the list of incompatible fonts.
    static {
        incompatibleFonts.add(BaseFont.COURIER);
        incompatibleFonts.add(BaseFont.COURIER_BOLD);
        incompatibleFonts.add(BaseFont.COURIER_OBLIQUE);
        incompatibleFonts.add(BaseFont.COURIER_BOLDOBLIQUE);
        incompatibleFonts.add(BaseFont.HELVETICA);
        incompatibleFonts.add(BaseFont.HELVETICA_BOLD);
        incompatibleFonts.add(BaseFont.HELVETICA_OBLIQUE);
        incompatibleFonts.add(BaseFont.HELVETICA_BOLDOBLIQUE);
        incompatibleFonts.add(BaseFont.SYMBOL);
        incompatibleFonts.add(BaseFont.TIMES_ROMAN);
        incompatibleFonts.add(BaseFont.TIMES_BOLD);
        incompatibleFonts.add(BaseFont.TIMES_ITALIC);
        incompatibleFonts.add(BaseFont.TIMES_BOLDITALIC);
        incompatibleFonts.add(BaseFont.ZAPFDINGBATS);
        incompatibleFonts.add("Times New Roman");
    }

    /**
     * Takes a template document, replaces the placeholders with the respective
     * values of the entered invoice and returns a converted PDF file.
     *
     * @param invoice
     *            The contents of the invoice which are to be filled in the
     *            template.
     * @return The PDF document as a ByteArrayOutputStream.
     * @throws Docx4JException
     *             Thrown when the {@link WordprocessingMLPackage} fails to load
     *             the OpenXML schemas.
     * @throws JAXBException
     *             Thrown if an exception occurs during the process of replacing
     *             the placeholders.
     * @throws IOException
     *             Thrown when the invoice template document cannot be loaded.
     */
    public static ByteArrayOutputStream createInvoicePDF(Invoice invoice)
            throws Docx4JException, JAXBException, IOException {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(DEFAULT_TEMPLATE_PATH));
        // Attempts to unite runs with identical styling in order to ensure
        // proper placeholder replacing.
        try {
            VariablePrepare.prepare(wordMLPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        HashMap<String, String> replacements = (HashMap<String, String>) populatePlaceholders(invoice);
        documentPart.variableReplace(replacements);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wordMLPackage.save(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        XWPFDocument xdoc = new XWPFDocument(in);
        replaceIncompatibleFonts(xdoc);

        PdfOptions pdfOptions = PdfOptions.create().fontEncoding(BaseFont.IDENTITY_H);

        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(xdoc, pdfOut, pdfOptions);

        return pdfOut;
    }

    /**
     * Maps each document placeholder to the desired value of the invoice
     * object.
     *
     * @param invoice
     *            The Invoice object containing the values
     * @return
     */
    private static Map<String, String> populatePlaceholders(Invoice invoice) {
        Company sender = invoice.getSender();
        Company recepient = invoice.getRecipient();
        Item item = invoice.getItems().get(0);
        Map<String, String> replacements = new LinkedHashMap<String, String>();
        replacements.put("recipient.name", recepient.getName());
        replacements.put("recipient.address", recepient.getAddress());
        replacements.put("recipient.eik", recepient.getEik());
        replacements.put("recipient.in", recepient.getVATNumber());
        replacements.put("recipient.mol", recepient.getMol());
        replacements.put("date", invoice.getInputDate().toLocalDate().format(DATE_FORMATTER));
        replacements.put("sender.name", sender.getName());
        replacements.put("sender.address", sender.getAddress());
        replacements.put("sender.eik", sender.getEik());
        replacements.put("sender.in", sender.getVATNumber());
        replacements.put("sender.mol", sender.getMol());
        replacements.put("number", invoice.getInvoiceNumber());
        replacements.put("item", item.getDescription());
        int quantity = item.getQuantity();
        replacements.put("quantity", Integer.toString(quantity));
        double priceWithoutVat = item.getPriceWithoutVAT();
        NumberFormat formatter = new DecimalFormat("#0.00");
        BigDecimal tax = new BigDecimal(invoice.getTax());
        replacements.put("tax", Integer.toString(invoice.getTax()));
        replacements.put("price", formatter.format(priceWithoutVat));
        BigDecimal total = new BigDecimal(quantity * priceWithoutVat);
        replacements.put("total", formatter.format(total));
        replacements.put("withVAT", formatter.format(total.add(percentage(total, tax))));

        return replacements;
    }

    /**
     * Scans every element (run) of the document for fonts which may be
     * incompatible with the {@link BaseFont.IDENTITY_H} unicode encoding and,
     * if found, replaces them with a compatible font family such as Arial.
     * Incompatible fonts are those of type 1 in the {@link BaseFont} class.
     *
     * @param xdoc
     *            The document which is to be scanned.
     */
    private static void replaceIncompatibleFonts(XWPFDocument xdoc) {
        for (XWPFParagraph paragraph : xdoc.getParagraphs()) {
            replaceIncompatibleFonts(paragraph);
        }
        List<XWPFTable> tables = xdoc.getTables();
        if (tables != null) {
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                if (rows != null) {
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        if (cells != null) {
                            for (XWPFTableCell cell : cells) {
                                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                                if (paragraphs != null) {
                                    for (XWPFParagraph paragraph : paragraphs) {
                                        replaceIncompatibleFonts(paragraph);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Replaces the incompatible fonts (if found) in a single paragraph element.
     *
     * @param run
     *            The run element to be checked for incompatible fonts.
     */
    private static void replaceIncompatibleFonts(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs != null) {
            for (XWPFRun run : runs) {
                if (incompatibleFonts.contains(run.getCTR().getRPr().getRFonts().getAscii())) {
                    run.getCTR().getRPr().getRFonts().setAscii(DEFAULT_FONT_FAMILY);
                }
                if (incompatibleFonts.contains(run.getCTR().getRPr().getRFonts().getHAnsi())) {
                    run.getCTR().getRPr().getRFonts().setHAnsi(DEFAULT_FONT_FAMILY);
                }
                if (incompatibleFonts.contains(run.getCTR().getRPr().getRFonts().getCs())) {
                    run.getCTR().getRPr().getRFonts().setCs(DEFAULT_FONT_FAMILY);
                }
            }
        }
    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(new BigDecimal(100));
    }
}
