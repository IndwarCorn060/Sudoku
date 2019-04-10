package sudokuGit;

public class SudokuAleatorio {
	
	private final static String[] modelos = {".97...4..46...73.2.2.46.......28...9.....1..8.3..7...1...3.2.96....9.7.3.........",
										".3.24...7..69.5..1.426....3..9...4.6....53.....5......21....7.......6..4......1..",
										".........5.1............7843.57..64....98..371.......8.486.7..1....3.......81....",
										"17......8..8...5.3...8...275...7......3.6...2...32......42.3.5..........76..1....",
										"..5.....9..6.5412.4..1.9..3..42....6.126..8...8..4..9.....8.9.1.......5.....2.4..",
										".9..48..57...1..6.1.....4.86........4.9.5.821......5..9.27..6..567..........8....",
										".6....7......78..114..26.3..1.......2..3...787.....562.........5...3...662..5.38.",
										"..17..9...2..6.41..47.9.8.......4..............257...8.1.....6..749.......9..2..5",
										".1....9........3.....6.....8..4.....4....971...31..5...5...7.......3.4.91...6..85",
										"..2.91.343.52...........97.28..1...3...4.....1...3.......3.418.......7....9.5..4."};
	
	public static String getSudokuAleatorio() {
		String cod = modelos[(int)(Math.random()*10)];
		//String cod = modelos[0];
		cod=cambiarNumeros(cod);
		byte[][] array = toArray(cod);
		if(Math.random()>0.5) {
			array = espejoArray(array);	
			System.out.println("Se ha aplicado el espejo");
		}
		array = cambiarFilas(array);
		array=cambiarRegiones(array);
		array=resolver(array,(int)(Math.random()*9));
		cod=toCod(array);
		return cod;
	}
	
	private static byte[][] resolver(byte[][] array, int n) {
		System.out.println("se ha intentado resolver "+n+" numeros");
		MetodosSudokuV2Objeto sudoku = new MetodosSudokuV2Objeto();
		for(int i=0; i<array.length; i++) {
			for(int e=0; e<array[i].length; e++) {
				if(array[i][e]!=0){
					sudoku.insertarElemento(i+1, e+1, array[i][e]);
				}
			}
		}
		sudoku.resolverSudokuNVeces(n);
		for(int i=0; i<array.length; i++) {
			for(int e=0; e<array[i].length; e++) {
				if(sudoku.array[i][e][0]!=0){
					array[i][e]=sudoku.array[i][e][0];
				}
			}
		}
		return array;
	}
	
	private static byte[][] getRegion(byte[][] array, int n){
		byte[][] aux = new byte[3][9];
		for(int i=0; i<aux.length; i++) {
			for(int e=0; e<aux[i].length; e++) {
				aux[i]=array[i+(3*n)];
			}
		}
		return aux;
	}
	
	private static byte[][] setRegion(byte[][] array, byte[][] region, int n){
		for(int i=0; i<region.length; i++) {
			for(int e=0; e<region[i].length; e++) {
				array[i+(3*n)]=region[i];
			}
		}
		return array;
	}
	
	private static byte[][] cambiarRegiones(byte[][] array) {
		byte[][] aux = new byte[3][9];
		for(int e=0; e<2; e++) {
			int n=(int)(Math.random()*6);
			switch(n) {
				case 1:
					aux=getRegion(array,0);
					array=setRegion(array,getRegion(array,1),0);
					array=setRegion(array,aux,1);
					break;
				case 2:
					aux=getRegion(array,0);
					array=setRegion(array,getRegion(array,2),0);
					array=setRegion(array,aux,2);
					break;
				case 3:
					aux=getRegion(array,1);
					array=setRegion(array,getRegion(array,2),1);
					array=setRegion(array,aux,2);
					break;
				case 4:
					aux=getRegion(array,0);
					array=setRegion(array,getRegion(array,1),0);
					array=setRegion(array,getRegion(array,2),1);
					array=setRegion(array,aux,2);
					break;
				case 5:
					aux=getRegion(array,0);
					array=setRegion(array,getRegion(array,2),0);
					array=setRegion(array,getRegion(array,1),2);
					array=setRegion(array,aux,1);
					break;
				default:
			}
			array=espejoArray(array);
		}
		return array;
	}
	
	public static byte[][] cambiarFilas(byte[][] array) {
		byte[] aux = new byte[9];
		for(int e=0; e<2; e++) {
			for(int i=0; i<9; i+=3) {
				int n=(int)(Math.random()*6);
				switch(n) {
					case 1:
						aux=array[i];
						array[i]=array[i+1];
						array[i+1]=aux;
						break;
					case 2:
						aux=array[i];
						array[i]=array[i+2];
						array[i+2]=aux;
						break;
					case 3:
						aux=array[i+1];
						array[i+1]=array[i+2];
						array[i+2]=aux;
						break;
					case 4:
						aux=array[i];
						array[i]=array[i+1];
						array[i+1]=array[i+2];
						array[i+2]=aux;
						break;
					case 5:
						aux=array[i];
						array[i]=array[i+2];
						array[i+2]=array[i+1];
						array[i+1]=aux;
						break;
					default:
				}
			}
			array = espejoArray(array);
		}
		return array;
	}
	
	private static byte[][] espejoArray(byte[][] array){
		byte[][] aux = new byte[9][9];
		for(int i=0; i<array.length; i++) {
			for(int e=0; e<array[i].length; e++) {
				aux[e][i]=array[i][e];
			}
		}
		return aux;
	}
	
	private static String cambiarNumeros(String cod) {
		int i=(int)(Math.random()*4)+7;
		for(int e=0; e<i; e++) {
			char n1=Character.forDigit(((int)(Math.random()*9)+1),10);
			char n2=Character.forDigit(((int)(Math.random()*9)+1),10);
			System.out.println("intercambiando numeros "+n1+" y "+n2);
			cod=cod.replace(n1, '0');
			cod=cod.replace(n2, n1);
			cod=cod.replace('0', n2);
		}
		return cod;
	}
	
	private static byte[][] toArray(String cod){
		byte[][] array = new byte[9][9];
		int cont=0;
		for(int i=0; i<array.length; i++) {
			for(int e=0; e<array[i].length; e++) {
				if(cod.charAt(cont)!='.') {
					array[i][e]=(byte) Character.getNumericValue(cod.charAt(cont));
				}
				cont++;
			}
		}
		return array;
	} 
	
	private static String toCod(byte[][] array) {
		String cod="";
		for(int i=0; i<array.length; i++) {
			for(int e=0; e<array[i].length; e++) {
				if(array[i][e]!=0) {
					cod+=array[i][e];
				}
				else {
					cod+=".";
				}
			}
		}
		return cod;
	}

}
