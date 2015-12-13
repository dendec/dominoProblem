/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.reporting;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.model.DominoeBucket;
import com.hys.enterprise.dominoes.model.DominoeTileChain;
import com.hys.enterprise.dominoes.solvers.DominoeSolverInterface;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Benchmark for algorithms comparison
 * @author denis
 */
public class Benchmark {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String REPORT_FILENAME = "results.csv";

    private final List<DominoeSolverInterface> solvers;
    private ReportBuilder reportBuilder = null;

    /**
     * 
     * @param solvers 
     * @throws IOException
     */
    public Benchmark(DominoeSolverInterface... solvers) throws IOException {
        this.solvers = Arrays.asList(solvers);
        this.reportBuilder = new ReportBuilder(REPORT_FILENAME);
    }

    /**
     * run benchmark.
     * @param iterations number of iterations with fixed dominoe quantity
     * @param minDominoeQuantity min dominoes set length
     * @param maxDominoeQuantity max dominoes set length
     * @throws DominoeBucket.DominoBucketIsEmptyException
     * @throws IOException
     */
    public void run(Integer iterations, Integer minDominoeQuantity, Integer maxDominoeQuantity) throws DominoeBucket.DominoBucketIsEmptyException, IOException {
        for (int dominoesNumber = minDominoeQuantity; dominoesNumber <= maxDominoeQuantity; dominoesNumber++) {
            for (int iter = 0; iter < iterations; iter++) {
                runIteration(dominoesNumber);
            }
        }
    }

    private void runIteration(Integer dominoesNumber) throws DominoeBucket.DominoBucketIsEmptyException, IOException {
        List<AbstractDominoe> dominoes = DominoeBucket.getInstance().getRandomTiles(dominoesNumber);
        ReportRecord reportRecord = new ReportRecord(dominoes);
        for (DominoeSolverInterface solver : solvers) {
            Long startTime = java.lang.System.currentTimeMillis();
            AbstractDominoe result = solver.solve(dominoes);
            Long calcTime = java.lang.System.currentTimeMillis() - startTime;
            reportRecord.addResult(solver, result, calcTime);
            logger.info(String.format("input for %s: %d. result: %d. Calculations takes %d millis", solver.getClass().getSimpleName(), 
                    dominoes.size(), ((DominoeTileChain) result).length(), calcTime));
            logger.info(reportRecord.toString());
        }
        reportBuilder.writeRecord(reportRecord);
    }

}
