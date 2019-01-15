package interfaz;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import finca.finca.Insumo;


public class DialogoEscogerInsumos extends JDialog implements ActionListener 
{

	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	
	private DialogoNuevoServicio interfaz;
	private InsumosVentana interfazCompra;
	
	private ArrayList<Insumo> insumos;
	private int numeroInsumos;

	private boolean compra;
	private JLabel lbInsumo;
	private JLabel lbDosis;
	
	private JComboBox jbInsumos[];
	private JTextField txtDosis[];
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	public DialogoEscogerInsumos(InsumosVentana inter,int nNumeroInsumos,ArrayList<Insumo> nInsumos)
	{
		
		super(inter);
		compra=true;
		setSize(500, 500);
		interfazCompra=inter;
		insumos=nInsumos;
		numeroInsumos=nNumeroInsumos;
		setLayout(new GridLayout(numeroInsumos+2,2));
		setTitle("Escoja Insumos");
		jbInsumos=new JComboBox[numeroInsumos];
		txtDosis=new JTextField[numeroInsumos];
		
		lbInsumo=new  JLabel("Escoja Insumo:");
		lbDosis=new JLabel("Cantidad Comprada: ");
		
		add(lbInsumo);
		add(lbDosis);
		
		for(int i=0;i<numeroInsumos;i++)
		{
			JComboBox jbi=new JComboBox();
			jbi.addActionListener(this);
			jbi.setActionCommand(""+i);
			JTextField txtDosisI=new JTextField();
			jbInsumos[i]=jbi;
			txtDosis[i]=txtDosisI;
			add(jbi);
			add(txtDosisI);
			
		}
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		
		
		actualizarInsumos(insumos);
	}
	
	public DialogoEscogerInsumos(DialogoNuevoServicio inter,int nNumeroInsumos,ArrayList<Insumo> nInsumos)
	{
		super(inter);
		compra=false;
		
		setSize(500, 500);
		interfaz=inter;
		insumos=nInsumos;
		numeroInsumos=nNumeroInsumos;
		setLayout(new GridLayout(numeroInsumos+2,2));
		setTitle("Escoja Insumos");
		jbInsumos=new JComboBox[numeroInsumos];
		txtDosis=new JTextField[numeroInsumos];
		
		lbInsumo=new  JLabel("Escoja Insumo:");
		lbDosis=new JLabel("Dosis, Unidad/hectarea:");
		
		add(lbInsumo);
		add(lbDosis);
		
		for(int i=0;i<numeroInsumos;i++)
		{
			JComboBox jbi=new JComboBox();
			jbi.addActionListener(this);
			jbi.setActionCommand(""+i);
			JTextField txtDosisI=new JTextField();
			jbInsumos[i]=jbi;
			txtDosis[i]=txtDosisI;
			add(jbi);
			add(txtDosisI);
			
		}
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		
		
		actualizarInsumos(insumos);
	}
	
	private void actualizarInsumos(ArrayList<Insumo> insumos) 
	{
		for (int c = 0; c < jbInsumos.length; c++) 
		{
			JComboBox jbInsumo=jbInsumos[c];
			 jbInsumo.removeAllItems( );
		        for( int i = 0; i < insumos.size( ); i++ )
		        {
		        	
		        		jbInsumo.addItem( ( ( Insumo )insumos.get( i ) ).darNombre( ) );
		        	
		            
		        }

		        if( insumos.size() > 0 )
		        {
		            jbInsumo.setSelectedIndex( 0 );
		            
		        }else
		        {
		            
		        }
			
		}
		
		
	}
 public String darInsumoJcombo(int i)
 {
	 return jbInsumos[i].getSelectedItem().toString();
 }
 public int darInsumoJcomboINDEX(int i)
 {
	 return jbInsumos[i].getSelectedIndex();
 }
 public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
	if(a.equals(GUARDAR))
	{
		if(compra)
		{
			String[] insumoCantidad=new String[numeroInsumos];
			for (int i = 0; i < insumoCantidad.length; i++) 
			{
				int indexInsumo=darInsumoJcomboINDEX(i);
				String cantidad=txtDosis[i].getText();
				String insuCantidad="IN"+indexInsumo+"|"+cantidad;
				System.out.println(insuCantidad);
				insumoCantidad[i]=insuCantidad;
			}
			interfazCompra.registrarInsumosCompra(insumoCantidad);
			setVisible(false);
		}
		else{
			String[] insumoDosis=new String[numeroInsumos];
			for (int i = 0; i < insumoDosis.length; i++) 
			{
				int indexInsumo = darInsumoJcomboINDEX(i);
				String dosis=txtDosis[i].getText();
				String insuDosis="IN"+indexInsumo+"|"+dosis;
				System.out.println(insuDosis);
				insumoDosis[i]=insuDosis;
				
			}
			interfaz.darInsumosUsados(insumoDosis);
			setVisible(false);
			
		}
		
		
	}
	else if(a.equals(CANCELAR))
	{
		setVisible(false);
		
	}
		// TODO Auto-generated method stub
		
	}

}
