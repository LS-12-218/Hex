package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Sestkotnik {
	private Polygon lik;
	
	public Sestkotnik() {
		this.lik = null;
	}
	
	public boolean vsebuje(Point p) {
		if (p != null && lik != null) return this.lik.contains(p);
		else return false;
	}
	
	public void narisi(Graphics2D g, double x, double y, double stranica) {
		//Nariše nov šestkotnik
		double a1 = stranica;
		double a2 = a1 * 0.5;
		double a3 = a1 * PrikazIgre.COS30;
		int[] koordinateX = {(int) x, (int) x, (int) (x + a3), (int) (x + 2 * a3), (int) (x + 2 * a3), (int) (x + a3)};
		int[] koordinateY = {(int) (y + a2), (int) (y + a1 + a2), (int) (y + 2 * a1), (int) (y + a1 + a2), (int) (y + a2), (int) y};
		Polygon novLik = new Polygon(koordinateX, koordinateY, 6);
		g.fillPolygon(novLik);
		this.lik = novLik;
		g.setColor(Color.BLACK);
		g.draw(this.lik);
	}
	
	public void narisi(Graphics2D g) {
		//Posodobi obstojeèi šestkotnik
		g.fillPolygon(this.lik);
		g.setColor(Color.BLACK);
		g.draw(this.lik);
	}
}
