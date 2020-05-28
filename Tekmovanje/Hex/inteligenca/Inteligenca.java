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
		algoritem.napredno = true;
		float q = 1.5f;
		if ((igra.krog % 2) == 1 && (igra.krog <= 25)) q = 1.1f;
		else if ((igra.krog % 2) == 0 && (igra.krog <= 20)) q = 1.0f;
		Ocena ocena = new OcenaTretine(1000, 400, 200, q);
		return algoritem.najboljsaPoteza(igra, ocena);
	}
}
