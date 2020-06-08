package inteligenca;

import logika.Igra;
import logika.Logika;

public class OcenaTretine extends Ocena {
	
	int a1; int a2; int a3; float q;
	
	public OcenaTretine(int a1, int a2, int a3, float q) {
		this.a1 = a1; this.a2 = a2; this.a3 = a3; this.q = q;
	}
	
	private int minIndex(int[] tabela) {
		//Indeks minimuma v tabeli
		if (tabela == null) return INF;
		else {
			int trMin = 0;
			for (int i = 1; i < tabela.length; i++) if (tabela[i] < tabela[trMin]) trMin = i;
			return trMin;
		}
	}
	
	private int minOdDo(int[] tabela, int zacetek, int konec) {
		//Minimum na odseku tabele
		if (tabela == null) return INF;
		else {
			zacetek %= tabela.length;
			int trMin = tabela[zacetek];
			if (konec <= tabela.length) for (int i = zacetek; i < konec; i++) trMin = Math.min(trMin, tabela[i]);
			else {
				for (int i = zacetek; i < tabela.length; i++) trMin = Math.min(trMin, tabela[i]);
				for (int i = 0; i < (konec % tabela.length); i++) trMin = Math.min(trMin, tabela[i]);
			}
			return trMin;
		}
	}
	
	private float oceniTabela(Igra igra, int[] dl) {
		//Izraèunaj minimum na vsaki tretini tabela in vrni rezultat glede na vrednosti a1, a2, a3
		int j = minIndex(dl);
		if (dl[j] == 0) return Ocena.INF;
		else {
			int m1 = minOdDo(dl, j + dl.length / 6, j + (3 * dl.length) / 6);
			int m2 = minOdDo(dl, j + (3 * dl.length) / 6, j + (5 * dl.length) / 6);
			return ((float) a1) / dl[j] + ((float) a2) / (Math.min(m1, m2)) + ((float) a3) / (Math.max(m1, m2));
		}
	}
	
	@Override
	public int oceni(Igra igra, int igralec) {
		int a = Math.round(oceniTabela(igra, igra.potezeDoZmageRdeci()));
		int b = Math.round(oceniTabela(igra, igra.potezeDoZmageModri()));
		
		if (igralec == Logika.RDECI) {
			if (a == INF) return INF;
			else if (b == INF) return MINUS_INF;
			else return (int) (a - q * b);
		}
		
		else if (igralec == Logika.MODRI) {
			if (b == INF) return INF;
			else if (a == INF) return MINUS_INF;
			else return (int) (b - q * a);
		}
		
		else return 0;
	}

}
