package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import glavno.Nadzornik;
import logika.Logika;
import splosno.Koordinati;

public class PrikazIgre extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 2L;
	protected static final double COS30 = Math.sqrt(0.75);
	
	//Vrednosti za geometrijo
	private double q; //kvocient širine in višine
	private double stranica; //dolžina stranice šestkotnika
	private int zacetniX; //Kje na platnu se zaène igralna plošèa
	private int zacetniY;
	private int visina;
	private Point miska; //trenutni koordinati miške
	private Sestkotnik[][] sestkotniki; //Seznam igralnih polj
	
	//Barve in debelina
	private final double PADDING = 0.1;
	private final double DEBELINA_ROBA = 0.005;
	private final Color BARVA_PRAZNO = Color.WHITE;
	private final Color BARVA_RDECI = new Color((float) 1.0, (float) 0.12, (float) 0.15);
	private final Color BARVA_MODRI = new Color((float) 0.2, (float) 0.4, (float) 1.0);
	private final Color BARVA_RDECI_TRANSPARENT = new Color((float) 1.0, (float) 0.12, (float) 0.15, (float) 0.5);
	private final Color BARVA_MODRI_TRANSPARENT = new Color((float) 0.2, (float) 0.4, (float) 1.0, (float) 0.5);
	private final Color BARVA_ZMAGA_TRANSPARENT = new Color((float) 0.5, (float) 1.0, (float) 0.0, (float) 0.15);
	private final Color BARVA_RDECI_ZMAGA = new Color((float) 0.8, (float) 0.06, (float) 0.0);
	private final Color BARVA_MODRI_ZMAGA = new Color((float) 0.0, (float) 0.2, (float) 0.9);
	
	
	public PrikazIgre () {
		int n = Nadzornik.trenutnaIgra.velikost;
		this.q = (3.0 * n + 1.0) / (6.0 * COS30 * n - 2.0 * COS30);
		
		this.sestkotniki = new Sestkotnik[n][n];
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) this.sestkotniki[i][j] = new Sestkotnik();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void posodobiSestkotnike() {
		//Posodobi število šestkotnikov
		int n = Nadzornik.trenutnaIgra.velikost;
		
		this.sestkotniki = new Sestkotnik[n][n];
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) this.sestkotniki[i][j] = new Sestkotnik();
		}
	
	private void dolzine() {
		//Posodobi vrednosti za geometrijo
		double h = Math.min((1.0 - 2.0 * this.PADDING) * this.getHeight(), this.q * (1.0 - 2.0 * this.PADDING) * this.getWidth());
		double w = h / this.q;
		this.zacetniX = (int) Math.round((this.getWidth() - w) * 0.5);
		this.zacetniY = (int) Math.round((this.getHeight() - h) * 0.5);
		this.stranica = h / (1.5 * Nadzornik.trenutnaIgra.velikost + 0.5);
		this.visina = (int) Math.round(h);
	}
	
	private double debelinaRobu() {return (this.visina * DEBELINA_ROBA);}
	
	
	private void oznaciIzbranoPolje(Graphics2D g) {
		//Mouse hover
		for (int i = 0; i < sestkotniki.length; i++) for (int j = 0; j < sestkotniki[0].length; j++)
			if (Nadzornik.trenutnaIgra.veljavnaPoteza(new Koordinati(i, j)) && sestkotniki[i][j].vsebuje(this.miska)) {
				if (Nadzornik.trenutnaIgra.trenutniIgralec == Logika.RDECI) g.setColor(this.BARVA_RDECI_TRANSPARENT);
				else g.setColor(this.BARVA_MODRI_TRANSPARENT);
				sestkotniki[i][j].narisi(g);
			}
	}
	
	private void pobarvajPolja(Graphics2D g, List<Koordinati> pobarvaj) {
		//Pobarva šestkotnike
		for (int i = 0; i < sestkotniki.length; i++) for (int j = 0; j < sestkotniki[0].length; j++)
			if (pobarvaj.contains(new Koordinati(i, j))) {
				switch(Nadzornik.trenutnaIgra.stanje) {
				case ZMAGA_MODRI:
					g.setColor(this.BARVA_MODRI_ZMAGA);
					break;
				case ZMAGA_RDECI:
					g.setColor(this.BARVA_RDECI_ZMAGA);
					break;
				default:
					g.setColor(this.BARVA_ZMAGA_TRANSPARENT);
					break;}
				sestkotniki[i][j].narisi(g);
			}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		dolzine();
		//Nastavitve
		g.setColor(new Color(0.9f,0.9f,0.9f));
		Rectangle re = g.getClipBounds();
		g.clearRect(re.x, re.y, re.width, re.height);
		g.fillRect(re.x, re.y, re.width, re.height);
		Graphics2D g2d = (Graphics2D) g;
		
		//Lokalne spremenljivke
		double a1 = 1.5 * this.stranica; double a2 = COS30 * this.stranica;
		double trenutniX = this.zacetniX; double trenutniY = this.zacetniY;
		int v = Nadzornik.trenutnaIgra.velikost;
		
		//Pobarvaj okvir plošèe
		int[] p1x = new int[4]; int[] p1y = new int[4];
		p1x[0] = (this.zacetniX); p1y[0] = (int) (trenutniY + this.stranica / 2);
		p1x[2] = (int) (p1x[0] + (2 * v - 1.0 / 6.0) * a2); p1y[2] = (int) (p1y[0] - a1 * 0.5);
		p1x[1] = (int) (p1x[2] - (2.0 / 3.0) * a2); p1y[1] = (int) (p1y[2] + this.stranica);
		p1x[3] = (int) (p1x[0] - a2 * 1.5); p1y[3] = (int) (p1y[2]);

		int[] p2x = new int[4]; int[] p2y = new int[4];
		p2x[0] = p1x[3]; p2y[0] = p1y[3]; p2x[1] = p1x[0]; p2y[1] = p1y[0];
		p2x[3] = (int) (trenutniX + (v - 5.0 / 6.0) * a2); p2y[3] = (int) (trenutniY + (v * 1.5 + 0.75) * this.stranica);
		p2x[2] = (int) (p2x[3] + 2.0 * a2 / 3.0); p2y[2] = (int) (p2y[3] - this.stranica);
		
		int[] p3x = new int[4]; int[] p3y = new int[4];
		p3x[0] = p1x[2]; p3y[0] = p1y[2]; p3x[1] = p1x[1]; p3y[1] = p1y[1];
		p3x[2] = (int) (trenutniX + (3 * v - 1) * a2); p3y[2] = (int) (trenutniY + v * a1);
		p3x[3] = (int) (p3x[2] + a2 * 1.5); p3y[3] = (int) (p3y[2] + a1 * 0.5);

		int[] p4x = new int[4]; int[] p4y = new int[4];
		p4x[0] = p2x[3]; p4y[0] = p2y[3]; p4x[1] = p2x[2]; p4y[1] = p2y[2];
		p4x[2] = p3x[2]; p4y[2] = p3y[2]; p4x[3] = p3x[3]; p4y[3] = p3y[3];

		g2d.setColor(BARVA_RDECI);
		g2d.fillPolygon(p1x, p1y, 4); g2d.fillPolygon(p4x, p4y, 4);
		g2d.setColor(BARVA_MODRI);
		g2d.fillPolygon(p2x, p2y, 4); g2d.fillPolygon(p3x, p3y, 4);
		
		
		
		//Pobarvaj polja
		g2d.setStroke(new BasicStroke((float) this.debelinaRobu(), BasicStroke.JOIN_MITER, BasicStroke.CAP_BUTT));
		for (int j = 0; j < sestkotniki.length; j++) {
			double shraniX = trenutniX;
			for (int i = 0; i < sestkotniki[0].length; i++) {
				int barva = Nadzornik.trenutnaIgra.igralec(i, j);
				if (barva == Logika.PRAZNO) g.setColor(this.BARVA_PRAZNO);
				else if (barva == Logika.RDECI) g.setColor(this.BARVA_RDECI);
				else if (barva == Logika.MODRI) g.setColor(this.BARVA_MODRI);
				sestkotniki[i][j].narisi(g2d, trenutniX, trenutniY, this.stranica);
				trenutniX += 2 * a2;
			}
			trenutniX = shraniX;
			trenutniX += a2;
			trenutniY += a1;
		}
		
		//Posebna stanja
		if (Nadzornik.trenutnaIgra.jeKonec())
			pobarvajPolja(g2d, Nadzornik.trenutnaIgra.zmagovalnaPolja());
		else if(Nadzornik.naPoteziClovek())
			oznaciIzbranoPolje(g2d);

		this.repaint();
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.miska = e.getPoint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.miska = null;
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Nadzornik.naPoteziClovek()) {
			Point p = e.getPoint();
			//Preveri, èe smo kliknili na polje za vsak šestkotnik posebej (poèasno)
			for (int i = 0; i < sestkotniki.length; i++) for (int j = 0; j < sestkotniki[0].length; j++)
				if (sestkotniki[i][j].vsebuje(p))
					if (Nadzornik.trenutnaIgra.odigraj(new Koordinati(i,j))) Nadzornik.nadaljuj();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//Neuporabljena metoda
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Neuporabljena metoda
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Neuporabljena metoda
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//Neuporabljena metoda
	}
}
