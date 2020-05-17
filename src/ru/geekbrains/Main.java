package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] gameField;
    public static int sizeOfField = 3;

    public static void main(String[] args) {
        letsPlay();
    }

        public static void letsPlay(){
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
        if (userSign=='\u004F'){
            System.out.println("Ваш знак - "+userSign+", так что Компьютер ходит первым, вот он и сходил - ");
            computerMove(computerSign);
            countOfMoves = countOfMoves + 1;
        } else
            System.out.println("Ваш знак - "+userSign+", так что ваш ход первый.");
        printOurField();
        do {
            yourMove(userSign);
            countOfMoves = countOfMoves + 1;
            if (countOfMoves == 9) {
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
            if (countOfMoves == 9) {
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
        for (int i = 0; i < sizeOfField; i++) {
            if (gameField[i][1] != '\u00B7' && gameField[i][0] == gameField[i][1] && gameField[i][0] == gameField[i][2])
                return true;
            for (int j = 1; j < sizeOfField; j++) {
                if (gameField[0][j] != '\u00B7' && gameField[0][j] == gameField[1][j] && gameField[0][j] == gameField[2][j])
                    return true;
            }
        }
        int tempotempo = 0; // проверка первой диагонали
        for (int i = 0; i < sizeOfField; i++) {
            if (gameField[i][i] == gameField[0][0] && gameField[0][0] != '\u00B7') {
                tempotempo = tempotempo + 1;
                if (tempotempo == 3) {
                    return true;
                }
            }

        }

        int tempotempo2 = 0;  // проверка второй диагонали
        for (int i = sizeOfField - 1; i >= 0; i--) {
            if (gameField[i][sizeOfField - 1 - i] == gameField[1][1] && gameField[1][1] != '\u00B7') {
                tempotempo2 = tempotempo2 + 1;
                if (tempotempo2 == 3) {
                    return true;
                }
            }
        }
        return false;
    }
}


