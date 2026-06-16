class Solution {
    public int countPrimes(int n) {
        if(n <= 2) return 0;
        boolean[] prime = new boolean[n];
        int count = 0;
        for(int i = 2;i<n;i++) {
            prime[i] = true;
        }

        for(int i =2;i<n;i++) {
            if(prime[i]) {
                count++;
                for(int j = i * 2;j<n;j+=i) {
                    prime[j]=false;
                }
            }
        }
        return count;
    }
}