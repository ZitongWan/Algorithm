import java.util.*;

public class GreedyAlgorithm {
    public static void main(String[] args) {
        new Greedy().run();
    }
}
class Greedy{  // 贪心算法
    Map<String, Set<String>> broadcasts = new HashMap<>();  // 创建广播电台,储存的形式是 "K1" = "北京","上海"
    public Map<String, Set<String>> fillBroadCasts(){
        Set<String> hashSet1 = new HashSet<>();  // 将电台放入到 broadcasts
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        Set<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        Set<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        Set<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        Set<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");
        broadcasts.put("K1",hashSet1);
        broadcasts.put("K2",hashSet2);
        broadcasts.put("K3",hashSet3);
        broadcasts.put("K4",hashSet4);
        broadcasts.put("K5",hashSet5);
        return broadcasts;
    }
    public Set<String> Areas(){
        Set<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("杭州");
        allAreas.add("大连");
        allAreas.add("成都");
        allAreas.add("深圳");
        allAreas.add("广州");
        return allAreas;
    }
    public void run(){
        Set<String> targetAreas = this.Areas();  // 存放所有目标地区的集合
        broadcasts = fillBroadCasts();  // 创建对应的广播电台和其覆盖的地区
        List<String> selects = new ArrayList<>();  // 存放对应的电台集合
        Set<String> tempSet = new HashSet<>();  // 临时的集合,存放覆盖地区的交集
        String maxKey;  // 最优解对应的key,即对应的编号,如 "K1"
        Set<String> temp = null;  // 这个是broadcasts.get(maxKey),用于比较tempSet和重新覆盖后的集合大小
        while (targetAreas.size() != 0){
            maxKey = null;
            for (String key : broadcasts.keySet()){
                tempSet.clear();
                Set<String> areas = broadcasts.get(key);  // 得到key对应的地区集合
                tempSet.addAll(areas);  // 将地区集合加入到tempSet中去
                tempSet.retainAll(targetAreas);  // 将tempSet和所有地区的集合取交集并返回tempSet,即最终交集的结果赋给了tempSet
                if (maxKey != null){
                    // 如果maxKey为空,这里使用get方法会报错
                    temp = broadcasts.get(maxKey);
                    temp.retainAll(targetAreas);  // 主要是因为这里retainAll返回的是一个boolean类型,无法直接比较
                }
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > temp.size())){
                    // broadcasts.get(maxKey).retainAll(targetAreas).size()但这里retainAll返回的是一个boolean类型
                    maxKey = key;
                }
            }
            if (maxKey != null){
                selects.add(maxKey);  // 将maxKey加入到最终的电台集合里去
                targetAreas.removeAll(broadcasts.get(maxKey));  // 将maxKey对应的地区除去
            }
        }
        System.out.println(selects);
    }
}
