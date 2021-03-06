package ohtu.kivipaperisakset;


// Kivi-Paperi-Sakset, jossa voidaan valita pelataanko vastustajaa
// vastaan vai ei
public class KPSParempiTekoaly extends KPS {

    private static final TekoalyParannettu tekoaly = new TekoalyParannettu(20);

//    public void pelaa() {
//        Tuomari tuomari = new Tuomari();
//        TekoalyParannettu tekoaly = new TekoalyParannettu(20);
//
//        System.out.print("Ensimmäisen pelaajan siirto: ");
//        String ekanSiirto = scanner.nextLine();
//        String tokanSiirto;
//
//
//        tokanSiirto = tekoaly.annaSiirto();
//        System.out.println("Tietokone valitsi: " + tokanSiirto);
//
//
//        while (onkoOkSiirto(ekanSiirto) && onkoOkSiirto(tokanSiirto)) {
//            tuomari.kirjaaSiirto(ekanSiirto, tokanSiirto);
//            System.out.println(tuomari);
//            System.out.println();
//
//            System.out.print("Ensimmäisen pelaajan siirto: ");
//            ekanSiirto = scanner.nextLine();
//
//            tokanSiirto = tekoaly.annaSiirto();
//            System.out.println("Tietokone valitsi: " + tokanSiirto);
//            tekoaly.asetaSiirto(ekanSiirto);
//
//        }
//
//        System.out.println();
//        System.out.println("Kiitos!");
//        System.out.println(tuomari);
//    }
    @Override
    protected String tokanSiirto() {
        return tekoaly.annaSiirto();
    }

    @Override
    protected void tokansiirtoIlmoitus(String ekanSiirto, String tokanSiirto) {
        System.out.println("Tietokone valitsi: " + tokanSiirto);
        tekoaly.asetaSiirto(ekanSiirto);
    }

}
