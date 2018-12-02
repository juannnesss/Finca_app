package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogoNuevaMaquina extends JDialog implements ActionListener
{
	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	
	private JLabel lbNombre;
	private JTextField txtNombre;
	
	
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private MaquinasVentana interfaz;
	
	public DialogoNuevaMaquina(MaquinasVentana inter)
	{
		super(inter);
		setSize(500, 500);
		interfaz=inter;
		setLayout(new GridLayout(2,2));
		setTitle("Nueva Maquina");
		
		lbNombre=new JLabel("Nombre/Modelo:");
		txtNombre=new JTextField();
		
		add(lbNombre);
		add(txtNombre);
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
		if(a.equals(GUARDAR))
		{
			interfaz.nuevaMaquina(txtNombre.getText());
			setVisible(false);
		}
		else if(a.equals(CANCELAR))
		{
			setVisible(false);
			
		}
		
	}

}
