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
	private static final String ESCOGER="escoger";
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
	
	private JLabel lbMaquina;
	private JComboBox jbMaquina;
	
	private JLabel lbEmpleado;
	private JComboBox jbEmpleado;
	
	private JLabel numeroInsumosLabel;
	private JTextField txtNumeroInsumos;
	private JButton bEscoger;
	
	
	private JLabel lbCosto;
	private JTextField txtCosto;
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private ServiciosVentana interfaz;
	
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

		lbMaquina=new JLabel("Escoja Maquina:");
		jbMaquina=new JComboBox();
		jbMaquina.addActionListener(this);
		jbMaquina.setActionCommand(COMBO_BOX_CAMBIO_MAQUINA);

		lbEmpleado=new JLabel("Escoja Empleado:");
		jbEmpleado=new JComboBox();
		jbEmpleado.addActionListener(this);
		jbEmpleado.setActionCommand(COMBO_BOX_CAMBIO_EMPLEADO);
		
		numeroInsumosLabel=new JLabel("Numero de Insumos usados:");
		txtNumeroInsumos=new JTextField();
		bEscoger=new JButton("Escoger");
		bEscoger.addActionListener(this);
		bEscoger.setActionCommand(ESCOGER);
		JPanel aux=new JPanel();
		aux.setLayout(new GridLayout(1, 2));
		aux.add(txtNumeroInsumos);
		aux.add(bEscoger);
		
		
		lbCosto=new JLabel("Costo:");
		txtCosto= new JTextField();
		
		add(lbFecha);
		add(txtFecha);
		add(lbTipo);
		add(txtTipo);
		add(lbLote);
		add(jbLote);
		add(lbMaquina);
		add(jbMaquina);
		add(lbEmpleado);
		add(jbEmpleado);
		add(numeroInsumosLabel);
		add(aux);
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
		actualizarMaquinas(interfaz.darFinca().darMaquinas());
		actualizarEmpleados(interfaz.darFinca().darEmpleados());

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
	 private void actualizarMaquinas(ArrayList<Maquina> maquinas) 
		{
			 jbMaquina.removeAllItems( );
		        for( int i = 0; i < maquinas.size( ); i++ )
		        {
		            jbMaquina.addItem( ( ( Maquina)maquinas.get( i ) ).darNombre( ) );
		        }

		        if( maquinas.size( ) > 0 )
		        {
		            jbMaquina.setSelectedIndex( 0 );
		            
		        }else
		        {
		            
		        }
			
		}
		 public String darMaquinaJcombo( )
		    {
		        return jbMaquina.getSelectedItem( ).toString( );
		    }
		 private void actualizarEmpleados(ArrayList<Empleado> empleados) 
			{
				 jbEmpleado.removeAllItems( );
			        for( int i = 0; i < empleados.size( ); i++ )
			        {
			            jbEmpleado.addItem( ( ( Empleado )empleados.get( i ) ).darNombre( ) );
			        }

			        if( empleados.size( ) > 0 )
			        {
			            jbEmpleado.setSelectedIndex( 0 );
			            
			        }else
			        {
			            
			        }
				
			}
			 public String darEmpleadoJcombo( )
			    {
			        return jbEmpleado.getSelectedItem( ).toString( );
			    }
		
	
			 
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
	if(a.equals(GUARDAR))
	{
		String fecha=txtFecha.getText();
		String nTipo=txtTipo.getText();
		String nLote=darLoteJcombo();
		String nMaquina=darMaquinaJcombo();
		String nEmpleado=darEmpleadoJcombo();
		
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
	if(a.equals(ESCOGER))
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

}
