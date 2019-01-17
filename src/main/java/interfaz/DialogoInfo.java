package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import finca.finca.IInfo;

public class DialogoInfo extends JDialog implements ActionListener
{

	private static final String OK="ok";
	private IInfo informacion;
	private int numeroItems;
	
	private JLabel jlEtiquetas[];
	private JLabel jlInfo[];
	private JButton buttonOk;
	
	public DialogoInfo(JFrame inter,IInfo info) 
	{
		super(inter);
		setSize(500, 500);
		setTitle("Informacion");
		informacion=info;
		numeroItems=informacion.darEtiquetas().length;
		setLayout(new GridLayout(numeroItems+2,2));
		jlEtiquetas=new JLabel[numeroItems];
		jlInfo=new JLabel[numeroItems];
		for(int i=0;i<numeroItems;i++)
		{
			jlEtiquetas[i]=new JLabel(informacion.darEtiquetas()[i]);
			System.out.println(informacion.darEtiquetas()[i]);
			System.out.println(informacion.darInfo()[i]);
			jlInfo[i]=new JLabel(informacion.darInfo()[i].split("[#]")[1]);
			add(jlEtiquetas[i]);
			add(jlInfo[i]);
		}
		
		buttonOk=new JButton("OK");
		buttonOk.setActionCommand(OK);
		buttonOk.addActionListener(this);
		add(buttonOk);
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		String a=e.getActionCommand();
		if(a.equals(OK))
		{
			setVisible(false);
		}
		
	}
}
