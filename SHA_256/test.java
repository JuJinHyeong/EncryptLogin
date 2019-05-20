import java.util.Arrays;
import java.util.Scanner;

public class test {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        System.out.println("Message: " + message);
        SHA256 sha256 = new SHA256(message);

        System.out.println(Arrays.toString(sha256.get_hash_value()));
    }
}
