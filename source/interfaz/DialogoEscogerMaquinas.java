package interfaz;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mundo.Maquina;


public class DialogoEscogerMaquinas extends JDialog implements ActionListener 
{

	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	
	private DialogoNuevoServicio interfaz;
	private ArrayList<Maquina> maquinas;
	private int numeroMaquinas;

	
	private JLabel lbMaquina;
	
	
	private JComboBox jbMaquinas[];
	
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	
	
	public DialogoEscogerMaquinas(DialogoNuevoServicio inter,int nNumeroMaquinas,ArrayList<Maquina> nMaquinas)
	{
		super(inter);
		setSize(500, 500);
		interfaz=inter;
		maquinas=nMaquinas;
		numeroMaquinas=nNumeroMaquinas;
		setLayout(new GridLayout(numeroMaquinas+2,2));
		setTitle("Escoja Maquinas");
		jbMaquinas=new JComboBox[numeroMaquinas];
		
		
		lbMaquina=new  JLabel("Escoja Maquina:");
		
		
		add(lbMaquina);
		add(new JLabel(""));
		
		
		for(int i=0;i<numeroMaquinas;i++)
		{
			JComboBox jbi=new JComboBox();
			jbi.addActionListener(this);
			jbi.setActionCommand(""+i);
			JTextField txtDosisI=new JTextField();
			jbMaquinas[i]=jbi;
			JLabel lbi=new JLabel("Maquina "+(i+1));
			add(lbi);
			add(jbi);
			
			
		}
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		
		
		actualizarMaquinas(maquinas);
	}
	
	private void actualizarMaquinas(ArrayList<Maquina> maquinas) 
	{
		for (int c = 0; c < jbMaquinas.length; c++) 
		{
			JComboBox jbMaquina=jbMaquinas[c];
			 jbMaquina.removeAllItems( );
		        for( int i = 0; i < maquinas.size( ); i++ )
		        {
		        	
		        		jbMaquina.addItem( ( ( Maquina )maquinas.get( i ) ).darNombre( ) );
		        	
		            
		        }

		        if( maquinas.size() > 0 )
		        {
		            jbMaquina.setSelectedIndex( 0 );
		            
		        }else
		        {
		            
		        }
			
		}
		
		
	}
 public String darMaquinaJcombo(int i)
 {
	 return jbMaquinas[i].getSelectedItem().toString();
 }
 public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
	if(a.equals(GUARDAR))
	{
		String[] maquinasUsadas=new String[numeroMaquinas];
		for (int i = 0; i < maquinasUsadas.length; i++) 
		{
			String iMaquina = darMaquinaJcombo(i);
			
			
			maquinasUsadas[i]=iMaquina;
			
		}
		interfaz.darMaquinasUsadas(maquinasUsadas);
		setVisible(false);
		
		
	}
	else if(a.equals(CANCELAR))
	{
		setVisible(false);
		
	}
		// TODO Auto-generated method stub
		
	}

}
