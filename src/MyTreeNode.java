import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyTreeNode<T>{
    private T data = null;
    private List<MyTreeNode> children = new ArrayList<>();
    private MyTreeNode parent = null;

    private int peso;

    public MyTreeNode(T data, int peso) {
        this.data = data;
        this.peso = peso;
    }

    public void addChild(MyTreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(T data, int peso) {
        MyTreeNode<T> newChild = new MyTreeNode<>(data, peso);
        this.addChild(newChild);
    }

    public void addChildren(List<MyTreeNode> children) {
        for(MyTreeNode t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<MyTreeNode> getChildren() {
        return children;
    }
    public int sumOfChildrenWeights(){
        int ret = 0;
        for (int i = 0; i < children.toArray().length; i++) {
            ret += children.get(i).getPeso();
        }
        return ret;
    }

    public MyTreeNode getChild(String data){
        if(!hasChild(data)) return null;
        for (int i = 0; i < children.toArray().length; i++) {
            if(children.get(i).getData().equals(data)){
                return children.get(i);
            }
        }
        return null;
    }

    public boolean hasChild(String data){
        for (int i = 0; i < children.toArray().length; i++) {
            if(children.get(i).getData().equals(data)){
                return true;
            }
        }
        return false;
    }

    public T getData() {
        return data;
    }

    public int getPeso() {
        return peso;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(MyTreeNode parent) {
        this.parent = parent;
    }

    public MyTreeNode getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTreeNode<?> that = (MyTreeNode<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}