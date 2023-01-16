package com.flightright.flightrightCase.controller;

import com.flightright.flightrightCase.service.VisitsCsvService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/visits")
public record VisitsCsvController(VisitsCsvService visitsCsvService) {

    @PostMapping
    public String getTotalVisits(@RequestParam String csvFilePath) {
        visitsCsvService.csvReadder(csvFilePath);
        return csvFilePath;
    }


}
