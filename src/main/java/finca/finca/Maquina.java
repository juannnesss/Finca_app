package finca.finca;

import java.io.Serializable;
import java.util.ArrayList;

public class Maquina implements Serializable
{
	private static final long serialVersionUID=300L;
	private String id;
	private String nombre;
	private ArrayList<Servicio> gastos;
	private ArrayList<Servicio> trabajos;
	private int diasDeTrabajo;
	private double horoMetro;
	
	public Maquina(int nID,String nNombre,int horas)
	{
		id="MA"+nID;
		nombre=nNombre;
		gastos=new ArrayList<Servicio>();
		trabajos=new ArrayList<Servicio>();
		diasDeTrabajo=horas;
		horoMetro=0;
	}
	public String darID()
	{
		return id;
	}
	public String darNombre()
	{
		return nombre;
		
	}
	
	public ArrayList<Servicio> darGastos()
	{
		return gastos;
	}
	
	public ArrayList<Servicio> darServicios()
	{
		return trabajos;
	}
	public int darHorasTrabajo()
	{
		return diasDeTrabajo;
	}
	public double darHorometro()
	{
		return horoMetro;
	}
	public void agregarGasto(Servicio servicio,double horometro)
	{
		gastos.add(servicio);
		setHorometro(horometro);
		
	}
	
	public void agregarTrabajo(Servicio trabajo)
	{
		trabajos.add(trabajo);
		diasDeTrabajo+=1;
	}
	public void agregarTrabajoCSV(Servicio trabajo) 
	{
		
		trabajos.add(trabajo);
	}
	public void eliminarGasto(Servicio servicio)
	{
		for (int i=0;i<gastos.size();i++)
		{
			if(servicio.equals(gastos.get(i)))
			{
				gastos.remove(i);
			}
					
		}
	}
	
	public void eliminarTrabajo(Servicio trabajo)
	{
		boolean x=true;
		for (int i=0;i<trabajos.size()&&x;i++)
		{
			if(trabajo.equals(trabajos.get(i)))
			{
				trabajos.remove(i);
				diasDeTrabajo-=1;
				x=false;
			}
					
		}
	}
	public double darGastosTotales()
	{
		double rta=0;
		for(int i=0;i<gastos.size();i++)
		{
			Servicio iServicio=gastos.get(i);
			rta+=iServicio.darCostoXAreaoPorPeso();
		}
		return rta;
	}
	public void setHorometro(double horometro)
	{
		horoMetro=horometro;
	}
	

}
