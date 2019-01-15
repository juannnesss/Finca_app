package finca.finca;

import java.time.LocalDate;
import java.util.ArrayList;

public class Compra 
{
private static final long serialVersionUID=800L;
	
private String id;	
private LocalDate fecha;
	
	private Insumo[] insumos;
	
	private Proovedor proovedor;
	
	private double totalCompra;
	
	
	
	public Compra(int ID,LocalDate nFecha,Insumo[] nInsumos,Proovedor nProovedor)
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
	public String darID()
	{
		return id;
	}
	public LocalDate darFecha()
	{
		return fecha;
	}
	public Insumo[] darInsumos()
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
		if(insumos.length==0)
		{
			return "";
		}
		else if(insumos.length==1)
		{
			return insumos[0].darID();
		}
		else {
			
				String linea="";
				int index=0;
				for(Insumo iInsumo:insumos)
				{
					linea+=iInsumo.darID();
					if(index<(insumos.length-1))
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
		if(insumos.length==0)
		{
			return "";
		}
		else if(insumos.length==1)
		{
			return Double.toString(insumos[0].darCantidadTotal());
		}
		else {
			
				String linea="";
				int index=0;
				for(Insumo iInsumo:insumos)
				{
					linea+=Double.toString(iInsumo.darCantidadTotal());
					if(index<(insumos.length-1))
					{
						linea+="#";
					}
					index++;
				
				}
				return linea;
		}
	}
}
