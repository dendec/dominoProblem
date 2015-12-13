/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.model;

import java.util.Objects;

/**
 * Dominoe entity
 * @author denis
 */
public class DominoeTile extends AbstractDominoe implements Comparable<DominoeTile> {

    private Integer valueLeft;
    private Integer valueRight;

    public static class InvalidDominoTileException extends Exception {

        public InvalidDominoTileException(Integer value1, Integer value2) {
            super(String.format("Can not create domino tile with values: %d, %d", value1, value2));
        }
    }

    /**
     * Creates dominoe entity with. 
     * Values should be more than MIN_VALUE and less than MAX_VALUE.
     * @param valueLeft 
     * @param valueRight
     * @throws InvalidDominoTileException
     */
    public DominoeTile(Integer valueLeft, Integer valueRight) throws DominoeTile.InvalidDominoTileException {
        if (isCorrectValue(valueLeft) && isCorrectValue(valueRight)) {
            this.valueLeft = valueLeft;
            this.valueRight = valueRight;
        } else {
            throw new DominoeTile.InvalidDominoTileException(valueLeft, valueRight);
        }
    }

    private Boolean isCorrectValue(Integer value) {
        return (value >= MIN_VALUE) && (value <= MAX_VALUE);
    }

    @Override
    public Integer getValueLeft() {
        return valueLeft;
    }

    @Override
    public Integer getValueRight() {
        return valueRight;
    }

    @Override
    public DominoeTile swap() {
        try {
            return new DominoeTile(valueRight, valueLeft);
        } catch (InvalidDominoTileException ex) {
            System.out.println("Something strange happens. " + ex.getMessage());
            return null;
        }
    }
 
    @Override
    public String toString() {
        return "[" + valueLeft + "|" + valueRight + ']';
    }

    @Override
    synchronized public int hashCode() {
        return Objects.hashCode(this.valueLeft) + Objects.hashCode(this.valueRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DominoeTile other = (DominoeTile) obj;
        return (this.valueLeft.equals(other.valueLeft) && this.valueRight.equals(other.valueRight))
                || (this.valueRight.equals(other.valueLeft) && this.valueLeft.equals(other.valueRight));
    }

    @Override
    public int compareTo(DominoeTile o) {
        return getValueLeft() * 10 + getValueRight() - o.getValueLeft() * 10 - o.getValueRight();
    }
    
}
