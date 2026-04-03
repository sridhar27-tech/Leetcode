class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //simple ahh solution
        int a =0;
        for(int i =m ;i<m+n;i++) {
            nums1[i] = nums2[a++];
        }
        Arrays.sort(nums1);
    }
}