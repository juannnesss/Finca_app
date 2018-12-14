package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.time.LocalDate;
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
	private JTextField txtNombre;
	
	private JLabel lbCedula;
	private JTextField txtCedula;

	private JLabel lbFecha;
	private JTextField txtFecha;
	
	private JLabel lbEps;
	private JTextField txtEps;
	
	private JLabel lbTallas;
	private JTextField txtTallas;
	
	private JLabel lbSalario;
	private JTextField txtSalario;
	
	private JButton bGuardar;
	private JButton bCancelar;
	private EmpleadosVentana interfaz;
	
	public DialogoNuevoEmpleado(EmpleadosVentana nPrincipal) 
	{
		super(nPrincipal);
		setSize(500, 500);
		interfaz=nPrincipal;
		setLayout(new GridLayout(7,2));
		setTitle("Nuevo Empleado");
		lbNombre=new JLabel("Nombre:");
		txtNombre=new JTextField();
		add(lbNombre);
		add(txtNombre);
		
		lbCedula=new JLabel("Cedula:");
		txtCedula=new JTextField();
		add(lbCedula);
		add(txtCedula);
		
		lbFecha=new JLabel("Fecha Ingreso(DD.MM.AA):");
		txtFecha=new JTextField();
		add(lbFecha);
		add(txtFecha);
		
		lbEps=new JLabel("Eps:");
		txtEps=new JTextField();
		add(lbEps);
		add(txtEps);
		
		lbTallas=new JLabel("Tallas(Zapatos.Pantalon.Camiseta):");
		txtTallas=new JTextField();
		add(lbTallas);
		add(txtTallas);
		
		lbSalario=new JLabel("Salarios Base:");
		txtSalario=new JTextField();
		add(lbSalario);
		add(txtSalario);
		
		
		
		
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
			String[] date=txtFecha.getText().split("[.]");
			
			LocalDate fecha= LocalDate.of(Integer.parseInt(20+date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
			String [] tallas=txtTallas.getText().split("[.]");
			
			interfaz.nuevoEmpleado(txtNombre.getText(),txtCedula.getText(),fecha, txtEps.getText(), tallas[0], tallas[1], tallas[2], Double.parseDouble(txtSalario.getText()));
			setVisible(false);
			
		}
		else if(a.equals(CANCELAR))
		{
			setVisible(false);
			
		}
		// TODO Auto-generated method stub
		
	}





}
