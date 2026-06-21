class Solution {
    public int countGoodSubstrings(String s) {
        if(s.length() < 3) return 0;

        int j = 0;
        int count = 0;

        while(j<s.length()-2) {
            char a = s.charAt(j);
            char b = s.charAt(j+1);
            char c = s.charAt(j+2);
            if(a != b && b != c && a != c) count++;
            j++;
        }
        return count;
    }
}