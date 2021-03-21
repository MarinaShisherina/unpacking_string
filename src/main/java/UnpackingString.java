import unpacker.Unpacker;

import java.util.Scanner;

public class UnpackingString {

    public static void main(String[] args) {

        System.out.println("Введите строку для распаковки");
        Scanner scanner = new Scanner(System.in);
        String userString = scanner.nextLine();

        try {
            System.out.println(new Unpacker(userString).unpack());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }
}
