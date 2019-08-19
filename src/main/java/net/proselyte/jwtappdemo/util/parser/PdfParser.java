package net.proselyte.jwtappdemo.util.parser;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.proselyte.jwtappdemo.model.Booking;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PdfParser {


    public PdfParser() {

    }

    public void create(ArrayList<Booking> bookings,String path){
        try {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(path));


            document.open();

            PdfPTable table = new PdfPTable(4);
            PdfPTable table0 = new PdfPTable(1);
            PdfPTable table1 = new PdfPTable(1);
            PdfPTable table2 = new PdfPTable(1);
            PdfPTable table3 = new PdfPTable(1);


            int starTime = 540;
            int endtime = 1260;

            ArrayList<Booking> booking1 = new ArrayList<>();
            ArrayList<Booking> booking2 = new ArrayList<>();
            ArrayList<Booking> booking3 = new ArrayList<>();
            bookings.forEach(booking -> {
                if(booking.getReversNumber() == 1){
                    booking1.add(booking);
                }
                if(booking.getReversNumber() == 2){
                    booking2.add(booking);
                }
                if(booking.getReversNumber() == 3){
                    booking2.add(booking);
                }
            });
            for (int i=starTime;i<endtime ;i+=10){

               addCustomRows(table0,i/60+":"+((i%60)<6 ? "0"+(i%60):(i%60)),20);
               checkBooking(booking1,table1,i);
               checkBooking(booking2,table2,i);
               checkBooking(booking3,table3,i);

            }






            table.addCell(table0);
            table.addCell(table1);
            table.addCell(table2);
            table.addCell(table3);



            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("Time", "1", "2","3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(0);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }
    private void addCustomRows(PdfPTable table,String text,int size){

        Phrase texts = new Phrase();
        texts.setFont(FontFactory.getFont(FontFactory.COURIER,6,BaseColor.BLACK));
        texts.add(text);
        PdfPCell horizontalAlignCell1 = new PdfPCell(texts);
        horizontalAlignCell1.setFixedHeight(size);
        horizontalAlignCell1.setBorderWidthLeft(0);
        horizontalAlignCell1.setBorderWidthRight(0);
        horizontalAlignCell1.setBorderWidthTop(0);
        horizontalAlignCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        horizontalAlignCell1.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell1);



    }

    private void checkBooking(ArrayList<Booking> booking, PdfPTable table, int i){
        if(booking.isEmpty()) {
            addCustomRows(table,"",20);
            return;
        }
        if(booking.get(0).getStartTime()==i){
            addCustomRows(table,booking.get(0).getClient().getUserName()+"\n"+booking.get(0).getClient().getPhone(),20);
            booking.remove(0);
        }else {
            addCustomRows(table,"",20);
        }
    }


}
