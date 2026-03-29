class Solution {
    public boolean canBeEqual(String s1, String s2) {
        char[] group1_s1 = {s1.charAt(0), s1.charAt(2)};
        char[] group1_s2 = {s2.charAt(0), s2.charAt(2)};
        Arrays.sort(group1_s1);
        Arrays.sort(group1_s2);

        char[] group2_s1 = {s1.charAt(1), s1.charAt(3)};
        char[] group2_s2 = {s2.charAt(1), s2.charAt(3)};
        Arrays.sort(group2_s1);
        Arrays.sort(group2_s2);

        return Arrays.equals(group1_s1, group1_s2) && Arrays.equals(group2_s1, group2_s2);
    }
}