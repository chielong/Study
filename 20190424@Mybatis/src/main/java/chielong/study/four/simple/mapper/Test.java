package chielong.study.four.simple.mapper;

/**
 * Created by chielong on 2019-05-10.
 */
public class Test {
    public static void main(String[] args) {
        String input = "%d %s %d";
        Object[] objects = new Object[]{1 , "2" , 3};
        String result = String.format(input , objects);
        System.out.println(result);
    }
}
