# LeetCode 42: Trapping Rain Water

## Problem Information
- **Problem Number:** 42
- **Difficulty:** Hard
- **Problem Link:** [https://leetcode.com/problems/trapping-rain-water/](https://leetcode.com/problems/trapping-rain-water/)

## Problem Statement
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

### Examples

**Example 1:**
```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The elevation map traps 6 units of rain water.
```

**Example 2:**
```
Input: height = [4,2,0,3,2,5]
Output: 9
```

## Visual Representation

![Trapping Rain Water Visualization](https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png)

The blue sections represent trapped water between the elevation bars.

---

## Core Intuition & Key Insight

### The Fundamental Principle
Water can only be trapped at a position if there are **taller bars on both its left and right sides**. The amount of water trapped at any index depends on:

**Water at index i = min(leftMax, rightMax) - height[i]**

Where:
- `leftMax` = Maximum height to the left of index i
- `rightMax` = Maximum height to the right of index i
- `height[i]` = Current bar height

### Why This Works
Think of it like a container: water fills up to the level of the shorter wall (the bottleneck). If the current bar is taller than the shorter wall, no water can be trapped.

---

## Solution 1: Brute Force Approach

### Algorithm
For each position in the array:
1. Find the maximum height to its left
2. Find the maximum height to its right
3. Calculate water = min(leftMax, rightMax) - height[i]
4. Add to total water

### Code (Java)
```java
public class TrapWaterBruteForce {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int n = height.length;
        int totalWater = 0;
        
        // For each element, find leftMax and rightMax
        for (int i = 0; i < n; i++) {
            // Find maximum height to the left
            int leftMax = 0;
            for (int j = i; j >= 0; j--) {
                leftMax = Math.max(leftMax, height[j]);
            }
            
            // Find maximum height to the right
            int rightMax = 0;
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            
            // Calculate water at current position
            totalWater += Math.min(leftMax, rightMax) - height[i];
        }
        
        return totalWater;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n²) - For each element, we scan left and right
- **Space Complexity:** O(1) - No extra space used

### Drawback
This approach is inefficient for large arrays due to repeated calculations.

---

## Solution 2: Dynamic Programming (Precomputation)

### Algorithm
Instead of recalculating leftMax and rightMax for every position, we can precompute them:

1. Create `leftMax[]` array - stores max height to the left of each index
2. Create `rightMax[]` array - stores max height to the right of each index
3. Traverse once to calculate water at each position

### Code (Java)
```java
public class TrapWaterDP {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        // Build leftMax array
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Build rightMax array
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Calculate total water
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return totalWater;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n) - Three separate passes through the array
- **Space Complexity:** O(n) - Two auxiliary arrays

---

## Solution 3: Two Pointers (Optimal)

### The Key Insight
We don't need to know the exact leftMax and rightMax for every position. We only need to know which side has the smaller maximum, because that's the limiting factor (bottleneck).

### Algorithm Flow
1. Use two pointers: `left` at start, `right` at end
2. Track `leftMax` and `rightMax` as we move
3. Move the pointer with the smaller max height
4. If current height < max on that side, add trapped water

### Why This Works
- If `height[left] < height[right]`, we know that whatever is to the right of `left` won't matter because there's already a taller bar at `right`
- We can safely calculate water at `left` position using `leftMax`
- Same logic applies when moving `right` pointer

### Code (Java) - Your Solution
```java
public class Trapwater {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Left side is the bottleneck
                if (height[left] >= leftMax) {
                    leftMax = height[left];  // Update max
                } else {
                    totalWater += leftMax - height[left];  // Add water
                }
                left++;
            } else {
                // Right side is the bottleneck
                if (height[right] >= rightMax) {
                    rightMax = height[right];  // Update max
                } else {
                    totalWater += rightMax - height[right];  // Add water
                }
                right--;
            }
        }

        return totalWater;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n) - Single pass through array
- **Space Complexity:** O(1) - Only using constant extra space

---

## Step-by-Step Example

Let's trace through `height = [0,1,0,2,1,0,1,3,2,1,2,1]` using the two-pointer approach:

```
Initial State:
height:  [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
          ↑                                ↑
        left                            right
leftMax = 0, rightMax = 0, water = 0
```

**Iteration 1:**
- `height[left]=0 < height[right]=1` → process left
- `height[left]=0 >= leftMax=0` → update leftMax=0
- Move left to 1

**Iteration 2:**
- `height[left]=1 < height[right]=1` → process left
- `height[left]=1 >= leftMax=0` → update leftMax=1
- Move left to 2

**Iteration 3:**
- `height[left]=0 < height[right]=1` → process left
- `height[left]=0 < leftMax=1` → water += 1-0 = 1
- Total water = 1

**Continuing this process...**

At position where `height=0` and leftMax=2, rightMax=3:
- Water trapped = min(2, 3) - 0 = 2 units

**Final Result:** 6 units of water

---

## Thinking Framework

### When to Use Each Approach

1. **Brute Force:** 
   - Understanding the problem
   - Small input sizes
   - Interview discussion starter

2. **Dynamic Programming:**
   - When space isn't a constraint
   - Clear and easy to understand
   - Good for explaining the concept

3. **Two Pointers:**
   - Production code
   - Space-constrained environments
   - Optimal solution for interviews

### Common Mistakes to Avoid
- Forgetting edge cases (empty array, array length < 3)
- Not handling the boundaries correctly
- Confusing when to update max vs when to add water
- Off-by-one errors in pointer movement

---

## Related Problems
- **Container With Most Water (LeetCode 11)** - Similar two-pointer technique
- **Trapping Rain Water II (LeetCode 407)** - 2D version
- **Product of Array Except Self** - Similar precomputation pattern

---

## Key Takeaways
1. **Water trapping depends on boundaries** - Need taller bars on both sides
2. **Two-pointer technique excels** when processing from both ends
3. **Space-time tradeoff** - Can trade O(n) space for clearer code
4. **Bottleneck principle** - Shorter wall determines water level
