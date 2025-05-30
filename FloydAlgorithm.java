import java.util.Arrays;

public class FloydAlgorithm {  // 弗洛伊德算法和迪杰斯特拉算法类似,但求的是各个点到另外所有点的最小距离,这个求的是所有,迪杰斯特拉求的是单个点
    // 利用的是中间顶点
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        final int N = 65535;
        int[][] matrix = new int[vertex.length][vertex.length];
        matrix[0] = new int[] {0,5,7,N,N,N,2};
        matrix[1] = new int[] {5,0,N,9,N,N,3};
        matrix[2] = new int[] {7,N,0,N,8,N,N};
        matrix[3] = new int[] {N,9,N,0,N,4,N};
        matrix[4] = new int[] {N,N,8,N,0,5,4};
        matrix[5] = new int[] {N,N,N,4,5,0,6};
        matrix[6] = new int[] {2,3,N,N,4,6,0};
        FloydGraph floydGraph = new FloydGraph(vertex.length,matrix,vertex);
        floydGraph.Floyd();
        floydGraph.show();
    }
}
class FloydGraph{
    char[] vertex;  // 存放顶点的数组
    int[][] dis;  // 保存各个顶点出发到其他顶点的距离
    int[][] pre;  // 保存到达目标顶点的前驱节点
    public FloydGraph(int length, int[][] matrix, char[] vertex){
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        for (int i = 0;i < length; i++){
            Arrays.fill(pre[i], i);
        }
    }
    public void show(){
        char[] vertex = {'A','B','C','D','E','F','G'};
        for (int i = 0; i < dis.length; i++){
            for (int j = 0; j < dis.length; j++){
                System.out.print(vertex[pre[i][j]] + " ");
            }
            System.out.println();
            for (int k = 0; k < dis.length; k++){
                System.out.print("(" + vertex[i] + "到" + vertex[k] + "的最短路径为" + dis[i][k] + ")");
            }
            System.out.println();
        }
    }
    public void Floyd(){
        int len;  // 各个顶点到其他顶点的距离
        for (int i = 0; i < dis.length; i++){  // 中间顶点
            for (int j = 0; j < dis.length; j++){  // 开始节点
                for (int k = 0; k < dis.length; k++){  // 最终顶点
                    len = dis[j][i] + dis[i][k];
                    if (len < dis[j][k]){
                        dis[j][k] = len;  // 更新距离  开始节点和最终节点的距离设置为len
                        pre[j][k] = pre[i][k];  // 更新前驱节点  将开始节点和最终节点的前驱节点 -> 中间节点到最终节点的前驱节点
//                        pre[j][k] = i;
                    }
                }
            }
        }
    }
}
