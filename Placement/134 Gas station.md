# LeetCode 134: Gas Station

## Problem Information
- **LeetCode Number:** 134
- **Difficulty:** Medium
- **Problem Link:** [https://leetcode.com/problems/gas-station/](https://leetcode.com/problems/gas-station/)
- **Topics:** Array, Greedy

---

## Problem Statement

There are `n` gas stations along a circular route, where the amount of gas at the `i-th` station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `i-th` station to its next `(i + 1)-th` station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return `-1`. If there exists a solution, it is guaranteed to be unique.

---

## Examples

### Example 1
```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3

Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas.
Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back.
```

### Example 2
```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1

Explanation:
You can't start at station 0 or 1, as there is not enough gas.
Starting at station 2:
Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 units but you only have 3.
Therefore, you can't complete the circuit.
```

---

## Constraints
- `n == gas.length == cost.length`
- `1 <= n <= 10^5`
- `0 <= gas[i], cost[i] <= 10^4`

---

## Core Intuition

This is a **greedy problem** with a brilliant insight:

### Key Observations:

1. **Feasibility Check:** If `total_gas >= total_cost`, a solution exists. If `total_gas < total_cost`, impossible to complete the circuit.

2. **Greedy Choice:** If you can't reach station `j` from station `i`, then no station between `i` and `j` can reach `j` either. Why? Because starting from any intermediate station gives you even less gas than starting from `i`.

3. **Starting Point:** If station `i` fails to reach station `j`, the next potential starting point is `j+1`.

4. **Single Pass:** We only need one traversal to find the answer.

---

## Approach 1: Brute Force (Try Every Starting Point)

### Algorithm
Try starting from each gas station and simulate the entire journey to see if we can complete the circuit.

### Implementation
```java
public class GasStationBruteForce {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        
        // Try each station as a starting point
        for (int start = 0; start < n; start++) {
            int tank = 0;
            int stationsVisited = 0;
            
            // Try to complete the circuit from this start
            for (int i = start; stationsVisited < n; i = (i + 1) % n) {
                tank += gas[i] - cost[i];
                
                // If we run out of gas, this start doesn't work
                if (tank < 0) {
                    break;
                }
                stationsVisited++;
            }
            
            // If we completed the circuit, return this start
            if (stationsVisited == n && tank >= 0) {
                return start;
            }
        }
        
        return -1;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n²) - For each of n stations, we potentially check all n stations
- **Space Complexity:** O(1) - Only using a few variables

### Why This Works But Is Inefficient
- We're doing redundant work by checking stations we've already determined can't work
- Once we know station `i` can't reach station `j`, we don't need to check stations between `i` and `j`

---

## Approach 2: Optimized Solution (Single Pass Greedy)

### Algorithm
Use a greedy approach with two key variables:
1. `total_tank`: Tracks if the solution is possible (sum of all `gas[i] - cost[i]`)
2. `curr_tank`: Tracks current gas from the current starting point

If `curr_tank` goes negative at station `i`, we know:
- The current starting point can't work
- No station before `i` can work as a starting point
- The next candidate is `i + 1`

### Implementation
```java
public class Gasstation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        
        for (int i = 0; i < gas.length; i++) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            
            // If we can't reach the next station
            if (curr_tank < 0) {
                // Start from the next station
                starting_station = i + 1;
                // Reset current tank
                curr_tank = 0;
            }
        }
        
        // If total gas < total cost, impossible
        return total_tank >= 0 ? starting_station : -1;
    }
}
```

### Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the arrays
- **Space Complexity:** O(1) - Only using a few variables

---

## Algorithm Flow with Example

Let's trace through Example 1: `gas = [1,2,3,4,5], cost = [3,4,5,1,2]`

```
Station:     0    1    2    3    4
Gas:        [1    2    3    4    5]
Cost:       [3    4    5    1    2]
Net:        [-2  -2   -2   +3   +3]
```

### Step-by-Step Execution

**Initialization:**
- total_tank = 0
- curr_tank = 0
- starting_station = 0

**i = 0 (Station 0):**
- total_tank = 0 + (1-3) = -2
- curr_tank = 0 + (1-3) = -2
- curr_tank < 0 ✓
- **Reset:** starting_station = 1, curr_tank = 0

**i = 1 (Station 1):**
- total_tank = -2 + (2-4) = -4
- curr_tank = 0 + (2-4) = -2
- curr_tank < 0 ✓
- **Reset:** starting_station = 2, curr_tank = 0

**i = 2 (Station 2):**
- total_tank = -4 + (3-5) = -6
- curr_tank = 0 + (3-5) = -2
- curr_tank < 0 ✓
- **Reset:** starting_station = 3, curr_tank = 0

**i = 3 (Station 3):**
- total_tank = -6 + (4-1) = -3
- curr_tank = 0 + (4-1) = 3
- curr_tank >= 0 ✓ (keep going)

**i = 4 (Station 4):**
- total_tank = -3 + (5-2) = 0
- curr_tank = 3 + (5-2) = 6
- curr_tank >= 0 ✓ (keep going)

**Final Check:**
- total_tank = 0 >= 0 ✓
- **Return starting_station = 3**

### Visual Journey Starting from Station 3

```
Station 3: tank = 0 + 4 = 4
          ↓ (cost 1)
Station 4: tank = 4 - 1 + 5 = 8
          ↓ (cost 2)
Station 0: tank = 8 - 2 + 1 = 7
          ↓ (cost 3)
Station 1: tank = 7 - 3 + 2 = 6
          ↓ (cost 4)
Station 2: tank = 6 - 4 + 3 = 5
          ↓ (cost 5)
Station 3: tank = 5 - 5 = 0 ✓ Complete!
```

---

## Key Concepts & Thinking Process

### 1. Why the Greedy Approach Works

**Mathematical Proof:**
- If `total_gas >= total_cost`, a solution exists
- If station `A` can't reach station `B`, no station between `A` and `B` can reach `B`
  - Proof: Any station between `A` and `B` has less accumulated gas than `A` did when reaching that point
  - Therefore, they have even less chance of reaching `B`

### 2. The Critical Reset Logic

```java
if (curr_tank < 0) {
    starting_station = i + 1;  // Jump to next station
    curr_tank = 0;              // Fresh start
}
```

This works because:
- If we can't reach station `i+1` from our current start
- Then no station before `i+1` can be a valid start
- So we skip directly to `i+1` as the next candidate

### 3. Why We Don't Need to Wrap Around

Once we finish the loop:
- If `total_tank >= 0`, we know a solution exists
- The `starting_station` we found is guaranteed to work
- We don't need to simulate the full circuit again

**Why?** If starting from station `s` can reach the end, and `total_tank >= 0`, then starting from `s` can definitely complete the full circuit because the remaining portion (from 0 to s-1) has enough net gas to support the deficit.

### 4. Handling Edge Cases

- **All stations have same net:** Returns 0 (any starting point works)
- **Single station:** Returns 0 if gas[0] >= cost[0], else -1
- **Impossible case:** Returns -1 when total_tank < 0

---

## Common Pitfalls

1. ❌ **Simulating the full circuit for each start:** O(n²) instead of O(n)
2. ❌ **Not understanding why we can skip stations:** Missing the greedy insight
3. ❌ **Trying to wrap around in the loop:** Unnecessary complexity
4. ❌ **Forgetting the total_tank check:** Could return invalid starting point

---

## Visual Representation

### Circular Route Diagram

```
        Station 0
        gas:1, cost:3
            ↓
Station 4 ←   → Station 1
gas:5, cost:2   gas:2, cost:4
    ↑               ↓
    ←   Station 3   → Station 2
       gas:4, cost:1  gas:3, cost:5
```

### Net Gas at Each Station

```
Station:  0     1     2     3     4
Net:     -2    -2    -2    +3    +3
         ❌    ❌    ❌    ✓     ✓
         (fail)(fail)(fail)(start)(continue)
```

---

## Interview Tips

1. **Start with feasibility:** Explain that if `total_gas < total_cost`, it's impossible
2. **Explain the greedy insight:** If you can't reach station `j` from station `i`, no intermediate station can either
3. **Walk through the reset logic:** Show why we can skip stations
4. **Discuss time complexity:** Emphasize the O(n) single pass vs O(n²) brute force
5. **Mention the uniqueness guarantee:** The problem states the solution is unique if it exists

### Common Interview Follow-ups

**Q: Why don't we need to check from station 0 to starting_station after the loop?**
A: If `total_tank >= 0`, and we've accumulated `curr_tank` from starting_station to end, the remaining portion (0 to starting_station-1) must have enough gas to complete the deficit. Otherwise, total_tank would be negative.

**Q: What if there are multiple valid starting points?**
A: The problem guarantees the solution is unique if it exists. But even if multiple existed, our algorithm would find the first valid one.

**Q: Can we optimize space further?**
A: Already O(1) space - can't optimize further!

---

## Related Problems
- LeetCode 135: Candy (Similar greedy approach)
- LeetCode 455: Assign Cookies
- LeetCode 2202: Maximize the Topmost Element After K Moves
- LeetCode 871: Minimum Number of Refueling Stops (Similar concept, harder)

---

## Summary

**The Gas Station problem is a beautiful example of greedy algorithms that teaches:**
- How to eliminate redundant work with smart insights
- Why local decisions can lead to global optimal solutions
- The power of single-pass algorithms
- How mathematical proofs justify greedy choices

**The key breakthrough:** Recognizing that if station `A` can't reach station `B`, then no station between them can either. This insight transforms an O(n²) problem into O(n).

**Remember:** Greedy ≠ Always trying the closest/smallest option. Here, greedy means intelligently skipping options we know won't work, making the best decision at each step based on what we've learned.
