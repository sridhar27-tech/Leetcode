class Solution {
    public int maxProduct(int[] nums) {
        int currentpass = 1;
        int max = nums[0];

        for(int i =0;i<nums.length;i++) {
            currentpass *= nums[i];
            max = Math.max(max, currentpass);
            if(currentpass == 0) currentpass = 1;
        }
        currentpass = 1;
        for(int i = nums.length - 1;i>=0;i--) {
            currentpass *= nums[i];
            max = Math.max(max, currentpass);
            if(currentpass == 0) currentpass = 1;
        }
        return max;
    }
}