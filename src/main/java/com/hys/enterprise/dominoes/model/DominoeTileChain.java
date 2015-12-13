/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Chains of dominoes. Immutable.
 * @author denis
 */
public class DominoeTileChain extends AbstractDominoe implements Comparable<DominoeTileChain> {

    private final LinkedList<AbstractDominoe> chain;

    public DominoeTileChain() {
        chain = new LinkedList<>();
    }

    /**
     * Add element to chain
     * @param other new element
     * @return new chain if element was connected, otherwise returns this.
     */
    public DominoeTileChain connect(AbstractDominoe other) {
        DominoeTileChain result = deepCopy();

        if (result.chain.isEmpty()) {
            result.chain.add(other);
            return result;
        }
        if (other.getValueRight().equals(getValueLeft())) {
            result.chain.addFirst(other);
            return result;
        }
        if (other.getValueLeft().equals(getValueRight())) {
            result.chain.addLast(other);
            return result;
        }
        if (other.getValueLeft().equals(getValueLeft())) {
            result.chain.addFirst(other.swap());
            return result;
        }
        if (other.getValueRight().equals(getValueRight())) {
            result.chain.addLast(other.swap());
            return result;
        }
        return this;
    }

    /**
     * Disconnect element if it first or last
     * @param other element to disconnect
     * @return new chain without element or this
     */
    public DominoeTileChain detach(AbstractDominoe other) {
        DominoeTileChain result = deepCopy();
        if (result.chain.getFirst().equals(other)) {
            result.chain.removeFirst();
            return result;
        } else if (result.chain.getLast().equals(other)) {
            result.chain.removeLast();
            return result;
        }
        return this;
    }

    @Override
    public Integer getValueLeft() {
        if (!chain.isEmpty()) {
            return chain.getFirst().getValueLeft();
        } else {
            return null;
        }
    }

    @Override
    public Integer getValueRight() {
        if (!chain.isEmpty()) {
            return chain.getLast().getValueRight();
        } else {
            return null;
        }
    }

    /**
     *
     * @return chain size
     */
    public Integer length() {
        return chain.size();
    }

    /**
     *
     * @return linked list of elements
     */
    public LinkedList<AbstractDominoe> getChain() {
        return chain;
    }

    @Override
    public Boolean canBeConnected(Connectable other) {
        if (chain.isEmpty()) {
            return true;
        } else {
            return super.canBeConnected(other);
        }
    }

    @Override
    public DominoeTileChain swap() {
        DominoeTileChain result = new DominoeTileChain();
        Collections.reverse(chain);
        for (AbstractDominoe dominoTile : chain) {
            result.connect(dominoTile.swap());
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (AbstractDominoe dominoTile : chain) {
            result.append(dominoTile.toString());
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (AbstractDominoe dominoTail : chain) {
            hash += hash * dominoTail.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DominoeTileChain other = (DominoeTileChain) obj;
        Boolean isAllTilesEqual = true;
        for (AbstractDominoe tile : chain) {
            isAllTilesEqual &= other.chain.contains(tile);
        }
        return isAllTilesEqual && Objects.equals(this.length(), other.length());
    }

    @Override
    public int compareTo(DominoeTileChain other) {
        return this.length() - other.length();
    }

    private DominoeTileChain deepCopy() {
        DominoeTileChain result = new DominoeTileChain();
        for (AbstractDominoe dominoeTile : chain) {
            result.chain.add(dominoeTile);
        }
        return result;
    }
    
}
