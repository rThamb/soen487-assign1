import lib.models.Album;
import lib.repos.ClientAlbumRepo;
import lib.repos.ClientArtistRepo;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class App {

    private static ClientAlbumRepo repo = new ClientAlbumRepo();
    private static ClientArtistRepo artistRepo = new ClientArtistRepo();

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
            //System.out.println("6 - List artists");
            //System.out.println("7 - Get artist details");
            //System.out.println("8 - Add Artist");
            //System.out.println("9 - Update artist");
            //System.out.println("10 - Delete Artist");
            //System.out.println("");
            System.out.println("11 - Upload Cover Image for Album");
            System.out.println("12 - Download Cover Image for Album");
            System.out.println("13 - Delete Cover Image for Album");
            System.out.println("-2 - Quit");

            System.out.print("Enter Option: ");
            try {
                selection = input.nextInt();
            }catch (Exception e){
                selection = 1000;
                input.nextLine();
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
            case 11:
                o11();
                break;
            case 12:
                o12();
                break;
            case 13:
                o13();
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
    public static void o3() throws Exception {
        System.err.println("Enter Album info (format) :    isrc; title; des; year;");

        Scanner input = new Scanner(System.in);
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = repo.add(entries);
        System.out.println(response);

    }
    public static void o4() throws Exception {
        System.err.println("Enter new values for albums (set desired isrc) :    isrc; title; des; year;");

        Scanner input = new Scanner(System.in);
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = repo.edit(entries);
        System.out.println(response);
    }
    public static void o5() throws Exception {
        System.err.println("Enter Album isrc to delete");
        Scanner input = new Scanner(System.in);
        String id = input.nextLine();
        String response = repo.delete(id);
        System.out.println(response);
    }
    public static void o6() throws Exception{
        String response = artistRepo.listArtists();
        System.out.println(response + "\n\n");
    }
    public static void o7() throws Exception{
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Artist nickname to retrieve details: ");
        String inputStr = input.nextLine();
        String response = artistRepo.getArtist(inputStr);
        System.out.println(response + "\n");
    }
    public static void o8() throws Exception {
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Artist info (format):    nickname; first name; last name; bio;");
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = artistRepo.add(entries);
        System.out.println(response + "\n");
    }
    public static void o9() throws Exception{
        Scanner input = new Scanner(System.in);
        System.err.println("Update info for artist (use existing nickname):    nickname; first name; last name; bio;");
        String inputStr = input.nextLine();
        String[] entries = inputStr.split(";");
        String response = artistRepo.edit(entries);
        System.out.println(response + "\n");
    }
    public static void o10() throws Exception{
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Artist nickname to delete: ");
        String inputStr = input.nextLine();
        String response = artistRepo.delete(inputStr);
        System.out.println(response + "\n");
    }

    public static void o11() throws Exception {
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Album isrc and filename: (Ensure directory `album_attachments/{isrc}` contains your file)");
        String[] inputs = input.nextLine().split(";");
        String filePath = String.format("album_attachments/%s/%s", inputs[0], inputs[1]);
        File f = new File(filePath);
        String response = repo.uploadFile(inputs[0], f);
        System.out.println(response + "\n");
    }

    public static void o12() throws Exception {
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Album isrc");
        String isrc = input.nextLine();

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://localhost:8081/api/album/download/" + isrc));
        }
    }

    public static void o13() throws Exception {
        Scanner input = new Scanner(System.in);
        System.err.println("Enter Album isrc");
        String isrc = input.nextLine();
        String response = repo.deleteFile(isrc);
        System.out.println(response + "\n");
    }
}
