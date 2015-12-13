/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Random dominoes generator
 * @author denis
 */
public class DominoeBucket {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Empty bucket exception.
     */
    public static class DominoBucketIsEmptyException extends Exception {

        public DominoBucketIsEmptyException() {
            super("Bucket is empty.");
        }
    }
    
    private final List<DominoeTile> dominoes = new ArrayList<>();
        
    private static DominoeBucket instance = null;
    
    private void init() {
        for (int value1 = DominoeTile.MIN_VALUE; value1 <= DominoeTile.MAX_VALUE; value1++) {
            for (int value2 = DominoeTile.MIN_VALUE; value2 <= value1; value2++) {
                try {
                    DominoeTile dominoTile = new DominoeTile(value1, value2);
                    dominoes.add(dominoTile);
                } catch (DominoeTile.InvalidDominoTileException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    private DominoeBucket() {
        init();
        logger.info(String.format("Bucket contains %d dominoes tiles", dominoes.size()));
    }
    
    /**
     * Singleton pattern
     * @return
     */
    public static DominoeBucket getInstance() {
        if (instance == null) {
            instance = new DominoeBucket();
        } 
        return instance;
    }
    
    /**
     * Get list of random dominoe tiles
     * @param number of random tiles
     * @return
     * @throws DominoBucketIsEmptyException
     */
    public List<AbstractDominoe> getRandomTiles(Integer number) throws DominoBucketIsEmptyException {
        Collections.shuffle(dominoes);
        List<AbstractDominoe> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (i < dominoes.size())
                result.add(dominoes.get(i));
            else
                throw new DominoBucketIsEmptyException();
        }
        return result;
    }
        
}
