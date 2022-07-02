class Info<T, K>{
    private T key;
    private K value;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public K getValue() {
        return value;
    }

    public void setValue(K value) {
        this.value = value;
    }
}

interface Content<T>{
    T test();
}

class GenericsContent<T> implements Content<T>{
    private T text;
    public GenericsContent(T text){
        this.text = text;
    }

    @Override
    public T test() {
        return text;
    }
}

public class GenericsExample implements Content<Integer> {
    public static void main(String[] args) {
        GenericsExample m = new GenericsExample();

        Info<String, Integer> myInfo = new Info<>();
        myInfo.setKey("Edwin");
        myInfo.setValue(5000);
        System.out.println(myInfo.getKey()+" "+myInfo.getValue());
    }

    @Override
    public Integer test() {
        return 100;
    }
}
