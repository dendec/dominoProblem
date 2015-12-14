/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes;

import com.hys.enterprise.dominoes.solvers.LongestChainBacktrackingSolver;
import com.hys.enterprise.dominoes.model.DominoBucket;
import com.hys.enterprise.dominoes.reporting.Benchmark;
import com.hys.enterprise.dominoes.solvers.LongestChainBFSSolver;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author denis
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
        try {
            Benchmark benchmark = new Benchmark(
                    new LongestChainBacktrackingSolver(),
                    new LongestChainBFSSolver()
            );
            CommandLineInterface commandLineInterface = new CommandLineInterface(benchmark);
        } catch (IOException | DominoBucket.DominoBucketIsEmptyException ex) {
            logger.error("Error: " + ex);
        }
    }

}
