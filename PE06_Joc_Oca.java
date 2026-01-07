package Practica6;
import java.util.InputMismatchException;
import java.util.Scanner;
import Practica6.PE06_Joc_Oca;

public class PE06_Joc_Oca {
    public static final String ROIG    = "\u001B[31m";
    public static final String RESET   = "\u001B[0m";
    public static final String GROC  = "\u001B[33m";
    public static final String VERD  = "\u001B[32m";

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

        // 6. DAUS
        int d1 = tirarDau();
        int d2 = (posicio[jugador] >= 60) ? 0 : tirarDau();
        int suma = d1 + d2;

        System.out.println("Has obtingut " + d1 + " i " + d2 + " = " + suma);

        // 7. MOVIMENT AMB RETROCÉS
        posicio[jugador] += suma;
        if (posicio[jugador] > META) {
            int sobra = posicio[jugador] - META;
            posicio[jugador] = META - sobra;
            System.out.println(GROC + "Has sobrepassat el final, retrocedeixes a " + posicio[jugador] + RESET);
        }

        // 8. COMPROVAR META
        if (posicio[jugador] == META) {
            System.out.println(VERD + noms[jugador] + " HA GUANYAT!" + RESET);
            break;
        }

        int casella = posicio[jugador];
        System.out.println("Casella " + casella);

        boolean repetirTorn = false;
            
        // 9. OCA
        if (esOca(oques, casella)) {
            System.out.println(GROC + "Oca! De oca en oca i tiro perquè em toca." + RESET);
            posicio[jugador] = seguentOca(oques, casella);
            repetirTorn = true;
        }
            
        // 10. PONT
        else if (casella == 6 || casella == 12) {
            System.out.println(GROC + "Pont! Vas a l'altre pont." + RESET);
            posicio[jugador] = (casella == 6 ? 12 : 6);
            repetirTorn = true;
        }

        // 11. FONDA
        else if (casella == 19) {
            System.out.println(GROC + "Fonda! Perds un torn." + RESET);
            penalitzacio[jugador] = 1;
        }

        // 12. DAUS 3-6 / 4-5
        else if (primeraTirada[jugador]) {
            if ((d1 == 3 && d2 == 6) || (d1 == 6 && d2 == 3)) {
                posicio[jugador] = 26;
                System.out.println(VERD + "Daus 3-6! Vas a la 26 i tornes a tirar." + RESET);
                repetirTorn = true;
            }
            if ((d1 == 4 && d2 == 5) || (d1 == 5 && d2 == 4)) {
                posicio[jugador] = 53;
                System.out.println(VERD + "Daus 4-5! Vas a la 53 i tornes a tirar." + RESET);
                repetirTorn = true;
            }
        }

        // 13. POU
            else if (casella == POU) {
                System.out.println(GROC + "Has caigut al pou!" + RESET);
                if (jugadorPou != -1 && jugadorPou != jugador) {
                    penalitzacio[jugadorPou] = 0;
                    System.out.println(GROC + "Un altre jugador surt del pou." + RESET);
                }
                jugadorPou = jugador;
                penalitzacio[jugador] = 2;
            }

        // 14. LABERINT
            else if (casella == 42) {
                System.out.println(GROC + "Laberint! Tornes a la 39." + RESET);
                posicio[jugador] = 39;
            }
        
        // 15. PRESÓ
            else if (casella == 52) {
                System.out.println(GROC + "Presó! Perds 3 torns." + RESET);
                penalitzacio[jugador] = 3;
            }
        
        // 16. MORT
            else if (casella == 58) {
                System.out.println(ROIG + "La mort! Tornes a l'inici." + RESET);
                posicio[jugador] = 0;
            }

        primeraTirada[jugador] = false;

            if (!repetirTorn) {
                torn++;
            }

        }
            
        j.close();
    }

    // ========================== METODES ============================== //

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
        System.out.println("Escriu qualsevol cosa per tirar els daus! ");
        j.next();
    }

    
    public int tirarDau() {
        return (int)(Math.random()*6)+1;
    }

    public boolean esOca(int[] oques, int casella) {
        for (int o : oques) if (o == casella) return true;
        return false;
    }

    public int seguentOca(int[] oques, int casella) {
        for (int o : oques) if (o > casella) return o;
        return casella;
    }
}