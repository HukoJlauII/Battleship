import java.util.Scanner;

public class GameBoard {
    private final static int SIZE = 10;
    private char[][] board;

    private static final char EMPTY = '-';
    private static final char SHIP = '#';
    private static final char HIT = 'X';
    private static final char MISS = '*';

    public GameBoard() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void printBoard() {
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < SIZE; i++) {
            if ((i + 1) / 10 == 0) {
                System.out.print(i + 1 + "  ");
            } else {
                System.out.print(i + 1 + " ");
            }
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printBoardForEnemy() {
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < SIZE; i++) {
            if ((i + 1) / 10 == 0) {
                System.out.print(i + 1 + "  ");
            } else {
                System.out.print(i + 1 + " ");
            }
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY || board[i][j] == SHIP) {
                    System.out.print("# ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void placeShips(Scanner scanner) {

        for (int i = 4; i > 0; i--) {
            for (int j = 0; j < 5 - i; j++) {
                boolean isPlaced = false;
                while (!isPlaced) {
                    isPlaced = choosePlaceForShip(scanner, i);
                    scanner.nextLine();
                }
            }
        }
    }

    public boolean choosePlaceForShip(Scanner scanner, int length) {
        System.out.print("Выберите координату для расстановки " + length + "-х палбуного корабля(Например A1): ");
        String coordinates = scanner.nextLine().toUpperCase();
        int x = coordinates.charAt(0) - 'A';
        int y = Integer.parseInt(coordinates.substring(1)) - 1;
        System.out.print("Выберите ориентацию корабля: 1 - горизонтально, 2 - вертикально ");
        int orient = scanner.nextInt();
        boolean isHorizontal = orient == 1;
        if (checkPlace(x, y, length, isHorizontal)) {
            placeShip(x, y, length, isHorizontal);
            return true;
        } else {
            System.out.println("Корабль не может стоять в выбранном вами месте");
            return false;
        }
    }

    public boolean checkPlace(int x, int y, int length, boolean isHorizontal) {
        if (isHorizontal) {
            if (x + length > SIZE) { //корабль помещается горизонтально по размерам доски
                return false;
            }
            switch (y) {
                case 0 -> { //корабль у верхнего края
                    if (x != 0 //корабль не прижат к левому краю
                            && (board[y][x - 1] == SHIP //проверка левой клетки от края корабля
                            || board[y + 1][x - 1] == SHIP) //проверка нижней диагональной левой клетки от края корабля
                            || x + length != SIZE //корабль не прижат к правому краю
                            && (board[y][x + length] == SHIP //проверка правой клетки от края корабля
                            || board[y + 1][x + length] == SHIP)) { //проверка нижней диагональной правой клетки от края корабля
                        return false;
                    }
                    for (int i = x; i < x + length; i++) {
                        if (board[y][i] == SHIP || board[y + 1][i] == SHIP) { // прокерка самой клетки и клетки,которая ниже неё
                            return false;
                        }
                    }
                }
                case SIZE - 1 -> { //корабль у нижнего края
                    if (x != 0 //корабль не прижат к левому краю
                            && (board[y][x - 1] == SHIP //проверка левой клетки от края корабля
                            || board[y - 1][x - 1] == SHIP) //проверка верхней диагональной левой клетки от края корабля
                            || x + length != SIZE //корабль не прижат к правому краю
                            && (board[y][x + length] == SHIP //проверка правой клетки от края корабля
                            || board[y - 1][x + length] == SHIP)) { //проверка верхней диагональной правой клетки от края корабля
                        return false;
                    }
                    for (int i = x; i < x + length; i++) {
                        if (board[y][i] == SHIP || board[y - 1][i] == SHIP) { // прокерка самой клетки и клетки,которая выше неё
                            return false;
                        }
                    }
                }
                default -> { //корабль не прижат к верхнему или нижнему краю
                    if (x != 0 //корабль не прижат к левому краю
                            && (board[y][x - 1] == SHIP //проверка левой клетки от края корабля
                            || board[y - 1][x - 1] == SHIP //проверка верхней диагональной левой клетки от края корабля
                            || board[y + 1][x - 1] == SHIP) //проверка нижней диагональной левой клетки от края корабля
                            || x + length != SIZE //корабль не прижат к правому краю
                            && (board[y][x + length] == SHIP //проверка правой клетки от края корабля
                            || board[y - 1][x + length] == SHIP //проверка верхней диагональной правой клетки от края корабля
                            || board[y + 1][x + length] == SHIP)) { //проверка нижней диагональной правой клетки от края корабля
                        return false;
                    }
                    for (int i = x; i < x + length; i++) {
                        if (board[y][i] == SHIP || board[y - 1][i] == SHIP || board[y + 1][i] == SHIP) { // прокерка самой клетки и клеткок,которые выше и ниже
                            return false;
                        }
                    }
                }
            }
        } else {
            if (y + length > SIZE) {//корабль помещается вертикально по размерам доски
                return false;
            }
            switch (x) {
                case 0 -> { //корабль у левого края
                    if (y != 0 //корабль не прижат к верхнему краю
                            && (board[y - 1][x] == SHIP //проверка верхней клетки от края корабля
                            || board[y - 1][x + 1] == SHIP) //проверка верхней диагональной правой клетки от края корабля
                            || y + length != SIZE //корабль не прижат к нижнему краю
                            && (board[y + length][x] == SHIP //проверка нижней клетки от края корабля
                            || board[y + length][x + 1] == SHIP)) { //проверка нижней диагональной правой клетки от края корабля
                        return false;
                    }
                    for (int i = y; i < y + length; i++) {
                        if (board[i][x] == SHIP || board[i][x + 1] == SHIP) {
                            return false; // прокерка самой клетки и клетки,которая правее неё
                        }
                    }
                }
                case SIZE - 1 -> { //корабль у правого края
                    if (y != 0 //корабль не прижат к верхнему краю
                            && (board[y - 1][x] == SHIP //проверка верхней клетки от края корабля
                            || board[y - 1][x - 1] == SHIP) //проверка верхней диагональной левой клетки от края корабля
                            || y + length != SIZE //корабль не прижат к нижнему краю
                            && (board[y + length][x] == SHIP //проверка нижней клетки от края корабля
                            || board[y + length][x - 1] == SHIP)) { //проверка нижней диагональной левой клетки от края корабля
                        return false;
                    }
                    for (int i = y; i < y + length; i++) {
                        if (board[i][x] == SHIP || board[i][x - 1] == SHIP) { // прокерка самой клетки и клетки,которая левее неё
                            return false;
                        }
                    }
                }
                default -> { //корабль не прижат к левому или правому краю
                    if (y != 0 //корабль не прижат к верхнему краю
                            && (board[y - 1][x] == SHIP //проверка верхней клетки от края корабля
                            || board[y - 1][x - 1] == SHIP //проверка верхней диагональной левой клетки от края корабля
                            || board[y - 1][x + 1] == SHIP) //проверка верхней диагональной правой клетки от края корабля
                            || y + length != SIZE //корабль не прижат к нижнему краю
                            && (board[y + length][x] == SHIP //проверка нижней клетки от края корабля
                            || board[y + length][x - 1] == SHIP //проверка нижней диагональной левой клетки от края корабля
                            || board[y + length][x + 1] == SHIP)) { //проверка нижней диагональной правой клетки от края корабля
                        return false;
                    }
                    for (int i = y; i < y + length; i++) {
                        if (board[i][x] == SHIP || board[i][x - 1] == SHIP || board[i][x + 1] == SHIP) { // прокерка самой клетки и клеткок,которые справа и слева
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeShip(int x, int y, int length, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = x; i < x + length; i++) {
                board[y][i] = SHIP;
            }
        } else {
            for (int i = y; i < y + length; i++) {
                board[i][x] = SHIP;
            }
        }
        printBoard();
    }

    public boolean makeAttack(Scanner scanner) {
        boolean isRightAttack = false;
        int x = 0;
        int y = 0;
        while (!isRightAttack) {
            System.out.print("Выберите клетку для атаки (Например A1): ");
            String coordinates = scanner.nextLine().toUpperCase();
            x = coordinates.charAt(0) - 'A';
            y = Integer.parseInt(coordinates.substring(1)) - 1;
            if (board[y][x] == SHIP || board[y][x] == EMPTY) {
                isRightAttack = true;
            } else {
                System.out.println("Вы уже атаковали данную клетку");
            }
        }

        if (board[y][x] == SHIP) {
            System.out.println("Попал!");
            board[y][x] = HIT;
            return true;
        } else {
            System.out.println("Промазал!");
            board[y][x] = MISS;
            return false;
        }
    }


    public boolean isAllShipsSunk() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }



}
