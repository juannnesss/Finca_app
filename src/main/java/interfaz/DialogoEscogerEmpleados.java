package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import finca.finca.Empleado;




public class DialogoEscogerEmpleados extends JDialog implements ActionListener 
{

	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	
	private DialogoNuevoServicio interfaz;
	private ArrayList<Empleado> empleados;
	private int numeroEmpleados;

	
	private JLabel lbEmpleados;
	
	
	private JComboBox jbEmpleados[];
	
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	
	
	public DialogoEscogerEmpleados(DialogoNuevoServicio inter,int nNumeroEmpleados,ArrayList<Empleado> nEmpleados)
	{
		super(inter);
		setSize(500, 500);
		interfaz=inter;
		empleados=nEmpleados;
		numeroEmpleados=nNumeroEmpleados;
		setLayout(new GridLayout(numeroEmpleados+2,2));
		setTitle("Escoja Empleados");
		jbEmpleados=new JComboBox[numeroEmpleados];
		
		
		lbEmpleados=new  JLabel("Escoja Empleado:");
		
		
		add(lbEmpleados);
		add(new JLabel(""));
		
		
		for(int i=0;i<numeroEmpleados;i++)
		{
			JComboBox jbi=new JComboBox();
			jbi.addActionListener(this);
			jbi.setActionCommand(""+i);
			
			jbEmpleados[i]=jbi;
			add(new JLabel("Empleado "+(i+1)));
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
		
		
		actualizarEmpleados(empleados);
	}
	
	private void actualizarEmpleados(ArrayList<Empleado> empleados) 
	{
		for (int c = 0; c < jbEmpleados.length; c++) 
		{
			JComboBox jbEmpleado=jbEmpleados[c];
			 jbEmpleado.removeAllItems( );
		        for( int i = 0; i < empleados.size( ); i++ )
		        {
		        	
		        		jbEmpleado.addItem( ( ( Empleado )empleados.get( i ) ).darNombre( ) );
		        	
		            
		        }

		        if( empleados.size() > 0 )
		        {
		            jbEmpleado.setSelectedIndex( 0 );
		            
		        }else
		        {
		            
		        }
			
		}
		
		
	}
 public String darEmpleadoJcombo(int i)
 {
	 
	 return jbEmpleados[i].getSelectedItem().toString();
 }
 public int darEmpleadoJcomboINDEX(int i)
 {
	 return jbEmpleados[i].getSelectedIndex();
 }
 public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
	if(a.equals(GUARDAR))
	{
		String[] empleadosUsados=new String[numeroEmpleados];
		for (int i = 0; i < empleadosUsados.length; i++) 
		{
			int indexEmpleado = darEmpleadoJcomboINDEX(i);
	
			
			empleadosUsados[i]=indexEmpleado+"";
			
		}
		interfaz.darEmpleadosUsados(empleadosUsados);
		setVisible(false);
		
		
	}
	else if(a.equals(CANCELAR))
	{
		setVisible(false);
		
	}
		// TODO Auto-generated method stub
		
	}

}
