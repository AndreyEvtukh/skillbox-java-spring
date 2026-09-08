package com.diploma.skillboxjavaspring.statistics.service;

import com.diploma.skillboxjavaspring.statistics.entity.StatisticEvent;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Service for exporting statistical events to CSV and PDF formats.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsExportService {

    private final StatisticsService statisticsService;

    /**
     * Exports all statistical events to a CSV file.
     *
     * @return CSV file content as a byte array
     */
    public byte[] exportToCsv() {
        List<StatisticEvent> events = statisticsService.findAll();
        log.info("=> Statistics events for CSV: {}", events.size());

        StringBuilder csv = new StringBuilder();

        csv.append("eventId,eventType,occurredAt,userId,checkIn,checkOut\n");

        for (StatisticEvent event : events) {
            csv.append(value(event.getEventId()))
                    .append(',')
                    .append(value(event.getEventType()))
                    .append(',')
                    .append(value(event.getOccurredAt()))
                    .append(',')
                    .append(value(event.getUserId()))
                    .append(',')
                    .append(value(event.getCheckIn()))
                    .append(',')
                    .append(value(event.getCheckOut()))
                    .append('\n');
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Exports all statistical events to a PDF file.
     *
     * @return PDF file content as a byte array
     * @throws IllegalStateException if the PDF file cannot be generated
     */
    public byte[] exportToPdf() {
        List<StatisticEvent> events = statisticsService.findAll();
        log.info("=> Statistics events for PDF: {}", events.size());

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(new Paragraph("Statistics"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.addCell("Event ID");
            table.addCell("Event Type");
            table.addCell("Occurred At");
            table.addCell("User ID");
            table.addCell("Check In");
            table.addCell("Check Out");

            for (StatisticEvent event : events) {
                table.addCell(value(event.getEventId()));
                table.addCell(value(event.getEventType()));
                table.addCell(value(event.getOccurredAt()));
                table.addCell(value(event.getUserId()));
                table.addCell(value(event.getCheckIn()));
                table.addCell(value(event.getCheckOut()));
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate PDF", e);
        }
    }

    /**
     * Converts an object value to a string.
     *
     * @param value value to convert
     * @return string representation of the value, or an empty string if the value is null
     */
    private String value(Object value) {
        return value == null ? "" : value.toString();
    }
}