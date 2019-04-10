package sudokuGit;

public class PruebaSudoku2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MetodosSudokuV2Objeto sudoku = new MetodosSudokuV2Objeto();
		
		sudoku.insertarElemento(1, 1, 2);
		sudoku.insertarElemento(2, 3, 1);
		sudoku.insertarElemento(2, 4, 7);
		sudoku.insertarElemento(2, 5, 8);
		sudoku.insertarElemento(2, 9, 2);
		sudoku.insertarElemento(3, 2, 6);
		sudoku.insertarElemento(3, 3, 7);
		sudoku.insertarElemento(3, 4, 4);
		sudoku.insertarElemento(4, 2, 2);
		sudoku.insertarElemento(4, 4, 3);
		sudoku.insertarElemento(4, 7, 9);
		sudoku.insertarElemento(5, 2, 4);
		sudoku.insertarElemento(5, 6, 7);
		sudoku.insertarElemento(5, 7, 2);
		sudoku.insertarElemento(6, 3, 3);
		sudoku.insertarElemento(7, 5, 9);
		sudoku.insertarElemento(7, 7, 4);
		sudoku.insertarElemento(7, 8, 8);
		sudoku.insertarElemento(8, 1, 9);
		sudoku.insertarElemento(8, 3, 2);
		sudoku.insertarElemento(8, 5, 4);
		sudoku.insertarElemento(8, 7, 6);
		sudoku.insertarElemento(8, 8, 7);
		sudoku.insertarElemento(9, 1, 6);
		sudoku.insertarElemento(9, 4, 8);
		sudoku.insertarElemento(9, 7, 3);
		
		sudoku.resolverSudoku();
		System.out.println("\nResultado: ");
		System.out.println(sudoku.toString());

	}

}
