import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;


/**
 * A custom Array class implementation with common array operations
 */
public class Array {
    private int[] data;
    private int size;
    private int capacity;
    
    // Default constructor
    public Array() {
        this.capacity = 10;
        this.data = new int[capacity];
        this.size = 0;
    }
    
    // Constructor with initial capacity
    public Array(int initialCapacity) {
        this.capacity = initialCapacity;
        this.data = new int[capacity];
        this.size = 0;
    }
    
    // Constructor with initial values
    public Array(int[] initialData) {
        this.capacity = initialData.length * 2;
        this.data = new int[capacity];
        this.size = initialData.length;
        System.arraycopy(initialData, 0, this.data, 0, initialData.length);
    }
    
    // 1. Add element to the end
    public void add(int value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }
    
    // 2. Insert element at specific index
    public void insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity();
        
        // Shift elements to the right
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        
        data[index] = value;
        size++;
    }
    
    // 3. Get element at index
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }
    
    // 4. Set element at index
    public void set(int index, int value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        data[index] = value;
    }
    
    // 5. Remove element at index
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        
        size--;
        data[size] = 0; // Optional: clear the last element
    }
    
    // 6. Remove first occurrence of value
    public boolean remove(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }
    
    // 7. Check if array contains value
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return true;
            }
        }
        return false;
    }
    
    // 8. Find index of first occurrence
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    // 9. Find index of last occurrence
    public int lastIndexOf(int value) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    // 10. Get current size
    public int size() {
        return size;
    }
    
    // 11. Check if array is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    // 12. Clear the array
    public void clear() {
        // Reset all elements to 0 (optional)
        for (int i = 0; i < size; i++) {
            data[i] = 0;
        }
        size = 0;
    }
    
    // 13. Sort the array (parallel Sort)
public void parallelSort() {
    ForkJoinPool.commonPool().invoke(
        new RecursiveAction() {
            @Override
            protected void compute() {
                parallelMergeSort(data, 0, size - 1);
            }
        }
    );
}

private void parallelMergeSort(int[] arr, int left, int right) {
    if (left >= right) return;

    // cutoff to avoid thread overhead
    if (right - left < 1000) {
        sequentialSort(arr, left, right);
        return;
    }

    int mid = (left + right) / 2;

    RecursiveAction leftTask = new RecursiveAction() {
        @Override
        protected void compute() {
            parallelMergeSort(arr, left, mid);
        }
    };

    RecursiveAction rightTask = new RecursiveAction() {
        @Override
        protected void compute() {
            parallelMergeSort(arr, mid + 1, right);
        }
    };

    RecursiveAction.invokeAll(leftTask, rightTask);
    merge(arr, left, mid, right);
}

private void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;

    while (i <= mid && j <= right) {
        temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];

    System.arraycopy(temp, 0, arr, left, temp.length);
}

private void sequentialSort(int[] arr, int left, int right) {
    for (int i = left; i <= right; i++) {
        for (int j = i + 1; j <= right; j++) {
            if (arr[i] > arr[j]) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
    }
}
    
    // 14. Reverse the array
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            int temp = data[i];
            data[i] = data[size - i - 1];
            data[size - i - 1] = temp;
        }
    }
    
    // 15. Convert to Java's built-in array
    public int[] toArray() {
        int[] result = new int[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }
    
    // 16. Get subarray
    public Array subArray(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        
        int[] subData = new int[toIndex - fromIndex];
        System.arraycopy(data, fromIndex, subData, 0, toIndex - fromIndex);
        return new Array(subData);
    }
    
    // 17. Fill array with value
    public void fill(int value) {
        for (int i = 0; i < size; i++) {
            data[i] = value;
        }
    }
    
    // 18. Copy array
    public Array copy() {
        return new Array(this.toArray());
    }
    
    // 19. Check if two arrays are equal
    public boolean equals(Array other) {
        if (other == null || this.size != other.size) {
            return false;
        }
        
        for (int i = 0; i < size; i++) {
            if (this.data[i] != other.data[i]) {
                return false;
            }
        }
        return true;
    }
    
    // 20. Get maximum value
    public int max() {
        if (size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        
        int max = data[0];
        for (int i = 1; i < size; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }
    
    // 21. Get minimum value
    public int min() {
        if (size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        
        int min = data[0];
        for (int i = 1; i < size; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }
    
    // 22. Get sum of all elements
    public int sum() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += data[i];
        }
        return sum;
    }
    
    // 23. Get average of elements
    public double average() {
        if (size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        return (double) sum() / size;
    }
    
    // 24. Binary search (requires sorted array)
    public int binarySearch(int value) {
        int left = 0;
        int right = size - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (data[mid] == value) {
                return mid;
            } else if (data[mid] < value) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    // Helper method to ensure capacity
    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            data = Arrays.copyOf(data, capacity);
        }
    }
    
    // 25. Display array as string
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

public class CustomArray {
    public static void main(String[] args) {
        System.out.println("=== Custom Array Class Demonstration ===\n");
        
        // 1. Create arrays using different constructors
        System.out.println("1. Creating Arrays:");
        Array arr1 = new Array();
        System.out.println("Default array: " + arr1);
        
        Array arr2 = new Array(5);
        System.out.println("Array with capacity 5: " + arr2);
        
        int[] initialData = {5, 10, 15};
        Array arr3 = new Array(initialData);
        System.out.println("Array with initial data [5, 10, 15]: " + arr3);
        
        // 2. Add elements
        System.out.println("\n2. Adding Elements:");
        arr1.add(10);
        arr1.add(20);
        arr1.add(30);
        arr1.add(40);
        System.out.println("After adding 10, 20, 30, 40: " + arr1);
        
        // 3. Insert elements
        System.out.println("\n3. Inserting Elements:");
        arr1.insert(2, 25);
        System.out.println("Insert 25 at index 2: " + arr1);
        
        // 4. Get and set elements
        System.out.println("\n4. Get and Set Operations:");
        System.out.println("Element at index 3: " + arr1.get(3));
        arr1.set(3, 35);
        System.out.println("After setting index 3 to 35: " + arr1);
        
        // 5. Remove elements
        System.out.println("\n5. Removing Elements:");
        arr1.removeAt(1);
        System.out.println("After removing element at index 1: " + arr1);
        
        boolean removed = arr1.remove(35);
        System.out.println("Remove value 35: " + removed + ", Array: " + arr1);
        
        // 6. Search operations
        System.out.println("\n6. Search Operations:");
        System.out.println("Contains 25? " + arr1.contains(25));
        System.out.println("Index of 25: " + arr1.indexOf(25));
        System.out.println("Last index of 25: " + arr1.lastIndexOf(25));
        
        // Add duplicate for demonstration
        arr1.add(25);
        System.out.println("After adding another 25: " + arr1);
        System.out.println("Last index of 25 now: " + arr1.lastIndexOf(25));
        
        // 7. Size and isEmpty
        System.out.println("\n7. Size Operations:");
        System.out.println("Size: " + arr1.size());
        System.out.println("Is empty? " + arr1.isEmpty());
        
        // 8. Sort array
        System.out.println("\n8. Sorting:");
        arr1.add(5);
        arr1.add(15);
        System.out.println("Before sort: " + arr1);
        arr1.sort();
        System.out.println("After sort: " + arr1);
        
        // 9. Binary search (requires sorted array)
        System.out.println("\n9. Binary Search:");
        System.out.println("Binary search for 25: " + arr1.binarySearch(25));
        System.out.println("Binary search for 100: " + arr1.binarySearch(100));
        
        // 10. Reverse array
        System.out.println("\n10. Reversing:");
        System.out.println("Before reverse: " + arr1);
        arr1.reverse();
        System.out.println("After reverse: " + arr1);
        
        // 11. Subarray
        System.out.println("\n11. Subarray:");
        Array subArr = arr1.subArray(1, 4);
        System.out.println("Subarray from index 1 to 3: " + subArr);
        
        // 12. Copy array
        System.out.println("\n12. Copy Array:");
        Array copyArr = arr1.copy();
        System.out.println("Original: " + arr1);
        System.out.println("Copy: " + copyArr);
        System.out.println("Are they equal? " + arr1.equals(copyArr));
        
        // 13. Fill array
        System.out.println("\n13. Fill Array:");
        subArr.fill(99);
        System.out.println("After filling with 99: " + subArr);
        
        // 14. Statistical operations
        System.out.println("\n14. Statistical Operations:");
        System.out.println("Array: " + arr3);
        System.out.println("Max: " + arr3.max());
        System.out.println("Min: " + arr3.min());
        System.out.println("Sum: " + arr3.sum());
        System.out.println("Average: " + arr3.average());
        
        // 15. Convert to built-in array
        System.out.println("\n15. Conversion to built-in array:");
        int[] builtInArray = arr3.toArray();
        System.out.println("Custom array: " + arr3);
        System.out.println("Built-in array: " + Arrays.toString(builtInArray));
        
        // 16. Clear array
        System.out.println("\n16. Clear Array:");
        System.out.println("Before clear: " + arr3);
        arr3.clear();
        System.out.println("After clear: " + arr3);
        System.out.println("Is empty? " + arr3.isEmpty());
        
        // 17. Edge case demonstration
        System.out.println("\n17. Edge Cases:");
        Array emptyArr = new Array();
        System.out.println("Empty array: " + emptyArr);
        System.out.println("Is empty? " + emptyArr.isEmpty());
        
        try {
            emptyArr.get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        System.out.println("\n=== Demonstration Complete ===");
    }
}
