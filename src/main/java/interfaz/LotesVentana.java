package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import finca.finca.Finca;
import finca.finca.Lote;
import finca.finca.Maquina;

public class LotesVentana extends JFrame implements ActionListener
{
	private final static String NUEVO = "nuevo";
	private final static String ELIMINAR = "eliminar";
	private final static String NUEVO_CULTIVO="nuevoCultivo";
	private final static String ELIMINAR_CULTIVO="eliminarCultivo";
	private final static String ASIGNAR_PROD="asignarProd";
	private final static String ATRAS = "atras";
	private final static String[] columTags = { "Nombre", "Ubicacion", "Coste Tierra x ha", "Area[ha]","Cultivo Actual","Rendimiento" };

	private InterfazFinca interfazPrincipal;
	private Finca finca;
	

	private JButton nLote;
	private JButton eLote;
	private JButton nCultivo;
	private JButton eCultivo;
	private JButton bAsig;
	private JButton atras;

	private JScrollPane scroll;

	private JTable tabla;
	
	public LotesVentana(InterfazFinca inter)
	{
		interfazPrincipal = inter;
		finca = interfazPrincipal.darFinca();
		setLayout(new BorderLayout());
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(6, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Florencia-Lote");
		setSize(700, 700);
		
		nLote = new JButton("Nuevo Lote");
		nLote.setActionCommand(NUEVO);
		nLote.addActionListener(this);
		panelBotones.add(nLote);

		eLote = new JButton("Eliminar Lote");
		eLote.setActionCommand(ELIMINAR);
		eLote.addActionListener(this);
		panelBotones.add(eLote);
		
		nCultivo= new JButton("Nuevo Cultivo");
		nCultivo.setActionCommand(NUEVO_CULTIVO);
		nCultivo.addActionListener(this);
		panelBotones.add(nCultivo);
		
		eCultivo=new JButton("Eliminar Cultivo");
		eCultivo.setActionCommand(ELIMINAR_CULTIVO);
		eCultivo.addActionListener(this);
		panelBotones.add(eCultivo);
		
		bAsig=new JButton("Asignar Produccion");
		bAsig.setActionCommand(ASIGNAR_PROD);
		bAsig.addActionListener(this);
		panelBotones.add(bAsig);

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
		
		ArrayList<Lote> lotes = finca.darLotes();
		int numeroLotes = lotes.size();
		Object[][] data = new Object[numeroLotes][columTags.length];
		for (int i = 0; i < numeroLotes; i++) 
		{
			Lote iLote = lotes.get(i);
			double value=iLote.darCultivoActual()!=null?iLote.darCultivoActual().calcularRendimiento():0;
			
			Object[] iOb = { iLote.darNombre(), iLote.darUbicacion(),
					iLote.darCosteTierra(),iLote.darArea(),(iLote.darCultivoActual()!=null?iLote.darCultivoActual().darProducto():"No Tiene")
					,(iLote.darCultivoActual()!=null?nf.format(value):"No Tiene C")};
			data[i] = iOb;
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
			DialogoNuevoLote jDia = new DialogoNuevoLote(this);
			jDia.setVisible(true);

		}
		if(a.equals(ELIMINAR))
		{
			eliminarLote();
		}
		if(a.equals(NUEVO_CULTIVO))
		{
			DialogoNuevoCultivo jDia = new DialogoNuevoCultivo(this);
			jDia.setVisible(true);
		}
		if(a.equals(ELIMINAR_CULTIVO))
		{
			eliminarCultivo();
		}
		if(a.equals(ASIGNAR_PROD))
		{
			asignarProduccion();
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
	public void nuevoLote(String nNombre, String nUbicacion, double nCoste, double nArea) 
	{
		finca.nuevoLote(nNombre, nUbicacion, nCoste, nArea);
	
		setVisible(false);
		interfazPrincipal.actualizarLotes();
	

	}
	public void nuevoCultivo(String nProducto, String dia, String mes, String anio, String nlote2) 
	{
		LocalDate date = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		Lote lote=finca.buscarLoteNombre(nlote2);
		if(lote.darCultivoActual()!=null)
		{
			String[] cortaDate=JOptionPane.showInputDialog(this,"Cuando Fue la Corta del Cultivo anterior?(DD.MM.AAAA)","Fecha Corta",JOptionPane.INFORMATION_MESSAGE).split("[.]");

			LocalDate coDate= LocalDate.of(Integer.parseInt(cortaDate[2]), Integer.parseInt(cortaDate[1]), Integer.parseInt(cortaDate[0]));
			finca.nuevoCultivo(nlote2, nProducto, date,coDate);
			setVisible(false);
			interfazPrincipal.actualizarCultivos();
			
		}
		else
		{
			finca.nuevoCultivo(nlote2, nProducto, date,null);
			setVisible(false);
			interfazPrincipal.actualizarCultivos();
		}
		

	}
	private void eliminarLote() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			Lote lote=finca.darLotes().get(index);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+lote.darNombre(),"Eliminar Lote",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION)
			{
				finca.eliminarLote(lote.darNombre());
				setVisible(false);
				interfazPrincipal.actualizarLotes();
				
			} 
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Lote", "ERROR",JOptionPane.ERROR_MESSAGE);

		}
		
		
	}
	private void eliminarCultivo() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			Lote lote=finca.darLotes().get(index);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar el cultivo: "
			+(lote.darCultivoActual()!=null?lote.darCultivoActual().darProducto():"NO TIENE CULTIVO SELECIONADO")
			+" ;Del Lote: "+lote.darNombre(),"Eliminar Cultivo",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION&&lote.darCultivoActual()!=null)
			{
				finca.buscarLoteNombre(lote.darNombre()).eliminarCultivo(finca.darCultivoPorLote(lote.darCultivoActual().darProducto(), lote.darNombre()));
				setVisible(false);
				interfazPrincipal.actualizarCultivos();
				
			}
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Lote", "ERROR",JOptionPane.ERROR_MESSAGE);

		}
		
		
	}
	private void asignarProduccion()
	{
		int index=tabla.getSelectedRow();
		try
		{
			Lote lote=finca.darLotes().get(index);
			if(lote.darCultivoActual()!=null)
			{
				String mens=JOptionPane.showInputDialog("Ingrese la Cantidad $ Producida");
				try
				{
					double prod=Double.parseDouble(mens);
					lote.asignarProduccionLote(prod);
					setVisible(false);
					interfazPrincipal.actualizarCultivos();
				}
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(this, "Ingrese una cantidad valida", "ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Lote Seleccionado No Tiene Cultivo", "ERROR",JOptionPane.ERROR_MESSAGE);

			}
			
			
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Lote", "ERROR",JOptionPane.ERROR_MESSAGE);

		}
	}
	

}
