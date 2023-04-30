package application;

import java.io.File;

class Queue {
    
    private static class Node {
        private File data;
        private Node next;

        public Node(File data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;  // front of the queue
    private Node tail;  // back of the queue

    public Queue() {
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(File file) {
        Node newNode = new Node(file);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
    }

    public File dequeue() {
        if (head == null) {
            return null;
        }
        File data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    public File peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }
}

