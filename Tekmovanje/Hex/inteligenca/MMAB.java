package inteligenca;

import logika.Igra;
import logika.Logika;
import splosno.Koordinati;

public class MMAB extends Algoritem {
	
	private class KoorOc {
		//Par koordinat in ocene
		Koordinati koordinati;
		int vrednost;
		private KoorOc(Koordinati k, int i) {
			this.koordinati = k; this.vrednost = i;
		}
	}
	
	
	int globina = 1;
	int stevec = 1;
	int imenovalec = 1;
	int potezeZacetek = 10;
	public boolean napredno = false;
	
	public MMAB(int globina, int poteze, int s, int i) {
		this.globina = globina;
		this.stevec = s; this.imenovalec = i;
		this.potezeZacetek = poteze;
	}
	
	@Override
	public Koordinati najboljsaPoteza(Igra igra, Ocena ocena) {
		KoorOc par = OMimaMax(igra.kopija(), ocena, this.potezeZacetek, this.globina, igra.trenutniIgralec);
		if (par.vrednost <= (Ocena.MINUS_INF + 1)) {
			Koordinati poteza = this.poskusajPreprecitiZmago(igra, ocena);
			if (poteza != null) return poteza;
		}
		return par.koordinati;
	}
	
	private int norma(KoorOc k, Igra i) {
		return Math.abs(k.koordinati.getX() - i.velikost / 2) + Math.abs(k.koordinati.getY() - i.velikost / 2);
	}
	
	private boolean vecji(KoorOc k1, KoorOc k2, Igra igra) {
		if (k2 == null) return true;
		else if (k1.vrednost == k2.vrednost)
			return (norma(k1, igra) < norma(k2, igra));
		else return (k1.vrednost > k2.vrednost);
	}
	
	private boolean manjsi(KoorOc k1, KoorOc k2, Igra igra) {
		if (k2 == null) return true;
		else if (k1.vrednost == k2.vrednost)
			return (norma(k1, igra) < norma(k2, igra));
		else return (k1.vrednost < k2.vrednost);
	}
	
	private void dodajMax (KoorOc[] kl, KoorOc k, Igra igra) {
		int i = 0;
		while (i < kl.length && vecji(k, kl[i], igra)) {
			if (i >= 1) kl[i - 1] = kl[i];
			i++;
		}
		if (i >= 1) kl[i - 1] = k;
	}
	
	private void dodajMin (KoorOc[] kl, KoorOc k, Igra igra) {
		int i = 0;
		while (i < kl.length && manjsi(k, kl[i], igra)) {
			if (i >= 1) kl[i - 1] = kl[i];
			i++;
		}
		if (i >= 1) kl[i - 1] = k;
	}
	
	private boolean dobraPozicija(Igra igra, int igralec, Koordinati k) {
		int x = k.getX(); int y = k.getY();
		return (
			((igra.igralec(x - 1, y + 2) == igralec) && (igra.igralec(x - 1, y + 1) == Logika.PRAZNO) && (igra.igralec(x, y + 1) == Logika.PRAZNO)) ||
			((igra.igralec(x + 1, y + 1) == igralec) && (igra.igralec(x, y + 1) == Logika.PRAZNO) && (igra.igralec(x + 1, y) == Logika.PRAZNO)) ||
			((igra.igralec(x + 2, y - 1) == igralec) && (igra.igralec(x + 1, y) == Logika.PRAZNO) && (igra.igralec(x + 1, y - 1) == Logika.PRAZNO)) ||
			((igra.igralec(x + 1, y - 2) == igralec) && (igra.igralec(x + 1, y - 1) == Logika.PRAZNO) && (igra.igralec(x, y - 1) == Logika.PRAZNO)) ||
			((igra.igralec(x - 1, y - 1) == igralec) && (igra.igralec(x, y - 1) == Logika.PRAZNO) && (igra.igralec(x - 1, y) == Logika.PRAZNO)) ||
			((igra.igralec(x - 2, y + 1) == igralec) && (igra.igralec(x - 1, y) == Logika.PRAZNO) && (igra.igralec(x - 1, y + 1) == Logika.PRAZNO))
		);
	}
	
	private KoorOc OMimaMax(Igra igra, Ocena o, int stPotez, int globina, int igralec) {
		//Ocena za igralca
		KoorOc[] najboljsePoteze = new KoorOc[stPotez];
		
		for (int i = 0; i < igra.velikost; i++) for (int j = 0; j < igra.velikost; j++) {
			Koordinati k = new Koordinati(i, j);
			
			if (igra.postavi(k)) {
				int ocena = o.oceni(igra, igralec);
				if (ocena == Ocena.INF) {
					igra.izbrisi(k);
					return new KoorOc(k, ocena - this.globina + globina);
				}
				dodajMax(najboljsePoteze, new KoorOc(k, ocena), igra);
				igra.izbrisi(k);
			}
		}
		
		if (globina <= 1 || stPotez <= 1) return najboljsePoteze[stPotez - 1];
		else {
			KoorOc kocM = null;
			for (int n = 0; n < stPotez; n++) if (najboljsePoteze[n] != null) {
				Koordinati koor = najboljsePoteze[n].koordinati;
				igra.postavi(koor);
				igra.zamenjajIgralca();
				igra.krog++;
				KoorOc t = OMimaMin(igra, o, (stPotez * stevec) / imenovalec, globina - 1, igralec);
				
				//Eksperimentalno
				if (napredno && globina == this.globina && t.vrednost >= -100 && dobraPozicija(igra, igralec, koor)) {
					if (igra.krog <= 10) t.vrednost += 25;
					else t.vrednost += 10;
				}
				
				t.koordinati = koor;
				igra.zamenjajIgralca();
				igra.krog--;
				igra.izbrisi(koor);
				if (vecji(t, kocM, igra)) kocM = t;
			}
			return kocM;
		}
	}
	
	private KoorOc OMimaMin(Igra igra, Ocena o, int stPotez, int globina, int igralec) {
		//Ocena za nasprotnika
		KoorOc[] najboljsePoteze = new KoorOc[stPotez];
		
		for (int i = 0; i < igra.velikost; i++) for (int j = 0; j < igra.velikost; j++) {
			Koordinati k = new Koordinati(i, j);
			
			if (igra.postavi(k)) {
				int ocena = o.oceni(igra, igralec);
				if (ocena == Ocena.MINUS_INF) {
					igra.izbrisi(k);
					return new KoorOc(k, ocena + this.globina - globina);
				}
				dodajMin(najboljsePoteze, new KoorOc(k, ocena), igra);
				igra.izbrisi(k);
			}
		}
		
		if (globina <= 1 || stPotez <= 1) return najboljsePoteze[stPotez - 1];
		else {
			KoorOc kocM = null;
			for (int n = 0; n < stPotez; n++) if (najboljsePoteze[n] != null) {
				Koordinati koor = najboljsePoteze[n].koordinati;
				igra.postavi(koor);
				igra.zamenjajIgralca();
				igra.krog++;
				KoorOc t = OMimaMax(igra, o, (stPotez * stevec) / imenovalec, globina - 1, igralec);
				t.koordinati = koor;
				igra.zamenjajIgralca();
				igra.krog--;
				igra.izbrisi(koor);
				if (manjsi(t, kocM, igra)) kocM = t;
			}
			return kocM;
		}
	}
	
	private Koordinati poskusajPreprecitiZmago(Igra igra, Ocena o) {
		//Poskuša prepreèiti zmago
		int igralec = igra.trenutniIgralec;
		igra.zamenjajIgralca();
		
		for (int i = 0; i < igra.velikost; i++) for (int j = 0; j < igra.velikost; j++) {
			Koordinati k = new Koordinati(i, j);
			
			if (igra.postavi(k)) {
				int ocena = o.oceni(igra, igralec);
				if (ocena == Ocena.MINUS_INF) {
					igra.izbrisi(k);
					igra.zamenjajIgralca();
					return k;
				}
				igra.izbrisi(k);
			}
		}
		
		igra.zamenjajIgralca();
		return null;
	}
}
