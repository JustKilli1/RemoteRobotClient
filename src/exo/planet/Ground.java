package exo.planet;

// Untergrund-Aufzaehlung

// Alle enums bieten automatisch folgenden Methoden:
// String name() - Liefert der Enum-Wert als String
// int ordinal() - Liefert den Enum-Wert als Zahl
// EnumObj EnumName.valueOf(String name) - wandelt einen String in Enum-Wert um

import java.awt.*;

public enum Ground {
	NICHTS(Color.WHITE),
	SAND(Color.YELLOW),
	GEROELL(Color.GRAY),
	FELS(Color.DARK_GRAY),
	WASSER(Color.BLUE),
	PFLANZEN(Color.GREEN),
	MORAST(Color.BLACK),
	LAVA(Color.ORANGE)
	;

	Color color;

	Ground(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
