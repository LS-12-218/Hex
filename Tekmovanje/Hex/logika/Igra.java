package logika;

import java.util.List;
import java.util.Vector;

import splosno.Koordinati;

public class Igra {
	public int velikost;
	public int trenutniIgralec;
	private Plosca plosca;
	public Stanje stanje;
	public int krog;
	
	public Igra(int n) {
		//Igra s plošèo velikosti n
		this.velikost = n;
		this.trenutniIgralec = Logika.RDECI;
		this.plosca = new Plosca(n);
		this.stanje = Stanje.IGRANJE;
		this.krog = 1;
	}
	
	public Igra() {
		//Igra s plošèo velikosti 11
		this.velikost = 11;
		this.trenutniIgralec = Logika.RDECI;
		this.plosca = new Plosca(11);
		this.stanje = Stanje.IGRANJE;
		this.krog = 1;
	}
	
	public Igra kopija () {
		//Kopija objekta
		Igra i = new Igra(this.velikost);
		i.stanje = this.stanje;
		i.trenutniIgralec = this.trenutniIgralec;
		i.plosca = this.plosca.clone();
		i.krog = this.krog;
		return i;
	}
	
	public boolean odigraj(Koordinati koordinati) {
		int x = koordinati.getX(); int y = koordinati.getY();
		if(this.plosca.veljavno(x, y)) {
			this.plosca.igraj(this.trenutniIgralec, x, y);
			this.trenutniIgralec = Logika.zamenjajIgralca(this.trenutniIgralec);
			krog++;
			
			//Preveri èe je kdo zmagal
			if (plosca.zmagaRdeci()) this.stanje = Stanje.ZMAGA_RDECI;
			else if (plosca.zmagaModri()) this.stanje = Stanje.ZMAGA_MODRI;
			
			return true;
		}
		else return false;
	}
	
	public void izbrisi(Koordinati k) {
		int x = k.getX(); int y = k.getY();
		this.plosca.igraj(Logika.PRAZNO, x, y);
	}
	
	public boolean postavi(Koordinati koordinati) {
		//Podobno kot metoda odigraj, ampak ne spremeni stanja igre
		int x = koordinati.getX(); int y = koordinati.getY();
		if(this.plosca.veljavno(x, y)) {
			this.plosca.igraj(this.trenutniIgralec, x, y);
			return true;
		}
		else return false;
	}
	
	public boolean veljavnaPoteza(Koordinati koordinati) {
		return this.plosca.veljavno(koordinati.getX(), koordinati.getY());
	}
	
	public List<Koordinati> zmagovalnaPolja() {
		switch(this.stanje) {
		case ZMAGA_MODRI:
			return this.plosca.najkrajsaPotModri();
		case ZMAGA_RDECI:
			return this.plosca.najkrajsaPotRdeci();
		default:
			return new Vector<Koordinati>();}
	}
	
	public boolean jeKonec() {
		//Vrne true èe je kdo zmagal
		switch(this.stanje) {
		case ZMAGA_MODRI:
			return true;
		case ZMAGA_RDECI:
			return true;
		default:
			return false;
		}
	}
	
	public void zamenjajIgralca() {this.trenutniIgralec ^= 3;}
	
	public int igralec (int i, int j) {
		if (plosca.znotraj(i, j)) return plosca.polja()[i][j];
		else return Logika.PRAZNO;
		}
	
	public int[] potezeDoZmageModri() {
		//Najmanjše število potez modrega igralca do zmage za vsa polja na robu
		int[] vrednosti = new int[this.velikost];
		int[][] matrika = this.plosca.dolzinePotiModri();
		for (int i = 0; i < this.velikost; i++)
			vrednosti[i] = matrika[this.velikost - 1][i];
		return vrednosti;
	}
	
	public int[] potezeDoZmageRdeci() {
		//Najmanjše število potez rdeèega igralca do zmage za vsa polja na robu
		int[] vrednosti = new int[this.velikost];
		int[][] matrika = this.plosca.dolzinePotiRdeci();
		for (int i = 0; i < this.velikost; i++)
			vrednosti[i] = matrika[i][this.velikost - 1];
		return vrednosti;
	}
}
