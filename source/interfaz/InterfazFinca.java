package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Cultivo;
import mundo.Empleado;
import mundo.Finca;
import mundo.Lote;
import mundo.Maquina;
import mundo.Servicio;


public class InterfazFinca extends JFrame implements ActionListener
{
	private final static String EMPLEADOS = "empleados";
	private final static String SERVICIOS = "servicios";
	private final static String LOTES = "lotes";
	private final static String MAQUINAS = "maquinas";
	private final static String INSUMOS = "insumos";


	private Finca finca;
	private EmpleadosVentana empleados;
	private MaquinasVentana maquinas;
	private LotesVentana lotes;
	private ServiciosVentana servicios;
	private InsumosVentana insumos;
	

	private JButton jbEmpleado;
	private JButton jbServicio;
	private JButton jbLote;
	private JButton jbMaquina;
	private JButton jbInsumos;



	public InterfazFinca(Finca nFinca) 
	{
		finca = nFinca;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2, 3));
		
		
		
		empleados=new EmpleadosVentana(this);
		maquinas=new MaquinasVentana(this);
		lotes=new LotesVentana(this);
		servicios=new ServiciosVentana(this);
		insumos=new InsumosVentana(this);
	

		
		
		
		
		
		jbEmpleado = new JButton("Empleados");
		jbEmpleado.setActionCommand(EMPLEADOS);
		jbEmpleado.addActionListener(this);
		add(jbEmpleado);
		
		jbMaquina = new JButton("Maquinas");
		jbMaquina.setActionCommand(MAQUINAS);
		jbMaquina.addActionListener(this);
		add(jbMaquina);
		
		jbLote = new JButton("Lotes");
		jbLote.setActionCommand(LOTES);
		jbLote.addActionListener(this);
		add(jbLote);

		jbServicio = new JButton("Servicios");
		jbServicio.setActionCommand(SERVICIOS);
		jbServicio.addActionListener(this);
		add(jbServicio);

		

		jbInsumos = new JButton("Insumos");
		jbInsumos.setActionCommand(INSUMOS);
		jbInsumos.addActionListener(this);
		add(jbInsumos);

		
		
		
		

	

		setTitle("Florencia");
		setSize(700, 700);

	

	}

	/**
	 * Este mÈtodo se encarga de salvar la informaciÛn de la discotienda, justo
	 * antes de cerrar la aplicaciÛn
	 */
	public void dispose() {
		try {
			finca.salvarFinca();
			super.dispose();
			System.exit(0);
		} catch (Exception e) {
			setVisible(true);
			int respuesta = JOptionPane.showConfirmDialog(this, "Problemas salvando la informaciÛn de la Finca:\n"
					+ e.getMessage() + "\nøQuiere cerrar el programa sin salvar?", "Error", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				super.dispose();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals(EMPLEADOS)) 
		{
			empleados=new EmpleadosVentana(this);
			
			this.setVisible(false);
			empleados.setVisible(true);
			
		}
		if (a.equals(MAQUINAS)) 
		{
			maquinas=new MaquinasVentana(this);
			this.setVisible(false);
			maquinas.setVisible(true);
			
		}
		if (a.equals(LOTES)) 
		{
			lotes=new LotesVentana(this);
			
			this.setVisible(false);
			lotes.setVisible(true);
			
		}
		if (a.equals(SERVICIOS)) 
		{
			servicios=new ServiciosVentana(this);
			
			this.setVisible(false);
			servicios.setVisible(true);
			
		}
		if (a.equals(INSUMOS)) 
		{
			insumos=new InsumosVentana(this);
			this.setVisible(false);
			insumos.setVisible(true);
			
		}
		

	}

	

	public static void main(String[] args) 
	{
		JFileChooser fc=new JFileChooser("./data");
		fc.setDialogTitle("Seleccione el Archivo:");
		int seleccion=fc.showSaveDialog(null);
		
		
		
		 
		
		
		Finca fin = null;
		try 
		{
			File fileFinca=fc.getSelectedFile();
			
			if (seleccion==JFileChooser.APPROVE_OPTION)
			 {
				File fileLotes=new File("./data/lotes.csv");
				File fileMaquinas=new File("./data/maquinas.csv");
				File fileEmpleados=new File("./data/empleados.csv");
				File fileInsumos=new File("./data/insumos.csv");
				
				System.out.println(fileEmpleados.getAbsolutePath());
				fin = new Finca(fileFinca,fileLotes,fileEmpleados,fileMaquinas,fileInsumos);
							
			 }
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		InterfazFinca id = new InterfazFinca(fin);
		id.setVisible(true);
	}


	public Finca darFinca() 
	{
		return finca;
	}

	public void actualizarEmpleados() 
	{
		empleados=new EmpleadosVentana(this);
		empleados.setVisible(true);
		
	}
	
	public void actualizarMaquinas()
	{
		maquinas=new MaquinasVentana(this);
		maquinas.setVisible(true);
	}
	public void actualizarLotes()
	{
		lotes=new LotesVentana(this);
		lotes.setVisible(true);
	}
	public void actualizarCultivos()
	{
		lotes=new LotesVentana(this);
		lotes.setVisible(true);
	}
	public void actualizarServicios()
	{
		servicios=new ServiciosVentana(this);
		servicios.setVisible(true);
	}
	public void actualizarInsumos()
	{
		insumos=new InsumosVentana(this);
		insumos.setVisible(true);
	}

	
	

	

	

	

	

}