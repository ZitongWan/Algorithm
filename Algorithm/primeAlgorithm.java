package Algorithm;

import java.util.Arrays;

public class primeAlgorithm {  // 适用于大型稠密图
    public static void main(String[] args) {
        char[] data = {'A','B','C','D','E','F','G'};
        int vertexes = data.length;
        int[][] weight = {
                {100,5,7,100,100,100,2},
                {5,100,100,9,100,100,3},
                {7,100,100,100,8,100,100},
                {100,9,100,100,100,4,100},
                {100,100,8,100,100,5,4},
                {100,100,100,4,5,100,6},
                {2,3,100,100,4,6,100}
        };
        MGraph graph = new MGraph(vertexes);
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, vertexes, data, weight);
        minTree.prim(graph,0);
    }
}
class MinTree{
    public void createGraph(MGraph graph, int vertex, char[] data, int[][] weight){
        // 创建对应的邻接矩阵
        int i;
        int j;
        for (i = 0; i < vertex; i++){
            graph.data[i] = data[i];
            for (j = 0; j < vertex; j++){
                graph.weight[i][j] = weight[i][j];
            }
        }
    }
    public void show(MGraph graph){
        for (int[] ros : graph.weight){
            System.out.println(Arrays.toString(ros));
        }
    }
    public void prim(MGraph graph, int v){
        // 普利姆算法
        int[] visited = new int[graph.vertexes];  // 用于标记节点是否被访问
        visited[v] = 1;  // 将传入的这个节点标记为已访问
        int h1 = -1;
        int h2 = -1;
        int minWeight = 100;  // 辅助权值,设置为较大的数据
        int result = 0;  // 最终的权值
        for (int i = 1; i < graph.vertexes; i++){  // 普利姆算法是最小生成树,一定会生成 n - 1 条边所以我们从1开始遍历
            // 这两层for循环是找到  已经访问过的节点的邻接节点的权值最小
            for (int j = 0; j < graph.vertexes; j++){  // j 是已经访问过的节点
                for (int k = 0; k < graph.vertexes; k++){  // k 是未被访问过的节点
                    if (visited[j] == 1 && visited[k] == 0 && graph.weight[j][k] < minWeight){
                        h1 = j;
                        h2 = k;
                        minWeight = graph.weight[j][k];
                    }
                }
            }
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值 : " + minWeight);
            visited[h2] = 1;  // 将h2标记为已访问
            result += minWeight;
            minWeight = 100;  // 重置辅助权值
        }
        System.out.printf("最终的权值为%d\n",result);
    }
}
class MGraph{
    int vertexes;  // 图的节点个数
    char[] data;  // 存放的节点数据
    int[][] weight;  // 存放边
    public MGraph(int vertexes){
        this.vertexes = vertexes;
        data = new char[vertexes];
        weight = new int[vertexes][vertexes];
    }
}
