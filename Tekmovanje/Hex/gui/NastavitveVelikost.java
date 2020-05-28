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
	JSlider vel;
	JLabel velDisplay;
	
	public NastavitveVelikost(PrikazNastavitev p) {
		this.p = p;
		
		
		this.vel = new JSlider(7, 15, p.velikost);
		this.velDisplay = new JLabel("Težavnost: " + p.tezavnostModri);
		this.vel.addChangeListener(p);
		vel.setMajorTickSpacing(1);
		vel.setPaintTicks(true);
		velDisplay.setFont(new Font("Custom", Font.BOLD, 14));
		posodobiVelikost();

		this.setLayout(new GridBagLayout());

		dodaj(vel, 0, 0.4); dodaj(velDisplay, 1, 0.4); dodaj(new JLabel(""), 2, 0.2);
	}
	
	private void dodaj(Component o, int y, double visina) {
		GridBagConstraints gb = new GridBagConstraints();
		//gb.anchor = GridBagConstraints.CENTER;
		gb.gridx = 0; gb.gridy = y;
		gb.weightx = 1.0; gb.weighty = visina;
		this.add(o, gb);
		JLabel hck = new JLabel("                                            ");
		hck.setFont(new Font("hck", Font.PLAIN, 20));
		this.add(hck, gb);
	}
	
	void posodobiVelikost() {
		velDisplay.setText("Velikost plošèe: " + p.velikost); velDisplay.setVisible(true); vel.setVisible(true);
		this.repaint();
		velDisplay.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, 10000, 10000);
	}

}
