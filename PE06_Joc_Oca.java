package Practica6;
import java.util.InputMismatchException;
import java.util.Scanner;
import Practica6.PE06_Joc_Oca;

public class PE06_Joc_Oca {
    public static final String ROIG    = "\u001B[31m";
    public static final String RESET   = "\u001B[0m";

    public static void main(String[] args) {
    PE06_Joc_Oca p = new PE06_Joc_Oca();
    p.principal();
    }

    public void principal () {
        Scanner j = new Scanner(System.in);
        int numJug = demanarJugadors(j);


    }

    public int demanarJugadors(Scanner j) {
        int n = 0;
        boolean correcte = false;

        while (!correcte) {
        try {
            System.out.println("Nombre de jugadors (2 a 4): ");
            n = j.nextInt();
            if (n < 2 || n > 4) {
                System.out.println("Error: han de ser entre 2 i 4.");
            } else {
                correcte = true;
            }
                } catch (InputMismatchException e) {
                    System.out.println(ROIG + "Error: has d'escriure un número enter." + RESET);
                    j.nextLine();
                }
            }
        return n;
    } 
}