class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        if(nums.length < 3) return result;

        for(int i =0;i<nums.length;i++) {
            if(i > 0 && nums[i] == nums[i-1]) continue;

            int j = i+1, k = nums.length - 1;

            while(j<k) {
                int total = nums[i] + nums[k] + nums[j];

                if(total > 0) {
                    k--;
                }
                else if(total < 0) {
                    j++;
                }
                else {
                    result.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k])));
                    j++;k--;

                    while(j < k && nums[j] == nums[j-1]) j++;
                    while(j < k && nums[k] == nums[k+1]) k--;
                }
            }
        }
        
        return result;
    }
}