package com.diploma.skillboxjavaspring.statistics.controller;

import com.diploma.skillboxjavaspring.statistics.service.StatisticsExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for exporting statistical data.
 */
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(
        name = "Statistics",
        description = "Endpoints for exporting statistical data"
)
public class StatisticsController {

    private final StatisticsExportService statisticsExportService;

    /**
     * Downloads statistical data as a CSV file.
     *
     * @return CSV file containing statistical events
     */
    @Operation(
            summary = "Export statistics to CSV",
            description = "Generates a CSV file containing all statistical events stored in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistics successfully exported to CSV"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication is required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Administrator role is required"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to generate CSV file"
            )
    })
    @GetMapping("/csv")
    public ResponseEntity<byte[]> downloadCsv() {

        byte[] csv = statisticsExportService.exportToCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"statistics.csv\""
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    /**
     * Downloads statistical data as a PDF file.
     *
     * @return PDF file containing statistical events
     */
    @Operation(
            summary = "Export statistics to PDF",
            description = "Generates a PDF file containing all statistical events stored in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistics successfully exported to PDF"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication is required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Administrator role is required"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to generate PDF file"
            )
    })
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        byte[] file = statisticsExportService.exportToPdf();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"statistics.pdf\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}