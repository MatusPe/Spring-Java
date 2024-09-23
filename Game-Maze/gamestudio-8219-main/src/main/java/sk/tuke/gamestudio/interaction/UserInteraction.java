package sk.tuke.gamestudio.interaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.Maze.core.Commander;
import sk.tuke.gamestudio.service.*;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class UserInteraction {


    private Scanner scanner=new Scanner(System.in);
    public UserInteraction(){

    }



    public int Mazewidth(){
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      WELCOME TO THE MAZE GAME                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        RulesInformation();
        int number = 0;
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║               Enter from 5 to 11 number that is odd               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        //System.out.println("Enter from 4 to 13 number that is odd");
        //int number;
        while (true)
            try{
                number = Integer.parseInt(scanner.nextLine());
                if (number >= 5 && number <= 12 && number % 2 != 0) {
                    break; // Exit the loop if the input is valid
                } else {
                    System.out.println("Invalid width. Please enter a number from 5 to 12, odd.");
                }

            }catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
        }return number;
    }
    public boolean DirectionFromTerminal(Commander commander){
        try {
            if (System.in.available() > 0) {
                String input = scanner.nextLine();
                // Process input here
                System.out.println("Input received: " + input);
                if(Objects.equals(input.toLowerCase(), "rules")){
                    RulesInformation();
                }
                commander.TerminalCordination(input);
                return  true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void RulesInformation(){
        int index=1;
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.printf("║%d Your goal is to reach flag at bottom-right  corner               ╣\n", index++);
        System.out.printf("║%d You can not pass walls and                                       ╣\n", index++);
        System.out.printf("║%d There is light out in Maze, so can see only one tile around you  ╣\n", index++);
        System.out.printf("║%d There are two ways of playing game, in windows and in Terminal   ╣\n", index++);
        System.out.printf("║%d You can use key A=left, W=up, S=down, D=right in windows         ╣\n", index++);
        System.out.printf("║%d In terminal * means unknow, # wall, space floor, $ flag          ╣\n", index++);
        System.out.printf("║%d You can use Terminal to move, type left, up, down or right       ╣\n", index++);
        System.out.printf("║%d Type rules if you want show rules of the game                    ╣\n", index++);
        System.out.printf("║%d Type any of letter to reload map from window                     ╣\n", index++);
        System.out.printf("║%d You have 20 second to find flag                                 ╣\n",index);
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }
    public void AfterWin(String Username, int Score){
        //System.out.println("Congratulation, your score was :"+Score);


        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.printf("             Congratulation, your score was : %d              \n", Score);
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");


        //System.out.println("LeaderBoard:");
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            LeaderBoard:                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");

        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              Player       Game      point      Date               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        //System.out.println("Player Game point Date");

    }


}
