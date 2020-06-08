package logika;

import java.util.Vector;

import splosno.Koordinati;

public class Plosca {
	private int[][] plosca;
	public int velikost;
	
	public Plosca(int n) {
		//Plošèa velikost n x n
		this.plosca = new int[n][n];
		this.velikost = n;
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) this.plosca[i][j] = Logika.PRAZNO;
	}
	
	public Plosca clone() {
		//Kopija objekta
		Plosca p = new Plosca(this.velikost);
		for (int i = 0; i < this.velikost; i++) for (int j = 0; j < this.velikost; j++) p.plosca[i][j] = this.plosca[i][j];
		return p;
	}
	
	public void igraj(int igralec, int x, int y) {this.plosca[x][y] = igralec;}
	
	boolean znotraj(int x, int y) {
		//Preveri, èe so koordinate na plošèi
		return (0 <= x && x < this.velikost && 0 <= y && y < this.velikost);
	}
	
	protected boolean veljavno(int x, int y) {
		//Preveri èe igralec lahko igra na koordinatah
		return znotraj(x,y) && this.plosca[x][y] == Logika.PRAZNO;
	}
	
	
	private boolean lahkoTvoriPot(int igralec, int x, int y) {
		//Pomožna metoda
		return znotraj(x,y) && (this.plosca[x][y] != Logika.zamenjajIgralca(igralec));
	}
	
	private int prejsnjaPot(int igralec, int x, int y, int[][] matrika) {
		//Pomožna metoda
		if (lahkoTvoriPot(igralec, x, y)) {
			if (this.plosca[x][y] == Logika.PRAZNO) return matrika[x][y] - 1;
			else return matrika[x][y];
		}
		else return -1;
	}
	
	private void plusDolzina(int igralec, int x, int y, int[][] matrika) {
		int d = matrika[x][y];
		//Posodobi najmanjše število potez glede na sosede
		if (lahkoTvoriPot(igralec, x + 1, y)) d = Math.min(d, matrika[x + 1][y]);
		if (lahkoTvoriPot(igralec, x - 1, y)) d = Math.min(d, matrika[x - 1][y]);
		if (lahkoTvoriPot(igralec, x, y + 1)) d = Math.min(d, matrika[x][y + 1]);
		if (lahkoTvoriPot(igralec, x - 1, y + 1)) d = Math.min(d, matrika[x - 1][y + 1]);
		if (lahkoTvoriPot(igralec, x, y - 1)) d = Math.min(d, matrika[x][y - 1]);
		if (lahkoTvoriPot(igralec, x + 1, y - 1)) d = Math.min(d, matrika[x + 1][y - 1]);
		if (this.plosca[x][y] == Logika.PRAZNO) d++;
		matrika[x][y] = d;
		
		//Rekurzivno ponovi za sosede z veèjo razdaljo
		if (prejsnjaPot(igralec, x + 1, y, matrika) > d) plusDolzina(igralec, x + 1, y, matrika);
		if (prejsnjaPot(igralec, x - 1, y, matrika) > d) plusDolzina(igralec, x - 1, y, matrika);
		if (prejsnjaPot(igralec, x, y + 1, matrika) > d) plusDolzina(igralec, x, y + 1, matrika);
		if (prejsnjaPot(igralec, x - 1, y + 1, matrika) > d) plusDolzina(igralec, x - 1, y + 1, matrika);
		if (prejsnjaPot(igralec, x, y - 1, matrika) > d) plusDolzina(igralec, x, y - 1, matrika);
		if (prejsnjaPot(igralec, x + 1, y - 1, matrika) > d) plusDolzina(igralec, x + 1, y - 1, matrika);
	}
	
	int[][] dolzinePotiRdeci() {
		int[][] dolzine = new int[velikost][velikost]; int n2 = velikost * velikost;
		//Naredi matriko razdalj
		for (int i = 0; i < velikost; i++) for (int j = 0; j < velikost; j++) {
			if (j == 0) {
				if (this.plosca[i][j] == Logika.RDECI) dolzine[i][j] = 0;
				else dolzine[i][j] = 1;
			}
			else dolzine[i][j] = n2;
		}
		//Izraèunaj razdalje v prvi vrstici
		for (int i = 0; i < velikost; i++) if (this.plosca[i][1] != Logika.MODRI) plusDolzina(Logika.RDECI, i, 1, dolzine);
		return dolzine;
	}
	
	int[][] dolzinePotiModri() {
		int[][] dolzine = new int[velikost][velikost]; int n2 = velikost * velikost;
		//Naredi matriko razdalj
		for (int i = 0; i < velikost; i++) for (int j = 0; j < velikost; j++) {
			if (i == 0) {
				if (this.plosca[i][j] == Logika.MODRI) dolzine[i][j] = 0;
				else dolzine[i][j] = 1;
			}
			else dolzine[i][j] = n2;
		}
		//Izraèunaj razdalje v prvi vrstici
		for (int j = 0; j < velikost; j++) if (this.plosca[1][j] != Logika.RDECI) plusDolzina(Logika.MODRI, 1, j, dolzine);
		return dolzine;
	}
	
	
	int[][] polja() {return this.plosca;}
	
	public boolean zmagaRdeci() {
		//Vrne èe je zmagal rdeèi igralec
		int[][] matrika = dolzinePotiRdeci();
		for(int i = 0; i < this.velikost; i ++)
			if (matrika[i][this.velikost - 1] == 0) return true;
		return false;
	}
	
	public boolean zmagaModri() {
		//Vrne èe je zmagal modri igralec
		int[][] matrika = dolzinePotiModri();
		for(int i = 0; i < this.velikost; i ++)
			if (matrika[this.velikost - 1][i] == 0) return true;
		return false;
	}
	
	
	
	private boolean jePovezan(int x, int y, int[][] matrika) {
		//Pomožna metoda
		return znotraj(x, y) && matrika[x][y] >= 0;
	}
	
	private void potMatrikaRek(int x, int y, int[][] matrika) {
		int d = matrika[x][y];
		//Minimiziraj d z razdaljami sosedov
		if (jePovezan(x + 1, y, matrika)) d = Math.min(d, matrika[x + 1][y]);
		if (jePovezan(x - 1, y, matrika)) d = Math.min(d, matrika[x - 1][y]);
		if (jePovezan(x, y + 1, matrika)) d = Math.min(d, matrika[x][y + 1]);
		if (jePovezan(x - 1, y + 1, matrika)) d = Math.min(d, matrika[x - 1][y + 1]);
		if (jePovezan(x, y - 1, matrika)) d = Math.min(d, matrika[x][y - 1]);
		if (jePovezan(x + 1, y - 1, matrika)) d = Math.min(d, matrika[x + 1][y - 1]);
		d++;
		matrika[x][y] = d;
		
		//Posodobi sosede
		if (znotraj(x + 1, y) && matrika[x + 1][y] > d) potMatrikaRek(x + 1, y, matrika);
		if (znotraj(x - 1, y) && matrika[x - 1][y] > d) potMatrikaRek(x - 1, y, matrika);
		if (znotraj(x, y + 1) && matrika[x][y + 1] > d) potMatrikaRek(x, y + 1, matrika);
		if (znotraj(x - 1, y + 1) && matrika[x - 1][y + 1] > d) potMatrikaRek(x - 1, y + 1, matrika);
		if (znotraj(x, y - 1) && matrika[x][y - 1] > d) potMatrikaRek(x, y - 1, matrika);
		if (znotraj(x + 1, y - 1) && matrika[x + 1][y - 1] > d) potMatrikaRek(x + 1, y - 1, matrika);
	}
	
	private Vector<Koordinati> najkrajsaPotRek(int x, int y, int[][]matrika, Vector<Koordinati> pot) {
		//Vrne pot od ene strani do druge
		pot.add(new Koordinati(x, y));
		if (matrika[x][y] == 0) return pot;
		else {
			int d = matrika[x][y] - 1;
			if (znotraj(x + 1, y) && matrika[x + 1][y] == d) return najkrajsaPotRek(x + 1, y, matrika, pot);
			else if (znotraj(x, y + 1) && matrika[x][y + 1] == d) return najkrajsaPotRek(x, y + 1, matrika, pot);
			else if (znotraj(x - 1, y + 1) && matrika[x - 1][y + 1] == d) return najkrajsaPotRek(x - 1, y + 1, matrika, pot);
			else if (znotraj(x - 1, y) && matrika[x - 1][y] == d) return najkrajsaPotRek(x - 1, y, matrika, pot);
			else if (znotraj(x, y - 1) && matrika[x][y - 1] == d) return najkrajsaPotRek(x, y - 1, matrika, pot);
			else if (znotraj(x + 1, y - 1) && matrika[x + 1][y - 1] == d) return najkrajsaPotRek(x + 1, y - 1, matrika, pot);
			else return new Vector<Koordinati>();
		}
	}
	
	public Vector<Koordinati> najkrajsaPotRdeci() {
		int[][] matrika = dolzinePotiRdeci(); int n2 = velikost * velikost;
		//Naredi matriko razdalj
		for (int i = 0; i < this.velikost; i++) for (int j = 0; j < this.velikost; j++)
				if (matrika[i][j] == 0) matrika[i][j] = n2;
				else matrika[i][j] = -1;
		
		for(int i = 0; i < this.velikost; i ++)
				if (matrika[i][this.velikost - 1] > 0) {
					matrika[i][this.velikost - 1] = -1;
					potMatrikaRek(i, this.velikost - 1, matrika);
				}
		//Vrni pot z minimalno dolžino
		int trenutniMin = n2;
		for(int j = 0; j < this.velikost; j ++) if (matrika[j][0] < trenutniMin && matrika[j][0] >= 0) trenutniMin = matrika[j][0];
		for(int j = 0; j < this.velikost; j ++) if (matrika[j][0] == trenutniMin)
			return najkrajsaPotRek(j, 0, matrika, new Vector<Koordinati>(this.velikost,this.velikost));
		return new Vector<Koordinati>();
	}
	
	public Vector<Koordinati> najkrajsaPotModri() {
		int[][] matrika = dolzinePotiModri(); int n2 = velikost * velikost;
		//Naredi matriko razdalj
		for (int i = 0; i < this.velikost; i++) for (int j = 0; j < this.velikost; j++)
				if (matrika[i][j] == 0) matrika[i][j] = n2;
				else matrika[i][j] = -1;
		
		for(int i = 0; i < this.velikost; i ++)
				if (matrika[this.velikost - 1][i] > 0) {
					matrika[this.velikost - 1][i] = -1;
					potMatrikaRek(this.velikost - 1, i, matrika);
				}
		//Vrni pot z minimalno dolžino
		int trenutniMin = n2;
		for(int j = 0; j < this.velikost; j ++) if (matrika[0][j] < trenutniMin && matrika[0][j] >= 0) trenutniMin = matrika[0][j];
		for(int j = 0; j < this.velikost; j ++) if (matrika[0][j] == trenutniMin)
			return najkrajsaPotRek(0, j, matrika, new Vector<Koordinati>(this.velikost,this.velikost));
		return new Vector<Koordinati>();
	}
	
	
	
	
	//debug
	
	public void izpisiDolzineRdeci() {
		int[][] ddd = dolzinePotiRdeci();
		for (int i = 0; i < velikost; i++) {
		System.out.print('\n');
		for (int k = 0; k < i; k++) System.out.print(' ');
		for (int j = 0; j < velikost; j++) {
			System.out.print(" " + ddd[j][i]);
		}
		}
	}
	public void izpisiDolzineModri() {
		int[][] ddd = dolzinePotiModri();
		for (int i = 0; i < velikost; i++) {
		System.out.print('\n');
		for (int k = 0; k < i; k++) System.out.print(' ');
		for (int j = 0; j < velikost; j++) {
			System.out.print(" " + ddd[j][i]);
		}
		}
	}
	
	public void izpisiPlosco() {
		for (int i = 0; i < velikost; i++) {
		System.out.print('\n');
		for (int k = 0; k < i; k++) System.out.print(' ');
		for (int j = 0; j < velikost; j++) {
			System.out.print(" " + this.plosca[j][i]);
		}
		}
	}
}
