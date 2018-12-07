package mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class Maquina implements Serializable
{
	private static final long serialVersionUID=300L;
	private String nombre;
	private ArrayList<Servicio> gastos;
	private ArrayList<Servicio> trabajos;
	private int diasDeTrabajo;
	private double horoMetro;
	
	public Maquina(String nNombre,int horas)
	{
		nombre=nNombre;
		gastos=new ArrayList<Servicio>();
		trabajos=new ArrayList<Servicio>();
		diasDeTrabajo=horas;
		horoMetro=0;
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
			rta+=iServicio.darCostoXArea();
		}
		return rta;
	}
	public void setHorometro(double horometro)
	{
		horoMetro=horometro;
	}

}
