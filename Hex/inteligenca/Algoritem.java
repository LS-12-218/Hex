package inteligenca;

import logika.Igra;
import splosno.Koordinati;

public abstract class Algoritem {
	//Abstraktni razred za vse algoritme za inteligenco
	public abstract Koordinati najboljsaPoteza (Igra igra, Ocena ocena);
	public Algoritem () {}
}
