package Algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HorseChessBoardAlgorithm {  // 马踏棋盘/骑士周游算法(贪心算法)
    public static void main(String[] args) {
        new HorseBoard().run();
    }
}
class HorseBoard{
    int X;  // 棋盘的列数
    int Y;  // 棋盘的行数
    boolean[] visited;  // 标记棋盘上的所有位置,用于确定是否所有位置被访问
    boolean finished;  // 标记棋盘的所有位置是否被访问,为true则表明访问成功
    public ArrayList<Point> next(Point curPoint){
        //       6     7
        //    5           0
        //          马
        //    4           1
        //       3     2
        ArrayList<Point> ps = new ArrayList<>();
        Point p1 = new Point();  // 一个新的对象,每一次都进行赋值
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) ps.add(new Point(p1));  // 5
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) ps.add(new Point(p1));  // 6
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) ps.add(new Point(p1));   // 7
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) ps.add(new Point(p1));   // 0
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) ps.add(new Point(p1));    // 1
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) ps.add(new Point(p1));    // 2
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) ps.add(new Point(p1));   // 3
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) ps.add(new Point(p1));   // 4
        return ps;
    }
    public void sort(ArrayList<Point> ps){
        //  ps.sort((o1, o2) -> {  // 比较都是两个两个比较排序
        //            int count1 = next(o1).size();  // o1下一组能走集合的大小
        //            int count2 = next(o2).size();  // o2下一组能走集合的大小
        //            return Integer.compare(count1,count2);  // 如果count1 > count2 返回一个正整数,相等返回0,小于返回一个负整数
        //            //return Integer.compare(count1, count2);
        //            //public static int compare(int x, int y) {
        //            //        return (x < y) ? -1 : ((x == y) ? 0 : 1);
        //            //    }
        //            // return (x < y) ? -1 : ((x == y) ? 0 : 1);
        //        });
        ps.sort(new Comparator<Point>() {  // 重写sort方法,使得ps按照本节点的下一组能走的集合的大小进行非递减排序
            @Override
            public int compare(Point o1, Point o2) {  // 比较都是两个两个比较排序
                int count1 = next(o1).size();  // o1下一组能走集合的大小
                int count2 = next(o2).size();  // o2下一组能走集合的大小
                return Integer.compare(count1,count2);  // 如果count1 > count2 返回一个正整数,相等返回0,小于返回一个负整数
                //return Integer.compare(count1, count2);
                //public static int compare(int x, int y) {
                //        return (x < y) ? -1 : ((x == y) ? 0 : 1);
                //    }
                // return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
    }
    /**
     * 这里的x,y 对应到棋盘上就不一样了,棋盘是二维数组,比如(1,2) 在棋盘上就是chessBoard[2][1]
     * @param chessBoard  棋盘
     * @param x  当前的x坐标
     * @param y  当前的y坐标
     * @param step  第几步
     */
    public void travelChessBoard(int[] [] chessBoard, int x, int y, int step){
        chessBoard[y][x] = step;  // 这里初始时候传入的step为1,即第一个位置
        visited[y * X + x] = true;  // 将一维数组中此索引对应的位置标记为true
        ArrayList<Point> ps = next(new Point(x, y));  // 获取当前位置可以走的下一个位置的集合
        sort(ps);  // 使ps按照本节点的下一组能走的集合的大小进行非递减排序
        while (!ps.isEmpty()){
            Point p = ps.remove(0);  // 取出ps的第一个元素,此时该元素也从ps里面删除
            if (!visited[p.y * X + p.x]) travelChessBoard(chessBoard,p.x, p.y, step + 1);  // 如果该位置没有被访问,就继续上述过程
        }
        if (step < X * Y && !finished){  // 如果没有达到目标数量或者已经达到了数量,但是处于最终回溯状态
            // 这里如果不添加 && !finished,那么当最终回溯的时候(step必定小于X * Y),那么此位置就要被设置为false,这是我们不希望的,因为会导致重新调用travelChessBoard,产生无限递归
            chessBoard[y][x] = 0;  // 将此位置设置为0,即无法走通
            visited[y * X + x] = false;
        } else{
            finished = true;
        }
    }
    public void run(){
        X = 8;  // 棋盘的列数
        Y = 8;  // 棋盘的行数
        int row = 8;  // 初始的行数
        int column = 8;  // 初始的列数
        int[][] chessBoard = new int[X][Y];  // 棋盘
        visited = new boolean[X * Y];
        long start = System.currentTimeMillis();
        travelChessBoard(chessBoard,column - 1,row - 1, 1);  // 骑士周游
        long end = System.currentTimeMillis();
        System.out.println("共耗时" + (end - start) + "ms...");
        for (int[] i : chessBoard){
            System.out.println(Arrays.toString(i));
        }
    }
}
