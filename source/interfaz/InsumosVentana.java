package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import mundo.Empleado;
import mundo.Finca;
import mundo.Insumo;
import mundo.Maquina;


public class InsumosVentana extends JFrame implements ActionListener
{
	public final static String KILOGRAMOS="Kg.";
	public final static String LITROS="Lt.";
	
	private final static String NUEVO = "nuevo";
	private final static String ELIMINAR = "eliminar";
	private final static String PROOVEDOR="proovedor";
	private final static String REGISTRAR="registrar";
	private final static String ATRAS = "atras";
	private final static String[] columTags = { "Nombre", "Cantidad", "Valor Unidad","Medicion"};

	private InterfazFinca interfazPrincipal;
	private Finca finca;
	

	private JButton nInsumo;
	private JButton eInsumo;
	private JButton nProovedor;
	private JButton registrar;
	private JButton atras;
	
	
	private JScrollPane scroll;

	private JTable tabla;
	
	public InsumosVentana(InterfazFinca inter)
	{
		interfazPrincipal = inter;
		finca = interfazPrincipal.darFinca();
		setLayout(new BorderLayout());
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(5, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Florencia-Insumos");
		setSize(700, 700);

		nInsumo = new JButton("Nuevo Insumo");
		nInsumo.setActionCommand(NUEVO);
		nInsumo.addActionListener(this);
		panelBotones.add(nInsumo);

		eInsumo = new JButton("Eliminar Insumo");
		eInsumo.setActionCommand(ELIMINAR);
		eInsumo.addActionListener(this);
		panelBotones.add(eInsumo);
		
		nProovedor = new JButton("Nuevo Proovedor");
		nProovedor.setActionCommand(PROOVEDOR);
		nProovedor.addActionListener(this);
		panelBotones.add(nProovedor);
		
		registrar=new JButton("Nueva Compra");
		registrar.setActionCommand(REGISTRAR);
		registrar.addActionListener(this);
		panelBotones.add(registrar);

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
		ArrayList<Insumo> insumos = finca.darInsumos();
		int numeroInsumos =insumos.size();
		Object[][] data = new Object[numeroInsumos][columTags.length];
		for (int i = 0; i < numeroInsumos; i++) 
		{
			Insumo iInsumo = insumos.get(i);
			Object[] iOb = { iInsumo.darNombre(), iInsumo.darCantidadTotal(),iInsumo.darValorUnidad(),
					(iInsumo.darTipoMedida().equals(KILOGRAMOS)?KILOGRAMOS:LITROS) };
			data[i] = iOb;
		}

		return data;

	}
	public void dispose() {
		interfazPrincipal.dispose();
	}
	public void valueChanged(ListSelectionEvent e) {
		

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String a = e.getActionCommand();
		if (a.equals(NUEVO)) 
		{
			DialogoNuevoInsumo jDia = new DialogoNuevoInsumo(this);
			jDia.setVisible(true);
			

		}
		if(a.equals(ELIMINAR))
		{
			eliminarInsumo();
		}
		if(a.equals(PROOVEDOR))
		{
			String proovedor=JOptionPane.showInputDialog(this,"Introduzca el Nombre del Nuevo Proovedor",
					"Nuevo Proovedor",JOptionPane.INFORMATION_MESSAGE);
			if(proovedor.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Ingrese Proovedor Valido", "ERROR",JOptionPane.ERROR_MESSAGE);

			}
			else
			{
				finca.nuevoProovedor(proovedor);
			}
		}
		if(a.equals(REGISTRAR))
		{
			try
			{
				int index=tabla.getSelectedRow();
				Insumo insumo=finca.darInsumos().get(index);
				String rta=JOptionPane.showInputDialog(this,"?Cuantos "+insumo.darTipoMedida()+" de "+insumo.darNombre()+" desea ingresar?",
						"Registrar Compra",JOptionPane.INFORMATION_MESSAGE);
				try
				{
					double cantidad=Double.parseDouble((rta));
					//se asume la distribucion en el lote 0
					insumo.registrarCompra(cantidad,finca.darLotes().get(0).darNombre());
					interfazPrincipal.actualizarInsumos();
					setVisible(false);
				}
				catch(Exception exx)
				{
					JOptionPane.showMessageDialog(this, "Ingrese Cantidad Valida", "ERROR",JOptionPane.ERROR_MESSAGE);

				}
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Selecione Insumo", "ERROR",JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		if (a.equals(ATRAS)) {
			this.setVisible(false);
			interfazPrincipal.setVisible(true);
		}
		// TODO Auto-generated method stub

	}
	
	public void nuevoInsumo(String nNombre,String cantidad,String valorUnidad,String tipo) 
	{
		finca.nuevoInsumo(nNombre, Integer.parseInt(cantidad),Double.parseDouble(valorUnidad),(tipo.equals(KILOGRAMOS)?KILOGRAMOS:LITROS));
	
		setVisible(false);
		interfazPrincipal.actualizarInsumos();
		// actualizarEmpleados(finca.darEmpleados());

	}
	private void eliminarInsumo() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			Insumo insumo=finca.darInsumos().get(index);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+insumo.darNombre(),"Eliminar Insumo",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION)
			{
				finca.eliminarInsumo(insumo.darNombre());
				setVisible(false);
				interfazPrincipal.actualizarInsumos();
				
			}
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Insumo", "ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
	}


}
