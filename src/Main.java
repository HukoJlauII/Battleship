import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard player1 = new GameBoard();
        Scanner scanner = new Scanner(System.in);
        GameBoard player2 = new GameBoard();
        player1.initGame(scanner, 1);
        player2.initGame(scanner, 2);
        GameBoard.startGame(player1, player2, scanner);

    }
}
