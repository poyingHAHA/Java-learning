public class Info<T> {
    private T info;

    public Info(T info){
        this.info = info ;
    }

    public T getInfo(){
        return this.info;
    }
}

// Type Erasure 型別擦除
