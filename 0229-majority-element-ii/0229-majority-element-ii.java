class Solution {
    public List<Integer> majorityElement(int[] nums) {
       Set<Integer> res = new HashSet<>();
        Map<Integer, Integer> map = new HashMap<>();
        int req = nums.length/3;
       for(int i=0;i<nums.length;i++) {
        map.put(nums[i], map.getOrDefault(nums[i], 0)+1);

        if(map.get(nums[i]) > req) res.add(nums[i]);
        
       } 
        return new ArrayList<>(res);
    }
}