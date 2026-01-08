# LeetCode 875: Koko Eating Bananas

## Problem Information
- **LeetCode Number:** 875
- **Difficulty:** Medium
- **Problem Link:** [https://leetcode.com/problems/koko-eating-bananas/](https://leetcode.com/problems/koko-eating-bananas/)
- **Topics:** Binary Search, Array

---

## Problem Statement

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return. Return the minimum integer k such that she can eat all the bananas within h hours.

You are given:
- An integer array `piles` where `piles[i]` is the number of bananas in the `i-th` pile
- An integer `h` representing the number of hours available

**Rules:**
1. Each hour, Koko chooses a pile and eats `k` bananas from it
2. If a pile has fewer than `k` bananas, she eats the entire pile but **cannot** eat from another pile in the same hour
3. Find the minimum eating speed `k` to finish all bananas within `h` hours

---

## Examples

### Example 1
```
Input: piles = [3, 6, 7, 11], h = 8
Output: 4

Explanation:
At speed k = 4:
- Pile 1 (3 bananas): 1 hour (3/4 = 1 hour)
- Pile 2 (6 bananas): 2 hours (6/4 = 2 hours)
- Pile 3 (7 bananas): 2 hours (7/4 = 2 hours)
- Pile 4 (11 bananas): 3 hours (11/4 = 3 hours)
Total: 1 + 2 + 2 + 3 = 8 hours ✓

At speed k = 3:
Total would be 10 hours (exceeds h = 8) ✗
```

### Example 2
```
Input: piles = [30, 11, 23, 4, 20], h = 5
Output: 30

Explanation:
Since h = 5 equals the number of piles, Koko must finish each pile in 1 hour.
Therefore, k must be at least 30 (the largest pile).
```

### Example 3
```
Input: piles = [1, 4, 3, 2], h = 9
Output: 2

Explanation:
At speed k = 2, total time is 6 hours (well within h = 9).
At speed k = 1, total time is 10 hours (exceeds h = 9).
```

---

## Constraints
- `1 <= piles.length <= 10^4`
- `piles.length <= h`
- `1 <= piles[i] <= 10^9`

---

## Core Intuition

This is a **"Binary Search on Answer"** problem - a powerful pattern where we binary search on the solution space rather than an array!

### Key Observations

1. **Minimum Speed:** At least 1 banana per hour (k = 1)
2. **Maximum Speed:** No more than max(piles) per hour (eating the largest pile in 1 hour)
3. **Monotonic Property:** If speed `k` works, any speed > `k` also works
4. **Search Space:** We're searching for the minimum `k` in range `[1, max(piles)]`

### The Brilliant Insight

Instead of linearly checking k = 1, 2, 3, ..., max(piles), we use **binary search** because:
- The answer space is sorted (1 to max(piles))
- We have a validation function: can we finish with speed k?
- We want to find the **minimum k** that satisfies the condition

---

## Approach 1: Brute Force (Linear Search)

### Algorithm
Try every possible speed from 1 to max(piles) and return the first speed that works.

### Implementation
```java
public class KokoEatingBananasBruteForce {
    public int minEatingSpeed(int[] piles, int h) {
        int maxPile = getMax(piles);
        
        // Try each speed from 1 to maxPile
        for (int speed = 1; speed <= maxPile; speed++) {
            if (canFinish(piles, h, speed)) {
                return speed;
            }
        }
        
        return maxPile;
    }
    
    private boolean canFinish(int[] piles, int h, int speed) {
        int hoursNeeded = 0;
        for (int pile : piles) {
            hoursNeeded += (pile + speed - 1) / speed; // Ceiling division
            if (hoursNeeded > h) return false; // Early exit
        }
        return hoursNeeded <= h;
    }
    
    private int getMax(int[] piles) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        return max;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n × m) where n = number of piles, m = max(piles)
  - We try m different speeds
  - For each speed, we check n piles
- **Space Complexity:** O(1)

### Why This Fails
For large inputs (max pile = 10^9), this would require billions of iterations!

---

## Approach 2: Optimized Solution (Binary Search on Answer)

### Algorithm
Binary search on the speed range [1, max(piles)]:
1. Calculate mid speed
2. Check if this speed can finish all piles within h hours
3. If yes: try slower speed (search left half)
4. If no: need faster speed (search right half)

### Implementation
```java
public class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = getMax(piles);
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canFinish(piles, h, mid)) {
                // mid works, try slower speed
                right = mid;
            } else {
                // mid too slow, need faster speed
                left = mid + 1;
            }
        }
        
        return left;
    }

    public boolean canFinish(int[] piles, int h, int speed) {
        int hoursNeeded = 0;
        for (int pile : piles) {
            // Ceiling division: (pile + speed - 1) / speed
            hoursNeeded += (pile + speed - 1) / speed;
        }
        return hoursNeeded <= h;
    }

    public int getMax(int[] piles) {
        int max = 0;
        for (int pile : piles) {
            if (pile > max) {
                max = pile;
            }
        }
        return max;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n log m) where n = number of piles, m = max(piles)
  - Binary search: O(log m) iterations
  - Each iteration checks all n piles: O(n)
- **Space Complexity:** O(1)

---

## Algorithm Flow with Example

Let's trace through: `piles = [3, 6, 7, 11], h = 8`

**Setup:**
- left = 1
- right = 11 (max pile)
- Goal: Find minimum speed k

### Binary Search Iterations

**Iteration 1:**
```
left = 1, right = 11
mid = 1 + (11 - 1) / 2 = 6

Check speed = 6:
- Pile 3: ⌈3/6⌉ = 1 hour
- Pile 6: ⌈6/6⌉ = 1 hour
- Pile 7: ⌈7/6⌉ = 2 hours
- Pile 11: ⌈11/6⌉ = 2 hours
Total: 6 hours ≤ 8 ✓

Since 6 works, try slower: right = 6
```

**Iteration 2:**
```
left = 1, right = 6
mid = 1 + (6 - 1) / 2 = 3

Check speed = 3:
- Pile 3: ⌈3/3⌉ = 1 hour
- Pile 6: ⌈6/3⌉ = 2 hours
- Pile 7: ⌈7/3⌉ = 3 hours
- Pile 11: ⌈11/3⌉ = 4 hours
Total: 10 hours > 8 ✗

Too slow, need faster: left = 4
```

**Iteration 3:**
```
left = 4, right = 6
mid = 4 + (6 - 4) / 2 = 5

Check speed = 5:
- Pile 3: ⌈3/5⌉ = 1 hour
- Pile 6: ⌈6/5⌉ = 2 hours
- Pile 7: ⌈7/5⌉ = 2 hours
- Pile 11: ⌈11/5⌉ = 3 hours
Total: 8 hours ≤ 8 ✓

Since 5 works, try slower: right = 5
```

**Iteration 4:**
```
left = 4, right = 5
mid = 4 + (5 - 4) / 2 = 4

Check speed = 4:
- Pile 3: ⌈3/4⌉ = 1 hour
- Pile 6: ⌈6/4⌉ = 2 hours
- Pile 7: ⌈7/4⌉ = 2 hours
- Pile 11: ⌈11/4⌉ = 3 hours
Total: 8 hours ≤ 8 ✓

Since 4 works, try slower: right = 4
```

**Termination:**
```
left = 4, right = 4
left < right is false → Exit

Return left = 4 ✓
```

---

## Understanding Ceiling Division

### The Problem
`7 / 4 = 1.75` in math, but we need 2 hours (can't use partial hours)

### Standard Approach (Uses Math.ceil)
```java
int hours = (int) Math.ceil((double) pile / speed);
```

### Optimized Approach (Integer Math Only)
```java
int hours = (pile + speed - 1) / speed;
```

### Why This Works

**Mathematical Proof:**
- For any a, b: `⌈a/b⌉ = (a + b - 1) / b` (integer division)

**Examples:**
```
7 / 4:
  Math.ceil(7.0 / 4) = Math.ceil(1.75) = 2
  (7 + 4 - 1) / 4 = 10 / 4 = 2 ✓

8 / 4:
  Math.ceil(8.0 / 4) = Math.ceil(2.0) = 2
  (8 + 4 - 1) / 4 = 11 / 4 = 2 ✓

3 / 4:
  Math.ceil(3.0 / 4) = Math.ceil(0.75) = 1
  (3 + 4 - 1) / 4 = 6 / 4 = 1 ✓
```

**Benefits:**
- No floating-point operations (faster)
- No type casting needed
- Avoids floating-point precision errors

---

## Visual Representation

### Binary Search Process

```
Search Space: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

Iteration 1: mid = 6 ✓ works → search [1, 6]
[1, 2, 3, 4, 5, 6] 7, 8, 9, 10, 11
               ↑

Iteration 2: mid = 3 ✗ fails → search [4, 6]
1, 2, 3 [4, 5, 6]
       ↑

Iteration 3: mid = 5 ✓ works → search [4, 5]
[4, 5] 6
     ↑

Iteration 4: mid = 4 ✓ works → search [4, 4]
[4] 5
 ↑

Answer: 4 (minimum speed that works)
```

### Eating Schedule at k = 4

```
Hour 1: Pile 0 [3 bananas] → eat all 3 ✓
Hour 2: Pile 1 [6 bananas] → eat 4
Hour 3: Pile 1 [2 left]    → eat 2 ✓
Hour 4: Pile 2 [7 bananas] → eat 4
Hour 5: Pile 2 [3 left]    → eat 3 ✓
Hour 6: Pile 3 [11 bananas]→ eat 4
Hour 7: Pile 3 [7 left]    → eat 4
Hour 8: Pile 3 [3 left]    → eat 3 ✓

Total: 8 hours (exactly h) ✓
```

---

## Key Concepts & Thinking Process

### 1. Binary Search on Answer Pattern

**When to Use:**
- You're looking for a minimum/maximum value
- The solution space is sorted/monotonic
- You can write a validation function
- Brute force would be O(n × m) or worse

**Recognition Keywords:**
- "Find minimum/maximum..."
- "At least / at most..."
- "Given constraint, find threshold..."

### 2. Why left < right (not left <= right)?

```java
while (left < right) {  // Not left <= right
    int mid = left + (right - left) / 2;
    if (canFinish(piles, h, mid)) {
        right = mid;     // Not mid - 1
    } else {
        left = mid + 1;
    }
}
return left;  // Or return right (they're equal)
```

**Reason:**
- We're finding the **minimum valid value**
- When `canFinish(mid)` is true, mid might be the answer, so `right = mid` (not mid - 1)
- This converges to the smallest valid speed
- When left == right, we've found our answer

### 3. Alternative Binary Search Template

You could also use `left <= right` with different logic:

```java
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (canFinish(piles, h, mid)) {
        answer = mid;      // Store potential answer
        right = mid - 1;   // Try smaller
    } else {
        left = mid + 1;
    }
}
return answer;
```

Both work! Choose one and be consistent.

### 4. Edge Case Handling

**When h == piles.length:**
```java
piles = [10, 20, 30], h = 3
Answer: 30 (must eat largest pile in 1 hour)
```

**When h >> piles.length:**
```java
piles = [5, 10], h = 100
Answer: 1 (can eat 1 banana per hour leisurely)
```

---

## Common Pitfalls

1. ❌ **Wrong ceiling division:**
   ```java
   // Wrong: loses precision
   int hours = pile / speed;
   
   // Correct:
   int hours = (pile + speed - 1) / speed;
   ```

2. ❌ **Integer overflow in ceiling division:**
   ```java
   // Potential overflow if pile and speed are large
   hoursNeeded += (pile + speed - 1) / speed;
   
   // Safer:
   hoursNeeded += pile / speed + (pile % speed == 0 ? 0 : 1);
   ```

3. ❌ **Not understanding monotonicity:**
   - If speed k doesn't work, speeds 1...k-1 won't work either
   - If speed k works, speeds k+1...max also work

4. ❌ **Off-by-one errors in binary search:**
   - Using `left <= right` with `right = mid` causes infinite loop
   - Using `left < right` with `right = mid - 1` might skip the answer

5. ❌ **Not considering h < piles.length:**
   - Problem guarantees `piles.length <= h`, so always solvable

---

## Interview Tips

### How to Approach

**Step 1: Clarify**
- "Can h be less than number of piles?" (No, per constraints)
- "Can piles have 0 bananas?" (No, minimum is 1)

**Step 2: Brute Force**
- "We could try every speed from 1 to max(piles)" - O(n × m)
- "But for large max values, this is too slow"

**Step 3: Optimize**
- "The key insight: if speed k works, all speeds > k work too"
- "This monotonic property allows binary search"
- "Binary search reduces O(m) to O(log m)"

**Step 4: Code**
- Start with binary search template
- Implement the validation function
- Test with examples

### Follow-up Questions

**Q: What if we want the maximum speed she could use?**
A: Just return the first speed that works from left to right (no binary search needed, or modify to find maximum instead of minimum).

**Q: What if she can skip some piles?**
A: Different problem - would need dynamic programming or greedy approach.

**Q: How would you handle floating-point speeds?**
A: Use `double` for speed and Math.ceil for time calculation, binary search on range [0.001, max(piles)].

---

## Related Problems

- **LeetCode 1011:** Capacity To Ship Packages Within D Days (Identical pattern)
- **LeetCode 410:** Split Array Largest Sum (Similar binary search on answer)
- **LeetCode 1482:** Minimum Number of Days to Make m Bouquets
- **LeetCode 774:** Minimize Max Distance to Gas Station
- **LeetCode 1283:** Find the Smallest Divisor Given a Threshold

---

## Summary

**Koko Eating Bananas teaches the powerful "Binary Search on Answer" pattern:**

🔑 **Key Takeaways:**
1. Not all binary search problems involve searching an array
2. If the solution space is monotonic, binary search can optimize
3. The validation function is critical - it determines search direction
4. Ceiling division without floats: `(a + b - 1) / b`

**Problem-Solving Template:**
```
1. Identify the search range [min_answer, max_answer]
2. Write canAchieve(target) validation function
3. Binary search:
   - If target works → try smaller (search left)
   - If target fails → try larger (search right)
4. Return the minimum valid target
```

**Remember:** When you see "find minimum/maximum value that satisfies...", think **Binary Search on Answer**! This pattern appears in many coding interviews and transforms O(n × m) solutions into O(n log m).
