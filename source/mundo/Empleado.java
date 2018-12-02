package mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class Empleado implements Serializable
{
	private static final long serialVersionUID=500L;
	private String nombre;
	private String cedula;
	private ArrayList<Servicio> serviciosPrestados;
	private double diasTrabajados;
	
	public Empleado(String nNombre, String nCedula, double nDias)
	{
		nombre=nNombre;
		cedula=nCedula;
		diasTrabajados=nDias;
		serviciosPrestados=new ArrayList<Servicio>();
	}
	
	public String darNombre()
	{
		return nombre;
	}
	public String darCedula()
	{
		return cedula;
	}
	public double darDiasTrabajados()
	{
		return diasTrabajados;
	}
	public ArrayList<Servicio> darServiciosPrestados()
	{
		return serviciosPrestados;
	}
	public void nuevoServicioPrestado(Servicio servicio)
	{
		serviciosPrestados.add(servicio);
	}

	public void agregarServicio(Servicio servicio) 
	{
		serviciosPrestados.add(servicio);
		diasTrabajados+=1;
		
	}
	public void eliminarServicio(Servicio servicio)
	{
		boolean x=true;
		for (int i=0;i<serviciosPrestados.size()&&x;i++)
		{
			if(servicio.equals(serviciosPrestados.get(i)))
			{
				serviciosPrestados.remove(i);
				diasTrabajados-=1;
				x=false;
			}
					
		}
	}

}
