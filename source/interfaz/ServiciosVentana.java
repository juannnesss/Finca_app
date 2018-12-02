package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import mundo.Empleado;
import mundo.Finca;
import mundo.Insumo;
import mundo.Lote;
import mundo.Maquina;
import mundo.Servicio;

import mundo.Servicio;

public class ServiciosVentana extends JFrame implements ActionListener
{

	private final static String NUEVO = "nuevo";
	private final static String ELIMINAR = "eliminar";
	private final static String ATRAS = "atras";
	private final static String[] columTags = { "Fecha", "Tipo", "Lote", "Cultivo","Numero Maquinas","Empleado(s)","Costo Total" };

	private InterfazFinca interfazPrincipal;
	private Finca finca;
	

	private JButton nServicio;
	private JButton eServicio;
	private JButton atras;
	
	private JScrollPane scroll;

	private JTable tabla;
	
	public ServiciosVentana(InterfazFinca inter)
	{
		interfazPrincipal = inter;
		finca = interfazPrincipal.darFinca();
		setLayout(new BorderLayout());
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setTitle("Florencia-Servicio");
		setSize(1000, 650);
		
		nServicio = new JButton("Nuevo Servicio");
		nServicio.setActionCommand(NUEVO);
		nServicio.addActionListener(this);
		panelBotones.add(nServicio);

		eServicio = new JButton("Eliminar Servicio");
		eServicio.setActionCommand(ELIMINAR);
		eServicio.addActionListener(this);
		panelBotones.add(eServicio);
		
		atras = new JButton("Atras");
		atras.setActionCommand(ATRAS);
		atras.addActionListener(this);
		panelBotones.add(atras);

		tabla = new JTable(cargarInfo(), columTags);
		scroll = new JScrollPane(tabla);

		add(scroll, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.EAST);
		
	}
	
	private Object[][] cargarInfo() 
	{
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setGroupingUsed(true);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		
		ArrayList<Servicio> servicios = finca.darServicios();
		int numeroServicios = servicios.size();
		Object[][] data = new Object[numeroServicios][columTags.length];
		int n=0;
		for (int i = numeroServicios-1; i >-1; i--) 
		{
			Servicio iServicio = servicios.get(i);
			
			Object[] iOb = { iServicio.darFecha().toString(), iServicio.darTipo(),
					iServicio.darLote().darNombre(),iServicio.darCultivo().darProducto(),
					(iServicio.darMaquinas().size()>1?iServicio.darMaquinas().size():iServicio.darMaquinas().get(0).darNombre()),
					(iServicio.darEmpleados().size()>1?iServicio.darEmpleados().size():iServicio.darEmpleados().get(0).darNombre())
					,nf.format(iServicio.calcularCostoTotalServicio())};
			data[n] = iOb;
			n+=1;
		}

		return data;

	}
	public void dispose() {
		interfazPrincipal.dispose();
	}
	public void valueChanged(ListSelectionEvent e) {
		

	}
	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals(NUEVO)) 
		{
			DialogoNuevoServicio jDia = new DialogoNuevoServicio(this);
			jDia.setVisible(true);

		}
		if(a.equals(ELIMINAR))
		{
			eliminarServicio();
		}
		if (a.equals(ATRAS)) 
		{
			this.setVisible(false);
			interfazPrincipal.setVisible(true);
		}
	}
	public Finca darFinca()
	{
		return finca;
	}

	public void nuevoServicio(String dia, String mes, String anio, String tipo, String nombreLote, String maquina,
			String empleado,String[] insumos, String costo) 
	{
		LocalDate date = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		Lote nLote = finca.buscarLoteNombre(nombreLote);
		ArrayList<Maquina> maquinas = new ArrayList<>();
		maquinas.add(finca.buscarMaquinaNombre(maquina));
		ArrayList<Empleado> empleados = new ArrayList<>();
		empleados.add(finca.buscarEmpleadoNombre(empleado));
		
		
		finca.nuevoServicio(date, tipo, nLote, maquinas, empleados,insumos, Double.parseDouble(costo));
		setVisible(false);
		interfazPrincipal.actualizarServicios();
	}
	private void eliminarServicio() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			
			int serviciosArray=finca.darServicios().size();
			int indexReal=(serviciosArray-1)-index;
			Servicio servicio=finca.darServicios().get(indexReal);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar Servicio en: "+servicio.darLote().darNombre()
					+" En la Fecha: "+servicio.darFecha().toString(),"Eliminar Servicio",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION)
			{
				finca.eliminarServicio(servicio.darLote().darNombre(), servicio.darTipo());
				setVisible(false);
				interfazPrincipal.actualizarServicios();
				
			} 
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Servicio", "ERROR",JOptionPane.ERROR_MESSAGE);

		}
		
		
	}
}
