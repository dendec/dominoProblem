/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.utils.DominoMatrix;
import com.hys.enterprise.dominoes.model.DominoTileChain;
import com.hys.enterprise.dominoes.model.DominoTile;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backtracking search algorithm implementation.
 * Works fine. Perform calculations in multiple threads. 
 * Can be very fast if all elements can be united in chain, but usually slow.
 * @author denis
 */
public class LongestChainBacktrackingSolver implements DominoSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile DominoTileChain longestChain;
    private Integer dominoeNumber;

    @Override
    public AbstractDomino solve(List<AbstractDomino> dominoes) {
        logger.info("Dominoes:" + dominoes);
        this.dominoeNumber = dominoes.size();
        DominoMatrix matrix = new DominoMatrix(dominoes);
        logger.debug(matrix.toString());
        longestChain = new DominoTileChain();
        IntStream.rangeClosed(0, AbstractDomino.MAX_VALUE).parallel().
            forEach(i -> {
                makeChain(i, new DominoMatrix(dominoes), new DominoTileChain());
                logger.info("Chain started from " + i + " is done");
            });
        logger.info("Solved. Longest chain length: " + longestChain.length());
        return longestChain;
    }

    private void makeChain(Integer i, DominoMatrix matrix, DominoTileChain currentChain) {
        for (int j = 0; j <= DominoTile.MAX_VALUE; j++) {
            if (matrix.isPresent(i, j)) {
                AbstractDomino dominoTile = matrix.take(i, j);
                logger.debug("Take " + dominoTile);
                logger.debug(matrix.toString());
                if (currentChain.canBeConnected(dominoTile)) {
                    logger.debug("Add  " + dominoTile + " to chain " + currentChain);
                    currentChain = currentChain.connect(dominoTile);
                    logger.debug("Current chain: " + currentChain.length());
                    logger.debug("Longest chain: " + longestChain.length());
                    setLongestChain(currentChain);
                    makeChain(j, matrix, currentChain);
                }
                if (dominoeNumber.equals(longestChain.length())) {
                    logger.info("Find longest chain");
                    return;
                }
                DominoTileChain x = currentChain.detach(dominoTile);
                currentChain = x;
                logger.debug("Detach: " + dominoTile);
                logger.debug("Current chain: " + currentChain);
                logger.debug("Longest chain: " + longestChain);
                matrix.put(dominoTile);
                logger.debug(matrix.toString());
            }
        }
    }

    private synchronized void setLongestChain(DominoTileChain currentChain) {
        if (longestChain.length() < currentChain.length()) {
            longestChain = currentChain;
            logger.info("Find new longest chain:" + longestChain);
        }
    }
}
