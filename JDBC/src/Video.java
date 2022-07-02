public class Video {
    private int videoId;
    private String videoName;
    private int price;

    public Video(int videoId, String videoname, int price){
        this.videoId = videoId;
        this.videoName = videoname;
        this.price = price;
    }

    @Override
    public String toString(){
        return "videoId: "+ videoId + ", name: " + videoName + ", price: "+ price;
    }
}
