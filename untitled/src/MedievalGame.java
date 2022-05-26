import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.Objects;
import java.lang.ClassNotFoundException;

import static java.lang.System.console;

public class MedievalGame {

    /* Instance Variables */
    Player player;

    /* Main Method */
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        MedievalGame game = new MedievalGame();

        game.player = game.start(console);

        game.addDelay(500);
        System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
        System.out.println(game.player);

        game.addDelay(1000);
        System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
        game.save();

        game.addDelay(2000);
        System.out.println("We just saved your game...");
        System.out.println("Now we are going to try to load your character to make sure the save worked...");

        game.addDelay(1000);
        System.out.println("Deleting character...");
        String charName = game.player.getName();
        game.player = null;

        game.addDelay(1500);
        game.player = game.load(charName, console);
        System.out.println("Loading character...");

        game.addDelay(2000);
        System.out.println("Now let's print out your character again to make sure everything loaded:");

        game.addDelay(500);
        System.out.println(game.player);
    } // End of main

    /* Instance Methods */
    private Player start(Scanner console) {
        // Add start functionality here
        Player player;
        Art.homeScreen();
        System.out.println("Welcome to your latest adventure!");
        System.out.println("Tell me traveler, have you been here before?");
        System.out.print("   Enter 'y' to load a game, 'n' to create a new game: ");

        String answer;
        answer = console.next().toLowerCase(Locale.ROOT);

        while (true){
            if (answer.equals("y")){
                System.out.println("Please enter your previous character name.");
                player = load(console.next(), console);
                break;
            }
            if (answer.equals("n")){
                System.out.println("Please enter your desired character name.");
                player = new Player(console.next());
                break;
            }
            System.out.println("The character typed is not valid. Please enter 'y' to load a game or 'n' to create a new game");
            answer = console.next().toLowerCase(Locale.ROOT);
        }

        return player;
    } // End of start

    private void save() {
        // Add save functionality here
        String fileName = player.getName() + ".svr";

        try {
            FileOutputStream userSaveFile = new FileOutputStream(fileName);
            ObjectOutputStream playerSaver = new ObjectOutputStream(userSaveFile);

            playerSaver.writeObject(player);

        }catch (IOException e) {
            System.out.println("There was an error saving your game, your file might not be available the next time you go to load a game.");
        }
    } // End of save

    private Player load(String playerName, Scanner console) {
        // Add load functionality here
        Player loadedPlayer;

        try{
            FileInputStream userLoadFile = new FileInputStream(playerName + ".svr");
            ObjectInputStream playerLoader = new ObjectInputStream(userLoadFile);

            //loadedPlayer = (Player) playerLoader.readObject();
            loadedPlayer = (Player) playerLoader.readObject();

        }catch(IOException | ClassNotFoundException e){
            addDelay(1500);
            System.out.println("Problems loading the file");
            addDelay(2000);
            loadedPlayer = new Player(playerName);
        }
        return loadedPlayer;
    } // End of load

    // Adds a delay to the console, so it seems like the computer is "thinking"
    // or "responding" like a human, not instantly like a computer.
    private void addDelay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}