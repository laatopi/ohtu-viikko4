/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import javax.swing.JTextField;

/**
 *
 * @author laatopi
 */
class Summa implements Komento {

    private final Sovelluslogiikka sovellus;
    private final JTextField tuloskentta;
    private final JTextField syotekentta;
    private int edellinen;

    public Summa(Sovelluslogiikka sovellus, JTextField tuloskentta, JTextField syotekentta) {
        this.sovellus = sovellus;
        this.tuloskentta = tuloskentta;
        this.syotekentta = syotekentta;
    }

    @Override
    public void suorita() {
        try {
            sovellus.plus(Integer.parseInt(syotekentta.getText()));
            this.edellinen = Integer.parseInt(tuloskentta.getText());
        } catch (Exception e) {
        }
        syotekentta.setText("");
        tuloskentta.setText("" + sovellus.tulos());

    }

    @Override
    public void peru() {
        tuloskentta.setText("" + this.edellinen);
    }

}
