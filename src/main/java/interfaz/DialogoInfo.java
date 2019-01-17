package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import finca.finca.IInfo;

public class DialogoInfo extends JDialog implements ActionListener
{

	private static final String OK="ok";
	private static final String ANTERIOR="ante";
	private static final String SIGUIENTE="sig";
	
	
	private IInfo informacion;
	private int numeroItems;
	
	private JFrame interfaz;
	
	private JLabel jlEtiquetas[];
	private JTextArea jlInfo[];
	
	private JButton buttonOk;
	private JButton anteriorButton;
	private JButton siguienteButton;
	
	private int siguiente;
	private int anterior;
	private ArrayList<IInfo> infoArrayList;
	
	public DialogoInfo(JFrame inter,IInfo info,int antes,int sigui,ArrayList<IInfo> infosArray) 
	{
		super(inter);
		interfaz=inter;
		setSize(500, 500);
		setTitle("Informacion");
		siguiente=sigui;
		anterior=antes;
		infoArrayList=infosArray;
		informacion=info;
		numeroItems=informacion.darEtiquetas().length;
		setLayout(new GridLayout(numeroItems+1,1));
		
		
		jlEtiquetas=new JLabel[numeroItems];
		jlInfo=new JTextArea[numeroItems];
		JPanel[] aux=new JPanel[numeroItems];
		for(int i=0;i<numeroItems;i++)
		{
			jlEtiquetas[i]=new JLabel(informacion.darEtiquetas()[i]);
			
			jlInfo[i]=new JTextArea(informacion.darInfo()[i].split("[#]").length>1?informacion.darInfo()[i].split("[#]")[1]:"");
			aux[i]=new JPanel();
			aux[i].setLayout(new GridLayout(1, 2));
			aux[i].add(jlEtiquetas[i]);
			aux[i].add(jlInfo[i]);
			add(aux[i]);
		}
		JPanel panelOpciones=new JPanel();
		panelOpciones.setLayout(new GridLayout(1, 3));
		
		anteriorButton=new JButton("<= Anterior");
		anteriorButton.setActionCommand(ANTERIOR);
		anteriorButton.addActionListener(this);
		panelOpciones.add(anteriorButton);
		
		buttonOk=new JButton("OK");
		buttonOk.setActionCommand(OK);
		buttonOk.addActionListener(this);
		panelOpciones.add(buttonOk);
		
		siguienteButton=new JButton("Siguiente =>");
		siguienteButton.setActionCommand(SIGUIENTE);
		siguienteButton.addActionListener(this);
		panelOpciones.add(siguienteButton);
		if(anterior==-1)
		{
			anteriorButton.setEnabled(false);
		}
		if(siguiente==-1)
		{
			siguienteButton.setEnabled(false);
		}
		
		
		add(panelOpciones);
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
		if(a.equals(OK))
		{
			setVisible(false);
		}
		if(a.equals(ANTERIOR))
		{
			JDialog jDialog=new DialogoInfo(interfaz, infoArrayList.get(anterior), anterior-1, siguiente-1,infoArrayList);
			jDialog.setVisible(true);
			setVisible(false);
		}
		if(a.equals(SIGUIENTE))
		{
			if(siguiente==infoArrayList.size()-1)
			{
				JDialog jDialog=new DialogoInfo(interfaz, infoArrayList.get(siguiente), anterior+1, -1,infoArrayList);
				jDialog.setVisible(true);
				setVisible(false);
			}
			else{
				JDialog jDialog=new DialogoInfo(interfaz, infoArrayList.get(siguiente), anterior+1, siguiente+1,infoArrayList);
				jDialog.setVisible(true);
				setVisible(false);
				
			}
			
		}
		
	}
}
