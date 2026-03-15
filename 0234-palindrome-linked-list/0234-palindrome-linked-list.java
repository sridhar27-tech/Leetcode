/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode temp = head;
        List<Integer> arr = new ArrayList<>();
        while(temp != null) {
            arr.add(temp.val);
            temp = temp.next;
        }
        int low = 0;
        int high = arr.size() -1;
        while(low<high) {
            if(arr.get(low) != arr.get(high)) return false;
            low++;
            high--;
        }
        return true;
    }
}