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

import mundo.Maquina;

public class DialogoNuevoInsumo extends JDialog implements ActionListener
{
	private static final String GUARDAR="guardar";
	private static final String CANCELAR="cancelar";
	private static final String COMBO_BOX="cambio";
	
	private JLabel lbNombre;
	private JTextField txtNombre;
	
	private JLabel lbCantidad;
	private JTextField txtCantidad;
	
	private JLabel lbValor;
	private JTextField txtValor;
	
	private JLabel lbTipoMedicion;
	private JComboBox jbTipoMedicion;
	
	private JButton bGuardar;
	private JButton bCancelar;
	
	private InsumosVentana interfaz;
	
	public DialogoNuevoInsumo(InsumosVentana inter)
	{
		super(inter);
		setSize(500, 500);
		interfaz=inter;
		setLayout(new GridLayout(5,2));
		setTitle("Nuevo Insumo");
		
		lbNombre=new JLabel("Nombre Producto:");
		txtNombre=new JTextField();
		
		add(lbNombre);
		add(txtNombre);
		
		lbCantidad=new JLabel("Cantidad Inicial:");
		txtCantidad=new JTextField();
		
		add(lbCantidad);
		add(txtCantidad);
		
		lbValor=new JLabel("Valor Unidad");
		txtValor=new JTextField();
		
		add(lbValor);
		add(txtValor);
		
		lbTipoMedicion=new JLabel("Escoja Presentacion(Medicion):");
		jbTipoMedicion=new JComboBox();
		jbTipoMedicion.addActionListener(this);
		jbTipoMedicion.setActionCommand(COMBO_BOX);
		
		add(lbTipoMedicion);
		add(jbTipoMedicion);
		
		bGuardar=new JButton("Guardar");
		bGuardar.setActionCommand(GUARDAR);
		bGuardar.addActionListener(this);
		add(bGuardar);
		
		bCancelar=new JButton("Cancelar");
		bCancelar.setActionCommand(CANCELAR);
		bCancelar.addActionListener(this);
		add(bCancelar);
		actualizarCombo();
	}
	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
		if(a.equals(GUARDAR))
		{
			
			interfaz.nuevoInsumo(txtNombre.getText(), txtCantidad.getText(),txtValor.getText(), jbTipoMedicion.getSelectedItem().toString());
			setVisible(false);
		}
		else if(a.equals(CANCELAR))
		{
			setVisible(false);
			
		}
		
	}
	
	private void actualizarCombo() {
		jbTipoMedicion.removeAllItems();
		jbTipoMedicion.addItem(interfaz.KILOGRAMOS);
		jbTipoMedicion.addItem(interfaz.LITROS);

		jbTipoMedicion.setSelectedIndex(0);

	}

}
