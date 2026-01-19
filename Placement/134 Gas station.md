# LeetCode 134: Gas Station

## Problem Information

- **LeetCode Number**: 134
- **Difficulty**: Medium
- **Problem Link**: [Gas Station](https://leetcode.com/problems/gas-station/)
- **Topics**: Array, Greedy

## Problem Statement

There are `n` gas stations along a circular route, where the amount of gas at the `ith` station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `ith` station to its next `(i + 1)th` station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return `-1`. 

**Note**: If there exists a solution, it is guaranteed to be unique.

## Examples

**Example 1:**
```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation: 
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
```

**Example 2:**
```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
```

## Constraints

- `n == gas.length == cost.length`
- `1 <= n <= 10^5`
- `0 <= gas[i], cost[i] <= 10^4`

## Core Idea & Intuition

### The Key Insight

This is a **greedy algorithm** problem with a brilliant insight:

1. **Total Gas Check**: If the total gas available is less than the total cost to travel, it's impossible to complete the circuit
2. **Greedy Choice**: If we run out of gas at station `i`, then **no station between the starting point and `i` can be a valid starting point**
3. **Why?** Because if we couldn't reach station `i` starting from station `s`, then starting from any station between `s` and `i` would have even less gas accumulated

### The Brilliant Observation

**Key Theorem**: If station A can't reach station B (first failure point), then:
- No station between A and B can reach B either
- Because they would have accumulated less or equal gas than starting from A
- So, the next potential starting point is `B + 1`

### Why This Works

If the sum of all `(gas[i] - cost[i])` is non-negative:
- There must exist a valid starting point
- By eliminating impossible starting points greedily, we'll find it in one pass
- The solution is guaranteed to be unique (given in problem)

## Solutions

### Approach 1: Brute Force (Try Every Starting Point)

**Concept**: Try starting from each gas station and simulate the entire journey.

```java
public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        
        // Try each station as a starting point
        for (int start = 0; start < n; start++) {
            int tank = 0;
            int stationsVisited = 0;
            int current = start;
            
            // Try to complete the circuit from this starting point
            while (stationsVisited < n) {
                tank += gas[current];      // Fill gas at current station
                tank -= cost[current];     // Travel to next station
                
                if (tank < 0) {
                    break;  // Can't proceed from this starting point
                }
                
                current = (current + 1) % n;  // Move to next station (circular)
                stationsVisited++;
            }
            
            // If we completed the circuit, return this starting point
            if (stationsVisited == n && tank >= 0) {
                return start;
            }
        }
        
        return -1;  // No valid starting point found
    }
}
```

**Time Complexity**: O(n²) - for each of n stations, we simulate n stations

**Space Complexity**: O(1) - only using a few variables

**Why it's inefficient**: We're doing a lot of redundant work. If starting from station 0 fails at station 5, we still try stations 1, 2, 3, and 4, even though we know they'll also fail.

### Approach 2: Greedy Algorithm (Optimized - One Pass)

**Concept**: Use the greedy observation - if we fail at station `i`, skip directly to `i+1` as the next candidate.

```java
public class GasStation {
    
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total_tank = 0;        // Total gas surplus/deficit for entire circuit
        int curr_tank = 0;         // Current tank level
        int starting_station = 0;  // Candidate starting station
        
        for (int i = 0; i < gas.length; i++) {
            // Calculate net gas at this station (gain - loss)
            int net_gas = gas[i] - cost[i];
            
            total_tank += net_gas;  // Accumulate total
            curr_tank += net_gas;   // Update current tank
            
            // If current tank goes negative, we can't start from starting_station
            if (curr_tank < 0) {
                // The next station after failure becomes new candidate
                starting_station = i + 1;
                curr_tank = 0;  // Reset current tank
            }
        }
        
        // If total gas >= 0, the circuit is possible from starting_station
        // Otherwise, it's impossible from any station
        return total_tank >= 0 ? starting_station : -1;
    }
}
```

**Time Complexity**: O(n) - single pass through the array

**Space Complexity**: O(1) - only using a few variables

**Why it's efficient**: We make a single pass and intelligently skip impossible starting points using the greedy observation.

## Algorithm Flow with Example

Let's trace through **Example 1**: `gas = [1,2,3,4,5]`, `cost = [3,4,5,1,2]`

### Step-by-Step Execution

```
Station:       0    1    2    3    4
gas:          [1    2    3    4    5]
cost:         [3    4    5    1    2]
net (gas-cost):[-2  -2   -2   3    3]
```

**Iteration by iteration:**

| i | gas[i] | cost[i] | net | total_tank | curr_tank | starting_station | Action |
|---|--------|---------|-----|------------|-----------|------------------|--------|
| 0 | 1 | 3 | -2 | -2 | -2 | 0 | curr_tank < 0, reset start to 1 |
| 1 | 2 | 4 | -2 | -4 | -2 | 1 | curr_tank < 0, reset start to 2 |
| 2 | 3 | 5 | -2 | -6 | -2 | 2 | curr_tank < 0, reset start to 3 |
| 3 | 4 | 1 | +3 | -3 | +3 | 3 | curr_tank positive, continue |
| 4 | 5 | 2 | +3 | 0 | +6 | 3 | curr_tank positive, continue |

**Final Check:**
- `total_tank = 0` (>= 0, so solution exists)
- `starting_station = 3`
- Return `3` ✓

### Why Station 3 Works?

Starting from station 3:
```
Station 3: tank = 0 + 4 - 1 = 3 (travel to station 4)
Station 4: tank = 3 + 5 - 2 = 6 (travel to station 0)
Station 0: tank = 6 + 1 - 3 = 4 (travel to station 1)
Station 1: tank = 4 + 2 - 4 = 2 (travel to station 2)
Station 2: tank = 2 + 3 - 5 = 0 (back to station 3) ✓
```

## Visual Representation

```
Circular Route Visualization:

        Station 0
       gas=1, cost=3
            ↓
Station 4 ←  ← → Station 1
gas=5, cost=2    gas=2, cost=4
     ↑              ↓
     ← Station 3 ← Station 2
    gas=4, cost=1  gas=3, cost=5

Net Gas Flow:
Station:  0    1    2    3    4
Net:     -2   -2   -2   +3   +3
         ❌   ❌   ❌   ✓    ✓

The algorithm discovers station 3 is the optimal start!
```

## Why the Greedy Algorithm Works - Proof

**Claim**: If we can't reach station `i` from station `s`, then no station between `s` and `i` can reach station `i`.

**Proof by Contradiction**:
- Suppose we start at station `s` and run out of gas at station `i`
- This means: `sum(gas[s...i-1]) - sum(cost[s...i-1]) < 0`
- Now suppose station `k` (where `s < k < i`) could reach station `i`
- But station `k` would have accumulated: `sum(gas[k...i-1]) - sum(cost[k...i-1])`
- This is a **subset** of what we accumulated starting from `s`
- Since the full accumulation from `s` to `i` was negative, any subset would be **less positive or more negative**
- Therefore, station `k` cannot reach station `i` either. **Contradiction!**

So the next candidate must be station `i+1`.

## Edge Cases

1. **All stations have negative net gas**: Return -1
   - Example: `gas = [1,1,1]`, `cost = [2,2,2]` → Output: -1

2. **Only one station**: 
   - Example: `gas = [5]`, `cost = [4]` → Output: 0

3. **Total gas equals total cost exactly**: Solution exists
   - Example: `gas = [1,2,3,4,5]`, `cost = [3,4,5,1,2]` → Output: 3

4. **Starting station is at the end**:
   - Example: `gas = [3,3,4]`, `cost = [3,4,4]` → Output: 2

## Key Takeaways

1. **Greedy Observation**: If A can't reach B, no station between A and B can reach B

2. **Single Pass Solution**: One iteration is enough due to the greedy property

3. **Total Gas Check**: If `sum(gas) < sum(cost)`, no solution exists

4. **Reset Strategy**: When current tank goes negative, reset and try from next station

5. **Circular Array Handling**: We don't need to actually loop circularly - if total_tank >= 0, the starting_station we found will work

## Common Mistakes to Avoid

❌ **Trying to simulate the full circle**: Unnecessary - total_tank check is sufficient

❌ **Not resetting curr_tank**: Must reset to 0 when we find a failing point

❌ **Overthinking the circular aspect**: The algorithm naturally handles it

✅ **Trust the math**: If total gas >= total cost, a solution exists from the found starting point

## Practice Tips

- Understand why the greedy approach works (proof by contradiction)
- Draw out the gas/cost differences to visualize
- Remember: this is a **one-pass greedy algorithm**
- The key insight is recognizing which starting points to skip

## Similar Problems

- LeetCode 45: Jump Game II
- LeetCode 55: Jump Game
- LeetCode 135: Candy
- LeetCode 621: Task Scheduler

## Complexity Summary

| Approach | Time Complexity | Space Complexity |
|----------|----------------|------------------|
| Brute Force (Try All) | O(n²) | O(1) |
| Greedy Algorithm | O(n) | O(1) |

## Visual Algorithm Flow

```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]

Pass through array once:
i=0: net=-2, curr_tank=-2 → Reset start to 1
i=1: net=-2, curr_tank=-2 → Reset start to 2  
i=2: net=-2, curr_tank=-2 → Reset start to 3
i=3: net=+3, curr_tank=+3 → Continue
i=4: net=+3, curr_tank=+6 → Continue

Check: total_tank = 0 >= 0 ✓
Return: starting_station = 3
```

---
