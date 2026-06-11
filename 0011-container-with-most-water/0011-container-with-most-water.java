class Solution {
    public int maxArea(int[] height) {
        int i =0;
        int j = height.length -1;
        int area = 0;

        while(i<j) {
            int min = Math.min(height[i], height[j]);
            area = Math.max(area, min *(j - i));

            if(height[i] > height[j]) {
                j--;
            }
            else {
                i++;
            }
        }

        return area;
    }
}