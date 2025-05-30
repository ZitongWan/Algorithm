import java.util.Arrays;

public class DynamicProblem {
    public static void main(String[] args) {
        new Bag().run();
    }
}
class Bag{
    int[] w = {1, 4, 3};  // 物品的重量
    int[] value = {1500, 3000, 2000};  // 物品的价值
    int m = 4;  // 背包的容量(能放入物品的最大重量)
    int n = value.length;  // 物品的个数
    int[][] v = new int[n + 1][m + 1];  // v[i][j] 表示在前i个物品中能装入容量为j的背包中的最大价值
    int[][] path = new int[n + 1][m + 1];  // 记录商品保存的情况
    public void initialization(){
        for (int i = 0; i < v.length; i++){
            v[i][0] = 0;  // 将第一列设置为0
        }
        // 将第一行设置为0
        Arrays.fill(v[0], 0);
    }
    public void show(){
        for (int[] arr : v){
            for (int ros : arr){
                System.out.print(ros + " ");
            }
            System.out.println();
        }
    }
    public void showResult(){
        // 展示最终的结果
        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0){
            if (path[i][j] == 1){
                System.out.printf("第%d个商品放到背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }
    public void run(){
        /*
        w[i - 1] 为本次物品的重量
        value[i - 1] 为本次物品的价值
        j 为包的重量
        v[i - 1][j] 为上一次策略
        value[i - 1] + v[i - 1][j - w[i - 1]] 为 本次物品的价值 + 剩下重量的最佳情况
        总的来说就分为两步:
        1):如果此物品超重,就用上一次的策略(包的重量变化)
        2):如果此物品能够放下,就比较 (上一次的策略 和 本次物品的价值 + 剩下重量的最佳情况)
        */
        for (int i = 1; i < v.length; i++){  // 不处理第一行,从第二行开始  i 表示的是物体
            for (int j = 1; j < v[0].length; j++){  // 不处理第一列,从第二列开始  j 表示的是包的重量
                if (w[i - 1] > j){  // 如果w[i - 1]的重量大于此时j(包的重量),就复制上一个的情况  (包放不下)
                    v[i][j] = v[i - 1][j];
                } else{
                    // v[i][j] = Math.max(v[i - 1][j], value[i - 1] + v[i - 1][j - w[i - 1]])
                    if (v[i - 1][j] < value[i - 1] + v[i - 1][j - w[i - 1]]){
                        v[i][j] = value[i - 1] + v[i - 1][j - w[i - 1]];
                        path[i][j] = 1;  // path[i][j] = 1 记录的就是满足条件下的最优解,如果只是复制上一个,就没有充分利用,不能说是最优解
                    } else{
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }
        this.showResult();
    }
}
