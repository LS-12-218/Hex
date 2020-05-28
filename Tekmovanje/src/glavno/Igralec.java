package glavno;

import inteligenca.Algoritem;
import inteligenca.Ocena;

public class Igralec {
	public boolean jeClovek;
	Algoritem algoritem;
	Ocena ocena;
	public int tezavnost = 1;
	
	public Igralec() {
		//Èlovek
		this.jeClovek = true;
		this.algoritem = null;
		this.ocena = null;
	}
	
	public Igralec(Algoritem a, Ocena o) {
		//Inteligenca
		this.jeClovek = false;
		this.algoritem = a;
		this.ocena = o;
	}
	
	public Igralec(Algoritem a, Ocena o, int tezavnost) {
		//Inteligenca s podatkom o težavnosti
		this.jeClovek = false;
		this.algoritem = a;
		this.ocena = o;
		this.tezavnost = tezavnost;
	}
	
	public void igraj() {
		if (!jeClovek)
			Nadzornik.trenutnaIgra.odigraj(this.algoritem.najboljsaPoteza(Nadzornik.trenutnaIgra, this.ocena));
		Nadzornik.nadaljuj();
	}
}
