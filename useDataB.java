public class useDataB {
    public static void main(String[] args){
        dataBaseSingleton abc = dataBaseSingleton.getInstance();
        abc.appendData("jack",dataBaseSingleton.userData);
    }
}
