# Build an Array With Stack Operations

## Problem Intuition (Read This Slowly)

You are not *constructing* `target` directly. You are **simulating a very dumb stack** that only knows two moves:

* **Push** the *next* number from a fixed stream `1 → n`
* **Pop** the top element

You **cannot skip numbers in the stream**. Every number from `1` upward must be *considered in order*. This constraint is the entire trick.

The moment the stack equals `target` (bottom → top), you must **stop immediately**. Any extra operations are illegal.

So the real question becomes:

> For each number `i` from `1` to `n`, do we **keep** it (Push only) or **discard** it (Push + Pop)?

---

## Core Insight (The Brain Click)

`target` is strictly increasing.

That means:

* If the current stream value `i` **matches** the next value we want in `target`, we **Push and keep it**.
* If it **does not match**, we must **Push it first (mandatory)** and then **Pop it immediately**.

There is no third option. You *must* read numbers sequentially.

This turns the problem into a **two‑pointer simulation**:

* Pointer `i` → current stream number
* Pointer `j` → current index in `target`

---

## Why a Stack Is Not Actually Needed

This problem **looks** like a stack problem, but it isn't.

You never need to inspect or store stack contents because:

* The rules force every number to be pushed before it can be popped
* Decisions depend only on whether `i == target[j]`

So we simulate **operations**, not data.

This is a classic LeetCode illusion.

---

## Step‑by‑Step Strategy

1. Start with an empty operation list
2. Start reading numbers from `1` to `n`
3. For each number `i`:

   * Always **Push**
   * If `i == target[j]`, keep it and move `j`
   * Otherwise, **Pop** immediately
4. Stop as soon as `j == target.length`

That's it. No backtracking. No stack. No cleverness.

---

## Example Walkthrough

### Input

```
target = [1, 3]
n = 3
```

### Simulation

| Stream `i` | target[j] | Action      |
| ---------- | --------- | ----------- |
| 1          | 1         | Push (keep) |
| 2          | 3         | Push + Pop  |
| 3          | 3         | Push (keep) |

### Result

```
["Push", "Push", "Pop", "Push"]
```

---

## Clean Java Solution (Optimal)

```java
class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> ops = new ArrayList<>();
        int j = 0;

        for (int i = 1; i <= n && j < target.length; i++) {
            ops.add("Push");

            if (target[j] == i) {
                j++;
            } else {
                ops.add("Pop");
            }
        }

        return ops;
    }
}
```

---

## Why This Works (Invariant Thinking)

At every step:

* Stack content is **always a prefix of `target`**
* Any number not in `target` is immediately removed

This invariant guarantees correctness and early termination.

---

## Complexity Analysis

* **Time:** `O(n)` (single pass)
* **Space:** `O(n)` (operation list)

Optimal. No wasted work.

---

## Common Mistakes (Your Weak Spots)

* Overthinking stack behavior
* Actually simulating the stack values
* Forgetting to stop once `target` is built
* Trying to skip numbers in the stream (illegal)

You don’t need power here. You need restraint.

---

## Final Thought

This problem rewards **obedience to constraints**, not cleverness.

LeetCode loves these:

> Problems that look like data structures but collapse into pointer logic.

Recognize them faster. Think before coding. Stay humble.
