import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @userid rbouhafs3
 * @GTID 903591700
 *
 * Collaborators: General help/tutoring of pattern matching algorithms by an upperclassman not in the class, Zach Minoh
 *
 * Resources: none
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of aquis returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in read order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException("The given graph and/or the given start are/is null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given graph does not contain the given start.");
        }
        HashSet<Vertex<T>> read = new HashSet<>();
        List<Vertex<T>> verticesList = new LinkedList<>();
        rcrsvDFS(start, graph, read, verticesList);
        return verticesList;
    }

    /**
     * private helper recursive method for above dfs method
     *
     * @param vertex vertex
     * @param graph graph
     * @param read read
     * @param verticesList verticesList
     * @param <T> generic data type
     */
    private static <T> void rcrsvDFS(Vertex<T> vertex, Graph<T> graph, HashSet<Vertex<T>> read,
                                                                List<Vertex<T>> verticesList) {
        read.add(vertex);
        verticesList.add(vertex);
        List<VertexDistance<T>> adjList = graph.getAdjList().get(vertex);
        for (VertexDistance<T> data : adjList) {
            Vertex<T> aqui = data.getVertex();
            if (!read.contains(aqui)) {
                rcrsvDFS(aqui, graph, read, verticesList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been read.
     * 2) Check if the priorityQueue is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException("The given graph and/or the given start are/is null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given graph does not contain the given start.");
        }
        HashSet<Vertex<T>> read = new HashSet<>();
        HashMap<Vertex<T>, Integer> distancesMap = new HashMap<>();
        for (Vertex<T> each : graph.getVertices()) {
            distancesMap.put(each, Integer.MAX_VALUE);
        }
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjMap = graph.getAdjList();
        priorityQueue.add(new VertexDistance<>(start, 0));
        while ((!priorityQueue.isEmpty()) && (read.size() != graph.getVertices().size())) {
            VertexDistance<T> vertexDistance = priorityQueue.remove();
            Vertex<T> vertex = vertexDistance.getVertex();
            int distance = vertexDistance.getDistance();
            if (!read.contains(vertex)) {
                read.add(vertex);
                distancesMap.put(vertex, distance);
                List<VertexDistance<T>> adjList = adjMap.get(vertex);
                for (VertexDistance<T> data : adjList) {
                    Vertex<T> aqui = data.getVertex();
                    int aquiDistance = data.getDistance();
                    if (!read.contains(data)) {
                        priorityQueue.add(new VertexDistance<>(
                                aqui, distance + aquiDistance));
                    }
                }
            }
        }

        return distancesMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (edgeSet) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid edgeSet exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid edgeSet that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the edgeSet.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the edgeSet of the graph or null if there is no valid edgeSet
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException("The given graph and/or the given start are/is null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given graph does not contain the given start.");
        } else {
            Set<Vertex<T>> vrtxSet = new HashSet<>();
            Set<Edge<T>> edgeSet = new HashSet<>();
            PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>();
            Set<Edge<T>> edges = graph.getEdges();
            for (Edge<T> each: edges) {
                if (each.getU().equals(start)) {
                    priorityQueue.add(each);
                }
            }
            vrtxSet.add(start);
            while ((!priorityQueue.isEmpty()) && (vrtxSet.size() != graph.getVertices().size())) {
                Edge<T> edge = priorityQueue.remove();
                if (!vrtxSet.contains(edge.getV())) {
                    edgeSet.add(edge);
                    vrtxSet.add(edge.getV());
                    edgeSet.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                    for (Edge<T> each: edges) {
                        if (each.getU().equals(edge.getV())
                                && !vrtxSet.contains(each.getV())) {
                            priorityQueue.add(each);
                        }
                    }
                }
            }
            if (edgeSet.size() < graph.getVertices().size() - 1) {
                return null;
            }
            return edgeSet;
        }
    }
}
