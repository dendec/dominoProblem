/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.model.DominoeTileChain;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * breadth-first search algorithm implementation.
 * Works fine. Perform calculations in multiple threads.
 * @author denis
 */
public class LongestChainBFSSolver implements DominoeSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<AbstractDominoe> dominoes;
    
    Map<Integer, Set<DominoeTileChain>> chains = new HashMap<>();
    private DominoeTileChain longestChain = null;
    
    private final Function<DominoeTileChain, Set<DominoeTileChain>> findNextChainsFunc = currentChain -> {
        Set<DominoeTileChain> nextChainSet = new HashSet<>();
        for (AbstractDominoe dominoe : dominoes) {
            if (!currentChain.getChain().contains(dominoe)) {
                DominoeTileChain nextChain = currentChain.connect(dominoe);
                if (nextChain.length() > currentChain.length()) {
                    nextChainSet.add(nextChain);
                }
            }
        }
        return nextChainSet;
    };
    
    @Override
    public AbstractDominoe solve(List<AbstractDominoe> dominoes) {
        initChains(dominoes);
        for (Integer chainSize = 1; chainSize <= dominoes.size(); chainSize++) {
            Set<DominoeTileChain> currentChainSet = chains.get(chainSize);
            Set<DominoeTileChain> nextChainSet = currentChainSet.parallelStream().
                map(findNextChainsFunc).flatMap(l -> l.stream()).collect(Collectors.toSet());
            logger.info("length: " + chainSize);
            logger.debug("current chainSet size: " + currentChainSet.size());
            logger.debug("next chainSet size: " + nextChainSet.size());
            if (!nextChainSet.isEmpty()) {
                longestChain = nextChainSet.iterator().next();
            }
            chains.remove(chainSize - 1);
            chains.put(chainSize + 1, nextChainSet);
        }
        logger.info("Solved. Longest chain length: " + longestChain.length());
        return longestChain;
    }

    private void initChains(List<AbstractDominoe> dominoes) {
        this.dominoes = dominoes;
        Set<DominoeTileChain> initialChains = new HashSet<>();
        for (AbstractDominoe dominoe : dominoes) {
            DominoeTileChain chain = new DominoeTileChain();
            chain = chain.connect(dominoe);
            initialChains.add(chain);
        }
        longestChain = initialChains.iterator().next();
        chains.put(1, initialChains);
    }

}