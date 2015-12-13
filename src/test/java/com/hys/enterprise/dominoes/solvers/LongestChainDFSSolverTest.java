/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.model.DominoeTile;
import com.hys.enterprise.dominoes.model.DominoeTileChain;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author denis
 */
public class LongestChainDFSSolverTest {
    
    private final LongestChainDFSSolver solver = new LongestChainDFSSolver(); 
    
    /**
     * Test of solve method with result length 1, of class LongestChainDFS.
     *
     * @throws
     * com.hys.enterprise.dominoes.model.DominoeTile.InvalidDominoTileException
     */
    @Test
    public void testSolve1() throws DominoeTile.InvalidDominoTileException {
        //[1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [0|0];
        List<AbstractDominoe> dominoes
                = Arrays.asList(new DominoeTile(1, 1),
                        new DominoeTile(2, 2), new DominoeTile(3, 3),
                        new DominoeTile(4, 4), new DominoeTile(5, 5),
                        new DominoeTile(6, 6), new DominoeTile(0, 0));
        Integer expResult = 1;
        DominoeTileChain result = (DominoeTileChain) solver.solve(dominoes);
        assertEquals("One element chain:", expResult, result.length());
    }

    /**
     * Test of solve method with result length 2, of class LongestChainDFS.
     *
     * @throws
     * com.hys.enterprise.dominoes.model.DominoeTile.InvalidDominoTileException
     */
    @Test
    public void testSolve2() throws DominoeTile.InvalidDominoTileException {
        //[1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [1|0];
        List<AbstractDominoe> dominoes
                = Arrays.asList(new DominoeTile(1, 1),
                        new DominoeTile(2, 2), new DominoeTile(3, 3),
                        new DominoeTile(4, 4), new DominoeTile(5, 5),
                        new DominoeTile(6, 6), new DominoeTile(1, 0));
        Integer expResult = 2;
        DominoeTileChain result = (DominoeTileChain) solver.solve(dominoes);
        assertEquals("Two element chain:", expResult, result.length());
    }

    /**
     * Test of solve method with result length 3, of class LongestChainDFS.
     *
     * @throws
     * com.hys.enterprise.dominoes.model.DominoeTile.InvalidDominoTileException
     */
    @Test
    public void testSolve3() throws DominoeTile.InvalidDominoTileException {
        //[0|0], [1|1], [2|2], [3|3], [4|4], [5|5], [6|6], [1|0];
        List<AbstractDominoe> dominoes
                = Arrays.asList(new DominoeTile(0, 0), new DominoeTile(1, 1),
                        new DominoeTile(2, 2), new DominoeTile(3, 3),
                        new DominoeTile(4, 4), new DominoeTile(5, 5),
                        new DominoeTile(6, 6), new DominoeTile(1, 0));
        Integer expResult = 3;
        DominoeTileChain result = (DominoeTileChain) solver.solve(dominoes);
        assertEquals("Three element chain:", expResult, result.length());
    }
}
