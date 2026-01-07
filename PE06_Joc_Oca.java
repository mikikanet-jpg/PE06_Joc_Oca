package Practica6;
import java.util.InputMismatchException;
import java.util.Scanner;
import Practica6.PE06_Joc_Oca;

public class PE06_Joc_Oca {
    public static final String ROIG    = "\u001B[31m";
    public static final String RESET   = "\u001B[0m";

    //CONSTANST
    public static final int META = 63;
    public static final int POU = 31;

    public static void main(String[] args) {
    PE06_Joc_Oca p = new PE06_Joc_Oca();
    p.principal();
    }

    public void principal () {
        //1. Demanar Jugadors
        Scanner j = new Scanner(System.in);
        int numJug = demanarJugadors(j);

        //2. Dades dels jugasors (Amb arrays)
        String[] noms = new String[numJug];
        int[] posicio = new int[numJug];
        int[] penalitzacio = new int[numJug];
        boolean[] primeraTirada = new boolean[numJug];

        for(int i = 0; i < numJug; i++) {
            primeraTirada[i] = true;
            posicio[i] = 0;
            penalitzacio[i] = 0;
        }

        //3. Demanar noms 
        demanarNoms(j, noms);

        //Oques
        int[] oques = {5,9,14,18,23,27,32,36,41,45,50,54,59};

        boolean guanyador = false;
        int torn = 0;
        int jugadorPou = -1;

        //4. Bucle Principal
        while (!guanyador) {
            
            int jugador = torn % numJug;

            System.out.println("\n--------------------------------");
            System.out.println("Torn del jugador " + (jugador+1) + ": " + noms[jugador]);
            
            //5. Penalitzacions
            if (penalitzacio[jugador] > 0) {
                System.out.println(ROIG + "No pots tirar. Torns restants: " + penalitzacio[jugador] + RESET);
                penalitzacio[jugador]--;
                torn++;
                continue;
            }

            esperarTiro(j);
        }

    }

    public int demanarJugadors(Scanner j) {
        int n = 0;
        boolean correcte = false;

        while (!correcte) {
        try {
            System.out.println("Nombre de jugadors (2 a 4): ");
            n = j.nextInt();
            if (n < 2 || n > 4) {
                System.out.println(ROIG + "Error: han de ser entre 2 i 4." + RESET);
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

    public void demanarNoms(Scanner j, String[] noms) {
        for(int i = 0; i < noms.length; i++) {
            System.out.println("Nom jugador " + (i+1) + ": ");
            noms[i] = j.next();
        }
    }

    public void esperarTiro(Scanner j) {
        System.out.println("Escriu 'tiro': ");
        j.next();
    }


}