package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Iterator;

public class Proovedor implements Serializable, IInfo
{
	private static final long serialVersionUID=900L;
	private String id;
	private String nombre;
	
	private String nIT;
	
	public Proovedor(int nID,String nNombre,String nNIT) 
	{
		id="PR"+nID;
		nombre=nNombre;
		nIT=nNIT;
	}
	public String darNombreInfo()
	{
		String rta=darID()+darNombre();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Nombre"+":","NIT"+":"};
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==3)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darNombre();
			rta[2]=etiquetas[2]+"#"+darNIT();
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Proovedores");
			return null;
		}
		
	}
	public String formatoDinero(double dinero) {
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setGroupingUsed(true);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf.format(dinero);
		
	}
	public String darID()
	{
		return id;
	}
	public String darNombre()
	{
		return nombre;
	}
	public String darNIT()
	{
		return nIT;
	}
	

}
