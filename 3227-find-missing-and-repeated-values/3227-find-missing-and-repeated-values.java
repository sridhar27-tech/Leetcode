class Solution {
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = grid.length;
        int m = n * n;
        int[] res = new int[2];
        for(int i =1;i<=m;i++) {
            map.put(i, 0);
        }

        for(int i =0;i<n;i++) {
            for(int j =0; j<n;j++) {
                map.put(grid[i][j], map.get(grid[i][j])+1);
                if(map.get(grid[i][j])==2) {
                    res[0] = grid[i][j];
                } 
            }
        }

        for(int num : map.keySet()) {
            if(map.get(num) == 0) res[1] = num;
        }
        return res;
    }
}