package inteligenca;

import logika.Igra;

public abstract class Ocena {
	static final int INF = Integer.MAX_VALUE;
	static final int MINUS_INF = -Integer.MAX_VALUE;
	public abstract int oceni(Igra igra, int igralec);
	public Ocena() {}
}
