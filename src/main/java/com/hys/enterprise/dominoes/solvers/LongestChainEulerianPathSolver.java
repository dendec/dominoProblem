/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hys.enterprise.dominoes.solvers;

import com.hys.enterprise.dominoes.model.AbstractDominoe;
import com.hys.enterprise.dominoes.utils.Graph;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author denis
 */
public class LongestChainEulerianPathSolver implements DominoeSolverInterface {

    private Graph<AbstractDominoe> graph;

    @Override
    public AbstractDominoe solve(List<AbstractDominoe> dominoes) {
       // Collections.sort(dominoes);
        graph = new Graph<>(dominoes);
        System.out.println(graph);
        Integer oddVertex = getOddVertex(graph).orElse(0);
        System.out.println("odd vertex: " + getOddVertex(graph));
        printEulerTourUtil(oddVertex);
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Optional<Integer> getOddVertex(Graph<AbstractDominoe> graph) {
        Integer vertex = null;
        for (Integer node = 0; node < graph.getNodesNumber(); node++) {
            System.out.println(node + " " + graph.getVertexDegree(node));
            if ((graph.getVertexDegree(node) % 2) != 0) {
                vertex = node;
                //System.out.println(vertex);
                //break;
            }
        }
        return Optional.ofNullable(vertex);
    }

    public void printEulerTourUtil(int vertex) {
        for (int destination = 0; destination < graph.getNodesNumber(); destination++) {
            if (graph.hasEdge(vertex, destination) && isVaildNextEdge(vertex, destination)) {
                System.out.println(" source : " + vertex + " destination " + destination);
                graph.removeEdge(vertex, destination);
                AbstractDominoe currentTile = graph.getNodes().get(vertex);
                AbstractDominoe destinationTile = graph.getNodes().get(destination);
                System.out.println(" source : " + currentTile + " destination " + destinationTile);
                
                Integer common = currentTile.getCommonValue(destinationTile).get();
                System.out.println("common: " + common);
                for (int z = 0; z < graph.getNodesNumber(); z++) {
                    if (graph.getNodes().get(z).getCommonValue(currentTile).orElse(-1).equals(common))
                        graph.removeEdge(vertex, z);
                }
                
                //graph.getNodes().get(vertex).
                printEulerTourUtil(destination);
            }
        }
    }

    private boolean isVaildNextEdge(int source, int destination) {
        int count = 0;
        for (int vertex = 0; vertex < graph.getNodesNumber(); vertex++) {
            if (graph.hasEdge(source, vertex)) {
                count++;
            }
        }
        
        System.out.println("count = " + count);

        if (count == 1) {
            return true;
        }

        int visited[] = new int[graph.getNodesNumber()];
        int count1 = DFSCount(source, visited);
        System.out.println("DFSCount1(source, visited) = " + count1);

        graph.removeEdge(source, destination);
        for (int vertex = 0; vertex < graph.getNodesNumber(); vertex++) {
            visited[vertex] = 0;
        }

        int count2 = DFSCount(source, visited);
        graph.addEdge(source, destination);
        System.out.println("DFSCount2(source, visited) = " + count2);
        return (count1 <= count2);
    }

    private int DFSCount(int source, int visited[]) {
        visited[source] = 1;
        int count = 1;
        int destination = 1;

        while (destination < graph.getNodesNumber()) {
            if (graph.hasEdge(source, destination) && visited[destination] == 0) {
                count += DFSCount(destination, visited);
            }
            destination++;
        }
        return count;
    }

}
