/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.utils;

import com.hys.enterprise.dominoes.model.Connectable;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author denis
 * @param <T>
 */
public class Graph<T extends Connectable> {

    private final Matrix<Integer> adjacencyMatrix;

    private final Integer nodesNumber;
    
    private final List<T> nodes;

    public Graph(List<T> nodes) {
        nodesNumber = nodes.size();
        this.nodes = nodes;
        adjacencyMatrix = getAdjacencyMatrix(nodes);
    }

    private Matrix<Integer> getAdjacencyMatrix(List<T> nodes) {
        Matrix<Integer> result = new Matrix<>(nodes.size(), Optional.of(0));
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if ((i != j) && nodes.get(i).canBeConnected(nodes.get(j))) {
                    result.set(1, i, j);
                }
            }
        }
        return result;
    }

    public void removeEdge(Integer source, Integer destination) {
        adjacencyMatrix.set(0, source, destination);
        adjacencyMatrix.set(0, destination, source);
    }

    public void addEdge(Integer source, Integer destination) {
        adjacencyMatrix.set(1, source, destination);
        adjacencyMatrix.set(1, destination, source);
    }

    public Boolean hasEdge(Integer source, Integer destination) {
        return (adjacencyMatrix.get(source, destination) == 1
                || adjacencyMatrix.get(destination, source) == 1);
        //return nodes.get(source).canBeConnected(nodes.get(destination));
    }

    public Integer DFSCount(Integer source, Integer visited[]) {
        visited[source] = 1;
        Integer count = 1;
        Integer destination = 1;

        while (destination <= nodesNumber) {
            if (hasEdge(source, destination) && visited[destination] == 0) {
                count += DFSCount(destination, visited);
            }
            destination++;
        }
        return count;
    }

    public List<T> getNodes() {
        return nodes;
    }

    public Integer getVertexDegree(Integer vertex) {
        Integer degree = 0;
        for (int destinationVertex = 0; destinationVertex < nodesNumber; destinationVertex++) {
            if (hasEdge(vertex, destinationVertex)) {
                degree++;
            }
        }
        return degree;
    }

    public Integer getNodesNumber() {
        return nodesNumber;
    } 
    
    @Override
    public String toString() {
        return "Graph{\nnodes = " + nodesNumber + "\nadjacencyMatrix =\n" + adjacencyMatrix + "}";
    }

    
    
}
