package hour.test;

import java.util.Arrays;
import java.util.Scanner;

public class userTest {

    public static void main(String[] args){
        int A=0;
        for(int i=0;i<10;i++){
            if(i++%2==0)
                i++;
            A++;
        }
        System.out.println(A);
    }

}
