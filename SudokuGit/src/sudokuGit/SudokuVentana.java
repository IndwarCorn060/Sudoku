package sudokuGit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class SudokuVentana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField[] input;
	private JButton resolver;
	private JButton reset;
	private JCheckBoxMenuItem mLog;
	private JCheckBoxMenuItem mDesplazar;
	private JTextArea txt;
	private JScrollPane log;
	private Container cp;
	private JMenuItem sudokuPrueba;
	private JMenuItem sudokuAleatorio;
	
	public SudokuVentana(String titulo) {
		super(titulo);
		this.input = new JTextField[81];
		this.resolver = new JButton("Resolver");
		this.reset = new JButton("Resetear");
		this.mLog = new JCheckBoxMenuItem("Mostrar Log");
		this.mLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		this.mDesplazar = new JCheckBoxMenuItem("Desplazar al escribir");
		this.mDesplazar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		this.mDesplazar.setSelected(true);
		this.sudokuPrueba = new JMenuItem("Cargar sudoku prueba");
		this.sudokuAleatorio = new JMenuItem("Sudoku Aleatorio");
		this.txt = new JTextArea();
		this.txt.setEditable(false);
		this.log = new JScrollPane(txt);
		
		JMenuBar barra = new JMenuBar();
			JMenu mSudoku = new JMenu("Sudoku");
			mSudoku.add(this.sudokuPrueba);
			mSudoku.add(this.sudokuAleatorio);
			JMenu mVer = new JMenu("Ver");
			mVer.add(this.mLog);
			mVer.add(this.mDesplazar);
		barra.add(mSudoku);
		barra.add(mVer);
		this.setJMenuBar(barra);
		
		this.cp = this.getContentPane();
		
		JPanel centro = new JPanel(new GridLayout(9,9));
			for(int i=0; i<this.input.length; i++) {
				this.input[i] = new JTextField(1);
				centro.add(this.input[i]);
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
		this.resolver.addKeyListener(cb);
		this.reset.addActionListener(cb);
		this.mLog.addActionListener(cb);
		this.sudokuPrueba.addActionListener(cb);
		this.sudokuAleatorio.addActionListener(cb);
		for(int i=0; i<this.input.length; i++) {
			this.input[i].addKeyListener(cb);
		}
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
	
	class ControlBoton implements ActionListener, KeyListener{
		
		private JFrame ventana;
		
		public ControlBoton(JFrame ventana) {
			this.ventana = ventana;
		}
		
		private void metodoResolver() {
			MetodosSudokuV2Objeto sudoku = new MetodosSudokuV2Objeto();
			int cont=0;
			for(int i=0; i<input.length; i++) {
				 try {
					sudoku.insertarElemento(i/9+1, i%9+1, Integer.parseInt(input[i].getText()));
				}
				catch(Exception ex) {
					if(!input[i].getText().equals("")) {
						cont++;
							input[i].setText("");
					}
				}
				
			}
			if(cont>0){
				JOptionPane.showMessageDialog(null, "Datos mal introducidos", "Error", JOptionPane.ERROR_MESSAGE);
			}
			sudoku.resolverSudoku();
			for(int i=0; i<input.length; i++) {
				if(input[i].getText().equals("")) {
					input[i].setText(String.valueOf(sudoku.getValor(i/9, i%9)));
					input[i].setBackground(Color.LIGHT_GRAY);
				}
			}
			txt.append(sudoku.getTxt());
			resolver.setEnabled(false);
			sudoku=null;
		}
		
		private void metodoReset() {
			for(int i=0; i<input.length; i++) {
				input[i].setText(null);	
				input[i].setEditable(true);
				input[i].setBackground(Color.WHITE);
			}
			txt.setText(null);
			resolver.setEnabled(true);
		}
		
		public void metodoSudokuPrueba() {
			metodoReset();
			input[0].setText("2");
			input[11].setText("1");
			input[12].setText("7");
			input[13].setText("8");
			input[17].setText("2");
			input[19].setText("6");
			input[20].setText("7");
			input[21].setText("4");
			input[28].setText("2");
			input[30].setText("3");
			input[33].setText("9");
			input[37].setText("4");
			input[41].setText("7");
			input[32].setText("2");
			input[47].setText("3");
			input[58].setText("9");
			input[60].setText("4");
			input[61].setText("8");
			input[63].setText("9");
			input[65].setText("2");
			input[67].setText("4");
			input[69].setText("6");
			input[70].setText("7");
			input[72].setText("6");
			input[75].setText("8");
		}
		
		public void sudokuAleatorio() {
			this.metodoReset();
			String cod = SudokuAleatorio.getSudokuAleatorio();
			System.out.println(cod);
			int cont=0;
			for(int i=0; i<input.length; i++) {
				if(cod.charAt(cont)!='.') {
					input[i].setText(Character.toString(cod.charAt(cont)));
					input[i].setEditable(false);
				}
				cont++;
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

		@Override
		public void keyPressed(KeyEvent evento) {
			// TODO Auto-generated method stub
			if(evento.getSource() instanceof JTextField) {
				switch(evento.getKeyCode()) {
					case KeyEvent.VK_DOWN:
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								input[(i+9)%81].grabFocus();
								i=81;
							}
						}
						break;
					case KeyEvent.VK_UP:
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								input[(i+72)%81].grabFocus();
								i=81;
							}
						}
						break;
					case KeyEvent.VK_LEFT:
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								input[((i+80)%9)+i/9*9].grabFocus();
								i=81;
							}
						}
						break;
					case KeyEvent.VK_ENTER:
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								try {
									input[(i+1)].grabFocus();
								}catch(Exception e) {
									resolver.grabFocus();
								}
							}
						}
						break;
					case KeyEvent.VK_BACK_SPACE:
						int n=0;
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								n=i;
								i=81;
							}
						}
						if(input[n].getText().equals("")) {
							try {
								input[(n-1)].grabFocus();
							} catch (Exception e) {
									
							}
						}
						else {
							input[n].setText(null);
						}
						break;
					case KeyEvent.VK_RIGHT:
						for(int i=0; i<input.length; i++) {
							if(input[i].equals(evento.getSource())) {
								input[((i+1)%9)+i/9*9].grabFocus();
								i=81;
							}
						}
						break;
					default:
						break;
				}
			}
			if(evento.getKeyCode()==KeyEvent.VK_ENTER&&resolver.isFocusOwner()) {
				resolver.doClick();
				System.out.println("ye");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		private boolean isInt(char txt) {
			try {
				Integer.parseInt(String.valueOf(txt));
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		@Override
		public void keyTyped(KeyEvent evento) {
			// TODO Auto-generated method stub
			if((evento.getSource() instanceof JTextField)&&mDesplazar.isSelected()&&isInt(evento.getKeyChar())) {
				for(int i=0; i<input.length; i++) {
					if(input[i].equals(evento.getSource())) {
						try {
							input[(i+1)].grabFocus();
						}catch(Exception e) {
							resolver.grabFocus();
						}
					}
				}
			}
		}
	}
}
