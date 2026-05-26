class Solution {
    public int numberOfSpecialChars(String word) {
        Set<Character> set = new HashSet<>();
        int count = 0;
        for(int i =0;i<word.length();i++) {
            set.add(word.charAt(i));
        }

        for(char ch : new HashSet<>(set)) {
            if(Character.isUpperCase(ch)) continue;
            char upper = Character.toUpperCase(ch);
            if (set.contains(upper)) {
                count++;
                set.remove(upper);
            }
        }

    return count;
    }
}