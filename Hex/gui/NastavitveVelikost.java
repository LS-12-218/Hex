package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class NastavitveVelikost extends JPanel {
	private static final long serialVersionUID = 11L;
	
	PrikazNastavitev p;
	JSlider velikost; //velikost slider
	JLabel velikostDisplay; //velikost label
	
	public NastavitveVelikost(PrikazNastavitev p) {
		this.p = p;
		
		//Slider
		this.velikost = new JSlider(7, 15, p.velikost);
		this.velikostDisplay = new JLabel("Velikost plošèe: " + p.velikost);
		this.velikost.addChangeListener(p);
		velikost.setMinorTickSpacing(1);
		velikost.setMajorTickSpacing(2);
		velikost.setPaintTicks(true);
		velikostDisplay.setFont(new Font("Custom", Font.BOLD, 14));
		posodobiVelikost();

		this.setLayout(new GridBagLayout());

		dodaj(velikost, 0, 0.4); dodaj(velikostDisplay, 1, 0.4); dodaj(new JLabel(""), 2, 0.2);
	}
	
	private void dodaj(Component o, int y, double visina) {
		//Pomožna metoda
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridx = 0; gb.gridy = y;
		gb.weightx = 1.0; gb.weighty = visina;
		this.add(o, gb);
		JLabel hck = new JLabel("                                            ");
		hck.setFont(new Font("hck", Font.PLAIN, 20));
		this.add(hck, gb);
	}
	
	void posodobiVelikost() {
		//Posodobi velikost èe se spremeni slider
		velikostDisplay.setText("velikost plošèe: " + p.velikost); velikostDisplay.setVisible(true); velikost.setVisible(true);
		this.repaint();
		velikostDisplay.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, 10000, 10000);
	}

}
