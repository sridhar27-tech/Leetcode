class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();
        boolean flag = true;
        int idx = 0;
        int n =strs[0].length();
        for(int i =0;i<strs.length;i++) {
            n = Math.min(n , strs[i].length());
        }
        while(idx < n) {
            String word = strs[0];
            char ch = word.charAt(idx);
            for(int i =0;i<strs.length;i++) {
                String str = strs[i];
                if(ch != str.charAt(idx)) {
                    return res.toString();
                }
            }
            res.append(ch);
            idx++;
        }
        return res.toString();
    }
}