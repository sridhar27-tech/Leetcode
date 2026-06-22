class Solution {
    public int maxNumberOfBalloons(String text) {
        Map<Character, Integer> map = new HashMap<>();
        int count = 0;
        for(char ch : "balloon".toCharArray()) {
            map.put(ch,0);
        } 

        for(int i =0;i<text.length();i++) {
            char ch = text.charAt(i);
            if(map.containsKey(ch)) {
                map.put(ch, map.get(ch)+1);
            }
        }
        int l = 0;
        int o = 0;
        if(map.get('l')%2 == 0) {
            l = map.get('l');
        }
        else {
            l = map.get('l') - 1;
        }
        if(map.get('o')%2 == 0) {
            o = map.get('o');
        }
        else {
            o = map.get('o') - 1;
        }

        int twos = Math.min(l,o);
        int ones = Integer.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            char key = entry.getKey();
            int value = entry.getValue();
            
            if (value != 'l' && value != 'o') {
                ones = Math.min(ones, value);
            }
        }
        int min = Math.min(ones * 2, twos);

        return min/2;

    }
}