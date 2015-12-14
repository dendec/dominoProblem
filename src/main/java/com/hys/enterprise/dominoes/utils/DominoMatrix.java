/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.utils;

import com.hys.enterprise.dominoes.model.AbstractDomino;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author denis
 */
public final class DominoMatrix extends Matrix<AbstractDomino> {
    
    private Integer dominoesNumber = 0;

    public DominoMatrix(List<AbstractDomino> dominoes) {
        super(AbstractDomino.MAX_VALUE + 1, Optional.empty());
        for (AbstractDomino dominoe : dominoes) {
            this.put(dominoe);
        }
    }

    public void put(AbstractDomino dominoe) {
        this.set(dominoe, dominoe.getValueLeft(), dominoe.getValueRight());
        this.set(dominoe, dominoe.getValueRight(), dominoe.getValueLeft());
        dominoesNumber ++;
    }

    @Override
    public AbstractDomino take(Integer row, Integer col) {
        AbstractDomino result = get(row, col);
        matrix[row][col] = Optional.empty();
        matrix[col][row] = Optional.empty();
        dominoesNumber --;
        return result;
    }
       
}
