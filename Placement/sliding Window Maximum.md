# Sliding Window Maximum

## 🧩 Problem

Given an integer array `nums` and an integer `k`, there is a sliding window of size `k` moving from the left of the array to the right.
You can only see the `k` numbers inside the window at any time.

Return an array containing the **maximum value in each window**.

### Example

```
Input:  nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
```

---

## 🛠 Approaches

---

## 1️⃣ Brute Force Approach

### 💡 Idea

For every window of size `k`, scan all `k` elements and find the maximum.

### 🔁 Flow

For each index `i` from `0` → `n - k`:

```
window = nums[i ... i+k-1]
find max in this window
append max to result
```

### 🧮 Complexity

| Metric | Value      |
| ------ | ---------- |
| Time   | **O(n·k)** |
| Space  | **O(1)**   |

Works but **too slow for large inputs**.

---

## 💻 Brute Force Code

### Java

```java
import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            res[i] = max;
        }
        return res;
    }
}
```

### Python

```python
class Solution:
    def maxSlidingWindow(self, nums, k):
        n = len(nums)
        res = []

        for i in range(n - k + 1):
            res.append(max(nums[i:i+k]))

        return res
```

### JavaScript

```javascript
var maxSlidingWindow = function(nums, k) {
    let res = [];

    for (let i = 0; i <= nums.length - k; i++) {
        let maxVal = -Infinity;
        for (let j = i; j < i + k; j++) {
            maxVal = Math.max(maxVal, nums[j]);
        }
        res.push(maxVal);
    }
    return res;
};
```

---

## 2️⃣ Efficient Approach — Monotonic Deque (O(n))

### 💡 Key Idea

Use a **double-ended queue (Deque)** to store **indices of useful elements only** — in **decreasing order of their values**.

This ensures the **front always holds the index of the maximum in the current window**.

---

### 🔁 Flow (Step-by-Step Example)

Given:

```
nums = [1,3,-1,-3,5,3,6,7], k = 3
```

Deque stores **indices**, but think of them as values here:

| Step | Window    | Action                       | Deque (front → back) | Max |
| ---- | --------- | ---------------------------- | -------------------- | --- |
| 1    | [1,3,-1]  | push 1,3,-1 (remove smaller) | 3,-1                 | 3   |
| 2    | [3,-1,-3] | remove out-of-window         | 3,-1,-3              | 3   |
| 3    | [-1,-3,5] | pop smaller & add 5          | 5                    | 5   |
| 4    | [-3,5,3]  | add 3                        | 5,3                  | 5   |
| 5    | [5,3,6]   | pop smaller & add 6          | 6                    | 6   |
| 6    | [3,6,7]   | pop smaller & add 7          | 7                    | 7   |

---

### 🧮 Complexity

| Metric | Value                                              |
| ------ | -------------------------------------------------- |
| Time   | **O(n)** — each element enters & leaves deque once |
| Space  | **O(k)**                                           |

---

## 💻 Efficient Code

### Java

```java
import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> q = new ArrayDeque<>();
        int[] res = new int[n - k + 1];
        int ri = 0;

        for (int i = 0; i < n; i++) {

            // Remove indices out of window
            while (!q.isEmpty() && q.peek() < i - k + 1)
                q.poll();

            // Remove smaller values
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i])
                q.pollLast();

            q.offer(i);

            // Record max once first window is formed
            if (i >= k - 1)
                res[ri++] = nums[q.peek()];
        }
        return res;
    }
}
```

---

### Python

```python
from collections import deque

class Solution:
    def maxSlidingWindow(self, nums, k):
        q = deque()
        res = []

        for i in range(len(nums)):

            # remove out-of-window indices
            if q and q[0] < i - k + 1:
                q.popleft()

            # maintain decreasing order
            while q and nums[q[-1]] < nums[i]:
                q.pop()

            q.append(i)

            # append result
            if i >= k - 1:
                res.append(nums[q[0]])

        return res
```

---

### JavaScript

```javascript
var maxSlidingWindow = function(nums, k) {
    let q = [];
    let res = [];

    for (let i = 0; i < nums.length; i++) {

        // remove out-of-window indices
        if (q.length && q[0] < i - k + 1) q.shift();

        // remove smaller values
        while (q.length && nums[q[q.length - 1]] < nums[i])
            q.pop();

        q.push(i);

        // record max
        if (i >= k - 1)
            res.push(nums[q[0]]);
    }

    return res;
};
```

---

## ✅ Summary

| Approach    | Time     | Space    | Notes           |
| ----------- | -------- | -------- | --------------- |
| Brute Force | O(n·k)   | O(1)     | Simple but slow |
| Deque       | **O(n)** | **O(k)** | Best solution   |

---

## ⭐ Recommended Use

Use **deque approach** for interviews & production — it’s fast and elegant 🚀

---
