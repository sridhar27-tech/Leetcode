class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        int left = 1;
        int right = 1;
        for(int i =0;i<nums.length;i++) {
            res[i] = left;
            left *= nums[i];
        }
        System.out.print(Arrays.toString(res));
        for(int i = nums.length - 1;i>=0;i--) {
            res[i] *= right;
            right *= nums[i];
        }

        return res;
    }
}