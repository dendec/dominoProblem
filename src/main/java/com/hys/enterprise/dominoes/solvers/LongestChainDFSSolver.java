/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.model.DominoTileChain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author denis
 */
public class LongestChainDFSSolver implements DominoSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DominoTileChain longestChain;

    @Override
    public AbstractDomino solve(List<AbstractDomino> dominoes) {
        longestChain = new DominoTileChain();
        Set<DominoTileChain> result = new HashSet<>();
        for (AbstractDomino dominoe : dominoes) {
            Integer startIndex = dominoes.indexOf(dominoe);
            logger.info("Start index = " + startIndex);
            DFS(startIndex, dominoes);
        }
        return longestChain;
    }

    private void DFS(Integer vertexIndex, List<AbstractDomino> dominoes) {
        Boolean[] visitedVertices = new Boolean[dominoes.size()];
        Arrays.fill(visitedVertices, false);
        DominoTileChain chain = new DominoTileChain();
        runDFS(vertexIndex, chain, dominoes, visitedVertices);
    }

    private void runDFS(Integer vertexIndex, DominoTileChain chain, List<AbstractDomino> dominoes, Boolean[] visitedVertices) {
        visitedVertices[vertexIndex] = true;
        for (int index = 0; index < dominoes.size(); index++) {
            if (dominoes.get(vertexIndex).canBeConnected(dominoes.get(index))
                    && (!visitedVertices[index])) {
                runDFS(index, chain.connect(dominoes.get(vertexIndex)), dominoes, visitedVertices);
            }
        }
        DominoTileChain newChain = chain.connect(dominoes.get(vertexIndex));
        if (longestChain.length() < newChain.length()) {
            longestChain = newChain;
            logger.info("New longest chain: " + longestChain);
        }
    }
}
