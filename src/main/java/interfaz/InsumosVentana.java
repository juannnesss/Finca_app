package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import finca.finca.Empleado;
import finca.finca.Finca;
import finca.finca.Insumo;
import finca.finca.Maquina;
import finca.finca.Proovedor;


public class InsumosVentana extends JFrame implements ActionListener
{
	public final static String KILOGRAMOS="Kg.";
	public final static String LITROS="Lt.";
	public final static String BULTOS="Bultos.";
	
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
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setGroupingUsed(true);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		
		ArrayList<Insumo> insumos = finca.darInsumos();
		int numeroInsumos =insumos.size();
		Object[][] data = new Object[numeroInsumos][columTags.length];
		for (int i = 0; i < numeroInsumos; i++) 
		{
			Insumo iInsumo = insumos.get(i);
			Object[] iOb = { iInsumo.darNombre(), iInsumo.darCantidadTotal(),nf.format(iInsumo.darValorUnidad()),
					iInsumo.darTipoMedida() };
			data[i] = iOb;
		}

		return data;

	}
	public void dispose() {
		interfazPrincipal.dispose();
	}
	public void valueChanged(ListSelectionEvent e) {
		

	}

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
				String nit=JOptionPane.showInputDialog(this,"Introduzca el NIT del Nuevo Proovedor",
						"Nuevo Proovedor",JOptionPane.INFORMATION_MESSAGE);
				finca.nuevoProovedor(proovedor,nit);
			}
		}
		if(a.equals(REGISTRAR))
		{
			nuevaCompra();
			/*
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
			*/
			
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
	public void nuevaCompra()
	{
		String rta=JOptionPane.showInputDialog(this,"?Cuantos Insumos?",
				"Registrar Compra",JOptionPane.INFORMATION_MESSAGE);
		int nNumeroInsumos=Integer.parseInt(rta);
		JDialog jDialog=new DialogoEscogerInsumos(this, nNumeroInsumos, finca.darInsumos());
		jDialog.setVisible(true);
	}

	public void registrarInsumosCompra(String[] insumoCantidad) 
	{

		ArrayList<Proovedor> proo=finca.darProovedores();
		String[] options=new String[proo.size()];
		int index=0;
		for(Proovedor iPro:proo)
		{
			options[index]=iPro.darNombre();
			index++;
			
		}
		int indexProovedor=JOptionPane.showOptionDialog(this, "Escoger Proovedor", "Nueva Compra", JOptionPane.DEFAULT_OPTION
				, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		String fecha=JOptionPane.showInputDialog(this,"Fecha de la Compra(dd.MM.yyyy)",
				"Registrar Compra",JOptionPane.INFORMATION_MESSAGE);
		LocalDate date;
		try{
			
			date=LocalDate.of(Integer.parseInt(fecha.split("[.]")[2]), Integer.parseInt(fecha.split("[.]")[1]),Integer.parseInt( fecha.split("[.]")[0]));
		
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Digite la fecha con el formato Indicado", "ERROR",JOptionPane.ERROR_MESSAGE);
			fecha=JOptionPane.showInputDialog(this,"Fecha de la Compra(dd.MM.yyyy)",
					"Registrar Compra",JOptionPane.INFORMATION_MESSAGE);

			date=LocalDate.of(Integer.parseInt(fecha.split("[.]")[2]), Integer.parseInt(fecha.split("[.]")[1]),Integer.parseInt( fecha.split("[.]")[0]));
		
			
		}
		Insumo[] insu=new Insumo[insumoCantidad.length];
		int n=0;
		for(String iLinea:insumoCantidad)
		{
			int ID=Integer.parseInt(iLinea.split("[|]")[0].substring(2));
			double nCantidad=Double.parseDouble(iLinea.split("[|]")[1]);
			Insumo insumoUsado=finca.buscarInsumoID(ID);
			insu[n]=new Insumo(ID, insumoUsado.darNombre(), nCantidad, insumoUsado.darValorUnidad(), insumoUsado.darTipoMedida(), insumoUsado.darUbicacion());
			n++;
		}
		finca.nuevaCompra(date, insu, proo.get(indexProovedor));
		
		
	}


}
