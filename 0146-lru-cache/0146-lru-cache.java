class LRUCache {

    class Node {
        int key, val;
        Node next, prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            next = null;
            prev = null;
        }
    }
    private final int capacity;
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> map;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node(0,0);
        tail = new Node(0,0);
        this.map = new HashMap<>();
        
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
           Node node = map.get(key);
           remove(node);
           addFast(node);
           return node.val; 
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            remove(map.get(key));
        }

        if(map.size() == this.capacity) {
            remove(tail.prev);
        }

        addFast(new Node(key, value));
    }

    public void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void addFast(Node node) {
        map.put(node.key, node);
        Node first = head.next;
        node.next = first;
        first.prev = node;
        head.next = node;
        node.prev = head;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */