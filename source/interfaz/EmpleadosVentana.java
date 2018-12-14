package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Empleado;
import mundo.Finca;

public class EmpleadosVentana extends JFrame implements ActionListener, ListSelectionListener 
{
	private final static String NUEVO_EMPLEADO = "nuevoEmpleado";
	private final static String ELIMINAR_EMPLEADO = "eliminarEmpleado";
	private final static String LIQUIDAR_EMPLEADOS = "liquidarEmpleados";
	private final static String ATRAS = "atras";
	private final static String[] columTags = { "Nombre", "Cedula", "Dias Trabajados" };

	private InterfazFinca interfazPrincipal;
	private Finca finca;
	private DialogoNuevoEmpleado nuevoEmpleado;

	private JButton nEmpleado;
	private JButton eEmpleado;
	private JButton liquidar;
	private JButton atras;

	private JList txtEmpleados;
	private JScrollPane scrollEmpleados;

	private JTable tabla;

	public EmpleadosVentana(InterfazFinca inter) 
	{
		interfazPrincipal = inter;
		finca = interfazPrincipal.darFinca();
		setLayout(new BorderLayout());
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(4, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Florencia-Empleados");
		setSize(700, 700);

		nEmpleado = new JButton("Nuevo Empleado");
		nEmpleado.setActionCommand(NUEVO_EMPLEADO);
		nEmpleado.addActionListener(this);
		panelBotones.add(nEmpleado);

		eEmpleado = new JButton("Eliminar Empleado");
		eEmpleado.setActionCommand(ELIMINAR_EMPLEADO);
		eEmpleado.addActionListener(this);
		panelBotones.add(eEmpleado);
		
		liquidar = new JButton("Liquidar Empleados");
		liquidar.setActionCommand(LIQUIDAR_EMPLEADOS);
		liquidar.addActionListener(this);
		panelBotones.add(liquidar);

		atras = new JButton("Atras");
		atras.setActionCommand(ATRAS);
		atras.addActionListener(this);
		panelBotones.add(atras);

		

		// actualizarEmpleados(interfazPrincipal.darFinca().darEmpleados());

		tabla = new JTable(cargarInfo(), columTags);
		scrollEmpleados = new JScrollPane(tabla);

		add(scrollEmpleados, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.EAST);

	}

	private Object[][] cargarInfo() {
		ArrayList<Empleado> empleados = finca.darEmpleados();
		int numeroEmpleados = empleados.size();
		Object[][] data = new Object[numeroEmpleados][columTags.length];
		for (int i = 0; i < numeroEmpleados; i++) {
			Empleado iEmpleado = empleados.get(i);
			Object[] iOb = { iEmpleado.darNombre(), iEmpleado.darCedula(), iEmpleado.darDiasTrabajados() };
			data[i] = iOb;
		}

		return data;

	}

	public void dispose() {
		interfazPrincipal.dispose();
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals(NUEVO_EMPLEADO)) {
			DialogoNuevoEmpleado jDia = new DialogoNuevoEmpleado(this);
			jDia.setVisible(true);

		}
		if(a.equals(ELIMINAR_EMPLEADO))
		{
			eliminarEmpleado();
		}
		if(a.equals(LIQUIDAR_EMPLEADOS))
		{
			liquidarEmpleados();
		}
		if (a.equals(ATRAS)) 
		{
			this.setVisible(false);
			interfazPrincipal.setVisible(true);
		}
		// TODO Auto-generated method stub

	}



	public void nuevoEmpleado(String nNombre, String nCedula,LocalDate nFecha,String nEps,String zapatos
			,String pantalon,String camiseta,double nSalario) 
	{
		finca.nuevoEmpleado(nNombre, nCedula, nFecha, nEps, zapatos, pantalon, camiseta, nSalario);
		setVisible(false);
		interfazPrincipal.actualizarEmpleados();
		

	}
	public void eliminarEmpleado() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			Empleado empleado=finca.darEmpleados().get(index);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+empleado.darNombre(),"Eliminar Empleado",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION)
			{
				finca.eliminarEmpleadoPorNombre(empleado.darNombre());
				setVisible(false);
				interfazPrincipal.actualizarEmpleados();
			}
			
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Empleado", "ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
		
	}
	public void liquidarEmpleados()
	{
		ArrayList<String[]> reporte=finca.liquidarEmpleados(LocalDate.now());
		
		String reporteString="A la fecha: "+LocalDate.now()+"\n";
		for(int i=0;i<reporte.size();i++)
		{
			String empleado=reporte.get(i)[0];
			String totalHorasMensaje=reporte.get(i)[1];
			reporteString+=empleado+": "+totalHorasMensaje+"\n";
			
		}
		JOptionPane.showMessageDialog(this, reporteString, "Reporte Empleados",JOptionPane.INFORMATION_MESSAGE);
	}
	

}
