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
        ListNode headClone = null;
        ListNode tailClone = null;
        ListNode temp = head;

        while(temp != null) {
            ListNode newNode = new ListNode(temp.val);
            if(headClone == null) {
                headClone = newNode;
                tailClone = newNode;
            }
            else {
                tailClone.next = newNode;
                tailClone = tailClone.next;
            }
            temp = temp.next;
        }

        ListNode curr = head;
        ListNode prev = null;

        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        curr = prev;
        ListNode clone = headClone;

        while(curr != null && clone != null) {
            if(curr.val != clone.val) return false;
            curr = curr.next;
            clone = clone.next;
        }

        return true;
    }
}