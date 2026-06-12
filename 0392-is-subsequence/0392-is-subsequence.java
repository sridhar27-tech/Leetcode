class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s.length() > t.length()) return false;
        if(s.length() == 0) return true;

        int pointer = 0;
        int i =0;
        while(i<t.length()) {
            if(s.charAt(pointer) == t.charAt(i)) {
                pointer++;
            }
            if(pointer == s.length()) return true;
            i++;
        }
        return false;
    }
}