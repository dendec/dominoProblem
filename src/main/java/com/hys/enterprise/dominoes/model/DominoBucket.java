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
public class DominoBucket {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Empty bucket exception.
     */
    public static class DominoBucketIsEmptyException extends Exception {

        public DominoBucketIsEmptyException() {
            super("Bucket is empty.");
        }
    }
    
    private final List<DominoTile> dominoes = new ArrayList<>();
        
    private static DominoBucket instance = null;
    
    private void init() {
        for (int value1 = DominoTile.MIN_VALUE; value1 <= DominoTile.MAX_VALUE; value1++) {
            for (int value2 = DominoTile.MIN_VALUE; value2 <= value1; value2++) {
                try {
                    DominoTile dominoTile = new DominoTile(value1, value2);
                    dominoes.add(dominoTile);
                } catch (DominoTile.InvalidDominoTileException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    private DominoBucket() {
        init();
        logger.info(String.format("Bucket contains %d dominoes tiles", dominoes.size()));
    }
    
    /**
     * Singleton pattern
     * @return
     */
    public static DominoBucket getInstance() {
        if (instance == null) {
            instance = new DominoBucket();
        } 
        return instance;
    }
    
    /**
     * Get list of random dominoe tiles
     * @param number of random tiles
     * @return
     * @throws DominoBucketIsEmptyException
     */
    public List<AbstractDomino> getRandomTiles(Integer number) throws DominoBucketIsEmptyException {
        Collections.shuffle(dominoes);
        List<AbstractDomino> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (i < dominoes.size())
                result.add(dominoes.get(i));
            else
                throw new DominoBucketIsEmptyException();
        }
        return result;
    }
        
}
