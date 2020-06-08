package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class NastavitveIgralec extends JPanel {
	private static final long serialVersionUID = 6L;
	
	PrikazNastavitev p;
	JRadioButton rbClovek;
	JRadioButton rbRacunalnik;
	JSlider tezavnost; //Težavnost slider
	JLabel tezavnostDisplay; //Težavnost label
	boolean rdec;
	
	public NastavitveIgralec(PrikazNastavitev p, boolean rdec) {
		this.rdec = rdec;
		this.p = p;
		ButtonGroup bg = new ButtonGroup();
		
		//Gumba za izbiro vrste igralca
		this.rbClovek = new JRadioButton("Èlovek", p.jeClovekRdeci && rdec || p.jeClovekModri && !rdec);
		this.rbRacunalnik = new JRadioButton("Raèunalnik", !p.jeClovekRdeci && rdec || !p.jeClovekModri && !rdec);
		Font f1 = new Font("Custom", Font.PLAIN, 18);
		this.rbClovek.setFont(f1); this.rbRacunalnik.setFont(f1);
		bg.add(this.rbClovek); bg.add(this.rbRacunalnik);
		this.rbClovek.addActionListener(p); this.rbRacunalnik.addActionListener(p);
		
		//Slider za izbiro težavnosti
		if (rdec) {this.tezavnost = new JSlider(1, 5, p.tezavnostRdeci); this.tezavnostDisplay = new JLabel("Težavnost: " + p.tezavnostRdeci);}
		else {this.tezavnost = new JSlider(1, 5, p.tezavnostModri); this.tezavnostDisplay = new JLabel("Težavnost: " + p.tezavnostModri);}
		this.tezavnost.addChangeListener(p);
		tezavnost.setMajorTickSpacing(1);
		tezavnost.setPaintTicks(true);
		tezavnostDisplay.setFont(new Font("Custom", Font.BOLD, 14));
		posodobiTezavnost();
		
		//Prikaz barve igralca
		JLabel oznakaIgralca = new JLabel();
		oznakaIgralca.setFont(new Font("Custom", Font.BOLD, 30));
		if (rdec) {oznakaIgralca.setText("RDEÈ"); oznakaIgralca.setForeground(new Color(1.0f, 0.2f, 0.23f));}
		else {oznakaIgralca.setText("MODER"); oznakaIgralca.setForeground(new Color(0.17f, 0.35f, 1.0f));}

		//Dodaj komponente
		this.setLayout(new GridBagLayout());

		dodaj(oznakaIgralca, 0, 0.25); dodaj(new JLabel(""), 1, 0.0);
		dodaj(this.rbClovek, 3, 0.0); dodaj(this.rbRacunalnik, 4, 0.0);
		dodaj(tezavnost, 6, 0.2); dodaj(tezavnostDisplay, 5, 0.2); dodaj(new JLabel(""), 7, 0.0);
	}
	
	private void dodaj(Component o, int y, double visina) {
		//Pomožna metoda
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridx = 0; gb.gridy = y;
		gb.weightx = 1.0; gb.weighty = visina;
		this.add(o, gb);
		JLabel hck = new JLabel("                                            ");
		hck.setFont(new Font("hck", Font.PLAIN, 22));
		this.add(hck, gb);
	}
	
	void posodobiTezavnost() {
		//Posodobi težavnost èe se spremeni slider
		if (rdec) {
			if (p.jeClovekRdeci) {tezavnostDisplay.setVisible(false); tezavnost.setVisible(false);}
			else {tezavnostDisplay.setText("Težavnost: " + p.tezavnostRdeci); tezavnostDisplay.setVisible(true); tezavnost.setVisible(true);}
		}
		else {
			if (p.jeClovekModri) {tezavnostDisplay.setVisible(false); tezavnost.setVisible(false);}
			else {tezavnostDisplay.setText("Težavnost: " + p.tezavnostModri); tezavnostDisplay.setVisible(true); tezavnost.setVisible(true);}
		}
		this.repaint();
		tezavnostDisplay.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, 10000, 10000);
	}

}
