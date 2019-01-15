package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DialogoNuevoLote extends JDialog implements ActionListener
{

	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	
	private JLabel lbNombre;
	private JTextField txtNombre;
	
	private JLabel lbUbicacion;
	private JTextField txtUbicacion;
	
	private JLabel lbCosto;
	private JTextField txtCosto;
	
	private JLabel lbArea;
	private JTextField txtArea;
	
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private LotesVentana interfaz;
	
	public DialogoNuevoLote(LotesVentana inter)
	{
		super(inter);
		setSize(500, 500);
		interfaz=inter;
		setLayout(new GridLayout(5,2));
		setTitle("Nuevo Lote");
		
		lbNombre=new JLabel("Nombre:");
		txtNombre=new JTextField();
		
		lbUbicacion=new JLabel("Ubicacion:");
		txtUbicacion=new JTextField();
		
		lbCosto=new JLabel("Costo por Hectarea:");
		txtCosto=new JTextField();
		
		lbArea=new JLabel("Area:");
		txtArea= new JTextField();
		
		
		
		
		
		
		
		
		add(lbNombre);
		add(txtNombre);
		add(lbUbicacion);
		add(txtUbicacion);
		add(lbUbicacion);
		add(txtUbicacion);
		add(lbCosto);
		add(txtCosto);
		add(lbArea);
		add(txtArea);
		
		
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
			try
			{
				double nCosto=Double.parseDouble(txtCosto.getText());
				double nArea=Double.parseDouble(txtArea.getText());
				interfaz.nuevoLote(txtNombre.getText(),txtUbicacion.getText()
						,nCosto,nArea);
				setVisible(false);
			}
			catch (Exception ex) 
			{
				JOptionPane.showConfirmDialog(this,"Error con el formato del Costo y/o Area","Error Nuevo Lote",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(a.equals(CANCELAR))
		{
			setVisible(false);
			
		}
		
	}

}
