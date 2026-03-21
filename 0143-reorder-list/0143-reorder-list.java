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
    public void reorderList(ListNode head) {
        if(head == null) return ;
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;

        while(temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        temp = head;
        for(int i =0;i<stack.size();i++) {
            ListNode next = temp.next;
            stack.peek().next = temp.next;
            temp.next = stack.pop();
            temp = temp.next.next;
        }
        temp.next = null;
    }
}