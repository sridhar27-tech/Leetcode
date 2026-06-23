function map(arr: number[], fn: (n: number, i: number) => number): number[] {
    const nums:number[] = arr.map(fn);
    return nums
};