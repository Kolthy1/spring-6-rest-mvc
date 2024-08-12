package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
