import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class test {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        SHA256 sha256 = new SHA256();
        System.out.println("Message: " + message);
        byte[] arr = sha256.hash(message.getBytes());

        System.out.println(Arrays.toString((arr)));

        for(int i=0; i<arr.length; i++){
            System.out.print(Integer.toString(arr[i] & 0xFF, 16));
        }
        System.out.println();
    }
}
