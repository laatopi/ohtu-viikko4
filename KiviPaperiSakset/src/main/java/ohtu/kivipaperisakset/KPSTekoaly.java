package ohtu.kivipaperisakset;


public class KPSTekoaly extends KPS {

    private static final Tekoaly tekoaly = new Tekoaly();

//    public void pelaa() {
//        Tuomari tuomari = new Tuomari();
//        Tekoaly tekoaly = new Tekoaly();
//
//        System.out.print("Ensimmäisen pelaajan siirto: ");
//        String ekanSiirto = scanner.nextLine();
//        String tokanSiirto;
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
