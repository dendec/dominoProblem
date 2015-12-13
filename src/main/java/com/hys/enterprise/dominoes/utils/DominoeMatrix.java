/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.utils;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author denis
 */
public final class DominoeMatrix extends Matrix<AbstractDominoe> {
    
    private Integer dominoesNumber = 0;

    public DominoeMatrix(List<AbstractDominoe> dominoes) {
        super(AbstractDominoe.MAX_VALUE + 1, Optional.empty());
        for (AbstractDominoe dominoe : dominoes) {
            this.put(dominoe);
        }
    }

    public void put(AbstractDominoe dominoe) {
        this.set(dominoe, dominoe.getValueLeft(), dominoe.getValueRight());
        this.set(dominoe, dominoe.getValueRight(), dominoe.getValueLeft());
        dominoesNumber ++;
    }

    @Override
    public AbstractDominoe take(Integer row, Integer col) {
        AbstractDominoe result = get(row, col);
        matrix[row][col] = Optional.empty();
        matrix[col][row] = Optional.empty();
        dominoesNumber --;
        return result;
    }
       
}
