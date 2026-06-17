class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int left  =0;
        int right = k;
        double max = Integer.MIN_VALUE;
        double sum = 0;
        for(int i =0;i<k;i++) {
            sum+=nums[i];
        }
        max = sum/k;
        if(k==0) return 0.00;
        while(right < nums.length){
            sum-=nums[left];
            sum+=nums[right];
            max = Math.max(max,sum/k);
            left++;
            right++;
        }
        return max;
    }
}