package inteligenca;

import logika.Igra;
import splosno.Koordinati;

public abstract class Algoritem {
	public abstract Koordinati najboljsaPoteza (Igra igra, Ocena ocena);
	public Algoritem () {}
}
