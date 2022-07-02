package obj;

public class Counter {
    private int count;

    public Counter(){
        this.count = 0;
    }

    public void increaseCount(){
        this.count++;
    }

    public int getCount(){
        return this.count;
    }
}
