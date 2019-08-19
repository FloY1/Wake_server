package net.proselyte.jwtappdemo.controller.rest.microservice;

import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.enums.Location;
import net.proselyte.jwtappdemo.service.BookingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class fileDownolad {
    private BookingService bookingService;

    public fileDownolad(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Value("${spring.profile.active:prod}")
    private String profile;

    @GetMapping("/print")
    public ResponseEntity<byte[]> getTimeList() {

        String path = "iTextTable.pdf";
        if(!"dev".equals(profile)){
            path = "/opt/tomcat/latest/iTextTable.pdf";
        }


        byte[] pdfContents = null;

        try {
            pdfContents = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
                pdfContents, headers, HttpStatus.OK);
        return response;
    }

}
