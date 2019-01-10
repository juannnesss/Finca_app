package mundo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Empleado implements Serializable
{
	private static final long serialVersionUID=500L;
	private String id;
	private String nombre;
	
	private String cedula;
	private LocalDate fechaIngreso;
	private String eps;
	private String[] tallas;
	private double salario;
	private ArrayList<Servicio> serviciosPrestados;
	private double diasTrabajados;
	//private String photoFile;
	
	public Empleado(int nID,String nNombre, String nCedula,LocalDate nFecha,String nEps,String zapatos
			,String pantalon,String camiseta,double nSalario,double nDias)
	{
		id="EM"+nID;
		nombre=nNombre;
		cedula=nCedula;
		fechaIngreso=nFecha;
		eps=nEps;
		tallas=new String[3];
		tallas[0]=zapatos;
		tallas[1]=pantalon;
		tallas[2]=camiseta;
		salario=nSalario;
		diasTrabajados=nDias;
		serviciosPrestados=new ArrayList<Servicio>();
	}
	public String darID()
	{
		return id;
	}
	public String darNombre()
	{
		return nombre;
	}
	public String darCedula()
	{
		return cedula;
	}
	public LocalDate darFechaIngreso()
	{
		return fechaIngreso;
	}
	public String darEps()
	{
		return eps;
	}
	public String[] darTallas()
	{
		return tallas;
	}
	public double darSalario()
	{
		return salario;
	}
	public double darDiasTrabajados()
	{
		return diasTrabajados;
	}
	public ArrayList<Servicio> darServiciosPrestados()
	{
		return serviciosPrestados;
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
	public void agregarServicioCSV(Servicio servicio) 
	{
		serviciosPrestados.add(servicio);
		
		
	}

}
