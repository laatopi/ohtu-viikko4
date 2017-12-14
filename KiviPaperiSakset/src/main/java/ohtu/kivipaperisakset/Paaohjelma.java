package ohtu.kivipaperisakset;

import java.util.Scanner;

public class Paaohjelma {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nValitse pelataanko"
                    + "\n (a) ihmistä vastaan "
                    + "\n (b) tekoälyä vastaan"
                    + "\n (c) parannettua tekoälyä vastaan"
                    + "\nmuilla valinnoilla lopetataan");

            KPS peli = null;
            Boolean peliloppu = false;
            String vastaus = scanner.nextLine();
            if (vastaus.endsWith("a")) {
                peli = KPS.luoKaksinpeli();
            } else if (vastaus.endsWith("b")) {
                peli = KPS.luoTekoalyPeli();
            } else if (vastaus.endsWith("c")) {
                peli = KPS.luoVaikeaTekoalyPeli();
            } else {
                peliloppu = true;
            }
            if (peliloppu) {
                break;
            }
            System.out.println("peli loppuu kun pelaaja antaa virheellisen siirron eli jonkun muun kuin k, p tai s");
            peli.pelaa();
        }

    }
}
