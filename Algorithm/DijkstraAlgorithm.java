package Algorithm;

import java.util.Arrays;

public class DijkstraAlgorithm {  // 计算一个图中单个顶点到其他各个顶点的最短距离
    public static void main(String[] args) {
//        // Exam2019
//        char[] vertex = {'A','B','C','D','E','F','G'};
//        final int N = 65535;
//        int[][] matrix = new int[vertex.length][vertex.length];
//        matrix[0] = new int[] {N,7,5,N,N,N,N};
//        matrix[1] = new int[] {7,N,9,7,8,N,N};
//        matrix[2] = new int[] {5,9,N,15,N,6,N};
//        matrix[3] = new int[] {N,7,15,N,5,8,9};
//        matrix[4] = new int[] {N,8,N,5,N,N,N};
//        matrix[5] = new int[] {N,N,6,8,N,N,11};
//        matrix[6] = new int[] {N,N,N,9,N,11,N};
//        Graph graph = new Graph(vertex, matrix);
//        graph.Dijkstra(6);

        // Exam2019
        char[] vertex = {'A','B','C','D','E','F','G','H','I'};
        final int N = 65535;
        int[][] matrix = new int[vertex.length][vertex.length];
        matrix[0] = new int[] {N,4,8,N,N,N,N,N,N};
        matrix[1] = new int[] {4,N,11,8,N,N,N,N,N};
        matrix[2] = new int[] {8,11,N,N,7,1,N,N,N};
        matrix[3] = new int[] {N,8,N,N,2,N,7,4,N};
        matrix[4] = new int[] {N,N,7,2,N,6,N,N,N};
        matrix[5] = new int[] {N,N,1,N,6,N,N,2,N};
        matrix[6] = new int[] {N,N,N,7,N,N,N,14,9};
        matrix[7] = new int[] {N,N,N,4,N,2,14,N,10};
        matrix[8] = new int[] {N,N,N,N,N,N,9,10,N};
        Graph graph = new Graph(vertex, matrix);
        graph.Dijkstra(0);
    }
}
class Graph{
    char[] vertexes;  // 顶点数组
    int[][] matrix;  // 邻接矩阵
    VisitedVertex vv;

    public Graph(char[] vertexes, int[][] matrix) {
        this.vertexes = vertexes;
        this.matrix = matrix;
    }
    public void show(){
        for (int i = 0; i < vertexes.length; i++){
            for (int j = 0; j < vertexes.length; j++){
                System.out.printf("%8d",matrix[i][j]);
            }
            System.out.println();
        }
    }
    public void update(int index){  // 这里的更新主要是更改前驱节点(pre)和该点周围节点到出发节点的距离(dis)
        // 更新index到周围顶点的前驱节点  更新dis(出发顶点到其他所有顶点的距离)
        // 更新index下标顶点到周围顶点的距离和周围顶点的前驱节点
        int len;
        for (int i = 0; i < matrix[index].length; i++){  // 根据邻接矩阵,遍历matrix[index]行
            len = vv.getDis(index) + matrix[index][i];  // 此index到起点的距离和index到i的距离
            if (!vv.visited(i) && len < vv.getDis(i)){  // 如果i顶点没有被访问过,并且len小于起点到i顶点的距离就更新,即经过index节点到达i节点的长度小于起点到达i节点的长度
                vv.updatePre(i, index);  // 将index设置为i的前驱
                vv.updateDis(i, len);  // 更新出发顶点到i的距离
            }
        }
    }
    public void Dijkstra(int index){
        vv = new VisitedVertex(vertexes.length, index);
        this.update(index);  // 更新index顶点到周围顶点的前驱节点并更新dis(出发顶点到其他所有顶点的距离)
        for (int i = 1; i < vertexes.length; i++){
            index = vv.updateArr();  // 选择并返回新的访问顶点
            update(index);  // 更新index顶点到周围顶点的前驱节点和dis
        }
        vv.show();
    }
}
class VisitedVertex{
    // 这里三个数组都是动态更新(难点)
    int[] alreadyArr;  // 记录各个顶点是否访问  1 表示访问  0 表示未访问
    int[] preVisited;  // 每个下标对应的值为前一个顶点的下标
    int[] dis;  // 记录出发顶点到其他所有顶点的距离
    public VisitedVertex(int length, int index){
        this.alreadyArr = new int[length];
        this.preVisited = new int[length];
        this.dis = new int[length];
        Arrays.fill(dis,65535);
        this.alreadyArr[index] = 1;
        this.dis[index] = 0;
    }
    public boolean visited(int index){
        // 判断index对应的顶点是否被访问过
        return alreadyArr[index] == 1;
    }
    public void updateDis(int index, int len){
        // 更新出发顶点到index顶点的距离
        dis[index] = len;
    }
    public void updatePre(int pre, int index){
        // 更新pre这个顶点的前驱节点为index
        preVisited[pre] = index;
    }
    public int getDis(int index){
        // 返回出发顶点到index顶点的距离
        return dis[index];
    }
    public int updateArr(){
        // 继续选择并返回新的访问节点 (第一次G走完后走的就是A)
        int min = 65535;
        int index = 0;
        for (int i = 0; i < alreadyArr.length; i++){
            if (alreadyArr[i] == 0 && dis[i] < min){
                min = dis[i];
                index = i;
            }
        }
        alreadyArr[index] = 1;
        return index;
    }
    public void show(){
        for (int i : alreadyArr){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
        for (int i : preVisited){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
        char[] vertex = {'A','B','C','D','E','F','G','H','I'};
        int count = 0;
        for (int i : dis){
            if (i != 65535){
                System.out.print(vertex[count] + "(" + i + ") ");
            } else{
                System.out.println("N ");
            }
            count++;
        }
        System.out.println();
    }
}
