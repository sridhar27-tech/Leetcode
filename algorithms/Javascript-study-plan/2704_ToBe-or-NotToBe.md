# 2704. To Be Or Not To Be — Full Explanation

This problem is less about algorithms and more about understanding how JavaScript functions, closures, and returned objects work together. You're essentially building a tiny mock version of Jest's `expect()` function.

---

## **1. What the Problem Wants**

You need to implement a function `expect(val)` that returns an object containing:

* `toBe(expected)` → returns `true` if `val === expected`, otherwise throws **"Not Equal"**.
* `notToBe(expected)` → returns `true` if `val !== expected`, otherwise throws **"Equal"**.

This allows calls like:

```js
expect(5).toBe(5);        // true
expect(5).notToBe(10);    // true
expect(5).toBe(10);       // throws "Not Equal"
```

This structure is intentionally designed to teach JavaScript concepts.

---

## **2. Core Concepts Behind the Problem**

### **A. Functions Can Return Objects**

JavaScript lets you return anything from a function: numbers, objects, arrays, or even other functions. Here, `expect(val)` returns an *object* with methods.

```js
return {
  toBe: ...,
  notToBe: ...
};
```

This is extremely common in JS libraries.

---

### **B. Closures (The Real Secret)**

A closure happens when an inner function remembers and accesses values from its outer function **even after the outer function finishes running**.

`val` is defined inside `expect()`, but used inside `toBe()` and `notToBe()` — that's a closure.

```js
var expect = function(val) {
  return {
    toBe: (other) => val === other,
  };
};
```

Even though `expect()` already returned, both methods can still access `val`. This is how "testing-style" APIs work.

---

### **C. Methods on Returned Objects**

Because `expect(val)` returns an object, you can directly call methods on it:

```js
expect(10).toBe(10);
```

This pattern is used heavily in testing libraries, UI frameworks, and promise chains.

---

### **D. Error Throwing**

If a comparison fails, you throw an error:

```js
throw "Not Equal";
```

LeetCode allows throwing simple strings, though real applications use:

```js
throw new Error("message");
```

Error throwing is fundamental for handling invalid states.

---

### **E. Strict Equality (===)**

The problem requires the strict equality operator:

* No type coercion
* Both type **and** value must match

```js
5 === "5"  // false
```

This avoids JavaScript's sneaky coercion rules.

---

## **3. Final Solution**

Here is the clean, idiomatic solution:

```js
var expect = function(val) {
  return {
    toBe: function(other) {
      if (val === other) return true;
      throw "Not Equal";
    },
    notToBe: function(other) {
      if (val !== other) return true;
      throw "Equal";
    }
  };
};
```

---

## **4. Why This Problem Matters**

While it looks small, it gives you exposure to:

* Returning objects from functions
* Functional patterns similar to testing libraries
* Closures (a core JS concept you'll see everywhere)
* Error handling
* JavaScript's equality rules

This problem is a warm-up for more advanced JS topics like:

* Currying
* Promise chains
* Custom testing frameworks
* Higher-order functions

Once you understand closures and returned-object APIs, a lot of JavaScript's design suddenly becomes much more intuitive.

---

## **5. Example Usage**

```js
expect(5).toBe(5);     // true
expect(5).notToBe(3);  // true
expect(5).toBe(3);     // throws "Not Equal"
expect(5).notToBe(5);  // throws "Equal"
```
