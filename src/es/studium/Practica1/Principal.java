package es.studium.Practica1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JButton;

public class Principal extends JFrame {
	int pidGestion;
	int pidJuego;
	int pidPaint;
	int pidBloc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private static JTextField textFieldEjecutar;
	ArrayList<String> listaEjecutar = new ArrayList<String>();
	static ArrayList<String> procesos = new ArrayList<String>();
	
	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("Pr\u00E1ctica PSP Tema 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nombre del proceso", "PID del proceso"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setBounds(95, 192, 335, 288);
		contentPane.add(table);
		JScrollPane textPan = new JScrollPane(table);
		textPan.setBounds(378, 102, 366, 282);
		contentPane.add(textPan);
		textFieldEjecutar = new JTextField();
		textFieldEjecutar.setBounds(10, 30, 271, 25);
		contentPane.add(textFieldEjecutar);
		textFieldEjecutar.setColumns(10);

		TextArea textArea = new TextArea();
		textArea.setBounds(0, 76, 380, 352);
		contentPane.add(textArea);
		/*
		 * Boton Ejecutar
		 */
		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(291, 31, 89, 23);
		contentPane.add(btnEjecutar);
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ejecutar = (textFieldEjecutar.getText());
				if (ejecutar.length() <= 0)
				{
					System.err.println("Se necesita un programa a ejecutar");
				}else {
					ProcessBuilder procesoEjecutar = new ProcessBuilder(ejecutar);
					
					try {
						procesoEjecutar.start();
						textArea.append(" " + ejecutar);
						rellenarTabla(getTable());
						rellenaTxtArea(textArea);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		/*
		 * Boton Bloc de Notas
		 */
		JButton btnBlocDeNotas = new JButton("Bloc de Notas");
		btnBlocDeNotas.setBounds(409, 31, 140, 23);
		contentPane.add(btnBlocDeNotas);
		btnBlocDeNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ejecutarComandos("notepad");
					rellenarTabla(getTable());
					pidBloc = Integer.parseInt((String)(getTable().getModel().getValueAt(getTable().getRowCount()-1, getTable().getColumnCount()-1)));
					btnBlocDeNotas.setEnabled(false);
			}
		});
		/*
		 * Boton Paint
		 */
		JButton btnPaint = new JButton("Paint");
		btnPaint.setBounds(604, 31, 140, 23);
		contentPane.add(btnPaint);
		btnPaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ejecutarComandos("mspaint");
					rellenarTabla(getTable());
					pidPaint = Integer.parseInt((String)(getTable().getModel().getValueAt(getTable().getRowCount()-1, getTable().getColumnCount()-1)));
					btnPaint.setEnabled(false);				
			}
		});
		/*
		 * Boton Gestion
		 */
		JButton btnGestion = new JButton("Gesti\u00F3n");
		btnGestion.setBounds(409, 65, 140, 23);
		contentPane.add(btnGestion);
		btnGestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String arg1 = "java";
				String arg2 = "-jar";
				String arg3 = ".\\Gestion.jar";
				String[] ejecutarGestion = { arg1, arg2, arg3 };
				ejecutarComandosArray(ejecutarGestion);
				rellenarTabla(getTable());
				pidGestion = Integer.parseInt((String)(getTable().getModel().getValueAt(getTable().getRowCount()-1, getTable().getColumnCount()-1)));
				btnGestion.setEnabled(false);
			}
		});
		/*
		 * Boton Juego
		 */
		JButton btnJuego = new JButton("Juego");
		btnJuego.setBounds(604, 65, 140, 23);
		contentPane.add(btnJuego);
		btnJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String arg1 = "java";
				String arg2 = "-jar";
				String arg3 = ".\\Juego.jar";
				String[] ejecutarJuego = { arg1, arg2, arg3 };
				ejecutarComandosArray(ejecutarJuego);
				rellenarTabla(getTable());
				pidJuego = Integer.parseInt((String)(getTable().getModel().getValueAt(getTable().getRowCount()-1, getTable().getColumnCount()-1)));
				btnJuego.setEnabled(false);
			}
		});
		/*
		 * Boton Terminar
		 */
		JButton btnTerminar = new JButton("Terminar");
		btnTerminar.setBounds(655, 398, 89, 23);
		contentPane.add(btnTerminar);
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pid = (String) getTable().getModel().getValueAt(getTable().getSelectedRow(), 1);
					ejecutarComandos("cmd /C taskkill /F /PID " + pid);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rellenarTabla(getTable());

					if (Integer.parseInt(pid) == pidBloc) {
						btnBlocDeNotas.setEnabled(true);
					}
					else if (Integer.parseInt(pid) == pidPaint) {
						btnPaint.setEnabled(true);
					}
					else if (Integer.parseInt(pid) == pidJuego) {
						btnJuego.setEnabled(true);
					}
					else if (Integer.parseInt(pid) == pidGestion) {
						btnGestion.setEnabled(true);
					}
			}
		});
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	/*
	 * Metodos
	 */
	//Añade al array list procesos el tasklist
	public static void ListarProcesos () {
		try {
			String linea;
			Process p= Runtime.getRuntime().exec("tasklist.exe /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((linea = input.readLine())!=null){
				if(!linea.trim().equals("")) {
					procesos.add(linea);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	//Extrae los datos de la lista dando una pocicion y un indice
	public static String extraerDatos(int posicionDato, int indiceEnLista) {

		String resultado = "";
		resultado = procesos.get(indiceEnLista);
		resultado = resultado.split(",")[posicionDato];
		resultado = resultado.substring(1, resultado.length() - 1);

		return resultado;
	}
	//Rellena los datos de la tabla con los datos de la lista creada a traves del comando tasklist
	public static void rellenarTabla(JTable tabla) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		procesos = new ArrayList<String>();
		String cadenaGuardar;
		Process process;
		String comando="cmd /C tasklist /FO csv /NH /FI \"Status eq running\"";
		try {
			process = Runtime.getRuntime().exec(comando);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((cadenaGuardar = bufferedReader.readLine()) != null) {
				procesos.add(cadenaGuardar);
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el comando");
			e.printStackTrace();
		}
		modelo.setNumRows(0);
		for (int i = 0; i < procesos.size(); i++) {
			Object[] fila = { extraerDatos(0, i), extraerDatos(1, i) };
			modelo.addRow(fila);
		}
	}
	@SuppressWarnings("unused")
	public void ejecutarComandos(String comando) {
		Process procesoEjecutarComando;
		try {
			procesoEjecutarComando = Runtime.getRuntime().exec(comando);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	public void ejecutarComandosArray(String[] comando) {
		Process procesoEjecutarComando;
		try {
			procesoEjecutarComando = Runtime.getRuntime().exec(comando);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private void ejecutarComandos2(JButton btnPaint) {
		String ejecutarPaint = "mspaint";
		ProcessBuilder procesoMspaint = new ProcessBuilder(ejecutarPaint);
		try
		{
			procesoMspaint.start();
			rellenarTabla(getTable());
			pidPaint = Integer.parseInt((String)(getTable().getModel().getValueAt(getTable().getRowCount()-1, getTable().getColumnCount()-1)));
			btnPaint.setEnabled(false);
		}
		catch (IOException ex)
		{
			System.err.println("Excepción de E/S!!");
		}
	}
	public void rellenaTxtArea(TextArea textArea) {

		for (String cadena : listaEjecutar) {
			textArea.append(cadena + "\n");
		}
		textArea.append("\n");
	}
	/*
	 * Fin de los metodos
	 */
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListarProcesos();
					Principal frame = new Principal();
					rellenarTabla(frame.getTable());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
