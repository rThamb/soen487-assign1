import lib.models.Album;
import lib.repos.ClientAlbumRepo;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.Scanner;

public class App {

    private static ClientAlbumRepo repo = new ClientAlbumRepo();

    public static void main(String[] args) throws Exception {

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

            System.out.print("Enter Option: ");
            try {
                selection = input.nextInt();
            }catch (Exception e){
                selection = 1000;
            }

            try {
                if(selection != -2)
                    handle(selection);
            }catch (Exception e){
                System.out.println("*** Action Failed ***");
            }
        }while(selection != -2);


    }

    public static void handle(int selection) throws Exception {
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

    public static void o1() throws Exception {
        String response = repo.listAlbums();
        System.out.println(response + "\n\n");
    }
    public static void o2() throws Exception {
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Album isrc");
        String id = input.nextLine();
        String response = repo.getAlbum(id);
        System.out.println(response);
    }
    public static void o3() throws IOException {
        System.err.println("Enter Album info (format) :    isrc; title; des; year;");

        Scanner input = new Scanner(System.in);
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = repo.add(entries);
        System.out.println(response);

    }
    public static void o4() throws IOException {
        System.err.println("Enter new values for albums (set desired isrc) :    isrc; title; des; year;");

        Scanner input = new Scanner(System.in);
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = repo.edit(entries);
        System.out.println(response);
    }
    public static void o5() throws IOException {
        System.err.println("Enter Album isrc to delete");
        Scanner input = new Scanner(System.in);
        String id = input.nextLine();
        String response = repo.delete(id);
        System.out.println(response);
    }






    public static void o6(){ }
    public static void o7(){ }
    public static void o8(){ }
    public static void o9(){ }
    public static void o10(){ }

}
