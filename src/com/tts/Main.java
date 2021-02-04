package com.tts;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        displayMenu();

    }

    public static void callContact(String name) {

        String s = findNumber(name);

        if (s != null)
            System.out.println("Calling " + name + " at " + s);
        else
            System.out.println("No person found named " + name );
    }
//      Added String email
    public static void saveContact(String name, long number, String email) {
//        added + " : " + email
        System.out.println("Saving contact " + name + " : " + number + " : " + email);

        try (PrintWriter pw = new PrintWriter(new FileWriter("file.txt",true))) {
//            added + " : " + email
            pw.println(name + ":" + number + " : " + email);
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeContact(String s) throws IOException {

            File file = new File("file.txt");
            File temp = new File("_temp_");
            PrintWriter out = new PrintWriter(new FileWriter(temp));
            Files.lines(file.toPath())
                    .filter(line -> !line.contains(s))
                    .forEach(out::println);
            out.flush();
            out.close();
            temp.renameTo(file);

    }

    public static void printList(String args) throws IOException
    {
        // Open the file.
        FileReader fr = new FileReader("file.txt");
        Scanner inFile = new Scanner(fr);

        // Read lines from the file till end of file
        while (inFile.hasNext())
        {
            // Read the next line.
            String line = inFile.nextLine();
            // Display the line.
            System.out.println(line);
        }

        // Close the file.
        inFile.close();
    }

    public static String findNumber(String name) {
        return name;
    }

    static String[] findEmail(String name){
        String[] s = new String[0];
        try(Scanner in = new Scanner(new File("file.txt"))) {


            boolean foundPerson = false;

            while(in.hasNextLine()) {
                s = in.nextLine().split(":");
                if (s[0].equals(name)) {
                    System.out.println("The number associated with " + name + " is " + s[1]);
                    System.out.println("The email associated with " + name + " is " + s[2]);
                    foundPerson = true;
                    break;
                }
            }

            if(!foundPerson) {
                System.out.println("Could not find " + name);
                s = null;
            }

            return s;

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return s;
    }

    public static void displayMenu() {

        try (Scanner in = new Scanner(System.in)) {
            String name;
            String email;

            do {

                System.out.println("\nWhat would you like to do? (1, 2, 3, 4 ... etc)");
                System.out.println("1. Call Contact");
                System.out.println("2. Save Contact");
                System.out.println("3. Find Contact");
                System.out.println("4. Del Contact");
                System.out.println("5. Print List");

                int choice = in.nextInt();
                in.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\nWho would you like to call? (Firstname Lastname)");
                        name = in.nextLine();

                        callContact(name);
                        break;

                    case 2:
                        System.out.println("\nWhat is the name of the person you would like save?(Firstname Lastname)");
                        name = in.nextLine();

                        System.out.println("\nWhat is the phone number of the person you are saving?(0123456789)");
                        long number = in.nextLong();
                        in.nextLine();

                        System.out.println("\nWhat is the email of the person you are saving?(john.brown@live.com)");
                        email = in.nextLine();
                        in.nextLine();

//                        add email in saveContact
                        saveContact(name, number, email);
                        break;

                    case 3:
                        System.out.println("\nWhat is the name of the person whose phone number you are searching? (Firstname Lastname) ");
//                        findNumber(in.nextLine());
                        findEmail(in.nextLine());
                        break;

                    case 4:
                        System.out.println("\nWhat contact would you like to delete?");
                        removeContact(in.nextLine());

                  case 5:
                        System.out.println("\nThis is the contact list");
                        printList(in.nextLine());

                    default:

                        break;
                }

                System.out.println("Do you wish to perform another action? (Y/N)");

                if(in.next().toLowerCase().charAt (0) != 'y')
                    break;

            }while (true);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
