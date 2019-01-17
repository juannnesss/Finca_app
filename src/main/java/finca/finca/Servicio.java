package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import interfaz.MaquinasVentana;

public class Servicio implements Serializable, IInfo 
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
	public String darNombreInfo()
	{
		String rta=darID()+darTipo();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Fecha"+":","Tipo"+":","Cultivo"+":","Lote"+":","Maquinas"+":"
		,"Empleados"+":","Insumos"+":","Costo Unidad"+":","Carga?"+":"};
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==10)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darFecha();
			rta[2]=etiquetas[2]+"#"+darTipo();
			rta[3]=etiquetas[3]+"#"+darCultivo().darNombreInfo();
			rta[4]=etiquetas[4]+"#"+darLote().darNombreInfo();
			
			//maquinas
			Iterator<Maquina> iteM=maquinas.iterator();
			rta[5]=etiquetas[5]+"#";
			while(iteM.hasNext())
			{
				Maquina iM=iteM.next();
				rta[5]+=iM.darNombre()+",";
			}
			
			//empleados
			Iterator<Empleado> iteE=empleados.iterator();
			rta[6]=etiquetas[6]+"#";
			while(iteE.hasNext())
			{
				Empleado iE=iteE.next();
				rta[6]+=iE.darNombreInfo()+",";
			}
			
			//insumo
			Iterator<Insumo> iteI=insumos.iterator();
			rta[7]=etiquetas[7]+"#";
			while(iteI.hasNext())
			{
				Insumo iI=iteI.next();
				rta[7]+=iI.darNombreInfo()+",";
			}
			
			rta[8]=etiquetas[8]+"#"+darCostoUnidad();
			rta[9]=etiquetas[9]+"#"+esCarga();
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Servicios");
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
