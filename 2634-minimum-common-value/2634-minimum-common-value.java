class Solution {
    public int getCommon(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int min = Math.max(nums1[n-1], nums2[m-1]);
        boolean found = false;
        Set<Integer> set = Arrays.stream(nums1).boxed().collect(Collectors.toSet());//yo whats thisðð

        for(int i =0;i<m;i++) {
            if(set.contains(nums2[i])) {
                min = Math.min(min, nums2[i]);
                found = true;
            }
        }
        if(found)
        return min;
        return -1;

    }
}