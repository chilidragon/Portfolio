import java.util.*;
import java.io.*;

//This progarm is called 20 questions. It lets one of player to choose a secret object
//and the computer will try to find what the object is by asking yes/no questions
public class QuestionsGame {

    //a field that store tree root
    private QuestionNode root; 

    //a field that read input from player
    private Scanner console;

    //a inner class defining the nodes
    private static class QuestionNode {
        public String data;
        public QuestionNode yes;
        public QuestionNode no;

    //a constructor initialize nodes
        public QuestionNode() {
            data = "computer";
        }
    }

    //a constructor start the game
    public QuestionsGame() {
        root = new QuestionNode();
        console = new Scanner(System.in);
    }

    //pre: take scanner input as a parameter
    //post: replace the current tree we read by reading a new 
    //      tree in the file
    public void read(Scanner input) {
        reader(root, input);

    }

    //helper method
    //pre: take questionNode node and scanner input as parameters
    //post：build a tree by reading inputs from scanner input
    private void reader(QuestionNode node, Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        node.data = data;
        if (type.equals("Q:")) {
            node.yes = new QuestionNode();
            reader(node.yes, input);
            node.no = new QuestionNode();
            reader(node.no, input);
        }
    }


    //pre: take PrintStream output as a parameter
    //
    //post: store the current question tree to a file
    public void write(PrintStream output) {
        writer(root, output);
    }

    //helper method
    //pre: take questionNode node and printStream output as parameters
    //post: write question into an output file
    private void writer(QuestionNode node, PrintStream output) {
        if (node.yes != null || node.no != null) {
            output.println("Q:");
        } else {
            output.println("A:");
        }

        output.println(node.data);

        if (node.yes != null || node.no != null) {
            writer(node.yes, output);
            writer(node.no, output);
        }
        
    }

    //play a complete guessing game using the current tree
    public void askQuestions() {
        playGame(root);
    }

    //helper method
    //pre: take QuestionNode node as a parameter
    //post: play a complete game by asking yes or no questions
    //      until the secret object has been found
    private void playGame(QuestionNode node) {
        boolean question = (node.yes != null) || (node.no != null);
        if (question) {
            boolean ansr = yesTo(node.data);
            if (ansr) {
                playGame(node.yes);
            } else {
                playGame(node.no);
            }
        } else {
            boolean win = yesTo("Would your object happen to be " + node.data + "?");
            if (win) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String data = console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String newQuestion = console.nextLine();
                boolean answer = yesTo("And what is the answer for your object?");
                modify(newQuestion, answer, data, node);
            }

        }
        
    }

    //helper method
    //pre: take String newQuestion, boolean answer, 
    //     String data, QuestionNode node as parameters
    //post: change node from an answer node into a question node
    //      and fill its two yes and no answer nodes
    private void modify(String newQuestion, boolean answer, String data, QuestionNode node){
        node.yes = new QuestionNode();
        node.no = new QuestionNode();
        if (answer) {
            node.yes.data = data;
            node.no.data = node.data;
        } else {
            node.no.data = data;
            node.yes.data = node.data;
        }
        node.data = newQuestion;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
