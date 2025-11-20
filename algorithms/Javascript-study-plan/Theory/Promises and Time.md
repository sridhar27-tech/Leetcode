# Promises and Time in JavaScript

JavaScript handles time in a strange, beautiful way. It's single-threaded, yet it behaves *as if* things run in parallel. Promises, timers, and the event loop work together to make async behavior feel smooth.

This document breaks down how promises relate to time, how scheduling works, what actually happens under the hood, and how this shows up in real systems.

---

## 1. What Are Promises?

A Promise is an object representing a value that will exist later. It captures the idea of *a result that isn’t here yet but will be*.

A promise has three states:

* **pending** (initial)
* **fulfilled** (success)
* **rejected** (failure)

Example:

```js
const p = new Promise((resolve, reject) => {
  resolve(42);
});

p.then(value => console.log(value));
```

This schedules a reaction to a future result.

---

## 2. Time in JavaScript: The Event Loop

JavaScript is single-threaded. It uses an event loop to handle:

* tasks (callbacks like `setTimeout`)
* microtasks (promise reactions)
* rendering

Important detail:
**Promise callbacks run before timer callbacks.**

This is why:

```js
console.log("A");
setTimeout(() => console.log("B"), 0);
Promise.resolve().then(() => console.log("C"));
console.log("D");
```

prints:

```
A
D
C
B
```

The promise reaction enters the microtask queue, which gets priority.

---

## 3. Basic Implementations: Promises + Time

### 3.1 Wrapping `setTimeout` in a Promise

```js
function delay(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

await delay(1000);
console.log("1 second later");
```

### 3.2 A simple async workflow

```js
async function fetchLater() {
  console.log("start");
  await delay(500);
  console.log("middle");
  await delay(500);
  console.log("end");
}
```

### 3.3 Polling with Promises

```js
function poll(fn, interval) {
  return new Promise(resolve => {
    const check = () => {
      const result = fn();
      if (result) return resolve(result);
      setTimeout(check, interval);
    };
    check();
  });
}
```

---

## 4. Under the Hood: How Promises Really Work

### 4.1 Promise resolution doesn’t run immediately

Calling `resolve()` doesn’t execute `.then()` right away. Instead, it queues a microtask.

Promise internals:

* Promise object has internal fields: `[[PromiseState]]`, `[[PromiseResult]]`, `[[PromiseFulfillReactions]]`, `[[PromiseRejectReactions]]`.
* When resolved, reactions are put into the **microtask queue**.
* After the current call stack empties, microtasks run.
* Timer callbacks only run after microtasks.

### 4.2 Why async/await is just syntactic sugar

```js
async function run() {
  await something();
}
```

becomes:

```js
function run() {
  return something().then(() => { … });
}
```

`await` just pauses your function and schedules the continuation as a microtask.

### 4.3 Timers are not exact

`setTimeout(fn, 10)` does **not guarantee** a 10ms delay. It guarantees:

* wait at least 10ms,
* run only when the event loop is free,
* run after microtasks.

This leads to subtle async timing bugs.

---

## 5. Industry Use Cases

Promises + time power a huge chunk of real-world systems.

### 5.1 API Calls

```js
async function getUser() {
  const res = await fetch("/api/user");
  return res.json();
}
```

### 5.2 Rate Limiting

```js
async function rateLimited(fn, delay) {
  let last = 0;
  return async function (...args) {
    const now = Date.now();
    if (now - last >= delay) {
      last = now;
      return fn(...args);
    }
  };
}
```

### 5.3 Delayed Retries

```js
async function retry(fn, attempts) {
  for (let i = 0; i < attempts; i++) {
    try {
      return await fn();
    } catch {}
    await delay(500);
  }
  throw new Error("Failed after retries");
}
```

### 5.4 UI Debouncing

React apps rely heavily on debouncing and throttling, especially for search bars.

### 5.5 Task Scheduling in Node.js

`process.nextTick`, `setImmediate`, and Promises all sit in different queues.

---

## 6. Common Pitfalls

### 6.1 Forgetting to return a Promise

```js
.then(() => fetch(...)) // correct
.then(() => { fetch(...); }) // wrong
```

### 6.2 Microtask starvation

Too many chained `.then()` calls can block timers.

### 6.3 Using `setTimeout(fn, 0)` for ordering

Microtasks will still run first.

### 6.4 Errors inside async functions become rejected promises

They don’t throw synchronously.

---

## 7. Interview Questions

These appear constantly in JavaScript rounds.

### 1. Explain the difference between microtasks and macrotasks.

Microtasks: promise callbacks.
Macrotasks: timers, I/O, events.

### 2. What is the output?

```js
console.log(1);
setTimeout(() => console.log(2), 0);
Promise.resolve().then(() => console.log(3));
console.log(4);
```

### 3. Why doesn’t `await` block the thread?

Because it splits the function and schedules continuation.

### 4. Implement a sleep function.

```js
const sleep = ms => new Promise(r => setTimeout(r, ms));
```

### 5. Implement a promise timeout wrapper.

```js
function withTimeout(promise, ms) {
  const t = new Promise((_, reject) => setTimeout(() => reject("timeout"), ms));
  return Promise.race([promise, t]);
}
```

### 6. Explain the event loop phases in Node.

Timers → Pending → Idle → Poll → Check → Close.

### 7. How does async/await handle errors?

Using `try/catch` around awaited values.

---

## 8. Summary

Promises and time form the backbone of modern JavaScript. Once you understand how the event loop schedules work, async code stops feeling like magic and starts feeling like a structured flow of microtasks and timers.

Master this, and debugging async behavior becomes far less painful.

---

End of file.
