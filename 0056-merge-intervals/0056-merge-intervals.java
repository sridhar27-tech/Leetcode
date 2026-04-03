class Solution {
    public int[][] merge(int[][] intervals) {
         Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int m = intervals.length;
        List<int[]> merge = new ArrayList<>();
        int[] prev = intervals[0];

        for(int i =1;i<m;i++) {
            int[] wait = intervals[i];

            if(wait[0] <= prev[1]) {
                prev[1] = Math.max(prev[1], wait[1]); 
            }
            else {
                merge.add(prev);
                prev = wait;
            }
        }

        merge.add(prev);
        return merge.toArray(new int[merge.size()][]);
    }
}