package ohtu;

import ohtu.verkkokauppa.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void tiliSiirtoaKutsutaanOikeillaParametreilla() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

    //aloitetaan asiointi, koriin lisätään kaksi eri tuotetta, 
    //joita varastossa on ja suoritetaan ostos. varmistettava että kutsutaan pankin 
    //metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void tiliSiirtoToimiiKahdellaEriTuotteella() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "raivo", 10));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 15);
    }

    //aloitetaan asiointi, koriin lisätään kaksi samaa tuotetta 
    //jota on varastossa tarpeeksi ja suoritetaan ostos. varmistettava 
    //että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void kahdenSamanTuotteenTilisiirtoOnnistuu() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);
    }

    // aloitetaan asiointi, koriin lisätään tuote jota on varastossa tarpeeksi 
    //ja tuote joka on loppu ja suoritetaan ostos. varmistettava 
    //että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void loppuneenTuotteenTilisiirtoOnnistuu() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "raivo", 10));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

    //varmistettava, että metodin aloitaAsiointi 
    //kutsuminen nollaa edellisen ostoksen tiedot (eli edellisen ostoksen hinta
    //ei näy uuden ostoksen hinnassa), katso tarvittaessa apua projektin MockitoDemo testeistä!
    @Test
    public void aloitAsiointiNollaaTiedot() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "raivo", 8));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 8);
    }

    //    varmistettava, että kauppa pyytää uuden viitenumeron jokaiselle 
    // maksutapahtumalle, katso tarvittaessa apua projektin
    // MockitoDemo testeistä!
    @Test
    public void uusiViiteNumeroJokaisellaMaksuTapahtumalla() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42).thenReturn(43).thenReturn(44);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "raivo", 8));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 43, "12345", "33333-44455", 8);
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 44, "12345", "33333-44455", 8);
    }

    @Test
    public void poistaKoristaToimiiOikein() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        k.tilimaksu("pekka", "12345");
        //String nimi, int viitenumero, String tililta, String tilille, int summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

}
