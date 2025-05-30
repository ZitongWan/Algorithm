package Algorithm;

import java.util.Arrays;

public class KruskalCaseAlgorithm {  // 适用于稀疏图
    public static void main(String[] args) {
        final int N = Integer.MAX_VALUE;
        char[] vertexes = {'A','B','C','D','E','F','G'};
//        int[][] matrices2019 = {
//                {0,7,5,N,N,N,N},
//                {7,0,9,7,8,N,N},
//                {5,9,0,15,N,6,N},
//                {N,7,15,0,5,8,9},
//                {N,8,N,5,0,N,N},
//                {N,N,6,8,N,0,11},
//                {N,N,N,9,N,11,0}
//        };
//        new Kruskal(vertexes,matrices2019).run();
        int[][] matrices2023 = {
                {0,5,1,4,N,N,N},
                {5,0,N,N,N,6,N},
                {1,N,0,3,2,N,N},
                {4,N,3,0,N,8,N},
                {N,N,2,N,0,7,9},
                {N,6,N,8,7,0,N},
                {N,N,N,N,9,N,0}
        };
        new Kruskal(vertexes,matrices2023).run();
    }
}
class Kruskal{  // 克鲁斯卡尔算法
    int edgeNum;  // 边的个数
    char[] vertexes;  // 储存顶点的数组
    int[][] matrix;  // 邻接矩阵
    final int INF = Integer.MAX_VALUE;
    public Kruskal(char[] vertex, int[][] matrices){
        int length = vertex.length;
        this.vertexes = new char[length];
        //  System.arraycopy(src, srcPos, dest, destPos, length);
        //  src（源数组）：这是您要从中复制数据的二维数组。
        //  srcPos（源位置）：这是源数组的起始行。
        //  dest（目标数组）：这是您要将数据复制到的二维数组。
        //  destPos（目标位置）：这是目标数组的起始行。
        //  length：这是要复制的行数。
        System.arraycopy(vertex, 0, vertexes, 0, length);
        this.matrix = new int[length][length];
        for (int i = 0; i < length; i++){
            System.arraycopy(matrices[i],0,matrix[i],0,length);
        }
        for (int i = 0; i < length; i++){
            for (int j = i + 1; j < length; j++){  // 不统计自身,因为自身置为0
                if (matrix[i][j] != INF)  edgeNum++;
            }
        }
    }
    public void show(){
        // 打印邻接矩阵
        for (int i = 0; i < vertexes.length; i++){
            for (int j = 0; j < vertexes.length; j++){
                System.out.printf("%12d",matrix[i][j]);
            }
            System.out.println();
        }
    }
    public void sortEdges(EData[] edges){
        // 将边(这里的边对应的有start和end)按照权值的大小进行排序,最终权值最小的排在第一位
        for (int i = 0; i < edges.length - 1; i++){
            for (int j = 0; j < edges.length - 1 - i; j++){
                if (edges[j].weight > edges[j + 1].weight){
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }
    public int getPosition(char ros){
        // 根据顶点的值返回对应的下标
        for (int i = 0; i < vertexes.length; i++){
            if (vertexes[i] == ros) return i;
        }
        return -1;
    }
    public EData[] getEdges(){
        // 根据邻接矩阵返回图中的边,最终形式为[['A','B',12]...]
        // 这里边的大小是按照字母的顺序来的,所以A指向的是B
        int index = 0;
        EData[] ros = new EData[edgeNum];
        for (int i = 0; i < vertexes.length; i++){  // 这里的i指的就是起始点
            for (int j = i + 1; j < vertexes.length; j++){  // j指的就是i的终点
                if (matrix[i][j] != INF) ros[index++] = new EData(vertexes[i], vertexes[j], matrix[i][j]);
            }
        }
        return ros;
    }

    /**
     *
     * @param ends  记录各个顶点对应的终点是哪个,并且end是逐步形成的
     * @param i  传入顶点对应的下标
     * @return  返回传入i对应的终点下标
     */
    public int getEnd(int[] ends, int i){
        while (ends[i] != 0){
            i = ends[i];
        }
        return i;
    }
    public void run(){
        int index = 0;  // 最终结果数组的索引
        // 6 5 3 5 5 6 0  这里的0实际上指的是自身,因为调用getEnd方法的时候会返回它的索引
        int[] ends = new int[vertexes.length];  // 最小生成树中每个顶点对应的终点,这里最终是所有顶点的个数,本次代码即7个值,虽然最终索引的值为0,但是getEnd()会返回其索引6
        EData[] result = new EData[vertexes.length - 1];  // 最终的最小生成树,最小生成树是顶点个数-1
        EData[] target = this.getEdges();  // 获取所有边的集合
        this.sortEdges(target);  // 将边按照权值的大小进行排序
        System.out.println(Arrays.toString(target));
        for (int i = 0; i < edgeNum; i++){  // 这里依次遍历所有的边
            // 经过排序之后target中元素的顺序就是按照权值的大小
            int p1 = getPosition(target[i].start);  // 获取第i条边的第一个顶点(起点)
            int p2 = getPosition(target[i].end);  // 获取第i条边的第二个顶点(终点)
            int m = getEnd(ends, p1);  // 获取该边起点的终点
            int n = getEnd(ends, p2);  // 获取该边终点的终点
            if (m != n){  // 如果他们没有构成回路(一个森林),就将此边添加到result中去  构成回路: 起点的终点和终点的终点相同
                ends[m] = n;  // 将m的终点设置为n
                result[index] = target[i];
                index++;
            }
        }
        System.out.println(Arrays.toString(ends));
        for (EData mon : result){
            System.out.println(mon.toString());
        }
    }
}
//        char[] vertexes = {'A','B','C','D','E','F','G'};
//        int[][] matrices = {
//                {0,12,INF,INF,INF,16,14},
//                {12,0,10,INF,INF,7,INF},
//                {INF,10,0,3,5,6,INF},
//                {INF,INF,3,0,4,INF,INF},
//                {INF,INF,5,4,0,2,8},
//                {16,7,6,INF,2,0,9},
//                {14,INF,INF,INF,8,9,0}
//        };
class EData{
    char start;
    char end;
    int weight;

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
