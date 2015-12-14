/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.reporting;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.model.DominoBucket;
import com.hys.enterprise.dominoes.model.DominoTileChain;
import com.hys.enterprise.dominoes.solvers.DominoSolverInterface;
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

    private final List<DominoSolverInterface> solvers;
    private ReportBuilder reportBuilder = null;

    /**
     * 
     * @param solvers 
     * @throws IOException
     */
    public Benchmark(DominoSolverInterface... solvers) throws IOException {
        this.solvers = Arrays.asList(solvers);
        this.reportBuilder = new ReportBuilder(REPORT_FILENAME);
    }

    /**
     * run benchmark.
     * @param iterations number of iterations with fixed domino quantity
     * @param minDominoeQuantity min dominoes set length
     * @param maxDominoeQuantity max dominoes set length
     * @throws DominoBucket.DominoBucketIsEmptyException
     * @throws IOException
     */
    public void run(Integer iterations, Integer minDominoeQuantity, Integer maxDominoeQuantity) throws DominoBucket.DominoBucketIsEmptyException, IOException {
        for (int dominoesNumber = minDominoeQuantity; dominoesNumber <= maxDominoeQuantity; dominoesNumber++) {
            for (int iter = 0; iter < iterations; iter++) {
                runIteration(dominoesNumber);
            }
        }
    }

     /**
     * run solvers once for certain dominoes number.
     * @param dominoesNumber number of iterations with fixed domino quantity
     * @throws com.hys.enterprise.dominoes.model.DominoBucket.DominoBucketIsEmptyException
     * @throws IOException
     */
    public void runIteration(Integer dominoesNumber) throws DominoBucket.DominoBucketIsEmptyException, IOException {
        List<AbstractDomino> dominoes = DominoBucket.getInstance().getRandomTiles(dominoesNumber);
        ReportRecord reportRecord = new ReportRecord(dominoes);
        for (DominoSolverInterface solver : solvers) {
            Long startTime = java.lang.System.currentTimeMillis();
            AbstractDomino result = solver.solve(dominoes);
            Long calcTime = java.lang.System.currentTimeMillis() - startTime;
            reportRecord.addResult(solver, result, calcTime);
            logger.info(String.format("input for %s: %d. result: %d. Calculations takes %d millis", solver.getClass().getSimpleName(), 
                    dominoes.size(), ((DominoTileChain) result).length(), calcTime));
            logger.info(reportRecord.toString());
        }
        reportBuilder.writeRecord(reportRecord);
    }

}
