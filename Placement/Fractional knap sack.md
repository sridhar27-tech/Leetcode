# Fractional Knapsack Problem

## Problem Overview

**Note:** This is not a LeetCode problem but a classic greedy algorithm problem. It's commonly found in algorithm courses and competitive programming platforms.

**Problem Statement:**
Given weights and values of n items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack. You can break items (take fractions of items) to maximize the total value.

## Problem Link
- [GeeksforGeeks - Fractional Knapsack](https://www.geeksforgeeks.org/fractional-knapsack-problem/)
- [Practice Problem](https://practice.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1)

---

## Core Idea & Intuition

### 💡 Key Insight
The fractional knapsack problem differs from the 0/1 knapsack in that we can take **fractions** of items. This makes it solvable using a **greedy approach** rather than dynamic programming.

### Intuition
Imagine you're shopping with limited money. To maximize value:
1. **Calculate value per unit weight** (value/weight ratio) for each item
2. **Prioritize items with highest value per unit weight**
3. **Take as much as possible** of the best items first
4. If you can't fit a whole item, take a fraction of it

This greedy choice leads to the optimal solution because taking the most valuable items per unit weight at each step maximizes overall value.

---

## Visual Example

Consider items with the following properties:

```
Item 1: Value = 60,  Weight = 10  → Ratio = 6.0
Item 2: Value = 100, Weight = 20  → Ratio = 5.0
Item 3: Value = 120, Weight = 30  → Ratio = 4.0

Knapsack Capacity = 50
```

### Step-by-Step Visualization

```
Initial State:
Capacity remaining: 50
Total value: 0

Step 1: Take Item 1 (ratio = 6.0)
├─ Weight = 10 (fits completely)
├─ Capacity remaining: 50 - 10 = 40
└─ Total value: 0 + 60 = 60

Step 2: Take Item 2 (ratio = 5.0)
├─ Weight = 20 (fits completely)
├─ Capacity remaining: 40 - 20 = 20
└─ Total value: 60 + 100 = 160

Step 3: Take Item 3 (ratio = 4.0) partially
├─ Weight = 30 (only 20 capacity left)
├─ Fraction taken: 20/30 = 2/3
├─ Value added: 120 × (20/30) = 80
├─ Capacity remaining: 20 - 20 = 0
└─ Total value: 160 + 80 = 240

Final Answer: 240
```

---

## Solutions

### Approach 1: Brute Force (Not Practical)

**Time Complexity:** O(2^n) - Exponential  
**Space Complexity:** O(n) - Recursion stack

**Concept:**
Generate all possible combinations of item fractions and find the maximum value. This involves:
- Trying all possible fractions (0 to 1) for each item
- Recursively exploring all combinations
- Keeping track of remaining capacity

**Why Not Practical:**
- Infinite possibilities for fraction values
- Extremely slow for even small inputs
- No need for this approach when greedy works optimally

```java
// Brute Force (Conceptual - Not Implemented)
// Would require trying all fraction combinations
// Time Complexity: O(2^n) or worse
// Not practical for this problem
```

---

### Approach 2: Greedy Algorithm (Optimal Solution)

**Time Complexity:** O(n log n) - Due to sorting  
**Space Complexity:** O(1) - No extra space (excluding input)

**Algorithm Flow:**

```
1. Calculate value-to-weight ratio for each item
2. Sort items in descending order of ratio
3. Initialize total value = 0
4. For each item in sorted order:
   a. If item fits completely:
      - Add entire item
      - Reduce remaining capacity
   b. If item doesn't fit completely:
      - Add fraction of item
      - Fill remaining capacity
      - Break (knapsack full)
5. Return total value
```

**Java Implementation:**

```java
public class FractionalKnapsack {
    
    static class Item {
        int value, weight;
        
        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
    
    static class ItemComparator implements java.util.Comparator<Item> {
        public int compare(Item a, Item b) {
            double r1 = (double) a.value / a.weight;
            double r2 = (double) b.value / b.weight;
            if (r1 < r2) return 1;
            else if (r1 > r2) return -1;
            else return 0;
        }
    }
    
    public static double getMaxValue(int capacity, Item[] items) {
        // Step 1: Sort items by value/weight ratio (descending)
        java.util.Arrays.sort(items, new ItemComparator());
        
        double totalValue = 0.0;
        
        // Step 2: Iterate through sorted items
        for (Item item : items) {
            if (capacity <= 0) break;
            
            // Step 3a: If item fits completely
            if (item.weight <= capacity) {
                capacity -= item.weight;
                totalValue += item.value;
            } 
            // Step 3b: If item doesn't fit, take fraction
            else {
                totalValue += item.value * ((double) capacity / item.weight);
                capacity = 0;
            }
        }
        
        return totalValue;
    }
    
    public static void main(String[] args) {
        Item[] items = {
            new Item(60, 10),
            new Item(100, 20),
            new Item(120, 30)
        };
        int capacity = 50;
        
        double maxValue = getMaxValue(capacity, items);
        System.out.println("Maximum value in Knapsack = " + maxValue);
        // Output: Maximum value in Knapsack = 240.0
    }
}
```

---

## Detailed Algorithm Walkthrough

### Example Trace

**Input:**
- Items: [(60, 10), (100, 20), (120, 30)]
- Capacity: 50

**Execution:**

| Step | Item | Ratio | Weight | Capacity Before | Action | Value Added | Capacity After | Total Value |
|------|------|-------|--------|-----------------|--------|-------------|----------------|-------------|
| 1 | Item 1 | 6.0 | 10 | 50 | Take all | 60 | 40 | 60 |
| 2 | Item 2 | 5.0 | 20 | 40 | Take all | 100 | 20 | 160 |
| 3 | Item 3 | 4.0 | 30 | 20 | Take 2/3 | 80 | 0 | 240 |

**Calculation for Step 3:**
- Remaining capacity: 20
- Item weight: 30
- Fraction: 20/30 = 0.667
- Value: 120 × 0.667 = 80

---

## Why Greedy Works Here

### Proof of Optimality

The greedy approach works for fractional knapsack because:

1. **Optimal Substructure:** Taking the item with the highest value/weight ratio first is always optimal
2. **Greedy Choice Property:** Local optimal choices lead to global optimum
3. **Fractional Nature:** We can always fill the knapsack completely by taking fractions

**Contrast with 0/1 Knapsack:**
- 0/1 Knapsack requires DP because we cannot take fractions
- Greedy fails for 0/1 because we might need to skip high-ratio items to fit better combinations

---

## Key Concepts

### 1. Value-to-Weight Ratio
```
Ratio = Value / Weight

Higher ratio = More valuable per unit weight
```

### 2. Greedy Strategy
- Make locally optimal choice at each step
- Assumption: Local optimum → Global optimum (true for this problem)

### 3. Sorting Importance
- Sorting ensures we process items in optimal order
- O(n log n) sorting is bottleneck for time complexity

---

## Complexity Analysis

### Time Complexity
- **Sorting:** O(n log n)
- **Iteration:** O(n)
- **Total:** O(n log n)

### Space Complexity
- **Additional Space:** O(1) (if sorting in-place)
- **Input Storage:** O(n)

---

## Common Mistakes & Edge Cases

### Edge Cases to Consider
1. **Empty knapsack** (capacity = 0)
2. **No items** (empty array)
3. **All items heavier than capacity**
4. **Capacity larger than total weight**
5. **Items with same ratio**

### Common Mistakes
❌ Forgetting to sort by ratio  
❌ Using integer division instead of double  
❌ Not handling the fractional case properly  
❌ Continuing iteration after capacity is full  

---

## Related Problems

1. **0/1 Knapsack** - Dynamic Programming approach
2. **Unbounded Knapsack** - Can take unlimited quantities
3. **Coin Change Problem** - Similar greedy approach
4. **Activity Selection** - Another greedy problem

---

## Practice Tips

1. **Understand the difference** between fractional and 0/1 knapsack
2. **Master sorting** with custom comparators
3. **Practice calculating ratios** and handling fractions
4. **Visualize the greedy choice** at each step
5. **Consider edge cases** in your solution

---

## Summary

| Aspect | Details |
|--------|---------|
| **Approach** | Greedy Algorithm |
| **Time** | O(n log n) |
| **Space** | O(1) |
| **Key Idea** | Sort by value/weight ratio, take highest first |
| **Works Because** | Can take fractions, greedy choice is optimal |

The fractional knapsack problem is a classic example of how greedy algorithms can provide optimal solutions when the problem has the right properties!
