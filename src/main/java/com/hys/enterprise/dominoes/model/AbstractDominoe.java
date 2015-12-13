/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.model;

import java.util.Optional;

/**
 * AbstractDominoe is the abstract base class for all domino-like models
 *
 * @author denis
 */
public abstract class AbstractDominoe implements Connectable {

    public static final Integer MIN_VALUE = 0;
    public static final Integer MAX_VALUE = 6;

    /**
     * Change order of domino values.
     * @return new instance with swapped values
     */
    public abstract AbstractDominoe swap();

    /**
     *
     * @return left value
     */
    public abstract Integer getValueLeft();

    /**
     *
     * @return right value
     */
    public abstract Integer getValueRight();

    /**
     * Checks possibility to create connection between this domino and other
     *
     * @param other domino for checking
     * @return true if it is possible, false if not
     */
    @Override
    public Boolean canBeConnected(Connectable other) {
        if (other instanceof AbstractDominoe) {
            AbstractDominoe otherDominoe = (AbstractDominoe) other;
            return otherDominoe.containsValue(getValueLeft())
                    || otherDominoe.containsValue(getValueRight());
        } else {
            return false;
        }
    }

    /**
     * Checks presence of given value
     *
     * @param value
     * @return true if it contains value, false if not
     */
    protected Boolean containsValue(Integer value) {
        return getValueLeft().equals(value) || getValueRight().equals(value);
    }
    
    public Optional<Integer> getCommonValue(AbstractDominoe other) {
        if (containsValue(other.getValueLeft()))
            return Optional.of(other.getValueLeft());
        else if(containsValue(other.getValueRight()))
            return Optional.of(other.getValueRight());
        else return Optional.empty();
    }
}
