class Solution {
    public int maxArea(int[] nums) {
        int i =0;
        int j = nums.length - 1;
        int max = 0;

        while(i<j) {
            max = Math.max(max, (j - i ) * Math.min(nums[i], nums[j]));
            if(nums[i] > nums[j]) j--;
            else i++;
        }
        return max;
    }
}