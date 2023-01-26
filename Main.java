import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] testGraph = {
                {
                        // a,b,c,d,e,f,g,h,i,j
                        -1, 4, 2, -1, -1, 7, -1, -1,
                },
                {
                        4, -1, -1, 2, -1, -1, -1, -1,
                },
                {
                        2, -1, -1, -1, 8, 3, -1, -1,
                },
                {
                        -1, 2, -1, -1, -1, 5, 6, -1,
                },
                {
                        -1, -1, 8, -1, -1, -1, -1, 3,
                },
                {
                        -1, -1, 3, 5, -1, -1, -1, 4,
                },
                {
                        -1, -1, -1, 6, -1, -1, -1, 2,
                },
                {
                        -1, -1, -1, -1, 3, 4, 2, -1,
                },
        };
        System.out.println(Arrays.deepToString(fullDijkstra(testGraph)));
    }

    // Finds the shortest path using dijkstra's from begin to end on the graph
    public static int[] shortestPath(int[][] graph, int begin, int end) {
        boolean[] visited = new boolean[graph.length];
        // 0: previous node; 1: distance from beginning
        int[][] shortestPath = new int[graph.length][3];
        int currentNode = begin;
        // Set all paths to max
        for (int i = 0; i < shortestPath.length; i++) {
            shortestPath[i][1] = Integer.MAX_VALUE;
            shortestPath[i][2] = i;
        }
        shortestPath[begin][1] = 0;
        Heap pQueue = new Heap(graph);
        // go until you visit the end node
        while (!visited[end]) {
            // go through the
            for (int i = 0; i < shortestPath[currentNode].length; i++) {
                if (graph[currentNode][i] != -1) {
                    int dist = shortestPath[currentNode][1] + graph[currentNode][i];
                    if (dist < shortestPath[currentNode][i]) {
                        shortestPath[i][1] = dist;
                        shortestPath[i][0] = currentNode;
                    }
                }
            }
            visited[currentNode] = true;
            currentNode = pQueue.removeMin();
        }
        int pathLen = 1;
        currentNode = end;
        while (currentNode != begin) {
            currentNode = shortestPath[currentNode][0];
            pathLen++;
        }

        int pos = 1;
        int[] finalPath = new int[pathLen];
        currentNode = end;
        finalPath[0] = currentNode;
        while (currentNode != begin) {
            currentNode = shortestPath[currentNode][0];
            finalPath[pos] = currentNode;
            pos++;
        }
        flipArray(finalPath);
        return finalPath;
    }

    public static void flipArray(int[] arr) {
        int temp = 0;
        for (int i = 0; i < arr.length / 2; i++) {
            temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }

    // Finds the full dijkstra's table, not just the shortest path
    public static int[][] fullDijkstra(int[][] graph) {
        boolean[] visited = new boolean[graph.length];
        int[][] shortestPath = new int[graph.length][3];
        int currentNode = 0;
        for (int i = 0; i < shortestPath.length; i++) {
            shortestPath[i][1] = Integer.MAX_VALUE;
            shortestPath[i][2] = i;
        }
        shortestPath[0][1] = 0;
        Heap pQueue = new Heap(shortestPath);
        while (currentNode != -1) {
            visited[currentNode] = true;
            System.out.println(currentNode);
            for (int i = 0; i < graph[currentNode].length; i++) {
                if (graph[currentNode][i] != -1) {
                    int dist = shortestPath[currentNode][1] + graph[currentNode][i];
                    System.out.println("Node: " + Integer.toString(i) + ", pathTo: " + dist);
                    if (dist < shortestPath[i][1]) {
                        shortestPath[i][1] = dist;
                        shortestPath[i][0] = currentNode;
                        if (!visited[i]) {
                            pQueue.insert(shortestPath[i]);
                        }
                    }
                }
            }
            System.out.println("Pqueue: " + pQueue);
            System.out.println("Shortest: " + Arrays.deepToString(shortestPath) + "\n");
            currentNode = pQueue.removeMin();
        }
        return shortestPath;
    }
}
