package sudokuGit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class SudokuVentana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField[][] input;
	private JButton resolver;
	private JButton reset;
	private JCheckBoxMenuItem mLog;
	private JTextArea txt;
	private JScrollPane log;
	private Container cp;
	private JMenuItem sudokuPrueba;
	private JMenuItem sudokuAleatorio;
	
	public SudokuVentana(String titulo) {
		super(titulo);
		this.input = new JTextField[9][9];
		this.resolver = new JButton("Resolver");
		this.reset = new JButton("Resetear");
		this.mLog = new JCheckBoxMenuItem("Mostrar Log");
		this.mLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		this.sudokuPrueba = new JMenuItem("Cargar sudoku prueba");
		this.sudokuAleatorio = new JMenuItem("Sudoku Aleatorio");
		this.txt = new JTextArea();
		this.log = new JScrollPane(txt);
		
		JMenuBar barra = new JMenuBar();
			JMenu mSudoku = new JMenu("Sudoku");
			mSudoku.add(this.sudokuPrueba);
			mSudoku.add(this.sudokuAleatorio);
			JMenu mVer = new JMenu("Ver");
			mVer.add(this.mLog);
		barra.add(mSudoku);
		barra.add(mVer);
		this.setJMenuBar(barra);
		
		this.cp = this.getContentPane();
		
		JPanel centro = new JPanel(new GridLayout(9,9));
			for(int i=0; i<this.input.length; i++) {
				for(int e=0; e<this.input[i].length; e++) {
					this.input[i][e] = new JTextField(1);
					centro.add(this.input[i][e]);
				}
			}
		centro.setPreferredSize(new Dimension(180,180));
		JPanel sur = new JPanel();
			sur.add(this.resolver);
			sur.add(this.reset);
		cp.add(centro, BorderLayout.CENTER);
		cp.add(sur, BorderLayout.SOUTH);
		log.setPreferredSize(new Dimension(300,50));
		log.setVisible(false);
		cp.add(log, BorderLayout.EAST);
		
		ControlBoton cb = new ControlBoton(this);
		this.resolver.addActionListener(cb);
		this.reset.addActionListener(cb);
		this.mLog.addActionListener(cb);
		this.sudokuPrueba.addActionListener(cb);
		this.sudokuAleatorio.addActionListener(cb);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SudokuVentana ventana = new SudokuVentana("Sudoku");
		
		ventana.pack();
		ventana.setVisible(true);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	class ControlBoton implements ActionListener{
		
		private JFrame ventana;
		
		public ControlBoton(JFrame ventana) {
			this.ventana = ventana;
		}
		
		private void metodoResolver() {
			MetodosSudokuV2Objeto sudoku = new MetodosSudokuV2Objeto();
			int cont=0;
			for(int i=0; i<input.length; i++) {
				for(int e=0; e<input[i].length; e++) {
					try {
						sudoku.insertarElemento(i+1, e+1, Integer.parseInt(input[i][e].getText()));
					}
					catch(Exception ex) {
						if(!input[i][e].getText().equals("")) {
							cont++;
							input[i][e].setText("");
						}
					}
				}
			}
			if(cont>0){
				JOptionPane.showMessageDialog(null, "Datos mal introducidos", "Error", JOptionPane.ERROR_MESSAGE);
			}
			sudoku.resolverSudoku();
			for(int i=0; i<input.length; i++) {
				for(int e=0; e<input[i].length; e++) {
					if(input[i][e].getText().equals("")) {
						input[i][e].setText(String.valueOf(sudoku.getValor(i, e)));
						input[i][e].setBackground(Color.LIGHT_GRAY);
					}
				}
			}
			txt.append(sudoku.getTxt());
			resolver.setEnabled(false);
			sudoku=null;
		}
		
		private void metodoReset() {
			for(int i=0; i<input.length; i++) {
				for(int e=0; e<input[i].length; e++) {
					input[i][e].setText(null);	
					input[i][e].setBackground(Color.WHITE);
				}
			}
			txt.setText(null);
			resolver.setEnabled(true);
		}
		
		public void metodoSudokuPrueba() {
			metodoReset();
			input[0][0].setText("2");
			input[1][2].setText("1");
			input[1][3].setText("7");
			input[1][4].setText("8");
			input[1][8].setText("2");
			input[2][1].setText("6");
			input[2][2].setText("7");
			input[2][3].setText("4");
			input[3][1].setText("2");
			input[3][3].setText("3");
			input[3][6].setText("9");
			input[4][1].setText("4");
			input[4][5].setText("7");
			input[4][6].setText("2");
			input[5][2].setText("3");
			input[6][4].setText("9");
			input[6][6].setText("4");
			input[6][7].setText("8");
			input[7][0].setText("9");
			input[7][2].setText("2");
			input[7][4].setText("4");
			input[7][6].setText("6");
			input[7][7].setText("7");
			input[8][0].setText("6");
			input[8][3].setText("8");
		}
		
		public void sudokuAleatorio() {
			this.metodoReset();
			String cod = SudokuAleatorio.getSudokuAleatorio();
			System.out.println(cod);
			int cont=0;
			for(int i=0; i<input.length; i++) {
				for(int e=0; e<input[i].length; e++) {
					if(cod.charAt(cont)!='.') {
						input[i][e].setText(Character.toString(cod.charAt(cont)));
					}
					cont++;
				}
			}
		}
		
		public void confirmacionAleatorio() {
			if(!resolver.isEnabled()) {
				int resp = JOptionPane.showConfirmDialog(null, "Borraras lo que has hecho, ¿seguir?");
				if(resp==JOptionPane.YES_OPTION) {
					this.sudokuAleatorio();
				}
			}
			else {
				this.sudokuAleatorio();
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent evento) {
			// TODO Auto-generated method stub
			
			if(evento.getSource()==resolver) {
				this.metodoResolver();
			}
			if(evento.getSource()==reset) {
				this.metodoReset();
			}
			if(evento.getSource()==mLog) {
				if(mLog.isSelected()) {
					log.setVisible(true);
					this.ventana.pack();
				}
				else {
					log.setVisible(false);
					this.ventana.pack();
				}
			}
			if(evento.getSource()==sudokuPrueba) {
				this.metodoSudokuPrueba();
			}
			if(evento.getSource()==sudokuAleatorio) {
				this.confirmacionAleatorio();
			}
		}
	}
}
