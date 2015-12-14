/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.reporting;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.model.DominoTileChain;
import com.hys.enterprise.dominoes.solvers.DominoSolverInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark report record
 * @author denis
 */
public class ReportRecord {
    
    class Result {
        private final String solverName;
        private final AbstractDomino result;
        private final Long time;

        public Result(String solverName, AbstractDomino result, Long time) {
            this.solverName = solverName;
            this.result = result;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Result{" + "solverName=" + solverName + ", result=" + result + ", time=" + time + '}';
        }
        
    }

    private final List<AbstractDomino> input;
    private final List<Result> results;

    /**
     * 
     * @param input set that was used as input for solvers
     */
    public ReportRecord(List<AbstractDomino> input) {
        this.input = input;
        this.results = new ArrayList<>();
    }

    /**
     * add result of solver running
     * @param solver contains algorithm that was used
     * @param result result that solver produce
     * @param time time that algorithm works
     */
    public void addResult(DominoSolverInterface solver, AbstractDomino result, Long time) {
        results.add(new Result(solver.getClass().getSimpleName(), result, time));
    }

    /**
     * Returns data for csv printer
     * @return 
     */
    public List<String> getData() {
        List<String> record = new ArrayList<>();
        record.add(input.toString());
        record.add(String.valueOf(input.size()));
        for (Result result : results) {
            record.add(result.time.toString());
            record.add(result.result.toString());
            if (result.result instanceof DominoTileChain)
                record.add(((DominoTileChain)result.result).length().toString());
            else
                record.add("");
        }
        return record;
    }
    
    /**
     * Returns header of report
     * @return
     */
    public List<String> getHeader() {
        List<String> record = new ArrayList<>();
        record.add("input");
        record.add("inputLength");
        for (Result result : results) {
            record.add(result.solverName + "Time");
            record.add(result.solverName + "Result");
            record.add(result.solverName + "ResultLength");
        }
        return record;
    }

    @Override
    public String toString() {
        return "ReportRecord{" + "input=" + input + ", results=" + results + '}';
    }

    
}
