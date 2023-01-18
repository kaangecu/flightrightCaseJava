package com.flightright.flightrightCase.service;

import com.flightright.flightrightCase.domain.model.User;
import com.flightright.flightrightCase.domain.model.Visit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
public class VisitsCsvService {

    private static Predicate<Visit> distinctByKey() {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(t.user(), Boolean.TRUE) == null;
    }

    public Map<String, Integer> getVisitsFromCsv(MultipartFile file) throws ResponseStatusException {
        Map<String, Integer> sourceCounts;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skips first line since in csv files first line contains headers
            Stream<String> lines = reader.lines().skip(1);

            Map<User, String> firstVisits = lines.filter(line -> !line.startsWith(",") && !line.endsWith(","))
                    .map(line -> line.split(","))
                    .filter(columns -> !asList(columns).contains(""))
                    .map(columns -> new Visit(new User(columns[0], columns[1]), columns[2]))
                    .filter(distinctByKey())
                    .collect(groupingBy(Visit::user, mapping(Visit::source, joining())));

            sourceCounts = firstVisits.values().stream()
                    .collect(groupingBy(identity(), summingInt(e -> 1)));

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "IO Exception");
        }

        return sourceCounts;
    }
}
