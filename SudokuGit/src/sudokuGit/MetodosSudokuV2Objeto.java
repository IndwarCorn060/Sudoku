package sudokuGit;

import java.util.Arrays;

import javax.swing.JTextArea;

public class MetodosSudokuV2Objeto {
	
	private final byte TMÑREGION=3;
	
	public byte [][][] array;
	
	private JTextArea txt;
	
	public MetodosSudokuV2Objeto(){
		this.array = new byte [9][9][];
		this.txt = new JTextArea();
	}
	
	public String getTxt() {
		return txt.getText();
	}

	@Override
	public String toString() {
		String cadena="";
		
		for(int i=0; i<this.array.length; i++){
			for(int e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0) {
					cadena+="  ";
				}
				else {
					cadena+=array[i][e][0]+" ";
				}
			}
			cadena+="\n";
		}
		
		return cadena;
	}
	
	public String mostrarTodo() {
		String cadena="";
		
		for(int i=0; i<this.array.length; i++){
			for(int e=0; e<this.array[i].length; e++) {
				
			}
		}
		
		return cadena;
	}
	
	public void insertarElemento(int fila, int columna, int valor) {
		fila=(byte)fila;
		columna=(byte)columna;
		valor=(byte)valor;
		this.array[fila-1][columna-1] = new byte [1];
		this.array[fila-1][columna-1][0]=(byte)valor;
	}
	
	public byte[][][] copiarSudoku(){
		byte[][][] aux= new byte[this.array.length][this.array[0].length][];
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				aux[i][e]= new byte[this.array[i][e].length];
				for(byte o=0; o<this.array[i][e].length; o++) {
					aux[i][e][o]=this.array[i][e][o];
				}
			}
		}
		return aux;
	}
	
	/*
	 * MOTODOS DE RESOLUCION
	 * +METODOS SIN CONTAR POSIBILIDADES
	 * -recuento
	 * -barrido
	 * -numeros bloqueados
	 * +METODOS CONTATNO POSIBILIDADES
	 * -subconjuntos desnudos par y trio
	 * -subconjuntos ocultos par
	 */
	
	private void intentarResolverCasillaPorUnicaOpcionEnLasPosibilidades(byte fila, byte columna) { //METODO DE RESOLUCION POR UNICA OPCION
		byte cont=0, posicion=0;
		for(byte o=0; o<this.array[fila][columna].length; o++) {
			if(this.array[fila][columna][o]!=0) {
				cont++;
				posicion=o;
			}
		}
		if(cont==1) {
			this.array[fila][columna][0]=posicion;
		}
	}

	private void eliminarPosibilidadesDespuesDeResolverPorFila(byte fila, byte columna, byte valor) {
		for(byte i=0; i<this.array[fila].length; i++) {
			if(this.array[fila][i][0]==0) {
				this.array[fila][i][valor]=0;
			}
		}
	}
	
	private void eliminarPosibilidadesDespuesDeResolverPorColumna(byte fila, byte columna, byte valor) {
		for(byte i=0; i<this.array.length; i++) {
			if(this.array[i][columna][0]==0) {
				this.array[i][columna][valor]=0;
			}
		}
	}
	
	private void eliminarPosibilidadesDespuesDeResolverPorRegion(byte fila, byte columna, byte valor) {
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][0]==0) {
					this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][valor]=0;
				}
			}
		}
	}
	
	private void eliminarPosibilidadesDespuesDeResolver(byte fila, byte columna, byte valor) {
		this.eliminarPosibilidadesDespuesDeResolverPorFila(fila, columna, valor);
		this.eliminarPosibilidadesDespuesDeResolverPorColumna(fila, columna, valor);
		this.eliminarPosibilidadesDespuesDeResolverPorRegion(fila, columna, valor);
	}
	
	private void intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnFila(byte fila, byte columna, byte valor) {
		byte cont=0;
		for(byte i=0; i<this.array[fila].length; i++) {
			if(this.array[fila][i][0]==0&&this.array[fila][i][valor]==valor) {
				cont++;
			}
		}
		if(cont==1) {
			this.array[fila][columna][0]=valor;
			this.eliminarPosibilidadesDespuesDeResolver(fila, columna, valor);
			System.out.println(fila+"-"+columna+"="+valor+" por fila");
		}
	}
	
	private void intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnColumna(byte fila, byte columna, byte valor) {
		byte cont=0;
		for(byte i=0; i<this.array.length; i++) {
			if(this.array[i][columna][0]==0&&this.array[i][columna][valor]==valor) {
				cont++;
			}
		}
		if(cont==1) {
			this.array[fila][columna][0]=valor;
			this.eliminarPosibilidadesDespuesDeResolver(fila, columna, valor);
			System.out.println(fila+"-"+columna+"="+valor+" por columna");
		}
	}
	
	private void intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnRegion(byte fila, byte columna, byte valor) {
		byte cont=0;
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][0]==0&&this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][valor]==valor) {
					cont++;
				}
			}
		}
		if(cont==1) {
			this.array[fila][columna][0]=valor;
			this.eliminarPosibilidadesDespuesDeResolver(fila, columna, valor);
			System.out.println(fila+"-"+columna+"="+valor+" por region");
		}
	}
	
	private void intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacion(byte fila, byte columna) { //MOTODO DE RESOLUCION POR BARRIDO
		for(byte o=1; o<this.array[fila][columna].length; o++) {
			if(this.array[fila][columna][0]==0&&this.array[fila][columna][o]!=0) {
				this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnFila(fila,columna,o);
				if(this.array[fila][columna][0]==0) {
					this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnColumna(fila,columna,o);
				}
				if(this.array[fila][columna][0]==0) {
					this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacionEnRegion(fila,columna,o);
				}
			}
		}
	}
	
	private void intentarResolverCasillaPorUnicaPosibilida() {//MOTODO DE RESOLUCION POR BARRIDO
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacion(i, e);
			}
		}
	}
	
	public void iniciarPosibilidades() {
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e]==(null)){
					this.array [i][e] = new byte[10];
					for(byte o=0; o<this.array[i][e].length; o++) {
						this.array[i][e][o]=o;
					}
				}
			}
		}
	}
	
	
	private void eliminarPosibilidadPorValor(byte fila, byte columna, byte valor) {
		this.array[fila][columna][valor]=0;
	}
	
	private void eliminarPosibilidadesPorConteoDeCasillaPorFila(byte fila, byte columna) {
		for(byte i=0; i<this.array[fila].length; i++) {
			if(this.array[fila][i][0]!=0) {
				this.eliminarPosibilidadPorValor(fila, columna, this.array[fila][i][0]);
			}
		}
	}
	
	private void eliminarPosibilidadesPorConteoDeCasillaPorColumna(byte fila, byte columna) {
		for(byte i=0; i<this.array.length; i++) {
			if(this.array[i][columna][0]!=0) {
				this.eliminarPosibilidadPorValor(fila, columna, this.array[i][columna][0]);
			}
		}
	}
	
	private void eliminarPosibilidadesPorConteoDeCasillaPorRegion(byte fila, byte columna) {
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][0]!=0) {
					this.eliminarPosibilidadPorValor(fila, columna, this.array[(fila/TMÑREGION)*TMÑREGION+i][(columna/TMÑREGION)*TMÑREGION+e][0]);
				}
			}
		}
	}
	
	private void eliminarPosibilidadesPorConteoDeCasilla(byte fila, byte columna) {
		this.eliminarPosibilidadesPorConteoDeCasillaPorFila(fila, columna);
		this.eliminarPosibilidadesPorConteoDeCasillaPorColumna(fila, columna);
		this.eliminarPosibilidadesPorConteoDeCasillaPorRegion(fila, columna);
	}
	
	
	private void eliminarPosibilidadesPorConteoDeTodasLasCasillas() {//METODO DE RESOLUCION POR CONTEO
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0) {
					this.eliminarPosibilidadesPorConteoDeCasilla(i, e);
					this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacion(i, e);
				}
			}
		}
	}

	
	public boolean hayTalPosibilidadEnLaPosicion(byte fila, byte columna, byte valor) {
		boolean f=false;
		if(this.array[fila][columna][valor]==valor) {
			f=true;
		}
		return f;
	}

	
	private void quitarUnValorDeLasPosibilidadesDeUnaFilaDeOtrasRegiones(byte fila, byte columna, byte valor) {
		for(byte e=0; e<this.array[fila].length; e++) {
			if(e==columna) {
				e=(byte)(e+TMÑREGION);
			}
			if(e<this.array[fila].length&&this.array[fila][e][0]==0&&this.array[fila][e][valor]==valor) {
				this.array[fila][e][valor]=0;
				System.out.println("quitado el valor "+valor+" de las posibilidades de la casilla "+fila+"-"+e+" por bloqueo en una fila");
			}
		}
	}
	
	private void quitarPosibilidadesPorBloqueEnUnBloqueDeUnValorDeUnaFila(byte fila, byte columna, byte valor) {//esto es el filtro
		byte cont1=0;//actua para las columnas
		boolean f=true;//verifica si todos los 0 de la linea tienen la posobilidad valor
		boolean t=true;//verifica si todos los 0 de las otras lineas no tienen la posibilidad valor
		while (cont1<TMÑREGION&&f) {
			if(this.array[fila][columna+cont1][0]==0&&this.array[fila][columna+cont1][valor]==0) {
				f=false;
			}
			cont1++;
		}
		cont1=0;//aqui reutilizo la variable
		if(f) {
			for(byte i=1; i<TMÑREGION; i++) {
				while(cont1<TMÑREGION&&t) {
					if(this.array[(fila/3)*3+((fila+i)%3)][columna+cont1][0]==0&&this.array[(fila/3)*3+((fila+i)%3)][columna+cont1][valor]==valor) {
						t=false;
					}
					cont1++;
				}
				cont1=0;
			}
		}
		if(f&&t) {//hay que quitar ese valor de las posibilidades de esa linea en las otras regiones
			this.quitarUnValorDeLasPosibilidadesDeUnaFilaDeOtrasRegiones(fila, columna, valor);
			//System.out.println(valor+" removido de las posibilidades de la fila: "+ (fila+1)+" de otras regiones: "+(columna+1));
		}
	}
	
	private void quitarUnValorDeLasPosibilidadesDeUnaColumnaDeOtrasRegiones(byte fila, byte columna, byte valor) {
		for(byte i=0; i<this.array.length; i++) {
			if(i==fila) {
				i=(byte)(i+TMÑREGION);
			}
			if(i<this.array.length&&this.array[i][columna][0]==0&&this.array[i][columna][valor]==valor) {
				this.array[i][columna][valor]=0;
				System.out.println("quitado el valor "+valor+" de las posibilidades de la casilla "+i+"-"+columna+" por bloqueo en una columna");
			}
		}
	}
	
	private void quitarPosibilidadesPorBloqueEnUnBloqueDeUnValorDeUnaColumna(byte fila, byte columna, byte valor) {
		byte cont1=0;//actua para las filas
		boolean f=true;//verifica si todos los 0 de la linea tienen la posobilidad valor
		boolean t=true;//verifica si todos los 0 de las otras lineas no tienen la posibilidad valor
		while (cont1<TMÑREGION&&f) {
			if(this.array[fila+cont1][columna][0]==0&&this.array[fila+cont1][columna][valor]==0) {
				f=false;
			}
			cont1++;
		}
		cont1=0;//aqui reutilizo la variable
		if(f) {
			for(byte e=1; e<TMÑREGION; e++) {
				while(cont1<TMÑREGION&&t) {
					if(this.array[fila+cont1][(columna/3)*3+((columna+e)%3)][0]==0&&this.array[fila+cont1][(columna/3)*3+((columna+e)%3)][valor]==valor) {
						t=false;
					}
					cont1++;
				}
				cont1=0;
			}
		}
		if(f&&t) {//hay que quitar ese valor de las posibilidades de esa linea en las otras regiones
			this.quitarUnValorDeLasPosibilidadesDeUnaColumnaDeOtrasRegiones(fila, columna, valor);
			//System.out.println(valor+" removido de las posibilidades de la columna: "+ (columna+1)+" de otras regiones: "+(fila+1));
		}
	}
	
	private void quitarPosibilidadesPorBloqueEnUnBloque(byte fila, byte columna) {//trabaja por regiones, por lo que trabajara 9 veces
		byte cont1=0;//verifica que haya mas de 1 0 en la linea
		byte cont2=0;//es el primer 0 de la linea
		for(byte i=0; i<TMÑREGION; i++) {//va a mirar los 0 en las filas
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[fila+i][columna+e][0]==0) {
					cont1++;
				}
			}
			if(cont1>1) {//la fila cumple la condicion
				while(this.array[fila+i][columna+cont2][0]!=0) {
					cont2++;
				}
				for(byte o=1; o<this.array[fila+i][columna+cont2].length; o++) {
					if(this.array[fila+i][columna+cont2][o]!=0) {//aqui empieza la comparacion con el primer 0 de la fila
						this.quitarPosibilidadesPorBloqueEnUnBloqueDeUnValorDeUnaFila((byte)(fila+i), columna, o);
					}
				}
				cont2=0;
			}
			cont1=0;
		}
		for(byte e=0; e<TMÑREGION; e++) {//va a mirar los 0 en las columnas
			for(byte i=0; i<TMÑREGION; i++) {
				if(this.array[fila+i][columna+e][0]==0) {
					cont1++;
				}
			}
			if(cont1>1) {//la columna cumple la condicion
				while(this.array[fila+cont2][columna+e][0]!=0) {
					cont2++;
				}
				for(byte o=1; o<this.array[fila+cont2][columna+e].length; o++) {
					if(this.array[fila+cont2][columna+e][o]!=0) {
						this.quitarPosibilidadesPorBloqueEnUnBloqueDeUnValorDeUnaColumna(fila, (byte)(columna+e), o);
					}
				}
				cont2=0;
			}
			cont1=0;
		}
	}

	
	private void quitarPosibilidadesPorBloqueoEnUnaRegionDeUnaFila(byte fila, byte columna, byte valor) {
		for(byte i=1; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[(fila/3)*3+((fila+i)%3)][(columna/3)*3+e][0]==0&&this.array[(fila/3)*3+((fila+i)%3)][(columna/3)*3+e][valor]==valor) {
					this.array[(fila/3)*3+((fila+i)%3)][(columna/3)*3+e][valor]=0;
					System.out.println("quitado el valor "+valor+" de la casilla "+((fila/3)*3+((fila+i)%3))+"-"+((columna/3)*3+e)+" por bloqueo dentro de una region en fila");
				}
			}
		}
	}
	
	private void quitarPosibilidadesPorBloqueoEnUnaRegionDeUnaColumna(byte fila, byte columna, byte valor) {
		for(byte e=1; e<TMÑREGION; e++) {
			for(byte i=0; i<TMÑREGION; i++) {
				if(this.array[(fila/3)*3+i][(columna/3)*3+((columna+e)%3)][0]==0&&this.array[(fila/3)*3+i][(columna/3)*3+((columna+e)%3)][valor]==valor) {
					this.array[(fila/3)*3+i][(columna/3)*3+((columna+e)%3)][valor]=0;
					System.out.println("quitado el valor "+valor+" de la casilla "+((fila/3)*3+i)+"-"+((columna/3)*3+((columna+e)%3)+" por bloqueo dentro de una region en columna"));
				}
			}
		}
	}

	private void quitarPosibilidadesPorBloqueoDeUnaLinea() {
		byte cont1=0;//nuemero de posiciones en las que esta el valor en la fila
		byte cont2=0;//numero de posiciones en las que esta el valor en la fila y en la region
		byte posicion=0; 
		for(byte o=1; o<10; o++){
			for(byte i=0; i<this.array.length; i++){
				for(byte e=0; e<this.array[i].length; e++) {
					if(this.array[i][e][0]==0&&this.array[i][e][o]==o) {//cuenta los valores "o" que haya en la fila
						cont1++;
						posicion=e; //es la ultima posicion donde el valor esta en la fila
					}
				}
				if(cont1>1&&cont1<=TMÑREGION) {//hay entre 2 o 3 valores?
					for(byte e=0; e<TMÑREGION; e++) {
						if(this.array[i][(posicion/3)*3+e][0]==0&&this.array[i][(posicion/3)*3+e][o]==o) {
							cont2++;
						}
					}
					if(cont1==cont2) {//esto indica que todos las posiciones en las que esta el valor "o" estan dentro de la misma region
						this.quitarPosibilidadesPorBloqueoEnUnaRegionDeUnaFila(i, posicion, o);
						//System.out.println("se han quitado las posibilidades por fila del valor "+o+" en la region "+(i+1)+" "+(posicion+1));
					}
					cont2=0;
				}
				cont1=0;
			}
			for(byte e=0; e<this.array[0].length; e++) {
				for(byte i=0; i<this.array.length; i++) {
					if(this.array[i][e][0]==0&&this.array[i][e][o]==o) {//cuenta los valores "o" que haya en la colummna
						cont1++;
						posicion=i; //es la ultima posicion donde el valor esta en la columna
					}
				}
				if(cont1>1&&cont1<=TMÑREGION) {//hay entre 2 o 3 valores?
					for(byte i=0; i<TMÑREGION; i++) {
						if(this.array[(posicion/3)*3+i][e][0]==0&&this.array[(posicion/3)*3+i][e][o]==o) {
							cont2++;
						}
					}
					if(cont1==cont2) {//esto indica que todos las posiciones en las que esta el valor "o" estan dentro de la misma region
						this.quitarPosibilidadesPorBloqueoEnUnaRegionDeUnaColumna(posicion, e, o);
						//System.out.println("se han quitado las posibilidades por columna del valor "+o+" en la region "+(posicion+1)+" "+(e+1));
					}
					cont2=0;
				}
				cont1=0;
			}
		}
	}
	
	private void quitarPosibilidadesPorBloqueo() {
		for(byte i=0; i<TMÑREGION*3; i+=TMÑREGION){
			for(byte e=0; e<TMÑREGION*3; e+=TMÑREGION) {
				this.quitarPosibilidadesPorBloqueEnUnBloque(i, e);
			}
		}
		this.quitarPosibilidadesPorBloqueoDeUnaLinea();
		for(byte i=0; i<this.array.length; i++){
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0) {
					this.intentarResolverCasillaPorUnicaPosibilidaEnLaUbicacion(i, e);
				}
			}
		}
	}
	
	private byte numPosibilidades(byte fila, byte columna) {
		byte cont=0;
		for(byte o=1; o<this.array[fila][columna].length; o++) {
			if(this.array[fila][columna][o]!=0) {
				cont++;
			}
		}
		return cont;
	}
	
	private void quitarPosibilidadesPorParDesnudoEnFila(byte fila, byte columna1, byte columna2, byte p1, byte p2) {
		for(byte e=0; e<this.array[fila].length; e++) {
			if(this.array[fila][e][0]==0&&e!=columna1&&e!=columna2) {
				if(this.array[fila][e][p1]==p1) {
					this.array[fila][e][p1]=0;
					System.out.println("quitado el valor "+p1+" de la casilla "+fila+"-"+e+" por fila (par desnudo)");
				}
				if(this.array[fila][e][p2]==p2) {
					this.array[fila][e][p2]=0;
					System.out.println("quitado el valor "+p2+" de la casilla "+fila+"-"+e+" por fila (par desnudo)");
				}
			}
		}
	}
	
	private void filtroParaQuitarPosibilidadesPorParDesnudoEnFila(byte fila) {
		byte cont1=0;//mira que solo haya 2 casillas iguales
		byte cont2=1;//se encarga de recojer el valor de las posibilidades
		byte p1=0;//posibilidad 1
		byte p2=0;//posibilidad 2
		byte segundoMiembro=0;
		for(byte e=0; e<this.array[fila].length; e++) {
			if(this.numPosibilidades(fila, e)==2) {
				for(byte e2=0; e2<this.array[fila].length; e2++) {
					if(e2!=e&&Arrays.equals(this.array[fila][e], this.array[fila][e2])) {
						cont1++;
						segundoMiembro=e2;
					}
				}
				if(cont1==1&&e<segundoMiembro&&(segundoMiembro-e>=TMÑREGION)) {
					while(this.array[fila][e][cont2]==0) {
						cont2++;
					}
					p1=cont2;
					cont2++;
					while(this.array[fila][e][cont2]==0) {
						cont2++;
					}
					p2=cont2;
					this.quitarPosibilidadesPorParDesnudoEnFila(fila, e, segundoMiembro, p1, p2);
					cont2=1;
				}
				cont1=0;
			}
		}
		
	}
	
	private void quitarPosibilidadesPorParDesnudoEnColumna(byte columna, byte fila1, byte fila2, byte p1, byte p2) {
		for(byte i=0; i<this.array.length; i++) {
			if(this.array[i][columna][0]==0&&i!=fila1&&i!=fila2) {
				if(this.array[i][columna][p1]==p1) {
					this.array[i][columna][p1]=0;
					System.out.println("quitado el valor "+p1+" de la casilla "+i+"-"+columna+" por columna (par desnudo)");
				}
				if(this.array[i][columna][p2]==p2) {
					this.array[i][columna][p2]=0;
					System.out.println("quitado el valor "+p2+" de la casilla "+i+"-"+columna+" por columna (par desnudo)");
				}
			}
		}
	}

	private void filtroParaQuitarPosibilidadesPorParDesnudoEnColumna(byte columna) {
		byte cont1=0;//mira que solo haya 2 casillas iguales
		byte cont2=1;//se encarga de recojer el valor de las posibilidades
		byte p1=0;//posibilidad 1
		byte p2=0;//posibilidad 2
		byte segundoMiembro=0;
		for(byte i=0; i<this.array.length; i++) {
			if(this.numPosibilidades(i, columna)==2) {
				for(byte i2=0; i2<this.array.length; i2++) {
					if(i2!=i&&Arrays.equals(this.array[i][columna], this.array[i2][columna])) {
						cont1++;
						segundoMiembro=i2;
					}
				}
				if(cont1==1&&i<segundoMiembro&&(segundoMiembro-i>=TMÑREGION)) {
					while(this.array[i][columna][cont2]==0) {
						cont2++;
					}
					p1=cont2;
					cont2++;
					while(this.array[i][columna][cont2]==0) {
						cont2++;
					}
					p2=cont2;
					this.quitarPosibilidadesPorParDesnudoEnColumna(columna, i, segundoMiembro, p1, p2);
					cont2=1;
				}
				cont1=0;
			}
		}
	}
	
	private void quitarPosibilidadesPorParDesnudoEnRegion(byte fila1, byte fila2, byte columna1, byte columna2, byte p1, byte p2) {
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[(fila1/3)*3+i][(columna1/3)*3+e][0]==0
					&&!Arrays.equals(this.array[(fila1/3)*3+i][(columna1/3)*3+e], this.array[fila1][columna1])
					&&!Arrays.equals(this.array[(fila1/3)*3+i][(columna1/3)*3+e], this.array[fila2][columna2])) 
					{
					this.array[(fila1/3)*3+i][(columna1/3)*3+e][p1]=0;
					this.array[(fila1/3)*3+i][(columna1/3)*3+e][p2]=0;
					System.out.println("quitados los valores "+p1+" y "+p2+" de la casilla "+((fila1/3)*3+i)+"-"+((columna1/3)*3+e)+" por region");
				}
			}
		}
	}
	
	private void filtroParaQuitarPosibilidadesPorParDesnudoEnRegion(byte fila, byte columna) {
		byte cont1=0;//mira que solo haya 2 0
		byte cont2=1;
		byte p1=0;
		byte p2=0;
		byte segundoMiembroFila=0;
		byte segundoMiembroColumna=0;
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				if(this.array[fila+i][columna+e][0]==0&&this.numPosibilidades((byte)(fila+i), (byte)(columna+e))==2
					&&!Arrays.equals(this.array[fila+i][columna+e], this.array[((fila+i)/3)*3+((fila+i+1)%3)][columna+e])
					&&!Arrays.equals(this.array[fila+i][columna+e], this.array[((fila+i)/3)*3+((fila+i+2)%3)][columna+e])
					&&!Arrays.equals(this.array[fila+i][columna+e], this.array[fila+i][((columna+e)/3)*3+((columna+e+1)%3)])
					&&!Arrays.equals(this.array[fila+i][columna+e], this.array[fila+i][((columna+e)/3)*3+((columna+e+2)%3)])
					) {
					for(byte i2=1; i2<TMÑREGION; i2++) {
						for(byte e2=1; e2<TMÑREGION; e2++) {
							if(this.array[((fila+i)/3)*3+((fila+i+i2)%3)][((columna+e)/3)*3+((columna+e+e2)%3)][0]==0
								&&Arrays.equals(this.array[fila+i][columna+e], this.array[((fila+i)/3)*3+((fila+i+i2)%3)][((columna+e)/3)*3+((columna+e+e2)%3)])
								) {
								cont1++;
								segundoMiembroFila=(byte)(((fila+i)/3)*3+((fila+i+i2)%3));
								segundoMiembroColumna=(byte)(((columna+e)/3)*3+((columna+e+e2)%3));
							}
						}
					}
					if(cont1==1&&segundoMiembroFila>fila+i) {
						while(this.array[fila+i][columna+e][cont2]==0) {
							cont2++;
						}
						p1=cont2;
						cont2++;
						while(this.array[fila+i][columna+e][cont2]==0) {
							cont2++;
						}
						p2=cont2;
						this.quitarPosibilidadesPorParDesnudoEnRegion((byte)(fila+i), segundoMiembroFila, (byte)(columna+e), segundoMiembroColumna, p1, p2);
						cont2=1;
					}
					cont1=0;
				}
			}
		}
	}
	
	private void parDesnudo() {
		for(byte u=0; u<this.array.length; u++) {
			this.filtroParaQuitarPosibilidadesPorParDesnudoEnFila(u);
			this.filtroParaQuitarPosibilidadesPorParDesnudoEnColumna(u);
		}
		for (byte i=0; i<this.array.length; i+=TMÑREGION) {
			for(byte e=0; e<this.array[i].length; e+=TMÑREGION) {
				this.filtroParaQuitarPosibilidadesPorParDesnudoEnRegion(i,e);
			}
		}
	}
	
	private boolean cumpleCondicionesParaElTrio(byte fila, byte columna, byte p1, byte p2, byte p3) {
		boolean f=true;
		for(byte o=1; o<this.array[fila][columna].length; o++) {
			if(o!=p1&&o!=p2&&o!=p3&&this.array[fila][columna][o]!=0) {
				f=false;
			}
		}
		return f;
	}
	
	private void filtroTrioDesnudo (byte fila, byte columna, byte p1, byte p2, byte p3) {
		byte cont=0;
		//fila
		byte segundoMiembroColumna=0, tercerMiembroColumna=0;
		for (byte e=0; e<this.array[fila].length; e++) {
			if(this.array[fila][e][0]==0&&e!=columna&&this.numPosibilidades(fila, e)>1&&this.numPosibilidades(fila, e)<4&&this.cumpleCondicionesParaElTrio(fila, e, p1, p2, p3)) {
				cont++;
				if(cont==1) {
					segundoMiembroColumna=e;
				}
				if(cont==2) {
					tercerMiembroColumna=e;
				}
			}
		}
		if(cont==2&&(!Arrays.equals(this.array[fila][segundoMiembroColumna], this.array[fila][tercerMiembroColumna])||(this.numPosibilidades(fila, segundoMiembroColumna)==3&&this.numPosibilidades(fila, tercerMiembroColumna)==3))) {
			for(byte e=0; e<this.array[fila].length; e++) {
				if(e!=columna&&e!=segundoMiembroColumna&&e!=tercerMiembroColumna&&this.array[fila][e][0]==0) {
					if(this.array[fila][e][p1]==p1) {
						this.array[fila][e][p1]=0;
						System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo en la fila");
					}
					if(this.array[fila][e][p2]==p2) {
						this.array[fila][e][p2]=0;
						System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo en la fila");
					}
					if(this.array[fila][e][p3]==p3) {
						this.array[fila][e][p3]=0;
						System.out.println("valor "+p3+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo en la fila");
					}
				}
			}
		}
		segundoMiembroColumna=0;
		tercerMiembroColumna=0;
		cont=0;
		//columna
		byte segundoMiembroFila=0, tercerMiembroFila=0;
		for (byte i=0; i<this.array.length; i++) {
			if(this.array[i][columna][0]==0&&i!=fila&&this.numPosibilidades(i, columna)>1&&this.numPosibilidades(i, columna)<4&&this.cumpleCondicionesParaElTrio(i, columna, p1, p2, p3)) {
				cont++;
				if(cont==1) {
					segundoMiembroFila=i;
				}
				if(cont==2) {
					tercerMiembroFila=i;
				}
			}
		}
		if(cont==2&&(!Arrays.equals(this.array[segundoMiembroFila][columna], this.array[tercerMiembroFila][columna])||(this.numPosibilidades(segundoMiembroFila, columna)==3&&this.numPosibilidades(tercerMiembroFila, columna)==3))) {
			for(byte i=0; i<this.array.length; i++) {
				if(i!=fila&&i!=segundoMiembroFila&&i!=tercerMiembroFila&&this.array[i][columna][0]==0) {
					if(this.array[i][columna][p1]==p1) {
						this.array[i][columna][p1]=0;
						System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo en la columna");
					}
					if(this.array[i][columna][p2]==p2) {
						this.array[i][columna][p2]=0;
						System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo en la columna");
					}
					if(this.array[i][columna][p3]==p3) {
						this.array[i][columna][p3]=0;
						System.out.println("valor "+p3+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo en la columna");
					}
				}
			}
		}
		segundoMiembroFila=0;
		tercerMiembroFila=0;
		cont=0;
		//region
		byte filaAux;
		byte columnaAux;
		for(byte i=0; i<TMÑREGION; i++) {
			for(byte e=0; e<TMÑREGION; e++) {
				filaAux=(byte)((fila/3)*3+(fila+i)%3);
				columnaAux=(byte)((columna/3)*3+(columna+e)%3);
				if(this.array[filaAux][columnaAux][0]==0&&!(filaAux==fila&&columnaAux==columna)
					&&this.numPosibilidades(filaAux, columnaAux)>1
					&&this.numPosibilidades(filaAux, columnaAux)<4
					&&this.cumpleCondicionesParaElTrio(filaAux, columnaAux, p1, p2, p3)) 
					{
					cont++;
					if(cont==1) {
						segundoMiembroFila=filaAux;
						segundoMiembroColumna=columnaAux;
					}
					if(cont==2) {
						tercerMiembroFila=filaAux;
						tercerMiembroColumna=columnaAux;
					}
				}
			}
		}
		if(cont==2&&(!Arrays.equals(this.array[segundoMiembroFila][segundoMiembroColumna], this.array[tercerMiembroFila][tercerMiembroColumna])||(this.numPosibilidades(segundoMiembroFila, segundoMiembroColumna)==3&&this.numPosibilidades(tercerMiembroFila, tercerMiembroColumna)==3))) {
			for(byte i=0; i<TMÑREGION; i++) {
				for(byte e=0; e<TMÑREGION; e++) {
					filaAux=(byte)((fila/3)*3+(fila+i)%3);
					columnaAux=(byte)((columna/3)*3+(columna+e)%3);
					if(this.array[filaAux][columnaAux][0]==0&&!(fila==filaAux&&columna==columnaAux)&&!(segundoMiembroFila==filaAux&&segundoMiembroColumna==columnaAux)&&!(tercerMiembroFila==filaAux&&tercerMiembroColumna==columnaAux)) {
						if(this.array[filaAux][columnaAux][p1]==p1) {
							this.array[filaAux][columnaAux][p1]=0;
							System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+filaAux+"-"+columnaAux+" devido a trio desnudo en la region");
						}
						if(this.array[filaAux][columnaAux][p2]==p2) {
							this.array[filaAux][columnaAux][p2]=0;
							System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+filaAux+"-"+columnaAux+" devido a trio desnudo en la region");
						}
						if(this.array[filaAux][columnaAux][p2]==p2) {
							this.array[filaAux][columnaAux][p2]=0;
							System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+filaAux+"-"+columnaAux+" devido a trio desnudo en la region");
						}
					}
				}
			}
		}
	}
	
	private void filtroTrioDesnudo (byte fila, byte columna, byte p1, byte p2) {
		byte p3=1;
		//fila
		for(byte columna2=(byte)(columna+1); columna2<this.array[fila].length; columna2++) {
			if(this.array[fila][columna2][0]==0&&this.numPosibilidades(fila, columna2)==2&&this.array[fila][columna2][p1]==p1&&this.array[fila][columna2][p2]==0) {
				while(this.array[fila][columna2][p3]==0||p3==p1) {
					p3++;
				}
				for(byte columna3=(byte)(columna+1); columna3<this.array[fila].length; columna3++) {
					if(this.array[fila][columna3][0]==0&&this.numPosibilidades(fila, columna3)==2&&this.array[fila][columna3][p2]==p2&&this.array[fila][columna3][p3]==p3) {
						for(byte e=0; e<this.array[fila].length; e++) {
							if(this.array[fila][e][0]==0&&e!=columna&&e!=columna2&&e!=columna3) {
								if(this.array[fila][e][p1]==p1) {
									this.array[fila][e][p1]=0;
									System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo* en la fila");
								}
								if(this.array[fila][e][p2]==p2) {
									this.array[fila][e][p2]=0;
									System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo* en la fila");
								}
								if(this.array[fila][e][p3]==p3) {
									this.array[fila][e][p3]=0;
									System.out.println("valor "+p3+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+e+" devido a trio desnudo* en la fila");
								}
							}
						}
					}
				}
				p3=1;
			}
		}
		//columna
		for(byte fila2=(byte)(fila+1); fila2<this.array.length; fila2++) {
			if(this.array[fila2][columna][0]==0&&this.numPosibilidades(fila2, columna)==2&&this.array[fila2][columna][p1]==p1&&this.array[fila2][columna][p2]==0) {
				while(this.array[fila2][columna][p3]==0||p3==p1) {
					p3++;
				}
				for(byte fila3=(byte)(fila+1); fila3<this.array.length; fila3++) {
					if(this.array[fila3][columna][0]==0&&this.numPosibilidades(fila3, columna)==2&&this.array[fila3][columna][p2]==p2&&this.array[fila3][columna][p3]==p3) {
						for(byte i=0; i<this.array[fila].length; i++) {
							if(this.array[i][columna][0]==0&&i!=fila&&i!=fila2&&i!=fila3) {
								if(this.array[i][columna][p1]==p1) {
									this.array[i][columna][p1]=0;
									System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo* en la columna");
								}
								if(this.array[i][columna][p2]==p2) {
									this.array[i][columna][p2]=0;
									System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo* en la columna");
								}
								if(this.array[i][columna][p3]==p3) {
									this.array[i][columna][p3]=0;
									System.out.println("valor "+p3+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+columna+" devido a trio desnudo* en la columna");
								}
							}
						}
					}
				}
				p3=1;
			}
		}
		//region
		for(byte fila2=fila; fila2<(fila/3)*3+3; fila2++) {
			for(byte columna2=(byte)((columna/3)*3); columna2<(columna/3)*3+3; columna2++) {
				if(fila2==fila&&columna2<=columna) {
					columna2=(byte)(columna);
				}
				if(this.array[fila2][columna2][0]==0&&this.numPosibilidades(fila2, columna2)==2&&this.array[fila2][columna2][p1]==p1&&this.array[fila2][columna2][p2]==0) {
					while(this.array[fila2][columna2][p3]==0||p3==p1) {
						p3++;
					}
					for(byte fila3=fila; fila3<(fila/3)*3+3; fila3++) {
						for(byte columna3=(byte)((columna/3)*3); columna3<(columna/3)*3+3; columna3++) {
							if(fila3==fila&&columna3<=columna) {
								columna3=(byte)(columna);
							}
							if(this.array[fila3][columna3][0]==0&&this.numPosibilidades(fila3, columna3)==2&&this.array[fila3][columna3][p2]==p2&&this.array[fila3][columna3][p3]==p3) {
								for(byte i=fila; i<(fila/3)*3+3; i++) {
									for(byte e=(byte)((columna/3)*3); e<(columna/3)*3+3; e++) {
										if(i==fila&&e<=columna) {
											e=(byte)(columna);
										}
										if(this.array[i][e][0]==0&&!(i==fila&&e==columna)&&!(i==fila2&&e==columna2)&&!(i==fila3&&e==columna3)) {
											if(this.array[i][e][p1]==p1) {
												this.array[i][e][p1]=0;
												System.out.println("valor "+p1+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+e+" devido a trio desnudo* en la region");
											}
											if(this.array[i][e][p2]==p2) {
												this.array[i][e][p2]=0;
												System.out.println("valor "+p2+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+e+" devido a trio desnudo* en la region");
											}
											if(this.array[i][e][p3]==p3) {
												this.array[i][e][p3]=0;
												System.out.println("valor "+p3+" ha sido eliminado de las posibilidades de la casilla "+i+"-"+e+" devido a trio desnudo* en la region");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void trioDesnudo() {
		byte p1, p2, p3;
		byte cont=1;
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0&&this.numPosibilidades(i, e)==3) {
					while(this.array[i][e][cont]==0) {
						cont++;
					}
					p1=cont;
					cont++;
					while(this.array[i][e][cont]==0) {
						cont++;
					}
					p2=cont;
					cont++;
					while(this.array[i][e][cont]==0) {
						cont++;
					}
					p3=cont;
					this.filtroTrioDesnudo(i, e, p1, p2, p3);
					cont=1;
				}
				if(this.array[i][e][0]==0&&this.numPosibilidades(i, e)==2) {
					while(this.array[i][e][cont]==0) {
						cont++;
					}
					p1=cont;
					cont++;
					while(this.array[i][e][cont]==0) {
						cont++;
					}
					p2=cont;
					this.filtroTrioDesnudo(i, e, p1, p2);
					cont=1;
				}
			}
		}
	}
	
	private void subconjuntosDesnudos() {
		this.parDesnudo();
		//this.trioDesnudo();
	}
	
	
	private byte numPosibilidadesCompartidas(byte fila1, byte columna1, byte fila2, byte columna2) {
		byte cont=0;
		for(byte o=1; o<this.array[fila1][columna1].length; o++) {
			if(this.array[fila1][columna1][o]!=0&&this.array[fila1][columna1][o]==this.array[fila2][columna2][o]) {
				cont++;
			}
		}
		return cont;
	}
	
	private void quitarPosibilidadesExceptoDos(byte fila, byte columna, byte p1, byte p2) {
		for(byte o=1; o<this.array[fila][columna].length; o++) {
			if(this.array[fila][columna][0]==0&&o!=p1&&o!=p2&&this.array[fila][columna][o]==o) {
				this.array[fila][columna][o]=0;
				System.out.println("El valor "+o+" ha sido eliminado de las posibilidades de la casilla "+fila+"-"+columna+" por Par Oculto");
			}
		}
	}
	
	private void filtoParOculto(byte fila, byte columna) {
		byte cont=0;
		byte p1=0;
		byte p2=0;
		boolean f=true;
		//fila
		byte columna2=0;
		for(byte e=0; e<this.array[fila].length; e++) {
			if(this.array[fila][e][0]==0&&e!=columna&&this.numPosibilidadesCompartidas(fila, columna, fila, e)==2) {
				cont++;
				columna2=e;
			}
		}
		if(cont==1) {
			cont=1;//reciclar variable
			while(p1==0) {
				if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila][columna2][cont]) {
					p1=cont;
				}
				cont++;
			}
			while(p2==0) {
				if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila][columna2][cont]) {
					p2=cont;
				}
				cont++;
			}
			for(byte e=0; e<this.array[fila].length; e++) {
				if(this.array[fila][e][0]==0&&e!=columna&&e!=columna2&&(this.array[fila][e][p1]==p1||this.array[fila][e][p2]==p2)) {
					f=false;
				}
			}
			if(f) {
				this.quitarPosibilidadesExceptoDos(fila, columna, p1, p2);
				this.quitarPosibilidadesExceptoDos(fila, columna2, p1, p2);
			}
		}
		cont=0;
		p1=0;
		p2=0;
		f=true;
		byte fila2=0;
		//columna
		if(this.numPosibilidades(fila, columna)>2) {
			for(byte i=0; i<this.array.length; i++) {
				if(this.array[i][columna][0]==0&&i!=fila&&this.numPosibilidadesCompartidas(fila, columna, i, columna)==2) {
					cont++;
					fila2=i;
				}
			}
			if(cont==1) {
				cont=1;//reciclar variable
				while(p1==0) {
					if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila2][columna][cont]) {
						p1=cont;
					}
					cont++;
				}
				while(p2==0) {
					if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila2][columna][cont]) {
						p2=cont;
					}
					cont++;
				}
				for(byte i=0; i<this.array.length; i++) {
					if(this.array[i][columna][0]==0&&i!=fila&&i!=fila2&&(this.array[i][columna][p1]==p1||this.array[i][columna][p2]==p2)) {
						f=false;
					}
				}
				if(f){
					this.quitarPosibilidadesExceptoDos(fila, columna, p1, p2);
					this.quitarPosibilidadesExceptoDos(fila2, columna, p1, p2);
				}
			}
			cont=0;
		}
		p1=0;
		p2=0;
		f=true;
		//region
		if(this.numPosibilidades(fila, columna)>2) {
			fila2=0;
			columna2=0;
			for(byte i=(byte)((fila/3)*3); i<((fila/3)*3)+3; i++) {
				for(byte e=(byte)((columna/3)*3); e<((columna/3)*3)+3; e++) {
					if(this.array[i][e][0]==0&&!(i==fila&&e==columna)&&this.numPosibilidadesCompartidas(fila, columna, i, e)==2) {
						cont++;
						fila2=i;
						columna2=e;
					}
				}
			}
			if(cont==1) {
				cont=1;//reciclar variable
				while(p1==0) {
					if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila2][columna2][cont]) {
						p1=cont;
					}
					cont++;
				}
				while(p2==0) {
					if(this.array[fila][columna][cont]!=0&&this.array[fila][columna][cont]==this.array[fila2][columna2][cont]) {
						p2=cont;
					}
					cont++;
				}
				for(byte i=(byte)((fila/3)*3); i<((fila/3)*3)+3; i++) {
					for(byte e=(byte)((columna/3)*3); e<((columna/3)*3)+3; e++) {
						if(this.array[i][e][0]==0&&!(i==fila&&e==columna)&&!(i==fila2&&e==columna2)&&(this.array[i][e][p1]==p1||this.array[i][e][p2]==p2)) {
							f=false;
						}
					}
				}
				if(f) {
					this.quitarPosibilidadesExceptoDos(fila, columna, p1, p2);
					this.quitarPosibilidadesExceptoDos(fila2, columna2, p1, p2);
				}
			}
		}
	}
	
	private void parOculto() {
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0&&this.numPosibilidades(i, e)>2) {
					this.filtoParOculto(i, e);
				}
			}
		}
	}
	
	private void filtoTrioOculto(byte fila, byte columna) {
		
		//fila
		
		for(byte e=0; e<this.array[fila].length; e++) {
			if(this.array[fila][e][0]==0&&e!=columna) {
				if(this.numPosibilidadesCompartidas(fila, columna, fila, e)==3) {
					
				}
				if(this.numPosibilidadesCompartidas(fila, columna, fila, e)==2) {
					
				}
			}
		}
	}
	
	private void trioOculto() {
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0&&this.numPosibilidades(i, e)>3) {
					this.filtoTrioOculto(i, e);
				}
			}
		}
	}
	
	private void subconjuntosOcultos() {
		this.parOculto();
		//this.trioOculto();
	}
	
	private byte numCeros() {
		byte numCeros=0;
		for(byte i=0; i<this.array.length; i++) {
			for(byte e=0; e<this.array[i].length; e++) {
				if(this.array[i][e][0]==0) {
					numCeros++;
				}
			}
		}
		return numCeros;
	}

	public void resolverSudokuAleatorio(byte ceros) {
		MetodosSudokuV2Objeto aux = new MetodosSudokuV2Objeto();
		aux.array=this.copiarSudoku();
		byte cont=0;
		boolean f;
		byte i=0, e, o;
		while(this.numCeros()>0&&i<9) {
			e=0;
			while(this.numCeros()>0&&e<9) {
				if(aux.array[i][e][0]==0) {
					o=1;
					while(this.numCeros()>0&&o<10) {
						f=false;
						if(aux.array[i][e][o]!=0) {
							aux.array[i][e][0]=o;
							System.out.println("probando el valor "+o+" en la casilla "+i+"-"+e);
							alTexto("probando el valor "+o+" en la casilla "+i+"-"+e);
							for(int u=0; u<this.numCeros(); u++) {
								if(aux.numCeros()>0) {
									aux.eliminarPosibilidadesPorConteoDeTodasLasCasillas();
								}	
							}
							System.out.println(aux.toString());
							if(aux.numCeros()==0) {
								this.array=aux.copiarSudoku();
								System.out.println("Exito");
							}
							else {
								System.out.println("Error");
								for(byte i2=0; i2<aux.array.length; i2++) {
									for(byte e2=0; e2<aux.array[i].length; e2++) {
										if(aux.array[i2][e2][0]==0) {
											cont++;
										}	
									}
									if(cont==1) {
										f=true;
									}
									cont=0;
								}
								aux.array=this.copiarSudoku();
								aux.array[i][e][0]=0;
								if(f) {
									System.out.println("en este caso podremos quitar el valor "+o+" de las posibilidades de la casilla "+i+"-"+e);
									alTexto("en este caso podremos quitar el valor "+o+" de las posibilidades de la casilla "+i+"-"+e);
									this.array[i][e][o]=0;
								}
							}
						}
						o++;
					}
				}
				e++;
			}
			i++;
		}
	}
	
	public int getValor(int fila, int columna) {
		return this.array[fila][columna][0];
	}
	
	private void alTexto() {
		if(txt!=null) {
			this.txt.append(this.toString()+"\n");
		}
	}
	
	private void alTexto(String texto) {
		if(txt!=null) {
			this.txt.append(texto+"\n");
		}
	}
	
	public void resolverSudokuNVeces(int n) {
		this.iniciarPosibilidades();
		System.out.println(this.toString());
		byte numCerosIniciales=this.numCeros();
		byte numCerosRondaAnterior=0;
		while(this.numCeros()>0&&numCerosRondaAnterior!=this.numCeros()&&numCerosIniciales-numCeros()<n) {
			numCerosRondaAnterior=this.numCeros();
			for(int u=0; u<numCerosIniciales; u++) {
				if(this.numCeros()>0) {
					this.eliminarPosibilidadesPorConteoDeTodasLasCasillas();
				}
			}
		}
	}
	
	public void resolverSudoku() {
		this.iniciarPosibilidades();
		System.out.println(this.toString());
		alTexto();
		byte numCerosIniciales=this.numCeros();
		byte numCerosRondaAnterior=0;
		while(this.numCeros()>0&&numCerosRondaAnterior!=this.numCeros()) {
			numCerosRondaAnterior=this.numCeros();
			for(int u=0; u<numCerosIniciales; u++) {
				if(this.numCeros()>0) {
					this.eliminarPosibilidadesPorConteoDeTodasLasCasillas();
				}
			}
			System.out.println("Resultado despues de aplicar el primer y segundo metodo, RECUENTO y BARRIDO: ");
			System.out.println(this.toString());
			alTexto();
			if(this.numCeros()>0) {
				this.quitarPosibilidadesPorBloqueo();
				System.out.println("Resultado despues de aplicar el tercero metodo, NUMEROS BLOQUEADOS: ");
				System.out.println(this.toString());
			}
			if(this.numCeros()>0) {
				this.subconjuntosDesnudos();
				System.out.println("subconjuntos desnudos hecho (par y trio)");
			}
			if(this.numCeros()>0) {
				this.subconjuntosOcultos();
				System.out.println("subconjuntos ocultos hecho (par)");
			}
			System.out.println("Quedan "+this.numCeros()+" huecos.\n");
		}
		if(this.numCeros()>0) {
			System.out.println("Con los metodos de este programa no se puede avanzar mas \nAhora haremos Prueba y error: ");
			this.resolverSudokuAleatorio(this.numCeros());
		}
		alTexto();
	}

}
