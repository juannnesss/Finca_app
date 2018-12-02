package mundo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Servicio implements Serializable
{
	private static final long serialVersionUID=400L;
	private LocalDate fecha;
	
	private String tipo;
	private Cultivo cultivo;
	private Lote lote;
	
	
	
	private ArrayList<Maquina> maquinas;
	
	private ArrayList<Empleado> empleados;
	private ArrayList<Insumo> insumos;
	
	private double costo;
	
	public Servicio(LocalDate nFecha,String nTipo,Lote nLote,Cultivo nCultivo,ArrayList<Maquina> nMaquinas,ArrayList<Empleado> nEmpleados,ArrayList<Insumo> nInsumos,double nCosto)
	{
		fecha=nFecha;
		tipo=nTipo;
		lote=nLote;
		cultivo=nCultivo;
		maquinas=nMaquinas;
		empleados=nEmpleados;
		insumos=nInsumos;
		costo=nCosto;
	}
	
	public LocalDate darFecha()
	{
		return fecha;
	}
	public String darTipo()
	{
		return tipo;
	}
	public Cultivo darCultivo()
	{
		return cultivo;
	}
	public ArrayList<Maquina> darMaquinas()
	{
		return maquinas;
	}
	public ArrayList<Empleado> darEmpleados()
	{
		return empleados;
	}
	public double darCostoXArea()
	{
		return costo;
	}
	public double darCostoTotalArea()
	{
		return lote==null?costo:costo*lote.darArea();
	}
	public ArrayList<Insumo> darInsumos()
	{
		return insumos;
	}
	public Lote darLote()
	{
		return lote;
	}
	public double calcularCostoTotalServicio()
	{
		double costoInsumos=0;
		for (int i = 0; i < insumos.size(); i++) 
		{
			Insumo iInsumo=insumos.get(i);
			double cantidad=iInsumo.darCantidad();
			double valorUnidad=iInsumo.darValorUnidad();
			costoInsumos+=(cantidad*valorUnidad);
			
		}
		return darCostoTotalArea()+costoInsumos;
	}

}
