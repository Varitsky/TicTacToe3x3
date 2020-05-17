package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] gameField;
    public static int sizeOfField = 5;

    public static void main(String[] args) {
        letsPlay();
    }

    public static void letsPlay() {
        char[] signs = yourSign();
        playTicTacToe(signs[0], signs[1]);
    }

    public static char[] yourSign() {
        char[] signs = new char[2];
        Random rand = new Random();
        int x = new Random().nextInt(2);
        if (x == 0) {
            signs[0] = '\u0058';
            signs[1] = '\u004F';
        } else {
            signs[0] = '\u004F';
            signs[1] = '\u0058';
        }
        return signs;
    }


    public static void playTicTacToe(char userSign, char computerSign) {
        loadOurEmptyFieldWithSize();
        int countOfMoves = 0;
        if (userSign == '\u004F') {
            System.out.println("Ваш знак - " + userSign + ", так что Компьютер ходит первым, вот он и сходил - ");
            computerMove(computerSign);
            countOfMoves = countOfMoves + 1;
        } else
            System.out.println("Ваш знак - " + userSign + ", так что ваш ход первый.");
        printOurField();
        do {
            yourMove(userSign);
            countOfMoves = countOfMoves + 1;
            if (countOfMoves == sizeOfField * sizeOfField) {
                if (theWinnerIsFound(gameField)) {
                    printOurField();
                    System.out.println("Победа за тобой");
                    break;
                }
                System.out.println("Место кончилось, победитель не выявлен");
                printOurField();
                break;
            }
            if (theWinnerIsFound(gameField)) {
                printOurField();
                System.out.println("Победа за тобой");
                break;
            }
            computerMove(computerSign);
            printOurField();
            countOfMoves = countOfMoves + 1;
            if (countOfMoves == sizeOfField * sizeOfField) {
                if (theWinnerIsFound(gameField)) {
                    printOurField();
                    System.out.println("Победа за тобой");
                    break;
                }
                System.out.println("Место кончилось, победитель не выявлен");
                printOurField();
                break;
            }
            if (theWinnerIsFound(gameField)) {
                printOurField();
                System.out.println("Победа за рандомом");
                break;
            }
        } while (true);

    }

    static void loadOurEmptyFieldWithSize() {
        gameField = new char[sizeOfField][sizeOfField];
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                gameField[i][j] = '\u00B7';
            }
        }
    }

    static void printOurField() {
        System.out.println();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean checkTheCell(int x, int y) {
        if (gameField[y][x] == '\u00B7') return true;
        return false;
    }

    public static void yourMove(char userSign) {
        int x, y;
        do {
            x = yourMoveX();
            y = yourMoveY();
            if (!checkTheCell(x, y)) {
                System.out.println("\n" + "\n" + "\n" + "\n" + "\n" + "Клетка уже занята!");
                System.out.println("Вот что у нас сейчас:");
                printOurField();
            }
        } while (!checkTheCell(x, y));
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n" + "\n");
        gameField[y][x] = userSign;
    }

    static int yourMoveX() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите координату по X, от 0 до " + (gameField.length - 1) + ": ");
        int x = -1;
        while (x < 0 || x > gameField.length - 1) {
            x = scanner.nextInt();
            if (x < 0 || x > gameField.length - 1) {
                System.out.print("Вы ввели неверное число, попробуйте от 0 до " + (gameField.length - 1) + ": ");
            }
        }
        return x;
    }

    static int yourMoveY() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите координату по Y, от 0 до " + (gameField.length - 1) + ": ");
        int y = -1;
        while (y < 0 || y > gameField.length - 1) {
            y = scanner.nextInt();
            if (y < 0 || y > gameField.length - 1) {
                System.out.print("Вы ввели неверное число, попробуйте от 0 до " + (gameField.length - 1) + ": ");
            }
        }
        return y;
    }

    public static void computerMove(char computerSign) {
        int x, y;
        do {
            Random rand = new Random();
            x = rand.nextInt(sizeOfField);
            y = rand.nextInt(sizeOfField);
        } while (!checkTheCell(x, y));
        gameField[y][x] = computerSign;
    }

    static boolean theWinnerIsFound(char[][] gameField) {
        int horizonalLineScore = 0;
        int verticlLineScore = 0;
        for (int h = 0; h < 2; h++) {
            for (int i = 0+h; i < sizeOfField-1+h; i++) {
                horizonalLineScore=0;
                for (int j = 0+h; j < sizeOfField-1+h; j++) {
                    if (gameField[i][j]==gameField[i][i] && gameField[i][i]!='\u00B7'){
                        horizonalLineScore=horizonalLineScore+1;
                        if (horizonalLineScore==4){
                            return true;
                        }
                    }
                }
            }

            for (int i = 0+h; i < sizeOfField-1+h; i++) {
                verticlLineScore=0;
                for (int j = 0+h; j < sizeOfField-1+h; j++) {
                    if (gameField[j][i]==gameField[i][i] && gameField[i][i]!='\u00B7'){
                        verticlLineScore=verticlLineScore+1;
                        if (verticlLineScore==4){
                            return true;
                        }
                    }
                }
            }
        }

        // Я разбиваю квадрат 5 на 5 на два квадрата 4 на 4, и считаю 4 очка в них.
        // Во время совмещение квадрата 4 на 4 со стартовой 0;0 и квадрата 4 на 4 со стартовой 1;1
        // в наш диапазон не входят две угловые точки 0;4 и и 4;0
        // я верю что их возможно как-то внести в диапазон, но на это у меня просто не хвататит времени.
        // но я этот факт осознаю и вот он отвратительный код на 4 оставишихся ситуации:

        if (gameField[0][1]==gameField[0][2] && gameField[0][1]==gameField[0][3]
                && gameField[0][1]==gameField[0][4] && gameField[0][1]!='\u00B7')
            return true;
        if (gameField[1][0]==gameField[2][0] && gameField[1][0]==gameField[3][0]
                && gameField[1][0]==gameField[4][0] && gameField[1][0]!='\u00B7')
            return true;
        if (gameField[4][1]==gameField[4][2] && gameField[4][1]==gameField[4][3]
                && gameField[4][1]==gameField[4][4] && gameField[4][1]!='\u00B7')
            return true;;
        if (gameField[1][4]==gameField[2][4] && gameField[1][4]==gameField[3][4]
                && gameField[1][4]==gameField[4][4] && gameField[1][4]!='\u00B7')
            return true;


        int firstDiagonalScore = 0; // проверка первой диагонали
        for (int j = 0; j < 2; j++) { // проверяем сумму 4 элементов идущих подряд из четырёх, 2 раза, сдвигая диапазон на 1 шаг по диагонали
            firstDiagonalScore = 0;
            for (int i = 0 + j; i < sizeOfField - 1 + j; i++) {
                if (gameField[i][i] == gameField[0][0] && gameField[0][0] != '\u00B7') {
                    firstDiagonalScore = firstDiagonalScore + 1;
                    if (firstDiagonalScore == 4) {
                        return true;
                    }
                }
            }
        }

        int secondDiagonalScore = 0;  // проверка второй диагонали
        for (int j = 0; j < 2; j++) { // проверяем сумму 4 элементов идущих подряд из четырёх, 2 раза, сдвигая диапазон на 1 шаг по диагонали
            secondDiagonalScore = 0;
            for (int i = sizeOfField - 2 + j; i >= 0 + j; i--) {
                if (gameField[i][sizeOfField - 1 - i] == gameField[2][2] && gameField[2][2] != '\u00B7') {
                    secondDiagonalScore = secondDiagonalScore + 1;
                    if (secondDiagonalScore == 4) {
                        return true;
                    }
                }
            }
        }
//            for (int i = sizeOfField - 1; i >= 0; i--) {
//                if (gameField[i][sizeOfField - 1 - i] == gameField[2][2] && gameField[2][2] != '\u00B7') {
//                    secondDiagonalScore = secondDiagonalScore + 1;
//                    if (secondDiagonalScore == 4) {
//                        return true;
//                    }
//                }
//
//            }
        return false;
    }

}



