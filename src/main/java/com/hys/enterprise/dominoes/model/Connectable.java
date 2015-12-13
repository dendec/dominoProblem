/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.model;

/**
 * 
 * @author denis
 */
public interface Connectable {
    Boolean canBeConnected(Connectable other);
}
