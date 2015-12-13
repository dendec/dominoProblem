/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import java.util.List;

/**
 * interface for solving any dominoe problems
 * @author denis
 */
public interface DominoeSolverInterface {
    AbstractDominoe solve(List<AbstractDominoe> dominoes);
}
