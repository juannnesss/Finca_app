package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Empleado;

public class DialogoNuevoEmpleado extends JDialog implements ActionListener
{
	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	private JLabel lbNombre;
	private JLabel lbCedula;
	private JTextField txtNombre;
	private JTextField txtCedula;
	
	
	private JButton bGuardar;
	private JButton bCancelar;
	private EmpleadosVentana interfaz;
	
	public DialogoNuevoEmpleado(EmpleadosVentana nPrincipal) 
	{
		super(nPrincipal);
		setSize(500, 500);
		interfaz=nPrincipal;
		setLayout(new GridLayout(3,2));
		setTitle("Nuevo Empleado");
		lbNombre=new JLabel("Nombre:");
		lbCedula=new JLabel("Cedula:");
		txtNombre=new JTextField();
		txtCedula=new JTextField();
		
		
		
		
		
		add(lbNombre);
		add(txtNombre);
		add(lbCedula);
		add(txtCedula);
		
		
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		
		// TODO Auto-generated constructor stub
	}

	
	
	 
	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
		if(a.equals(GUARDAR))
		{
			
			interfaz.nuevoEmpleado(txtNombre.getText(),txtCedula.getText());
			setVisible(false);
			
		}
		else if(a.equals(CANCELAR))
		{
			setVisible(false);
			
		}
		// TODO Auto-generated method stub
		
	}





}
