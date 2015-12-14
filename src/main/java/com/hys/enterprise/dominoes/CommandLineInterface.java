/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes;

import com.hys.enterprise.dominoes.model.DominoBucket;
import com.hys.enterprise.dominoes.reporting.Benchmark;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author denis
 */
public class CommandLineInterface {

    private final ConsoleReader consoleReader;

    public CommandLineInterface(Benchmark benchmark) throws DominoBucket.DominoBucketIsEmptyException, IOException {
        consoleReader = new ConsoleReader();
        if (isSingleRun()) {
            singleRunPrompt(benchmark);
        } else {
            multipleRunPrompt(benchmark);
        }
    }

    private Boolean isSingleRun() {
//        Pattern p = Pattern.compile("^(s|b)\\", Pattern.DOTALL);
        String input = consoleReader.getString("\"s\" for single run, \"b\" for benchmark: ", "s", "b");
        return "s".equals(input);
    }

    private void singleRunPrompt(Benchmark benchmark) throws DominoBucket.DominoBucketIsEmptyException, IOException {
        Integer dominoesNumber = consoleReader.getInteger("Enter number of dominoes: ", 1, 28);
        benchmark.runOnce(dominoesNumber);
    }

    private void multipleRunPrompt(Benchmark benchmark) throws DominoBucket.DominoBucketIsEmptyException, IOException {
        Integer iterations = consoleReader.getInteger("Enter number of iterations: ");
        Integer min = consoleReader.getInteger(
                String.format("Enter minimum number of dominoes (%d, %d): ", 1, 28), 1, 28);
        Integer max = consoleReader.getInteger(
                String.format("Enter maximum number of dominoes (%d, %d): ", min, 28), min, 28);
        benchmark.run(iterations, min, max);
    }

}
