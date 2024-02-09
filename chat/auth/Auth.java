import java.util.Scanner;

public class Auth {

public static void main(String[] args) {

    String Username1;
    String Username2;
    String Password1;
    String Password2;

    Password1 = "123";
    Password2 = "123";
    Username1 = "Luis";
    Username2 = "Pablo";

    Scanner input1 = new Scanner(System.in);
    System.out.println("Enter Username : ");
    String username = input1.next();

    Scanner input2 = new Scanner(System.in);
    System.out.println("Enter Password : ");
    String password = input2.next();

    if (username.equals(Username) && password.equals(Password)) {

        System.out.println("Access Granted! Welcome!");
    }

    else if (username.equals(Username)) {
        System.out.println("Invalid Password!");
    } else if (password.equals(Password)) {
        System.out.println("Invalid Username!");
    } else {
        System.out.println("Invalid Username & Password!");
    }

}

}