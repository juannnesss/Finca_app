package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mundo.Empleado;
import mundo.Insumo;
import mundo.Lote;
import mundo.Maquina;

public class DialogoNuevoServicio extends JDialog implements ActionListener 
{

	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	private static final String ESCOGER_MAQUINAS="escogerMaquinas";
	private static final String ESCOGER_EMPLEADOS="escogerEmpleados";
	private static final String ESCOGER_INSUMOS="escogerInsumos";
	private static final String COMBO_BOX_CAMBIO_LOTE="cambioLote";
	private static final String COMBO_BOX_CAMBIO_MAQUINA="cambioMaquina";
	private static final String COMBO_BOX_CAMBIO_EMPLEADO="cambioEmpleado";
	private static final String COMBO_BOX_CAMBIO_INSUMO="cambioInsumo";
	
	
	private JLabel lbFecha;
	private JTextField txtFecha;
	
	private JLabel lbTipo;
	private JTextField txtTipo;
	
	private JLabel lbLote;
	private JComboBox jbLote;
	
	
	
	private JLabel numeroMaquinasLabel;
	private JTextField txtNumeroMaquinas;
	private JButton bEscogerMaquinas;
	
	private JLabel numeroEmpleadosLabel;
	private JTextField txtNumeroEmpleados;
	private JButton bEscogerEmpleados;
	

	
	private JLabel numeroInsumosLabel;
	private JTextField txtNumeroInsumos;
	private JButton bEscogerInsumos;
	
	
	private JLabel lbCosto;
	private JTextField txtCosto;
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private ServiciosVentana interfaz;
	
	
	private String empleadosUsados[];
	private String maquinasUsadas[];
	private String insumosUsados[];
	
	public DialogoNuevoServicio(ServiciosVentana serviciosVentana)
	{
		super(serviciosVentana);
		setSize(500, 500);
		interfaz=serviciosVentana;
		setLayout(new GridLayout(8,2));
		setTitle("Nuevo Servicio");
		
		lbFecha=new JLabel("Fecha(DD.MM.AA):");
		txtFecha=new JTextField();
		
		lbTipo= new JLabel("Tipo:");
		txtTipo=new JTextField();
		
		lbLote=new JLabel("Escoja Lote:");
		jbLote=new JComboBox();
		jbLote.addActionListener(this);
		jbLote.setActionCommand(COMBO_BOX_CAMBIO_LOTE);

		numeroMaquinasLabel=new JLabel("Numero de Maquinas requeridas:");
		txtNumeroMaquinas=new JTextField();
		bEscogerMaquinas=new JButton("Escoger");
		bEscogerMaquinas.addActionListener(this);
		bEscogerMaquinas.setActionCommand(ESCOGER_MAQUINAS);
		//bEscogerMaquinas.setEnabled(false);
		JPanel auxMaquinas=new JPanel();
		auxMaquinas.setLayout(new GridLayout(1, 2));
		auxMaquinas.add(txtNumeroMaquinas);
		auxMaquinas.add(bEscogerMaquinas);

		numeroEmpleadosLabel=new JLabel("Numero de Empleados requeridos:");
		txtNumeroEmpleados=new JTextField();
		bEscogerEmpleados=new JButton("Escoger");
		bEscogerEmpleados.addActionListener(this);
		bEscogerEmpleados.setActionCommand(ESCOGER_EMPLEADOS);
		bEscogerEmpleados.setEnabled(false);
		JPanel auxEmpleados=new JPanel();
		auxEmpleados.setLayout(new GridLayout(1, 2));
		auxEmpleados.add(txtNumeroEmpleados);
		auxEmpleados.add(bEscogerEmpleados);
		
		numeroInsumosLabel=new JLabel("Numero de Insumos usados:");
		txtNumeroInsumos=new JTextField();
		bEscogerInsumos=new JButton("Escoger");
		bEscogerInsumos.addActionListener(this);
		bEscogerInsumos.setActionCommand(ESCOGER_INSUMOS);
		bEscogerInsumos.setEnabled(false);
		JPanel auxInsumos=new JPanel();
		auxInsumos.setLayout(new GridLayout(1, 2));
		auxInsumos.add(txtNumeroInsumos);
		auxInsumos.add(bEscogerInsumos);
		
		
		lbCosto=new JLabel("Costo:");
		txtCosto= new JTextField();
		
		add(lbFecha);
		add(txtFecha);
		add(lbTipo);
		add(txtTipo);
		add(lbLote);
		add(jbLote);
		add(numeroMaquinasLabel);
		add(auxMaquinas);
		add(numeroEmpleadosLabel);
		add(auxEmpleados);
		add(numeroInsumosLabel);
		add(auxInsumos);
		add(lbCosto);
		add(txtCosto);
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		bGuardar.setEnabled(false);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		actualizarLotes(interfaz.darFinca().darLotes());
	

	}
	private void actualizarLotes(ArrayList<Lote> lotes) 
	{
		 jbLote.removeAllItems( );
	        for( int i = 0; i < lotes.size( ); i++ )
	        {
	        	if(lotes.get(i).darCultivoActual()!=null)
	        	{
	        		jbLote.addItem( ( ( Lote )lotes.get( i ) ).darNombre( ) );
	        	}
	            
	        }

	        if( lotes.size( ) > 0 )
	        {
	            jbLote.setSelectedIndex( 0 );
	            
	        }else
	        {
	            
	        }
		
	}
	 public String darLoteJcombo( )
	    {
	        return jbLote.getSelectedItem( ).toString( );
	    }

	
			 
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
	if(a.equals(GUARDAR))
	{
		String fecha=txtFecha.getText();
		String nTipo=txtTipo.getText();
		String nLote=darLoteJcombo();
		String[] nMaquina=(txtNumeroMaquinas.getText().equals(0))?new String[0]:maquinasUsadas;
		String[] nEmpleado=(txtNumeroEmpleados.getText().equals(0))?new String[0]:empleadosUsados;;
		
		String[] nInsumo=(txtNumeroInsumos.getText().equals(0))?new String[0]:insumosUsados;
		
				
		String nCosto=txtCosto.getText();
		String[] date=fecha.split("[.]");
		
		String dia=date[0];
		String mes=date[1];
		String anio=date[2];
		anio="20"+anio;
			
			interfaz.nuevoServicio(dia, mes, anio, nTipo, nLote, nMaquina, nEmpleado,nInsumo, nCosto);
			
			setVisible(false);
		
		
	}
	if(a.equals(ESCOGER_MAQUINAS))
	{
		int intNumero=Integer.parseInt(txtNumeroMaquinas.getText());
		
		if(intNumero==0)
		{
			maquinasUsadas=new String[0];
			bEscogerEmpleados.setEnabled(true);
		}
		else
		{
			DialogoEscogerMaquinas jDia = new DialogoEscogerMaquinas(this, intNumero, interfaz.darFinca().darMaquinas());
			jDia.setVisible(true);
			bEscogerEmpleados.setEnabled(true);
		}
		
	}
	if(a.equals(ESCOGER_EMPLEADOS))
	{
		int intNumero=Integer.parseInt(txtNumeroEmpleados.getText());
		if(intNumero==0)
		{
			empleadosUsados=new String[0];
			bEscogerInsumos.setEnabled(true);
		}
		else
		{
			DialogoEscogerEmpleados jDia = new DialogoEscogerEmpleados(this, intNumero, interfaz.darFinca().darEmpleados());
			jDia.setVisible(true);
			bEscogerInsumos.setEnabled(true);
		}
	}
	if(a.equals(ESCOGER_INSUMOS))
	{
		int intNumero=Integer.parseInt(txtNumeroInsumos.getText());
		if(intNumero==0)
		{
			insumosUsados=new String[0];
			bGuardar.setEnabled(true);
		}
		else
		{
			DialogoEscogerInsumos jDia = new DialogoEscogerInsumos(this, intNumero, interfaz.darFinca().darInsumos());
			jDia.setVisible(true);
			bGuardar.setEnabled(true);
		}
	}
	else if(a.equals(CANCELAR))
	{
		setVisible(false);
		
	}
		// TODO Auto-generated method stub
		
	}
	public void darInsumosUsados(String[] insumoDosis) 
	{
		
		insumosUsados=insumoDosis;
	}
	public void darEmpleadosUsados(String[] empleados) 
	{
		
		empleadosUsados=empleados;
	}
	public void darMaquinasUsadas(String[] maquinas) 
	{
		maquinasUsadas=maquinas;
		
	}

}
