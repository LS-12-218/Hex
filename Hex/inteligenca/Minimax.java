package inteligenca;

import logika.Igra;
import logika.Logika;
import splosno.Koordinati;

public class Minimax extends Algoritem {
	
	private class KoorOc {
		Koordinati koordinati;
		int vrednost;
		private KoorOc(Koordinati k, int i) {
			this.koordinati = k; this.vrednost = i;
		}
	}
	
	
	int globina = 1;
	
	public Minimax(int globina) {this.globina = globina;}
	
	@Override
	public Koordinati najboljsaPoteza(Igra igra, Ocena ocena) {
		return MinimaxRek(igra.kopija(), ocena, this.globina, igra.trenutniIgralec).koordinati;
	}
	
	private KoorOc MinimaxRek(Igra igra, Ocena o, int globina, int igralec) {
		KoorOc najboljsi = null;
		for (int i = 0; i < igra.velikost; i++) for (int j = 0; j < igra.velikost; j++) {
			Koordinati k = new Koordinati(i, j);
			if (igra.odigraj(k)) {
			KoorOc koc;
			
			switch(igra.stanje) {
			case ZMAGA_MODRI:
				if (igralec == Logika.MODRI) return new KoorOc(k, Ocena.INF);
				else return new KoorOc(k, Ocena.MINUS_INF);
			case ZMAGA_RDECI:
				if (igralec == Logika.RDECI) return new KoorOc(k, Ocena.INF);
				else return new KoorOc(k, Ocena.MINUS_INF);
				
			default:
			if (globina <= 1) koc = new KoorOc(k, o.oceni(igra, igralec));
			else koc = MinimaxRek(igra, o, globina - 1, igralec);
			igra.izbrisi(k);
			
			if (najboljsi == null || igralec == igra.trenutniIgralec && najboljsi.vrednost < koc.vrednost || igralec != igra.trenutniIgralec && najboljsi.vrednost > koc.vrednost)
				najboljsi = koc;
			break;
			}}}
		
		return najboljsi;
	}
}
