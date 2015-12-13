/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.model.DominoeTileChain;
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
public class LongestChainDFSSolver implements DominoeSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DominoeTileChain longestChain;

    @Override
    public AbstractDominoe solve(List<AbstractDominoe> dominoes) {
        longestChain = new DominoeTileChain();
        Set<DominoeTileChain> result = new HashSet<>();
        for (AbstractDominoe dominoe : dominoes) {
            Integer startIndex = dominoes.indexOf(dominoe);
            logger.info("Start index = " + startIndex);
            DFS(startIndex, dominoes);
        }
        return longestChain;
    }

    private void DFS(Integer vertexIndex, List<AbstractDominoe> dominoes) {
        Boolean[] visitedVertices = new Boolean[dominoes.size()];
        Arrays.fill(visitedVertices, false);
        DominoeTileChain chain = new DominoeTileChain();
        runDFS(vertexIndex, chain, dominoes, visitedVertices);
    }

    private void runDFS(Integer vertexIndex, DominoeTileChain chain, List<AbstractDominoe> dominoes, Boolean[] visitedVertices) {
        visitedVertices[vertexIndex] = true;
        for (int index = 0; index < dominoes.size(); index++) {
            if (dominoes.get(vertexIndex).canBeConnected(dominoes.get(index))
                    && (!visitedVertices[index])) {
                runDFS(index, chain.connect(dominoes.get(vertexIndex)), dominoes, visitedVertices);
            }
        }
        DominoeTileChain newChain = chain.connect(dominoes.get(vertexIndex));
        if (longestChain.length() < newChain.length()) {
            longestChain = newChain;
            logger.info("New longest chain: " + longestChain);
        }
    }
}
