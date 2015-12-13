/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.reporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Builds benchmark report in csv format
 * @author denis
 */
public class ReportBuilder {

    private final String NEW_LINE_SEPARATOR = "\n";

    private FileWriter fileWriter;
    private final File file;
    private final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    /**
     * 
     * @param fileName
     * @throws IOException
     */
    public ReportBuilder(String fileName) throws IOException {
        this.file = new File(fileName);
    }

    /**
     * Writes record to csv file
     * @param record
     * @throws IOException
     */
    public void writeRecord(ReportRecord record) throws IOException {
        fileWriter = new FileWriter(file, true);
        CSVPrinter csvFilePrinter;
        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
        if (!file.exists() || file.length() == 0) {
            csvFilePrinter.printRecord(record.getHeader());
        }
        csvFilePrinter.printRecord(record.getData());
        
        if (fileWriter != null) {
            fileWriter.flush();
            fileWriter.close();
        }
        csvFilePrinter.close();
    }
}
