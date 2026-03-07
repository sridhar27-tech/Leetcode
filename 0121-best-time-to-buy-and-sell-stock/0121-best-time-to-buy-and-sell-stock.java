class Solution {
    public int maxProfit(int[] nums) {
        int max = 0;
        int min = nums[0];
        for(int i=0;i<nums.length;i++) {
            min = Math.min(min, nums[i]);
            int profit = nums[i] - min;
            max = Math.max(profit, max); 
        }
        return max;
    }
}