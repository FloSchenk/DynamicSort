import java.util.ArrayList;

public class Sort {
    //stooge source: https://github.com/smallnest/algorithm/blob/master/src/main/java/com/colobu/algorithm/sort/StoogeSort.java


    private static Sort instance = new Sort();

    public Port port;

    private Sort(){
        port = new Port();
    }

    public String getVersion(){
        return "StoogeSort";
    }

    public static Sort getInstance(){
        return instance;
    }

    public ArrayList<Integer> innerSort(ArrayList<Integer> values){
        stoogeSort(values,0,values.size() - 1);
        return values;
    }

    public  static <T extends Comparable<? super T>> void stoogeSort(ArrayList<Integer> values ,int startIndex,int endIndex) {
        if (values.get(endIndex).compareTo(values.get(startIndex)) < 0) {

            //replaced SwapUtils from GitHub
            if (startIndex == endIndex) {
                return;
            }
            int temp = values.get(startIndex);
            values.set(startIndex, values.get(endIndex));
            values.set(endIndex, temp);
        }

        if ((endIndex - startIndex + 1) >= 3){
            int t = (endIndex - startIndex + 1)/3;
            stoogeSort(values,startIndex,endIndex - t);
            stoogeSort(values,startIndex + t,endIndex);
            stoogeSort(values,startIndex,endIndex - t);
        }
    }

    public class Port implements ISort{
        public void printVersion(){
            System.out.println(getVersion());
        }

        public ArrayList<Integer> sort(ArrayList<Integer> input){
            System.out.println("- Stooge Algorithm - ");
            return innerSort(input);
        }
    }
}