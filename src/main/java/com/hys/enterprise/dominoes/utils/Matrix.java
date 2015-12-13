/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.utils;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author denis
 * @param <T>
 */
public class Matrix<T> {

    protected final Optional<T>[][] matrix;
    private final Integer size;

    public Matrix(Integer size, Optional<T> initValue) {
        this.size = size;
        matrix = (Optional<T>[][]) (new Optional[size][size]);
        init(initValue);
    }

    private void init(Optional<T> initValue) {
        for (Optional<T>[] row : matrix) {
            Arrays.fill(row, initValue);
        }
    }

    public void set(T item, Integer row, Integer col) {
        matrix[row][col]
                = Optional.ofNullable(item);
    }

    public T take(Integer row, Integer col) {
        T result = get(row, col);
        matrix[row][col] = Optional.empty();
        return result;
    }
    
    public T get(Integer row, Integer col) {
        return matrix[row][col].get();
    }

    public Boolean isPresent(Integer row, Integer col) {
        return matrix[row][col].isPresent();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n");
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (matrix[row][col].isPresent()) {
                    result.append(matrix[row][col].get()).append(" ");
                } else {
                    result.append("_____ ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

}
