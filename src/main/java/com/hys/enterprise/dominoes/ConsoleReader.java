/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for interaction with user via command line interface
 *
 * @author denis
 */
public class ConsoleReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Scanner scanner = new Scanner(System.in);

    public Integer getInteger(String promptText) {
        Integer number = null;
        do {
            try {
                number = Integer.parseInt(getString(promptText));
            } catch (NumberFormatException e) {
                logger.error("Invalid number");
            }
        } while (number == null);
        return number;
    }

    public Integer getInteger(String promptText, Integer minValue, Integer maxValue) {
        Integer number = null;
        do {
            number = getInteger(promptText);
            if ((number > maxValue) || (number < minValue)) {
                logger.error(String.format("Number should be in range: [%d, %d]", minValue, maxValue));
                number = null;
            }
        } while (number == null);
        return number;
    }

    public String getString(String promptText) {
        System.out.print(promptText);
        return scanner.next();
    }

    public String getString(String promptText, String... acceptedValues) {
        String str = null;
        List<String> accepted = Arrays.asList(acceptedValues);
        do {
            str = getString(promptText).toLowerCase();
            if (!accepted.contains(str)) {
                logger.error(String.format("String should be: %s", accepted.toString()));
                str = null;
            }
        } while (str == null);
        return str;
    }
}
