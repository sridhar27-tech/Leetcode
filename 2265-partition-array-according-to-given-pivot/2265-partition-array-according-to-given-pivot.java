class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int[] high = new int[nums.length];
        int[] res = new int[nums.length];
        int count = 0;
        int l =0;
        int h = 0;
        for(int i =0;i<nums.length;i++) {
            if(nums[i] < pivot) res[l++] = nums[i];
            else if(nums[i] > pivot) high[h++] = nums[i];
            else count++;
        }

        if(l == nums.length) return res;

        for(int i=l;i<l+count;i++) {
            res[i] = pivot;
        }
        l+=count;

        for(int i =0;i<nums.length - l;i++) {
            res[l+i] = high[i];
        }

        return res;
    }
}