/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.solvers.LongestChainBacktrackingSolver;
import com.hys.enterprise.dominoes.model.DominoeBucket;
import com.hys.enterprise.dominoes.reporting.Benchmark;
import com.hys.enterprise.dominoes.solvers.DominoeSolverInterface;
import com.hys.enterprise.dominoes.solvers.LongestChainBFSSolver;
import com.hys.enterprise.dominoes.solvers.LongestChainDFSSolver;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
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
            benchmark.run(10, 5, 19);
        } catch (IOException | DominoeBucket.DominoBucketIsEmptyException ex) {
            logger.error("Error: " + ex);
        }
    }

    private static void run(DominoeSolverInterface solver, List<AbstractDominoe> dominoes) {
        Long startTime = java.lang.System.currentTimeMillis();
        AbstractDominoe result = solver.solve(dominoes);
        logger.info(String.format("%s result: %s", solver.getClass().getSimpleName(), result));
        logger.info("Time: " + (java.lang.System.currentTimeMillis() - startTime));
    }

    private static Integer getNumber(String message) {
        Integer dominoesNumber = null;
        do {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            try {
                dominoesNumber = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                logger.error("Invalid number");
            }
        } while (dominoesNumber == null);
        return dominoesNumber;
    }
}
