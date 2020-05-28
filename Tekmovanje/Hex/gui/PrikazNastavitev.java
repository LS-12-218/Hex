package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import glavno.Igralec;
import glavno.Nadzornik;
import inteligenca.*;

public class PrikazNastavitev extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 5L;
	
	boolean jeClovekRdeci = true;
	boolean jeClovekModri = true;
	int tezavnostRdeci = 1;
	int tezavnostModri = 1;
	int velikost = 11;
	JDialog prikaz;
	JButton zacni;
	
	NastavitveIgralec p1;
	NastavitveIgralec p2;
	NastavitveVelikost p3;
	JPanel p4 = new JPanel();
	
	public PrikazNastavitev() {
		if (Nadzornik.igralec[0] != null && !Nadzornik.igralec[0].jeClovek) {
			jeClovekRdeci = false;
			tezavnostRdeci = Nadzornik.igralec[0].tezavnost;
		}
		if (Nadzornik.igralec[1] != null && !Nadzornik.igralec[1].jeClovek) {
			jeClovekModri = false;
			tezavnostModri = Nadzornik.igralec[1].tezavnost;
		}
		if (Nadzornik.trenutnaIgra != null) velikost = Nadzornik.trenutnaIgra.velikost;
		
		this.setLayout(new GridBagLayout());
		p1 = new NastavitveIgralec(this, true);
		p2 = new NastavitveIgralec(this, false);
		p3 = new NastavitveVelikost(this);
		
		zacni = new JButton("ZAÈNI IGRO");
		zacni.setFont(new Font("zacni", Font.PLAIN, 30));
		zacni.addActionListener(this);
		p4.add(zacni);
		
		var a = new GridBagConstraints();
		a.gridx = 0; a.gridy = 0; a.fill = GridBagConstraints.BOTH; a.weightx = 0.5; a.weighty = 0.6; this.add(p1, a);
		var b = new GridBagConstraints();
		b.gridx = 1; b.gridy = 0; b.fill = GridBagConstraints.BOTH; b.weightx = 0.5; b.weighty = 0.6; this.add(p2, b);
		var c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 1; c.fill = GridBagConstraints.NONE; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER; c.weightx = 1.0; c.weighty = 0.2; this.add(p3, c);
		var d = new GridBagConstraints();
		d.gridx = 0; d.gridy = 2; d.fill = GridBagConstraints.NONE; d.gridwidth = 2; d.anchor = GridBagConstraints.CENTER; d.weightx = 1.0; d.weighty = 0.2; this.add(p4, d);
	}
	
	private Igralec novIgralec(int tezavnost) {
		if (tezavnost == 1) return new Igralec(new MMAB(1, 2, 1, 1), new OcenaTretine(100, 125, 125, 1.75f), tezavnost);
		else if (tezavnost == 2) return new Igralec(new MMAB(1, 2, 1, 1), new PreprostaOcena(), tezavnost);
		else if (tezavnost == 5) {MMAB a = new MMAB(8, 12, 2, 3); a.napredno = true; return new Igralec(a, new OcenaTretine(1000, 400, 200, 1.05f), tezavnost);}
		else return new Igralec(new MMAB((tezavnost - 1) * 2, (tezavnost + 1) * 2, 2, 3), new OcenaTretine(1000, 400, 200, 1.05f), tezavnost);
	}
	
	public void prikazi() {
		JOptionPane pOkno = new JOptionPane("", JOptionPane.PLAIN_MESSAGE);
		JPanel[] opc = {this};
		pOkno.setOptions(opc);
		prikaz = pOkno.createDialog(Nadzornik.okno, "Nastavitve");
		prikaz.setVisible(true);
		}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == p1.tz) {
			this.tezavnostRdeci = p1.tz.getValue();
			p1.posodobiTezavnost();
		}
		else if (e.getSource() == p2.tz) {
			this.tezavnostModri = p2.tz.getValue();
			p2.posodobiTezavnost();
		}
		else if (e.getSource() == p3.vel) {
			this.velikost = p3.vel.getValue();
			p3.posodobiVelikost();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == p1.rbClovek) {this.jeClovekRdeci = true; p1.posodobiTezavnost();}
		else if (e.getSource() == p1.rbRacunalnik) {this.jeClovekRdeci = false; p1.posodobiTezavnost();}
		else if (e.getSource() == p2.rbClovek) {this.jeClovekModri = true; p2.posodobiTezavnost();}
		else if (e.getSource() == p2.rbRacunalnik) {this.jeClovekModri = false; p2.posodobiTezavnost();}
		
		else if (e.getSource() == this.zacni) {
			prikaz.setVisible(false);
			Igralec igralec1; Igralec igralec2;
			if (jeClovekRdeci) igralec1 = new Igralec();
			else igralec1 = novIgralec(tezavnostRdeci);
			if (jeClovekModri) igralec2 = new Igralec();
			else igralec2 = novIgralec(tezavnostModri);
			Nadzornik.novaIgra(igralec1, igralec2, this.velikost);
		}
	}

}
