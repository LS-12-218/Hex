package inteligenca;

import logika.Igra;
import logika.Logika;

public class PreprostaOcena extends Ocena {
	
	private int min(int[] tabela) {
		//Vrne minimum
		if (tabela == null) return INF;
		else {
			int trMin = tabela[0];
			for (int i = 1; i < tabela.length; i++) trMin = Math.min(trMin, tabela[i]);
			return trMin;
		}
	}
	
	@Override
	public int oceni(Igra igra, int igralec) {
		int a = min(igra.potezeDoZmageRdeci());
		int b = min(igra.potezeDoZmageModri());
		if (igralec == Logika.RDECI) {
			if (a == 0) return INF;
			else if (b == 0) return MINUS_INF;
			else return b - a;
		}
		else if (igralec == Logika.MODRI) {
			if (b == 0) return INF;
			else if (a == 0) return MINUS_INF;
			else return a - b;
		}
		else return 0;
	}
}
