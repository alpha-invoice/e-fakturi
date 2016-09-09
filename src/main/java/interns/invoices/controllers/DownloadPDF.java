package interns.invoices.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest service providing "/preview" and "/download". Preview: provides pdf file
 * for preview. Download starts downloading the file in the browser.
 */
@RestController
public class DownloadPDF {
    private static final String THE_NAME_OF_THE_FILE_SENDING_PREVIEW = "invoice.pdf";
    private static final String CONTENT_DISPOSITION_TYPE_INLINE_STRING = "inline;filname=";
    private static final String THE_NAME_OF_THE_FILE_SENDING_DOWNLOAD = "invoice.pdf";
    private static final String CONTENT_DISPOSITION_TYPE_ATACHMENT_STRING = "attachment;filename=";
    private static final String samplePDF = "C:/Users/dimitarpahnev/Desktop/sample.pdf";

    /**
     * Return response with pdf file for preview.
     * 
     * @return input stream for the pdf file
     * @throws FileNotFoundException
     */
    @RequestMapping(path = "/preview")
    public ResponseEntity<InputStreamResource> previewPDF() throws FileNotFoundException {
        File pdf = new File(samplePDF);
        if (pdf.exists() && pdf.isFile()) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            CONTENT_DISPOSITION_TYPE_INLINE_STRING +
                                    THE_NAME_OF_THE_FILE_SENDING_PREVIEW)
                    .contentLength(pdf.length())
                    .body(new InputStreamResource(new FileInputStream(pdf)));

        }
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * Return response with attached pdf file, downloads the file in the
     * browser.
     * 
     * @return input stream with attached file.
     * @throws FileNotFoundException
     */
    @RequestMapping(path = "/download")
    public ResponseEntity<InputStreamResource> downloadPDF() throws FileNotFoundException {
        File pdf = new File(samplePDF);
        if (pdf.exists() && pdf.isFile()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            CONTENT_DISPOSITION_TYPE_ATACHMENT_STRING +
                                    THE_NAME_OF_THE_FILE_SENDING_DOWNLOAD)
                    .contentLength(pdf.length())
                    .body(new InputStreamResource(new FileInputStream(pdf)));
        }
        return ResponseEntity.badRequest().body(null);
    }

}
