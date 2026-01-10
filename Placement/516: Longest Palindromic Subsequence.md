# LeetCode 516: Longest Palindromic Subsequence

## Problem Link
[LeetCode Problem 516](https://leetcode.com/problems/longest-palindromic-subsequence/)

---

## Problem Statement

Given a string `s`, find the length of the longest palindromic subsequence in `s`.

A **subsequence** is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

A **palindrome** is a sequence that reads the same backward as forward.

### Examples

**Example 1:**
```
Input: s = "bbbab"
Output: 4
Explanation: One possible longest palindromic subsequence is "bbbb".
```

**Example 2:**
```
Input: s = "cbbd"
Output: 2
Explanation: One possible longest palindromic subsequence is "bb".
```

**Constraints:**
- `1 <= s.length <= 1000`
- `s` consists only of lowercase English letters.

---

## Intuition

The key insight is that finding the longest palindromic subsequence in a string is equivalent to finding the **Longest Common Subsequence (LCS)** between the original string and its reverse.

**Why does this work?**

A palindrome reads the same forwards and backwards. So if we have a subsequence that appears in both the original string and its reverse in the same order, that subsequence must be a palindrome!

For example:
- String: `"bbbab"`
- Reverse: `"babbb"`
- LCS: `"bbbb"` (which is a palindrome)

---

## Approach 1: Brute Force (Recursion)

### Core Idea

Generate all possible subsequences and check which ones are palindromes, then return the length of the longest one.

### Algorithm

1. For each character, we have two choices: include it or exclude it
2. Recursively generate all subsequences
3. Check if each subsequence is a palindrome
4. Track the maximum length

### Code

```java
public int longestPalindromeSubseqBruteForce(String s) {
    return helper(s, 0, s.length() - 1);
}

private int helper(String s, int left, int right) {
    // Base case: single character or invalid range
    if (left > right) return 0;
    if (left == right) return 1;
    
    // If characters match, they can be part of palindrome
    if (s.charAt(left) == s.charAt(right)) {
        return 2 + helper(s, left + 1, right - 1);
    }
    
    // If they don't match, try excluding either left or right
    return Math.max(helper(s, left + 1, right), 
                    helper(s, left, right - 1));
}
```

### Time Complexity
- **O(2^n)** - For each position, we make two recursive calls
- Extremely inefficient for large inputs

### Space Complexity
- **O(n)** - Recursion stack depth

---

## Approach 2: Dynamic Programming (Optimized Solution)

### Core Idea

The problem exhibits **optimal substructure** and **overlapping subproblems**, making it perfect for DP.

**State Definition:**
- `dp[i][j]` = length of longest palindromic subsequence in substring `s[i...j]`

**Recurrence Relation:**
- If `s[i] == s[j]`: `dp[i][j] = dp[i+1][j-1] + 2`
- If `s[i] != s[j]`: `dp[i][j] = max(dp[i+1][j], dp[i][j-1])`

**Base Case:**
- `dp[i][i] = 1` (single character is always a palindrome)

### Thinking Process

1. **Build from smaller subproblems:** Start with substrings of length 1, then 2, then 3, and so on
2. **Use previously computed results:** When we compute `dp[i][j]`, we already know the answers for smaller substrings
3. **Two scenarios at each step:**
   - Characters match → they form part of the palindrome
   - Characters don't match → we must exclude one of them

---

## Algorithm Flow with Example

Let's trace through `s = "bbbab"` step by step.

### Step 1: Initialize DP Table

Create a 2D array `dp[5][5]` (size n x n where n = 5)

```
     0  1  2  3  4
   ┌──┬──┬──┬──┬──┐
0  │ 1│  │  │  │  │
   ├──┼──┼──┼──┼──┤
1  │  │ 1│  │  │  │
   ├──┼──┼──┼──┼──┤
2  │  │  │ 1│  │  │
   ├──┼──┼──┼──┼──┤
3  │  │  │  │ 1│  │
   ├──┼──┼──┼──┼──┤
4  │  │  │  │  │ 1│
   └──┴──┴──┴──┴──┘
```

Base case: All single characters have palindrome length 1.

### Step 2: Fill for Length 2 Substrings

```
s[0:1] = "bb" → s[0] == s[1] → dp[0][1] = 2
s[1:2] = "bb" → s[1] == s[2] → dp[1][2] = 2
s[2:3] = "ba" → s[2] != s[3] → dp[2][3] = max(dp[3][3], dp[2][2]) = 1
s[3:4] = "ab" → s[3] != s[4] → dp[3][4] = max(dp[4][4], dp[3][3]) = 1
```

```
     0  1  2  3  4
   ┌──┬──┬──┬──┬──┐
0  │ 1│ 2│  │  │  │
   ├──┼──┼──┼──┼──┤
1  │  │ 1│ 2│  │  │
   ├──┼──┼──┼──┼──┤
2  │  │  │ 1│ 1│  │
   ├──┼──┼──┼──┼──┤
3  │  │  │  │ 1│ 1│
   ├──┼──┼──┼──┼──┤
4  │  │  │  │  │ 1│
   └──┴──┴──┴──┴──┘
```

### Step 3: Fill for Length 3 Substrings

```
s[0:2] = "bbb" → s[0] == s[2] → dp[0][2] = dp[1][1] + 2 = 3
s[1:3] = "bba" → s[1] != s[3] → dp[1][3] = max(dp[2][3], dp[1][2]) = 2
s[2:4] = "bab" → s[2] == s[4] → dp[2][4] = dp[3][3] + 2 = 3
```

```
     0  1  2  3  4
   ┌──┬──┬──┬──┬──┐
0  │ 1│ 2│ 3│  │  │
   ├──┼──┼──┼──┼──┤
1  │  │ 1│ 2│ 2│  │
   ├──┼──┼──┼──┼──┤
2  │  │  │ 1│ 1│ 3│
   ├──┼──┼──┼──┼──┤
3  │  │  │  │ 1│ 1│
   ├──┼──┼──┼──┼──┤
4  │  │  │  │  │ 1│
   └──┴──┴──┴──┴──┘
```

### Step 4: Fill for Length 4 Substrings

```
s[0:3] = "bbba" → s[0] != s[3] → dp[0][3] = max(dp[1][3], dp[0][2]) = 3
s[1:4] = "bbab" → s[1] == s[4] → dp[1][4] = dp[2][3] + 2 = 3
```

```
     0  1  2  3  4
   ┌──┬──┬──┬──┬──┐
0  │ 1│ 2│ 3│ 3│  │
   ├──┼──┼──┼──┼──┤
1  │  │ 1│ 2│ 2│ 3│
   ├──┼──┼──┼──┼──┤
2  │  │  │ 1│ 1│ 3│
   ├──┼──┼──┼──┼──┤
3  │  │  │  │ 1│ 1│
   ├──┼──┼──┼──┼──┤
4  │  │  │  │  │ 1│
   └──┴──┴──┴──┴──┘
```

### Step 5: Fill for Length 5 (Full String)

```
s[0:4] = "bbbab" → s[0] == s[4] → dp[0][4] = dp[1][3] + 2 = 4
```

**Final Table:**
```
     0  1  2  3  4
   ┌──┬──┬──┬──┬──┐
0  │ 1│ 2│ 3│ 3│ 4│ ← Answer!
   ├──┼──┼──┼──┼──┤
1  │  │ 1│ 2│ 2│ 3│
   ├──┼──┼──┼──┼──┤
2  │  │  │ 1│ 1│ 3│
   ├──┼──┼──┼──┼──┤
3  │  │  │  │ 1│ 1│
   ├──┼──┼──┼──┼──┤
4  │  │  │  │  │ 1│
   └──┴──┴──┴──┴──┘
```

**Answer: `dp[0][4] = 4`**

The longest palindromic subsequence is **"bbbb"** with length **4**.

---

## Optimized Solution Code

### Method 1: Using LCS Approach (Your Provided Code)

```java
public int longestPalindromeSubseq(String s) {
    StringBuilder sb = new StringBuilder(s);
    sb.reverse();
    String r = sb.toString();
    
    int[][] dp = new int[s.length() + 1][s.length() + 1];
    
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 1; j <= s.length(); j++) {
            if (s.charAt(i - 1) == r.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }
    
    return dp[s.length()][s.length()];
}
```

**Explanation:**
1. Reverse the string
2. Find LCS between original and reversed string
3. The LCS is the longest palindromic subsequence

**Time Complexity:** O(n²)  
**Space Complexity:** O(n²)

---

### Method 2: Direct DP Approach

```java
public int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];
    
    // Base case: single characters
    for (int i = 0; i < n; i++) {
        dp[i][i] = 1;
    }
    
    // Fill table for substrings of length 2 to n
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            
            if (s.charAt(i) == s.charAt(j)) {
                dp[i][j] = dp[i + 1][j - 1] + 2;
            } else {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
    }
    
    return dp[0][n - 1];
}
```

**Time Complexity:** O(n²)  
**Space Complexity:** O(n²)

---

## Space Optimized Version

Since we only need the previous row to compute the current row, we can optimize space:

```java
public int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[] dp = new int[n];
    
    for (int i = n - 1; i >= 0; i--) {
        int prev = 0;
        dp[i] = 1;
        
        for (int j = i + 1; j < n; j++) {
            int temp = dp[j];
            if (s.charAt(i) == s.charAt(j)) {
                dp[j] = prev + 2;
            } else {
                dp[j] = Math.max(dp[j], dp[j - 1]);
            }
            prev = temp;
        }
    }
    
    return dp[n - 1];
}
```

**Time Complexity:** O(n²)  
**Space Complexity:** O(n)

---

## Complexity Analysis

### Time Complexity
- **Brute Force:** O(2^n) - exponential
- **DP Solution:** O(n²) - we fill an n×n table, each cell takes O(1) time

### Space Complexity
- **Brute Force:** O(n) - recursion stack
- **DP Solution:** O(n²) - 2D table
- **Optimized DP:** O(n) - 1D array

---

## Key Takeaways

1. **Transform the problem:** Longest palindromic subsequence = LCS(s, reverse(s))
2. **Build bottom-up:** Start with smaller subproblems (length 1, 2, ..., n)
3. **Two cases to handle:**
   - Characters match → extend the palindrome
   - Characters don't match → exclude one character
4. **Memoization prevents recomputation:** Each subproblem solved once
5. **Optimal substructure:** Solution to larger problem uses solutions to smaller subproblems

---

## Visual Representation

![Palindrome Concept](https://miro.medium.com/max/1400/1*wgXJaCKqfT8JrRpKrVbpXA.png)

*A palindrome reads the same forwards and backwards*

---

## Related Problems

- [5. Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)
- [647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)
- [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)

---

## Practice Tips

1. Start with small examples to understand the pattern
2. Draw the DP table to visualize state transitions
3. Identify the base cases clearly
4. Practice both the LCS approach and direct DP approach
5. Try to optimize space complexity once you understand the solution
