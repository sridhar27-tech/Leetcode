class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        //sliding window solution
        int sum =0;
        int min = Integer.MAX_VALUE;
        int count = 0;
        for(int i=0;i<nums.length;i++) {
            sum+=nums[i];
                while(sum >= target) {
                    sum-=nums[count];
                    min = Math.min(min, i - count + 1);
                    count++;
                }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}