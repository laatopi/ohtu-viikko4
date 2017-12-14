/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.kivipaperisakset;
import java.util.Scanner;

/**
 *
 * @author laatopi
 */
public abstract class KPS {

    private static final Scanner scanner = new Scanner(System.in);
    
    public static KPS luoKaksinpeli(){
        return new KPSPelaajaVsPelaaja();
    }
    
    public static KPS luoTekoalyPeli(){
        return new KPSTekoaly();
    }
    
    public static KPS luoVaikeaTekoalyPeli(){
        return new KPSParempiTekoaly();
    }
    
    public void pelaa() {
        Tuomari tuomari = new Tuomari();
        System.out.println("jeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println("JEEEEEEE");
        System.out.println("Ensimmäisen pelaajan siirto: ");
        
        String ekanSiirto = scanner.nextLine();
        System.out.println("");
        String tokanSiirto = tokanSiirto();

        tokansiirtoIlmoitus(ekanSiirto, tokanSiirto);


        while (onkoOkSiirto(ekanSiirto) && onkoOkSiirto(tokanSiirto)) {
            tuomari.kirjaaSiirto(ekanSiirto, tokanSiirto);
            System.out.println(tuomari);
            System.out.println();

            System.out.print("Ensimmäisen pelaajan siirto: ");
            ekanSiirto = scanner.nextLine();

            tokanSiirto = tokanSiirto();
            tokansiirtoIlmoitus(ekanSiirto, tokanSiirto);
        }

        System.out.println();
        System.out.println("Kiitos!");
        System.out.println(tuomari);

    }
    
    private static boolean onkoOkSiirto(String siirto) {
        return "k".equals(siirto) || "p".equals(siirto) || "s".equals(siirto);
    }
   
    protected abstract String tokanSiirto();
    
    protected abstract void tokansiirtoIlmoitus(String ekanSiirto, String tokanSiirto);

}
