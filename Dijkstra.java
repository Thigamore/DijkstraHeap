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
            for (int i = 0; i < graph[currentNode].length; i++) {
                if (graph[currentNode][i] != -1) {
                    int dist = shortestPath[currentNode][1] + graph[currentNode][i];
                    if (dist < shortestPath[i][1]) {
                        shortestPath[i][1] = dist;
                        shortestPath[i][0] = currentNode;
                        if (!visited[i]) {
                            pQueue.insert(shortestPath[i]);
                        }
                    }
                }
            }
            currentNode = pQueue.removeMin();
        }
        return shortestPath;
    }
}

public class Heap {
    int[][] arr;
    int size;

    public Heap(int[][] shortestPath) {
        // path length, prev node, current node
        this.arr = new int[shortestPath.length][3];
        this.size = 0;
        initialHeapify();
    }

    private void initialHeapify() {
        int current = (this.size / 2) - 1;
        for (; current >= 0; current--) {
            heapify(current);
        }
    }

    private void heapify(int i) {
        int smallest = i;
        int lChild = this.leftChild(i);
        int rChild = this.rightChild(i);
        if (lChild < this.size && this.arr[lChild][1] < this.arr[smallest][1]) {
            smallest = lChild;
        }
        if (rChild < this.size && this.arr[rChild][1] < this.arr[smallest][1]) {
            smallest = rChild;
        }
        if (smallest != i) {
            int[] temp = this.arr[smallest];
            this.arr[smallest] = this.arr[i];
            this.arr[i] = temp;
            heapify(smallest);
        }
    }

    public void insert(int[] node) {
        this.size++;
        this.arr[this.size - 1] = node;
        int parent = this.size - 1;
        while (parent != 0) {
            parent = this.parent(parent);
            heapify(parent);
        }
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void delete(int i) {

    }

    public int removeMin() {
        if (this.size <= 0) {
            return -1;
        }
        int toReturn = this.arr[0][2];
        this.arr[0] = new int[3];
        this.arr[0][1] = Integer.MAX_VALUE;
        this.arr[0][2] = 99;
        int[] temp = arr[0];
        arr[0] = arr[this.size - 1];
        arr[this.size - 1] = temp;
        heapify(0);
        this.size--;
        return toReturn;
    }

    public String toString() {
        String str = "";
        for (int[] arr : this.arr) {
            str += Integer.toString(arr[2]) + " ";
        }
        return str;
    }
}
