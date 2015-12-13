/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.utils.DominoeMatrix;
import com.hys.enterprise.dominoes.model.DominoeTileChain;
import com.hys.enterprise.dominoes.model.DominoeTile;
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
public class LongestChainBacktrackingSolver implements DominoeSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile DominoeTileChain longestChain;
    private Integer dominoeNumber;

    @Override
    public AbstractDominoe solve(List<AbstractDominoe> dominoes) {
        logger.info("Dominoes:" + dominoes);
        this.dominoeNumber = dominoes.size();
        DominoeMatrix matrix = new DominoeMatrix(dominoes);
        logger.debug(matrix.toString());
        longestChain = new DominoeTileChain();
        IntStream.rangeClosed(0, AbstractDominoe.MAX_VALUE).parallel().
            forEach(i -> {
                makeChain(i, new DominoeMatrix(dominoes), new DominoeTileChain());
                logger.info("Chain started from " + i + " is done");
            });
        logger.info("Solved. Longest chain length: " + longestChain.length());
        return longestChain;
    }

    private void makeChain(Integer i, DominoeMatrix matrix, DominoeTileChain currentChain) {
        for (int j = 0; j <= DominoeTile.MAX_VALUE; j++) {
            if (matrix.isPresent(i, j)) {
                AbstractDominoe dominoTile = matrix.take(i, j);
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
                DominoeTileChain x = currentChain.detach(dominoTile);
                currentChain = x;
                logger.debug("Detach: " + dominoTile);
                logger.debug("Current chain: " + currentChain);
                logger.debug("Longest chain: " + longestChain);
                matrix.put(dominoTile);
                logger.debug(matrix.toString());
            }
        }
    }

    private synchronized void setLongestChain(DominoeTileChain currentChain) {
        if (longestChain.length() < currentChain.length()) {
            longestChain = currentChain;
            logger.info("Find new longest chain:" + longestChain);
        }
    }
}
