import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard player1 = new GameBoard();
        Scanner scanner = new Scanner(System.in);
        player1.printBoard();
        System.out.println("Выберите расстановку игрока 1:");
        player1.placeShips(scanner);
        GameBoard player2 = new GameBoard();
        player2.printBoard();
        System.out.println("Выберите расстановку игрока 2:");
        player2.placeShips(scanner);
        boolean isFirstPlayerAttack = true;
        while (!player1.isAllShipsSunk() && !player2.isAllShipsSunk()) {
            boolean resultOfAttack;
            if (isFirstPlayerAttack) {
                System.out.println("Ход игрока 1");
                player2.printBoardForEnemy();
                resultOfAttack = player2.makeAttack(scanner);
                if (player2.isAllShipsSunk()) {
                    System.out.println("Игрок 1 победил!");
                    break;
                }
                if (!resultOfAttack)
                    isFirstPlayerAttack = false;
            } else {
                System.out.println("Ход игрока 2");
                player1.printBoardForEnemy();
                resultOfAttack = player1.makeAttack(scanner);
                if (player1.isAllShipsSunk()) {
                    System.out.println("Игрок 2 победил!");
                    break;
                }
                if (!resultOfAttack)
                    isFirstPlayerAttack = true;
            }

        }
    }
}
