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
import javax.swing.JTextField;

import mundo.Lote;


public class DialogoNuevoCultivo extends JDialog implements ActionListener
{
	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	private static final String COMBO_BOX_CAMBIO="cambio";

	private JLabel lbLote;
	private JComboBox jbLote;
	
	private JLabel lbProducto;
	private JTextField txtProducto;
	
	private JLabel lbFecha;
	private JTextField txtFecha;
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private LotesVentana interfaz;
	
	public DialogoNuevoCultivo(LotesVentana lotesVentana)
	{
		super(lotesVentana);
		setSize(500, 500);
		interfaz=lotesVentana;
		setLayout(new GridLayout(4,2));
		setTitle("Nuevo Cultivo");
		
		lbLote=new JLabel("Escoja Lote(Solo Disponibles):");
		jbLote=new JComboBox();
		jbLote.addActionListener(this);
		jbLote.setActionCommand(COMBO_BOX_CAMBIO);
		
		lbProducto=new JLabel("Producto");
		txtProducto=new JTextField();
		
		lbFecha=new JLabel("Fecha(DD.MM.AAAA):");
		txtFecha=new JTextField();
		
		
		add(lbLote);
		add(jbLote);
		add(lbProducto);
		add(txtProducto);
		add(lbFecha);
		add(txtFecha);
		
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
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
	        	if(lotes.get(i).darCultivoActual()==null)
	        	{
	        		jbLote.addItem( ( ( Lote )lotes.get( i ) ).darNombre( ) );
	        	}
	            
	        }

	        if( lotes.size( ) > 0 )
	        {
	        	try
	        	{
		            jbLote.setSelectedIndex( 0 );
	        		
	        	}
	        	catch (Exception e) 
	    		{
	    			JOptionPane.showMessageDialog(this, "ERROR CORREGIR NO HAY LOTES DISPONNIBLES Lote", "ERROR",JOptionPane.ERROR_MESSAGE);

	    		}
	    		
	            
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
				String nlote=darLoteJcombo();
				String nProducto=txtProducto.getText();
				try
				{
					String[] date=txtFecha.getText().split("[.]");
					String dia=date[0];
					String mes=date[1];
					String anio=date[2];
					interfaz.nuevoCultivo(nProducto,dia,mes,anio,nlote);
					setVisible(false);
				}
				catch (Exception ex) 
				{
					JOptionPane.showConfirmDialog(this,"Error con el formato de la Fecha","Error Nuevo Cultivo",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(a.equals(CANCELAR))
			{
				setVisible(false);
				
			}
		
	}

}
