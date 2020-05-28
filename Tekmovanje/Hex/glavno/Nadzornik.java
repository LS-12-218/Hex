package glavno;

import gui.Okno;
import gui.PrikazIgre;
import gui.PrikazNastavitev;
import logika.Igra;

public class Nadzornik {
	public static Igra trenutnaIgra;
	public static Okno okno;
	public static Igralec[] igralec = new Igralec[2];
	public static boolean igraVTeku = false;
	
	public static void zacni() {
		trenutnaIgra = new Igra();
		okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		(new PrikazNastavitev()).prikazi();
	}
	
	public static void novaIgra(Igralec igralec1, Igralec igralec2, int velikost) {
		igraVTeku = true;
		trenutnaIgra = new Igra(velikost);
		//okno.platno = new PrikazIgre();
		okno.platno.posodobiSestkotnike();
		igralec[0] = igralec1; igralec[1] = igralec2;
		nadaljuj();
	}
	
	public static boolean naPoteziClovek() {
		return igraVTeku && igralec[trenutnaIgra.trenutniIgralec - 1].jeClovek;
	}
	
	public static void zapri() {
		okno.zapri();
		System.exit(0);
	}
	
	public static void nadaljuj() {
		switch(trenutnaIgra.stanje) {
		case IGRANJE:
			if (!naPoteziClovek()) igralec[trenutnaIgra.trenutniIgralec - 1].igraj();
			break;
		case ZMAGA_MODRI:
			okno.konec("Modri");
			break;
		case ZMAGA_RDECI:
			okno.konec("Rdeèi");
			break;
		default:
			break;
		}
	}
}
