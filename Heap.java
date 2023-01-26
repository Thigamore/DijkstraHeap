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

    public static void main(String[] args) {
        int[][] arr = { { 9, 0, 0 }, { 6, 1, 1 }, { 3, 2, 2 }, { 8, 3, 3 }, { 7, 4, 4 }, { 5, 5, 5 }, { 0, 6, 6 } };
        Heap heap = new Heap(arr);
        System.out.println(heap);
        System.out.println();
        heap.insert(arr[0]);
        System.out.println(heap);
    }
}
