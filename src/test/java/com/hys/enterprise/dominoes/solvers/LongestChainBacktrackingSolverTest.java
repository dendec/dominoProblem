/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import com.hys.enterprise.dominoes.model.DominoBucket;
import com.hys.enterprise.dominoes.model.DominoTile;
import com.hys.enterprise.dominoes.model.DominoTileChain;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author denis
 */
public class LongestChainBacktrackingSolverTest {
    
    private final LongestChainBacktrackingSolver solver = new LongestChainBacktrackingSolver(); 
    
     /**
     * Test of solve method of full set, of class LongestChainBFS.
     *
     * @throws com.hys.enterprise.dominoes.model.DominoBucket.DominoBucketIsEmptyException
     */
    @Test
    public void testSolve28() throws DominoBucket.DominoBucketIsEmptyException {
        List<AbstractDomino> dominoes
                = DominoBucket.getInstance().getRandomTiles(28);
        Integer expResult = 28;
        DominoTileChain result = (DominoTileChain) solver.solve(dominoes);
        System.out.println(result);
        assertEquals("28 element chain:", expResult, result.length());
    }
    
    /**
     * Test of solve method with result length 1, of class LongestChainDFS.
     *
     * @throws com.hys.enterprise.dominoes.model.DominoBucket.DominoBucketIsEmptyException
     */
    @Test
    public void testSolve1() throws DominoTile.InvalidDominoTileException {
        //[1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [0|0];
        List<AbstractDomino> dominoes
                = Arrays.asList(new DominoTile(1, 1),
                        new DominoTile(2, 2), new DominoTile(3, 3),
                        new DominoTile(4, 4), new DominoTile(5, 5),
                        new DominoTile(6, 6), new DominoTile(0, 0));
        Integer expResult = 1;
        DominoTileChain result = (DominoTileChain) solver.solve(dominoes);
        assertEquals("One element chain", expResult, result.length());
    }

    /**
     * Test of solve method with result length 2, of class LongestChainDFS.
     *
     * @throws
     * com.hys.enterprise.dominoes.model.DominoeTile.InvalidDominoTileException
     */
    @Test
    public void testSolve2() throws DominoTile.InvalidDominoTileException {
        //[1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [1|0];
        List<AbstractDomino> dominoes
                = Arrays.asList(new DominoTile(1, 1),
                        new DominoTile(2, 2), new DominoTile(3, 3),
                        new DominoTile(4, 4), new DominoTile(5, 5),
                        new DominoTile(6, 6), new DominoTile(1, 0));
        Integer expResult = 2;
        DominoTileChain result = (DominoTileChain) solver.solve(dominoes);
        assertEquals("Two element chain", expResult, result.length());
    }

    /**
     * Test of solve method with result length 3, of class LongestChainDFS.
     *
     * @throws
     * com.hys.enterprise.dominoes.model.DominoeTile.InvalidDominoTileException
     */
    @Test
    public void testSolve3() throws DominoTile.InvalidDominoTileException {
        //[0|0], [1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [1|0];
        List<AbstractDomino> dominoes
                = Arrays.asList(new DominoTile(0, 0), new DominoTile(1, 1),
                        new DominoTile(2, 2), new DominoTile(3, 3),
                        new DominoTile(4, 4), new DominoTile(5, 5),
                        new DominoTile(6, 6), new DominoTile(1, 0));
        Integer expResult = 3;
        DominoTileChain result = (DominoTileChain) solver.solve(dominoes);
        assertEquals("Three element chain", expResult, result.length());
    }
}
