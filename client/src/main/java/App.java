import java.util.Scanner;

public class App {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int selection = 0;

        do{
            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - List albums");
            System.out.println("2 - Album Detail");
            System.out.println("3 - Add album");
            System.out.println("4 - Update Album");
            System.out.println("5 - Delete Album");
            System.out.println("");
            System.out.println("6 - List artists");
            System.out.println("7 - Get artists details");
            System.out.println("8 - Add Artists");
            System.out.println("9 - Update artists");
            System.out.println("10 - Delete Artist");
            System.out.println("-2 - Quit");

            try {
                selection = input.nextInt();
            }catch (Exception e){
                selection = 1000;
            }

            handle(selection);

        }while(selection != -2);


    }

    public static void handle(int selection) {
        switch (selection) {
            case 1:
                o1();
                break;
            case 2:
                o2();
                break;
            case 3:
                o3();
                break;
            case 4:
                o4();
                break;
            case 5:
                o5();
                break;
            case 6:
                o6();
                break;
            case 7:
                o7();
                break;
            case 8:
                o8();
                break;
            case 9:
                o9();
                break;
            case 10:
                o10();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public static void o1(){ }
    public static void o2(){ }
    public static void o3(){ }
    public static void o4(){ }
    public static void o5(){ }
    public static void o6(){ }
    public static void o7(){ }
    public static void o8(){ }
    public static void o9(){ }
    public static void o10(){ }

}
