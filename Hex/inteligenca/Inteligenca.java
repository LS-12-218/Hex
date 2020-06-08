package inteligenca;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra {

	public Inteligenca(String ime) {
		super(ime);
	}

	public Inteligenca() {
		super("LSolo");
	}
	
	public Koordinati izberiPotezo(Igra igra) {
		MMAB algoritem = new MMAB(8, 11, 2, 3);
		Ocena ocena = new OcenaTretine(1000, 400, 200, 1.1f);
		return algoritem.najboljsaPoteza(igra, ocena);
	}
}
