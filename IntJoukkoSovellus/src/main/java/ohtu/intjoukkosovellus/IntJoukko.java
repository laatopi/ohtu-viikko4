package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    private int[] lukujononNollaus() {
        for (int i = 0; i < this.lukujono.length; i++) {
            this.lukujono[i] = 0;
        }
        return this.lukujono;
    }

    public IntJoukko() {
        lukujono = new int[KAPASITEETTI];
        lukujononNollaus();
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        lukujono = new int[kapasiteetti];
        lukujononNollaus();
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            return;
        }
        if (kasvatuskoko < 0) {
            return;
        }
        lukujono = new int[kapasiteetti];
        lukujononNollaus();
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            lukujono[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm % lukujono.length == 0) {
                suurennaTaulukkoa();
            }
            return true;
        }
        return false;
    }

    public void suurennaTaulukkoa() {
        int[] taulukkoOld = new int[lukujono.length];
        taulukkoOld = lukujono;
        kopioiTaulukko(lukujono, taulukkoOld);
        lukujono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, lukujono);
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return true;
            }
        }
        return false;
    }

    public int haeLuvunSijainti(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean poista(int luku) {
        int kohta = haeLuvunSijainti(luku);
        if (kohta != -1) {
            for (int j = kohta; j < alkioidenLkm - 1; j++) {
                lukujono[j] = lukujono[j + 1];
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + lukujono[0] + "}";
        }
        return lukujononPitkaEsitys();
    }

    public String lukujononPitkaEsitys() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += lukujono[i];
            tuotos += ", ";
        }
        tuotos += lukujono[alkioidenLkm - 1];
        tuotos += "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(lukujono, 0, taulu, 0, taulu.length);
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            x.lisaa(a.lukujono[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            x.lisaa(b.lukujono[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (a.lukujono[i] == b.lukujono[j]) {
                    leikkaus.lisaa(b.lukujono[j]);
                }
            }
        }
        return leikkaus;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            erotus.lisaa(a.lukujono[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            erotus.poista(i);
        }
        return erotus;
    }

}
