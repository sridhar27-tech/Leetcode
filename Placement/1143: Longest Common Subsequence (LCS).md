# 🧩 LeetCode 1143: Longest Common Subsequence (LCS)

## 🔗 Problem Link

[https://leetcode.com/problems/longest-common-subsequence/](https://leetcode.com/problems/longest-common-subsequence/)

---

## 📌 Problem Statement

Given two strings `text1` and `text2`, return the **length of their longest common subsequence**.

A **subsequence** of a string is a new string generated from the original string with some characters deleted (can be none) **without changing the relative order** of the remaining characters.

---

## 💡 Intuition

When comparing two strings:

* If the current characters **match**, they can be part of the LCS.
* If they **don’t match**, we have a choice:

  * Skip a character from `text1`
  * Skip a character from `text2`

We want the **maximum length** possible from these choices.

This naturally leads to a **Dynamic Programming** solution because:

* The same subproblems repeat
* Each decision depends on smaller substrings

---

## 🧠 Core Idea & Thinking View

Think of the problem as:

> "At every position `(i, j)`, what is the LCS of `text1[i…end]` and `text2[j…end]`?"

We build the solution **bottom-up** using a DP table.

---

## 🐌 Brute Force Approach (Recursive)

### 🔸 Idea

Try all possible subsequences by recursively comparing characters.

### 🔸 Algorithm

1. Start from index `i` in `text1` and `j` in `text2`
2. If characters match → `1 + recurse(i+1, j+1)`
3. Else → `max(recurse(i+1, j), recurse(i, j+1))`

### 🔸 Code (Java)

```java
class Solution {
    public int lcs(String s1, String s2, int i, int j) {
        if (i == s1.length() || j == s2.length()) return 0;
        
        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcs(s1, s2, i + 1, j + 1);
        } else {
            return Math.max(lcs(s1, s2, i + 1, j), lcs(s1, s2, i, j + 1));
        }
    }
}
```

### 🔸 Time Complexity

* **O(2^(n+m))** ❌ (Very slow)

---

## ⚡ Optimized Approach (Dynamic Programming)

### 🔸 Idea

Store results of subproblems to avoid recomputation.

### 🔸 DP Definition

`dp[i][j]` = length of LCS of `text1[0..i-1]` and `text2[0..j-1]`

### 🔸 Transition

* If characters match:

  ```
  dp[i][j] = 1 + dp[i-1][j-1]
  ```
* Else:

  ```
  dp[i][j] = max(dp[i-1][j], dp[i][j-1])
  ```

---

### 🔸 Code (Java)

```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }
}
```

---

## 🔄 Algorithm Flow (With Example)

### Example

```
text1 = "abcde"
text2 = "ace"
```

### Step-by-Step

* Compare characters row by row
* Matching characters: `a`, `c`, `e`
* Build DP table gradually

Final Answer:

```
LCS Length = 3
```

---

## 📊 DP Table Visualization

![LCS DP Table](https://upload.wikimedia.org/wikipedia/commons/8/8c/Longest_common_subsequence_problem.svg)

---

## ⏱️ Complexity Analysis

| Approach            | Time       | Space    |
| ------------------- | ---------- | -------- |
| Brute Force         | O(2^(n+m)) | O(n+m)   |
| Dynamic Programming | O(n × m)   | O(n × m) |

---

## ✅ Key Takeaways

* LCS is a **classic DP problem**
* Brute force explores all possibilities ❌
* DP efficiently reuses previous results ✅
* Pattern appears in many string comparison problems

---

## 🏁 Final Notes

* This problem helps build intuition for **2D DP**
* Foundation for problems like:

  * Edit Distance
  * Shortest Common Supersequence

Happy Coding 🚀
