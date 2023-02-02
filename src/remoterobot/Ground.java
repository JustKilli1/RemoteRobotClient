package remoterobot;

public enum Ground {
	NICHTS ("NICHTS"),
	SAND ("SAND"),
	GEROELL ("GEROELL"),
	FELS ("FELS"),
	WASSER ("WASSER"),
	PFLANZEN ("PFLANZEN"),
	MORAST ("MORAST"),
	LAVA ("LAVA"),
	;
		String name;
		
		Ground(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
}