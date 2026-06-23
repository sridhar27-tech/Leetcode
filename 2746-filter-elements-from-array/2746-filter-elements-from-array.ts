type Fn = (n: number, i: number) => any

function filter(arr: number[], fn: Fn): number[] {
    const res: number[] = []
    arr.forEach((item, idx) => {
        if(fn(item, idx)) {
            res.push(item);
        }
    });
    return res;
};