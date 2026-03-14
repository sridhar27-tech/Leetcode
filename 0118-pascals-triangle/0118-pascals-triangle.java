class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(List.of(1));

        for(int i =0;i<numRows-1;i++) {
            
            List<Integer> row = new ArrayList<>();
            List<Integer> dummy = new ArrayList<>();
            dummy.add(0);
            dummy.addAll(res.get(res.size()-1));
            dummy.add(0);

            for(int j=1;j<dummy.size();j++) {
                row.add(dummy.get(j) + dummy.get(j-1));
            }
            res.add(row);
        }
        return res;
    }
}