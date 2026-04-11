class Solution {
    public int addDigits(int n) {
       return solve(n, 0);
    }

    private int solve(int n , int sum) {
        while(n>0) {
            sum+=n%10;
            n/=10;
        }
        if(sum < 10) return sum;
        return solve(sum, 0);
    }
}