package com.flightright.flightrightCase.controller;

import com.flightright.flightrightCase.domain.dto.VisitSourceResponse;
import com.flightright.flightrightCase.service.VisitsCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitsCsvController {
    private final VisitsCsvService visitsCsvService;

    @PostMapping(consumes = "multipart/form-data")
    public List<VisitSourceResponse> getTotalVisits(@RequestParam MultipartFile csvFile) throws ResponseStatusException {
        return visitsCsvService.getVisitsFromCsv(csvFile)
                .entrySet()
                .stream()
                .map(entry -> new VisitSourceResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
