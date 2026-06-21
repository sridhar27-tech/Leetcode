class Solution {
    public int countGoodSubstrings(String s) {
        if(s.length() < 3) return 0;

        int i =0,j = 2;
        int count = 0;

        while(j<s.length()) {
            char[] arr = s.substring(i,j+1).toCharArray();
            Set<Character> set = new HashSet<>();
            for(char ch : arr) {
                set.add(ch);
            }
            System.out.print(set);
            if(set.size() == 3) count++;
            i++;j++;
        }
        return count;
    }
}