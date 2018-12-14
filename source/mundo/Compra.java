package mundo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Compra 
{
private static final long serialVersionUID=800L;
	
	private LocalDate fecha;
	
	private Insumo[] insumos;
	
	private String proovedor;
	
	private double totalCompra;
	
	
	
	public Compra(LocalDate nFecha,Insumo[] nInsumos,String nProovedor)
	{
		fecha=nFecha;
		insumos=nInsumos;
		proovedor=nProovedor;
		totalCompra=0;
		for(Insumo iInsu: insumos)
		{
			totalCompra+=iInsu.darCantidadTotal()*iInsu.darValorUnidad();
			
		}
		
	}
	public LocalDate darFecha()
	{
		return fecha;
	}
	public Insumo[] darInsumos()
	{
		return insumos;
	}
	public String darProovedor()
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

}
