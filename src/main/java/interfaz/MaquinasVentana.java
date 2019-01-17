package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import finca.finca.Empleado;
import finca.finca.Finca;
import finca.finca.IInfo;
import finca.finca.Lote;
import finca.finca.Maquina;
import finca.finca.Servicio;

public class MaquinasVentana extends JFrame implements ActionListener {
	private final static String NUEVO = "nuevo";
	private final static String ELIMINAR = "eliminar";
	private final static String SET_HOROMETRO="setHorometro";
	private final static String ATRAS = "atras";
	private final static String INFO = "info";
	private final static String[] columTags = { "Nombre", "Servicios", "Gastos", "Horas Trabajados" ,"Horometro"};

	private InterfazFinca interfazPrincipal;
	private Finca finca;
	

	private JButton nMaquina;
	private JButton eMaquina;
	private JButton sHorometro;
	private JButton atras;
	private JButton bInfo;

	private JScrollPane scroll;

	private JTable tabla;

	public MaquinasVentana(InterfazFinca inter) {
		interfazPrincipal = inter;
		finca = interfazPrincipal.darFinca();
		setLayout(new BorderLayout());
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(5, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setTitle("Florencia-Maquina");
		setSize(700, 700);

		nMaquina = new JButton("Nueva Maquina");
		nMaquina.setActionCommand(NUEVO);
		nMaquina.addActionListener(this);
		panelBotones.add(nMaquina);

		eMaquina = new JButton("Eliminar Maquina");
		eMaquina.setActionCommand(ELIMINAR);
		eMaquina.addActionListener(this);
		panelBotones.add(eMaquina);
		
		sHorometro = new JButton("Set Horometro");
		sHorometro.setActionCommand(SET_HOROMETRO);
		sHorometro.addActionListener(this);
		panelBotones.add(sHorometro);

		atras = new JButton("Atras");
		atras.setActionCommand(ATRAS);
		atras.addActionListener(this);
		panelBotones.add(atras);
		
		bInfo=new JButton("Info");
		bInfo.setActionCommand(INFO);
		bInfo.addActionListener(this);
		panelBotones.add(bInfo);

		tabla = new JTable(cargarInfo(), columTags);
		scroll = new JScrollPane(tabla);

		add(scroll, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.EAST);
	}

	private Object[][] cargarInfo() 
	{
		ArrayList<Maquina> maquinas = finca.darMaquinas();
		int numeroMaquinas = maquinas.size();
		Object[][] data = new Object[numeroMaquinas][columTags.length];
		for (int i = 0; i < numeroMaquinas; i++) 
		{
			Maquina iMaquina = maquinas.get(i);
			Object[] iOb = { iMaquina.darNombre(), iMaquina.darServicios().size(),
					iMaquina.darGastos().size(),iMaquina.darHorasTrabajo(),iMaquina.darHorometro() };
			data[i] = iOb;
		}

		return data;

	}
	public void dispose() {
		interfazPrincipal.dispose();
	}
	public void valueChanged(ListSelectionEvent e) {
		

	}

	
	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals(NUEVO)) {
			DialogoNuevaMaquina jDia = new DialogoNuevaMaquina(this);
			jDia.setVisible(true);

		}
		if(a.equals(ELIMINAR))
		{
			eliminarMaquina();
		}
		if(a.equals(SET_HOROMETRO))
		{
			setHorometro();
		}
		if (a.equals(ATRAS)) {
			this.setVisible(false);
			interfazPrincipal.setVisible(true);
		}
		if(a.equals(INFO))
		{
			panelInfo();
		}

	}



	public void nuevaMaquina(String nNombre) 
	{
		finca.nuevaMaquina(nNombre, 0);
		setVisible(false);
		interfazPrincipal.actualizarMaquinas();
		// actualizarEmpleados(finca.darEmpleados());

	}
	private void eliminarMaquina() 
	{
		int index=tabla.getSelectedRow();
		try
		{
			Maquina maquina=finca.darMaquinas().get(index);
			int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+maquina.darNombre(),"Eliminar Empleado",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION)
			{
				finca.eliminarMaquina(maquina.darNombre());
				setVisible(false);
				interfazPrincipal.actualizarMaquinas();
				
			}
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Selecione Maquina", "ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
	}
	public void panelInfo() 
	{
		int index=tabla.getSelectedRow();
		if(index!=-1)
		{
		ArrayList<Maquina> sers=finca.darMaquinas();
		ArrayList<IInfo> infos=new ArrayList<IInfo>();
		Iterator<Maquina> iteS=sers.iterator();
		while(iteS.hasNext())
		{
			IInfo next=iteS.next();
			System.out.println(next.darNombreInfo());
			infos.add(next);
		}
		
		
			if(index==0)
			{
				IInfo info=infos.get(index);
				int anterior=-1;
				int siguiente=index+1;
				JDialog jDialog=new DialogoInfo(this, info,anterior,siguiente,infos);
				jDialog.setVisible(true);
			}
			else if (index==(sers.size()-1))
			{
				IInfo info=infos.get(index);
				int anterior=index-1;
				int siguiente=-1;
				JDialog jDialog=new DialogoInfo(this, info,anterior,siguiente,infos);
				jDialog.setVisible(true);
			}
			else {
				IInfo info=infos.get(index);
				int anterior=index-1;
				int siguiente=index+1;
				JDialog jDialog=new DialogoInfo(this, info,anterior,siguiente,infos);
				jDialog.setVisible(true);
			}
		}
		
		else
		{
			JOptionPane.showMessageDialog(this, "Selecione Maquina", "ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
			
		
	}
	public void setHorometro()
	{
		int index=tabla.getSelectedRow();
	
		if(index!=-1)
		{
			Double horometro=Double.parseDouble(JOptionPane.showInputDialog("Introduzca el valor: "));
			finca.darMaquinas().get(index).setHorometro(horometro);
			setVisible(false);
			interfazPrincipal.actualizarMaquinas();
			
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Selecione Maquina", "ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
	}
}
