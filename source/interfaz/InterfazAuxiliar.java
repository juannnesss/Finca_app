package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Cultivo;
import mundo.Empleado;
import mundo.Finca;
import mundo.Lote;
import mundo.Maquina;
import mundo.Servicio;

public class InterfazFincas extends JFrame implements ActionListener, ListSelectionListener {
	private final static String NUEVO_EMPLEADO = "nuevoEmpleado";
	private final static String NUEVO_SERVICIO = "nuevoServicio";
	private final static String NUEVO_LOTE = "nuevoLote";
	private final static String NUEVO_CULTIVO = "nuevoCultivo";
	private final static String NUEVO_MAQUINA = "nuevoMaquina";
	private final static String ELIMINAR_EMPLEADO = "eliminarEmpleado";
	private final static String ELIMINAR_SERVICIO = "eliminarServicio";
	private final static String ELIMINAR_LOTE = "eliminarLote";
	private final static String ELIMINAR_CULTIVO = "eliminarCultivo";
	private final static String ELIMINAR_MAQUINA = "eliminarMaquina";

	private Finca finca;
	private DialogoNuevoEmpleado nuevoEmpleado;
	private DialogoNuevoServicio nuevoServicio;
	private DialogoNuevoLote nuevoLote;
	private DialogoNuevoCultivo nuevoCultivo;
	private DialogoNuevaMaquina nuevaMaquina;

	private JButton nEmpleado;
	private JButton nServicio;
	private JButton nLote;
	private JButton nCultivo;
	private JButton nMaquina;
	private JButton eEmpleado;
	private JButton eServicio;
	private JButton eLote;
	private JButton eCultivo;
	private JButton eMaquina;

	private JList txtEmpleados;
	private JScrollPane scrollEmpleados;

	private JList txtLotes;
	private JScrollPane scrollLotes;

	private JList txtMaquinas;
	private JScrollPane scrollMaquinas;

	private JList txtCultivos;
	private JScrollPane scrollCultivos;

	private JList txtServicios;
	private JScrollPane scrollServicios;

	public InterfazFinca(Finca nFinca) 
	{
		finca = nFinca;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		JPanel listasPanel = new JPanel();
		listasPanel.setLayout(new GridLayout(1, 5));
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(2, 5));

		nEmpleado = new JButton("Nuevo Empleado");
		nEmpleado.setActionCommand(NUEVO_EMPLEADO);
		nEmpleado.addActionListener(this);
		aux.add(nEmpleado);

		nServicio = new JButton("Nuevo Servicio");
		nServicio.setActionCommand(NUEVO_SERVICIO);
		nServicio.addActionListener(this);
		aux.add(nServicio);

		nLote = new JButton("Nuevo Lote");
		nLote.setActionCommand(NUEVO_LOTE);
		nLote.addActionListener(this);
		aux.add(nLote);

		nCultivo = new JButton("Nuevo Cultivo");
		nCultivo.setActionCommand(NUEVO_CULTIVO);
		nCultivo.addActionListener(this);
		aux.add(nCultivo);

		nMaquina = new JButton("Nuevo Maquina");
		nMaquina.setActionCommand(NUEVO_MAQUINA);
		nMaquina.addActionListener(this);
		aux.add(nMaquina);
		
		eEmpleado = new JButton("Eliminar Empleado");
		eEmpleado.setActionCommand(ELIMINAR_EMPLEADO);
		eEmpleado.addActionListener(this);
		aux.add(eEmpleado);

		eServicio = new JButton("Eliminar Servicio");
		eServicio.setActionCommand(ELIMINAR_SERVICIO);
		eServicio.addActionListener(this);
		aux.add(eServicio);

		eLote = new JButton("Eliminar Lote");
		eLote.setActionCommand(ELIMINAR_LOTE);
		eLote.addActionListener(this);
		aux.add(eLote);

		eCultivo = new JButton("Eliminar Cultivo");
		eCultivo.setActionCommand(ELIMINAR_CULTIVO);
		eCultivo.addActionListener(this);
		aux.add(eCultivo);

		eMaquina = new JButton("Eliminar Maquina");
		eMaquina.setActionCommand(ELIMINAR_MAQUINA);
		eMaquina.addActionListener(this);
		aux.add(eMaquina);
		

		txtEmpleados = new JList();
		txtEmpleados.addListSelectionListener((ListSelectionListener) this);
		txtEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollEmpleados = new JScrollPane(txtEmpleados);

		txtLotes = new JList();
		txtLotes.addListSelectionListener(this);
		txtLotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollLotes = new JScrollPane(txtLotes);

		txtMaquinas = new JList<>();
		txtMaquinas.addListSelectionListener(this);
		txtMaquinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollMaquinas = new JScrollPane(txtMaquinas);

		txtCultivos = new JList<>();
		txtCultivos.addListSelectionListener(this);
		txtCultivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollCultivos = new JScrollPane(txtCultivos);

		txtServicios = new JList<>();
		txtServicios.addListSelectionListener(this);
		txtServicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollServicios = new JScrollPane(txtServicios);

		listasPanel.add(scrollEmpleados);
		listasPanel.add(scrollServicios);
		listasPanel.add(scrollLotes);
		listasPanel.add(scrollCultivos);
		listasPanel.add(scrollMaquinas);

		add(listasPanel);
		add(aux);

		setTitle("Florencia");
		setSize(700, 700);

		actualizarEmpleados(finca.darEmpleados());

		actualizarLotes(finca.darLotes());
		actualizarMaquinas(finca.darMaquinas());
		actualizarCultivos(finca.darCultivosActuales());
		actualizarServicios(finca.darServicios());

	}

	/**
	 * Este mÈtodo se encarga de salvar la informaciÛn de la discotienda, justo
	 * antes de cerrar la aplicaciÛn
	 */
	public void dispose() {
		try {
			finca.salvarFinca();
			super.dispose();
		} catch (Exception e) {
			setVisible(true);
			int respuesta = JOptionPane.showConfirmDialog(this, "Problemas salvando la informaciÛn de la Finca:\n"
					+ e.getMessage() + "\nøQuiere cerrar el programa sin salvar?", "Error", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				super.dispose();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals(NUEVO_EMPLEADO)) {
			DialogoNuevoEmpleado jDia = new DialogoNuevoEmpleado(this);
			jDia.setVisible(true);
		}
		if (a.equals(NUEVO_LOTE)) {
			DialogoNuevoLote jDia = new DialogoNuevoLote(this);
			jDia.setVisible(true);
		}
		if (a.equals(NUEVO_MAQUINA)) {
			DialogoNuevaMaquina jDia = new DialogoNuevaMaquina(this);
			jDia.setVisible(true);
		}
		if (a.equals(NUEVO_CULTIVO)) {
			DialogoNuevoCultivo jDia = new DialogoNuevoCultivo(this);
			jDia.setVisible(true);
		}
		if (a.equals(NUEVO_SERVICIO)) {
			DialogoNuevoServicio jDia = new DialogoNuevoServicio(this);
			jDia.setVisible(true);
		}
		if(a.equals(ELIMINAR_EMPLEADO))
		{
			eliminarEmpleado();
		}
		if(a.equals(ELIMINAR_MAQUINA))
		{
			eliminarMaquina();
		}
		if(a.equals(ELIMINAR_CULTIVO))
		{
			eliminarCultivo();
		}
		if(a.equals(ELIMINAR_LOTE))
		{
			eliminarLote();
		}
		if(a.equals(ELIMINAR_SERVICIO))
		{
			eliminarServicio();
		}

	}

	

	public static void main(String[] args) {
		Finca fin = null;
		try {
			fin = new Finca("./data/Florencia.finca");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		InterfazFinca id = new InterfazFinca(fin);
		id.setVisible(true);
	}

	public void nuevoEmpleado(String nNombre, String nCedula) {
		finca.nuevoEmpleado(nNombre, nCedula);
		System.out.println("actualizar");
		actualizarEmpleados(finca.darEmpleados());

	}

	public Finca darFinca() {
		return finca;
	}

	public void actualizarEmpleados(ArrayList<Empleado> empleados) {
		txtEmpleados.removeAll();
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < empleados.size(); i++) {
			names.add(empleados.get(i).darNombre());
		}
		txtEmpleados.setListData(names.toArray());
		if (txtEmpleados.getModel().getSize() > 0) {
			txtEmpleados.setSelectedIndex(0);

		}
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void nuevoLote(String nNombre, String nUbicacion, double nCoste, double nArea) {
		finca.nuevoLote(nNombre, nUbicacion, nCoste, nArea);
		actualizarLotes(finca.darLotes());

	}

	public void nuevaMaquina(String nNombre) {
		finca.nuevaMaquina(nNombre);
		actualizarMaquinas(finca.darMaquinas());
	}

	public void actualizarLotes(ArrayList<Lote> lotes) {
		txtLotes.removeAll();
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < lotes.size(); i++) {
			names.add(lotes.get(i).darNombre());
		}
		txtLotes.setListData(names.toArray());
		if (txtLotes.getModel().getSize() > 0) {
			txtLotes.setSelectedIndex(0);

		}
	}

	public void actualizarMaquinas(ArrayList<Maquina> maquinas) {
		txtMaquinas.removeAll();
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < maquinas.size(); i++) {
			names.add(maquinas.get(i).darNombre());
		}
		txtMaquinas.setListData(names.toArray());
		if (txtMaquinas.getModel().getSize() > 0) {
			txtMaquinas.setSelectedIndex(0);

		}
	}

	public void actualizarCultivos(ArrayList<Cultivo> cultivosActuales) {
		txtCultivos.removeAll();
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < cultivosActuales.size(); i++) {
			Cultivo iCultivo = cultivosActuales.get(i);
			String line = iCultivo.darProducto() + "[" + iCultivo.darLote().darNombre() + "]";
			names.add(line);
		}
		txtCultivos.setListData(names.toArray());
		if (txtCultivos.getModel().getSize() > 0) {
			txtCultivos.setSelectedIndex(0);

		}
	}

	public void actualizarServicios(ArrayList<Servicio> servicios) {
		txtServicios.removeAll();
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < servicios.size(); i++) {
			Servicio iServicio = servicios.get(i);
			String line = iServicio.darTipo() + "|" + iServicio.darCultivo().darLote().darNombre() + "|"
					+ iServicio.darMaquinas().get(0).darNombre();
			names.add(line);
		}
		txtServicios.setListData(names.toArray());
		if (txtServicios.getModel().getSize() > 0) {
			txtServicios.setSelectedIndex(0);

		}

	}

	public void nuevoServicio(String dia, String mes, String anio, String tipo, String nombreLote, String maquina,
			String empleado, String costo) {
		LocalDate date = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		Lote nLote = finca.buscarLoteNombre(nombreLote);
		ArrayList<Maquina> maquinas = new ArrayList<>();
		maquinas.add(finca.buscarMaquinaNombre(maquina));
		ArrayList<Empleado> empleados = new ArrayList<>();
		empleados.add(finca.buscarEmpleadoNombre(empleado));
		finca.nuevoServicio(date, tipo, nLote, maquinas, empleados, Double.parseDouble(costo));
		actualizarServicios(finca.darServicios());
	}

	public void nuevoCultivo(String nProducto, String dia, String mes, String anio, String nlote2) {
		LocalDate date = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		finca.nuevoCultivo(nlote2, nProducto, date);
		actualizarCultivos(finca.darCultivosActuales());

	}
	public void eliminarEmpleado()
	{
		String empleado=txtEmpleados.getSelectedValue().toString();
		int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+empleado,"Eliminar Empleado",JOptionPane.YES_NO_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			finca.eliminarEmpleadoPorNombre(empleado);
		}
		actualizarEmpleados(finca.darEmpleados());
	}
	public void eliminarMaquina()
	{
		String maquina=txtMaquinas.getSelectedValue().toString();
		int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+maquina,"Eliminar Maquina",JOptionPane.YES_NO_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			finca.eliminarMaquina(maquina);
		}
		actualizarMaquinas(finca.darMaquinas());
	}
	public void eliminarCultivo() 
	{
		String cultivoylote=txtCultivos.getSelectedValue().toString();
		String cultivo=cultivoylote.split("\\[")[0];
		String sub=cultivoylote.split("\\[")[1];
		String lote=(sub).substring(0,sub.length()-1);
		int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+cultivo+", En el Lote: "+lote,"Eliminar Cultivo",JOptionPane.YES_NO_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			finca.buscarLoteNombre(lote).eliminarCultivo(finca.darCultivoPorLote(cultivo, lote));
		}
		actualizarCultivos(finca.darCultivosActuales());
		
	}
	public void eliminarLote()
	{
		String lote=txtLotes.getSelectedValue().toString();
		int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+lote,"Eliminar Lote",JOptionPane.YES_NO_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			finca.eliminarLote(lote);
		}
		actualizarLotes(finca.darLotes());
	}
	public void eliminarServicio()
	{
		String tipoLoteMaquina=txtServicios.getSelectedValue().toString();
		String[] tlm=tipoLoteMaquina.split("\\|");
		//System.out.println(tlm);
		String tipo=tlm[0];
		String lote=tlm[1];
		String maquina=tlm[2];
		int resp=JOptionPane.showConfirmDialog(this,"Esta seguro de Eliminar: "+tipo+", En el Lote: "+lote+", Por La Maquina: "+maquina
				,"Eliminar Servicio",JOptionPane.YES_NO_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			finca.eliminarServicio(lote, tipo);
		}
		actualizarServicios(finca.darServicios());
		
	}
}
