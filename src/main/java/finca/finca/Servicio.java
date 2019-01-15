package finca.finca;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import interfaz.MaquinasVentana;

public class Servicio implements Serializable
{
	private static final long serialVersionUID=400L;
	private String id;
	private LocalDate fecha;
	private String tipo;
	private Cultivo cultivo;
	private Lote lote;
	
	
	
	private ArrayList<Maquina> maquinas;
	
	private ArrayList<Empleado> empleados;
	private ArrayList<Insumo> insumos;
	
	private double costo;
	private boolean carga;
	
	public Servicio(boolean nCarga,int nID,LocalDate nFecha,String nTipo,Lote nLote,Cultivo nCultivo,ArrayList<Maquina> nMaquinas,ArrayList<Empleado> nEmpleados,ArrayList<Insumo> nInsumos,double nCosto)
	{
		carga=nCarga;
		id="SE"+nID;
		fecha=nFecha;
		tipo=nTipo;
		lote=nLote;
		cultivo=nCultivo;
		maquinas=nMaquinas;
		empleados=nEmpleados;
		insumos=nInsumos;
		costo=nCosto;
	}
	public String darID()
	{
		return id;
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
	public double darCostoUnidad()
	{
		return costo;
	}
	public double darCostoXAreaoPorPeso()
	{if(carga)
	{
		return lote.darArea()>0?costo*(insumos.get(0).darCantidadTotal()/lote.darArea()):costo;
	}
	else{
		return costo;
	}
	}
	public double darCostoTotalAreaoPorPeso()
	{
		if(carga)
		{
			return costo*insumos.get(0).darCantidadTotal();
		}
		else{
			return lote==null?costo:costo*lote.darArea();
		}
		
	}
	public ArrayList<Insumo> darInsumos()
	{
		return insumos;
	}
	public Lote darLote()
	{
		return lote;
	}
	public boolean esCarga()
	{
		return carga;
	}
	public double calcularCostoTotalServicio()
	{
		double costoInsumos=0;
	
		if(!carga)
		{
			for (int i = 0; i < insumos.size(); i++) 
			{
				Insumo iInsumo=insumos.get(i);
				double cantidad=iInsumo.darCantidadTotal();
				double valorUnidad=iInsumo.darValorUnidad();
				costoInsumos+=(cantidad*valorUnidad);
				
			}
		}
		
		
		return darCostoTotalAreaoPorPeso()+costoInsumos;
		
	}
	public String generarLineaMaquinasCSV()
	{
		if(maquinas.size()==0)
		{
			return "";
		}
		else if(maquinas.size()==1)
		{
			return maquinas.get(0).darID();
		}
		else {
			String linea="";
			int index=0;
			for(Maquina iMaquina:maquinas)
			{
				linea+=iMaquina.darID();
				if(index<(maquinas.size()-1))
				{
					linea+="#";
				}
				index++;
			}
			System.out.println(linea);
			return linea;
		}
	}
	public String generarLineaEmpleadosCSV()
	{
		if(empleados.size()==0)
		{
			return "";
		}
		else if(empleados.size()==1)
		{
			return empleados.get(0).darID();
		}
		else {
			String linea="";
			int index=0;
			for(Empleado iEmpleado:empleados)
			{
				linea+=iEmpleado.darID();
				if(index<(empleados.size()-1))
				{
					linea+="#";
				}
				index++;
			}
			System.out.println(linea);
			return linea;
		}
		
	}

	public String generarLineaInsumosDosisCSV()
	{
		double area=lote.darArea();
		if(insumos.size()==0)
		{
			return "";
		}
		else if(insumos.size()==1)
		{
			return insumos.get(0).darID()+"|"+(area==0?0:insumos.get(0).darCantidadTotal()/area);
		}
		else {
			String linea="";
			int index=0;
			for(Insumo iInsumo:insumos)
			{
				linea+=iInsumo.darID()+"|"+(area==0?0:iInsumo.darCantidadTotal()/area);
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
