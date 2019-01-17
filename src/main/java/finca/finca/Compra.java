package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Compra implements Serializable, IInfo
{
private static final long serialVersionUID=800L;
	
private String id;	
private LocalDate fecha;
	
	private ArrayList<Insumo> insumos;
	
	private Proovedor proovedor;
	
	private double totalCompra;
	
	
	
	public Compra(int ID,LocalDate nFecha,ArrayList<Insumo> nInsumos,Proovedor nProovedor)
	{
		id="CO"+ID;
		fecha=nFecha;
		insumos=nInsumos;
		proovedor=nProovedor;
		totalCompra=0;
		for(Insumo iInsu: insumos)
		{
			totalCompra+=iInsu.darCantidadTotal()*iInsu.darValorUnidad();
			
		}
		
	}
	public String darNombreInfo() 
	{
		return darID()+"/"+darFecha()+"/"+darProovedor().darNombreInfo();
		
	}
	public String[] darEtiquetas() 
	{
		String[] rta= {"ID"+":","Fecha"+":","Insumos"+":","Proovedor"+":","Total Compra"+":"};
		return rta;
	}
	public String[] darInfo() 
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==5)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darFecha();
			rta[2]=etiquetas[2]+"#";
			Iterator<Insumo> iteI=insumos.iterator();
			while (iteI.hasNext())
			{
				Insumo iI=iteI.next();
				rta[2]+=iI.darNombreInfo()+",";
			}
			rta[3]=etiquetas[3]+"#"+darProovedor();
			rta[4]=etiquetas[4]+"#"+formatoDinero(darTotalCompra());
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Compra");
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
	public LocalDate darFecha()
	{
		return fecha;
	}
	public ArrayList<Insumo> darInsumos()
	{
		return insumos;
	}
	public Proovedor darProovedor()
	{
		return proovedor;
	}
	public double darTotalCompra()
	{
		if(totalCompra==0)
		{
			System.out.println("el total de la compra fue 0.0 ??????");
			
		}
		return totalCompra;
	}
	public String generarInsumosLineaCSV()
	{
		if(insumos.size()==0)
		{
			return "";
		}
		else if(insumos.size()==1)
		{
			return insumos.get(0).darID();
		}
		else {
			
				String linea="";
				int index=0;
				for(Insumo iInsumo:insumos)
				{
					linea+=iInsumo.darID();
					if(index<(insumos.size()-1))
					{
						linea+="#";
					}
					index++;
				
				}
				return linea;
		}
	}

	public String generarCantidadLineaCSV()
	{
		if(insumos.size()==0)
		{
			return "";
		}
		else if(insumos.size()==1)
		{
			return Double.toString(insumos.get(0).darCantidadTotal());
		}
		else {
			
				String linea="";
				int index=0;
				for(Insumo iInsumo:insumos)
				{
					linea+=Double.toString(iInsumo.darCantidadTotal());
					if(index<(insumos.size()-1))
					{
						linea+="#";
					}
					index++;
				
				}
				return linea;
		}
	}
}
