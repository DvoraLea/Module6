package linkedLists;

public class Node <T>{
	private T data;
	private Node<T> next;
	
	public Node(T data) {
		this.data = data;
		next = null;
	}
	
	public T getInfo() {
		return data;
	}
	
	public Node<T> getNext(){
		return next;
	}
	
	public void setNext(Node<T> node) {
		this.next = node;
	}
	
	public void setData(T data) {
		this.data = data;
	}

}
