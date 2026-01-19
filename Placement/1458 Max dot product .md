# LeetCode 1458: Max Dot Product of Two Subsequences

## Problem Information

- **LeetCode Number**: 1458
- **Difficulty**: Hard
- **Problem Link**: [Max Dot Product of Two Subsequences](https://leetcode.com/problems/max-dot-product-of-two-subsequences/)
- **Topics**: Dynamic Programming, Array

## Problem Statement

Given two arrays `nums1` and `nums2`, return the maximum dot product between non-empty subsequences of `nums1` and `nums2` with the same length.

A subsequence of an array is a new array formed from the original array by deleting some (can be none) of the elements without disturbing the relative positions of the remaining elements.

**Dot Product Formula**: If we have two subsequences `[a₁, a₂, ..., aₙ]` and `[b₁, b₂, ..., bₙ]`, their dot product is: `a₁ × b₁ + a₂ × b₂ + ... + aₙ × bₙ`

## Examples

**Example 1:**
```
Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
Output: 18
Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
Their dot product is (2*3 + (-2)*(-6)) = 6 + 12 = 18.
```

**Example 2:**
```
Input: nums1 = [3,-2], nums2 = [2,-6,7]
Output: 21
Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
Their dot product is (3*7) = 21.
```

**Example 3:**
```
Input: nums1 = [-1,-1], nums2 = [1,1]
Output: -1
Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
Their dot product is -1.
```

## Constraints

- `1 <= nums1.length, nums2.length <= 500`
- `-1000 <= nums1[i], nums2[i] <= 1000`

## Core Idea & Intuition

### The Key Insight

The problem asks for the maximum dot product of subsequences, which means we need to:

1. **Choose** which elements to include from both arrays
2. **Pair them up** (same positions in subsequences multiply together)
3. **Maximize** the sum of these products

### Why Dynamic Programming?

This is a classic DP problem because:

- We have **overlapping subproblems**: calculating the max dot product for smaller subarrays helps solve larger ones
- We need to make **optimal choices**: at each position, decide whether to include elements or skip them
- The problem has **optimal substructure**: the optimal solution contains optimal solutions to subproblems

### The Decision at Each Step

For each pair of indices `(i, j)` representing positions in `nums1` and `nums2`, we have three choices:

1. **Include both elements**: Take `nums1[i] × nums2[j]` and add to previous best result
2. **Skip nums1[i]**: Use the best result from `nums1[0...i-1]` with `nums2[0...j]`
3. **Skip nums2[j]**: Use the best result from `nums1[0...i]` with `nums2[0...j-1]`

## Solutions

### Approach 1: Brute Force (Recursive)

**Concept**: Generate all possible subsequences from both arrays and calculate their dot products.

```java
public class Solution {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        return helper(nums1, nums2, 0, 0, false);
    }
    
    private int helper(int[] nums1, int[] nums2, int i, int j, boolean hasSelected) {
        // Base case: reached end of either array
        if (i == nums1.length || j == nums2.length) {
            return hasSelected ? 0 : Integer.MIN_VALUE;
        }
        
        // Option 1: Take both current elements
        int take = nums1[i] * nums2[j] + helper(nums1, nums2, i + 1, j + 1, true);
        
        // Option 2: Skip nums1[i]
        int skipFirst = helper(nums1, nums2, i + 1, j, hasSelected);
        
        // Option 3: Skip nums2[j]
        int skipSecond = helper(nums1, nums2, i, j + 1, hasSelected);
        
        return Math.max(take, Math.max(skipFirst, skipSecond));
    }
}
```

**Time Complexity**: O(3^(m+n)) - exponential, trying all combinations

**Space Complexity**: O(m + n) - recursion stack depth

**Why it's inefficient**: This approach recalculates the same subproblems many times. For arrays of size 500, this would take years to complete!

### Approach 2: Dynamic Programming (Optimized)

**Concept**: Use a 2D DP table where `dp[i][j]` represents the maximum dot product using elements from the first `i` elements of `nums1` and first `j` elements of `nums2`.

```java
public class MaxDotProductOfTwoSubsequences {
    
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        
        // dp[i][j] = max dot product using first i elements of nums1 
        // and first j elements of nums2
        int[][] dp = new int[n + 1][m + 1];
        
        // Initialize with negative infinity
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Current product of nums1[i-1] and nums2[j-1]
                int product = nums1[i - 1] * nums2[j - 1];
                
                // Three choices:
                // 1. Take current pair and add to previous best (or start fresh)
                int takeWithPrevious = Math.max(0, dp[i - 1][j - 1]) + product;
                
                // 2. Skip current element from nums1
                int skipFirst = dp[i - 1][j];
                
                // 3. Skip current element from nums2
                int skipSecond = dp[i][j - 1];
                
                // Take the maximum of all choices
                dp[i][j] = Math.max(takeWithPrevious, Math.max(skipFirst, skipSecond));
            }
        }
        
        return dp[n][m];
    }
}
```

**Time Complexity**: O(m × n) - we fill each cell once

**Space Complexity**: O(m × n) - for the DP table

## Algorithm Flow with Example

Let's trace through **Example 1**: `nums1 = [2, 1, -2, 5]`, `nums2 = [3, 0, -6]`

### Step-by-Step DP Table Construction

Initial state: All cells initialized to negative infinity, except we'll compute as we go.

```
nums1 →  [2,  1, -2,  5]
nums2 ↓  [3,  0, -6]

DP Table (dp[i][j]):
       0    1(2)   2(1)   3(-2)  4(5)
   0  -∞    -∞     -∞      -∞     -∞
1(3)  -∞     6      6       6     15
2(0)  -∞     6      6       6     15
3(-6) -∞    12     12      18     18
```

### Key Computations:

**Position (1,1) - nums1[0]=2, nums2[0]=3:**

- product = 2 × 3 = 6
- takeWithPrevious = max(0, -∞) + 6 = 6
- skipFirst = -∞, skipSecond = -∞
- dp[1][1] = 6

**Position (3,3) - nums1[2]=-2, nums2[2]=-6:**

- product = (-2) × (-6) = 12
- takeWithPrevious = max(0, dp[2][2]) + 12 = max(0, 6) + 12 = 18
- skipFirst = dp[2][3] = 6
- skipSecond = dp[3][2] = 12
- dp[3][3] = max(18, 6, 12) = **18** ✓

**The subsequence chosen**: `[2, -2]` from nums1 and `[3, -6]` from nums2

**Result**: 2×3 + (-2)×(-6) = 6 + 12 = 18

## Visual Representation of DP State Transitions

```
For each cell dp[i][j], we consider:

    dp[i-1][j-1] ----→ dp[i][j]
         |                ↑
         |                |
    dp[i-1][j] --------→ dp[i][j]
         |                ↑
         |                |
    dp[i][j-1] --------→ dp[i][j]

Choice 1: Include both elements (diagonal)
Choice 2: Skip nums1[i] (from top)
Choice 3: Skip nums2[j] (from left)
```

## Why `max(0, dp[i-1][j-1])`?

This is a crucial detail! We take `max(0, dp[i-1][j-1])` because:

1. If previous dot product is **negative**, we might be better off **starting fresh** with just the current pair
2. If previous dot product is **positive**, we add it to continue building the subsequence
3. This ensures we always have at least **one pair** in our subsequence (never empty)

**Example**: If `dp[i-1][j-1] = -5` and current product is `3`:

- Without max(0, ...): -5 + 3 = -2 (worse)
- With max(0, ...): 0 + 3 = 3 (better, start fresh!)

## Edge Cases

1. **All negative products**: Return the least negative single product
   - Example: `nums1 = [-1, -1]`, `nums2 = [1, 1]` → Output: -1

2. **Large positive and negative numbers mixed**: Algorithm handles naturally
   - It will skip negative products and accumulate positive ones

3. **Single element arrays**: Returns the product of those two elements

## Key Takeaways

1. **DP State Definition**: `dp[i][j]` = maximum dot product using first `i` and `j` elements

2. **Transition Formula**: `dp[i][j] = max(max(0, dp[i-1][j-1]) + product, dp[i-1][j], dp[i][j-1])`

3. **Starting Fresh**: The `max(0, ...)` allows us to restart subsequence if previous sum is negative

4. **Three Choices Pattern**: Common in 2D DP - include both, skip first, skip second

5. **Guaranteed Non-empty**: By taking max with current product, we ensure at least one pair is selected

## Practice Tips

- Draw the DP table for small examples to understand state transitions
- Think about what each cell represents: "best result so far"
- Remember: we can always "start fresh" if previous results hurt us
- This pattern (2D DP with three choices) appears in many subsequence problems

## Similar Problems

- LeetCode 72: Edit Distance
- LeetCode 1143: Longest Common Subsequence
- LeetCode 97: Interleaving String
- LeetCode 1035: Uncrossed Lines

## Complexity Summary

| Approach | Time Complexity | Space Complexity |
|----------|----------------|------------------|
| Brute Force (Recursive) | O(3^(m+n)) | O(m + n) |
| Dynamic Programming | O(m × n) | O(m × n) |

---

**Happy Coding! 🚀**
