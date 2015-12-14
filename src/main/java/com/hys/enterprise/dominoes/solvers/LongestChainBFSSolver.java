/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.model.DominoTileChain;
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
public class LongestChainBFSSolver implements DominoSolverInterface {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<AbstractDomino> dominoes;
    
    Map<Integer, Set<DominoTileChain>> chains = new HashMap<>();
    private DominoTileChain longestChain = null;
    
    private final Function<DominoTileChain, Set<DominoTileChain>> findNextChainsFunc = currentChain -> {
        Set<DominoTileChain> nextChainSet = new HashSet<>();
        for (AbstractDomino dominoe : dominoes) {
            if (!currentChain.getChain().contains(dominoe)) {
                DominoTileChain nextChain = currentChain.connect(dominoe);
                if (nextChain.length() > currentChain.length()) {
                    nextChainSet.add(nextChain);
                }
            }
        }
        return nextChainSet;
    };
    
    @Override
    public AbstractDomino solve(List<AbstractDomino> dominoes) {
        initChains(dominoes);
        for (Integer chainSize = 1; chainSize <= dominoes.size(); chainSize++) {
            Set<DominoTileChain> currentChainSet = chains.get(chainSize);
            Set<DominoTileChain> nextChainSet = currentChainSet.parallelStream().
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

    private void initChains(List<AbstractDomino> dominoes) {
        this.dominoes = dominoes;
        Set<DominoTileChain> initialChains = new HashSet<>();
        for (AbstractDomino dominoe : dominoes) {
            DominoTileChain chain = new DominoTileChain();
            chain = chain.connect(dominoe);
            initialChains.add(chain);
        }
        longestChain = initialChains.iterator().next();
        chains.put(1, initialChains);
    }

}