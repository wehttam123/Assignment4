package cpsc457;

//TODO: Class for the linked lists nodes (aka so we can implement append())
public class LinkedNode<AnyType> {
  public AnyType data;
  public LinkedNode<AnyType> next;

  public LinkedNode(AnyType data) {
    this.data = data;
  }
}
