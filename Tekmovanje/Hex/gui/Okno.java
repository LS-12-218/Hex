package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

import glavno.Nadzornik;

public class Okno extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public PrikazIgre platno;
	JButton menuIgra = new JButton("Nova Igra");
	JButton menuIzhod = new JButton("Izhod");
	
	public Okno() {
		//Nastavitve
		this.setTitle("Hex");
		this.setPreferredSize(new Dimension(1000,800));
		this.setMinimumSize(new Dimension(300,300));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Dodaj prikaz igre
		platno = new PrikazIgre();
		this.add(platno);
		
		//Dodaj vrstico z menujem
		menuIgra.addActionListener(this);
		menuIzhod.addActionListener(this);
		JPanel njp = new JPanel();
		this.add(njp, BorderLayout.NORTH);
		njp.setSize(this.getBounds().width, njp.getBounds().height);
		njp.setBackground(Color.LIGHT_GRAY);
		menuIgra.setBackground(new Color(0.8f,0.8f,0.85f));
		menuIzhod.setBackground(new Color(0.8f,0.8f,0.85f));
		njp.setLayout(new BorderLayout());
		njp.setBorder(new BasicBorders.MenuBarBorder(new Color(0.6f,0.6f,0.6f), new Color(0.8f,0.8f,0.8f)));
		njp.add(menuIgra, BorderLayout.WEST); njp.add(menuIzhod, BorderLayout.EAST);
	}
	
	public void zapri() {
		this.removeAll();
		this.setVisible(false);
	}
	
	public void konec(String igralec) {
		//Prikaže pogovorno okno ob koncu igre
		JOptionPane pOkno = new JOptionPane(igralec + " je zmagal!", JOptionPane.PLAIN_MESSAGE);
		String[] opc = {"Nova Igra", "Izhod"};
		pOkno.setOptions(opc);
		pOkno.createDialog(this, "Igra je konèana").setVisible(true);
		String a = (String) pOkno.getValue();
		if (a.equals("Nova Igra")) (new PrikazNastavitev()).prikazi();
		else if (a.equals("Izhod")) Nadzornik.zapri();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuIgra) (new PrikazNastavitev()).prikazi();
		else if (e.getSource() == menuIzhod) Nadzornik.zapri();
	}

}
