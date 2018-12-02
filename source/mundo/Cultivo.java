package mundo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cultivo implements Serializable
{
	private static final long serialVersionUID=600L;
	
	private String producto;
	private LocalDate siembraFecha;
	private LocalDate cortaFecha;
	private ArrayList<Servicio> servicios;
	private Cultivo anterior;
	private Cultivo siguiente;
	private Lote lote;
	private double produccion;

	public Cultivo(String nProducto,LocalDate nPeriodo,Lote nLote)
	{
		producto=nProducto;
		siembraFecha=nPeriodo;
		servicios=new ArrayList<Servicio>();
		anterior=null;
		siguiente=null;
		lote=nLote;
		produccion=0;
		
	}
	
	public String darProducto()
	{
		return producto;
	}
	public LocalDate darFechaSiembra()
	{
		return siembraFecha;
	}
	public ArrayList<Servicio> darServicios()
	{
		return servicios;
	}
	public Cultivo darAnterior()
	{
		return anterior;
	}
	public Cultivo darSiguiente()
	{
		return siguiente;
	}
	public double darProduccion()
	{
		return produccion;
	}
	public LocalDate darFechaCorta()
	{
		return cortaFecha;
	}
	public void asignarFechaCorta(LocalDate corta)
	{
		cortaFecha=corta;
	}
	public void actualizarAnterior(Cultivo nCultivo)
	{
		anterior=nCultivo;
	}
	public void actualizarSiguiente(Cultivo nCultivo)
	{
		siguiente=nCultivo;
	}
	
	public boolean esUltimo()
	{
		return (darSiguiente()==null);
	}
	public boolean esPrimero()
	{
		return (darAnterior()==null);
	}
	public void agregarServicio(Servicio servicio)
	{
		servicios.add(servicio);
	}
	public Lote darLote()
	{
		return lote;
	}
	public boolean eliminarServicio(Servicio servicio)
	{
		boolean rta=false;
		for (int i=0;i<servicios.size();i++)
		{
			if(servicio.equals(servicios.get(i)))
			{
				servicios.remove(i);
				rta=true;
			}
					
		}
		return rta;
	}
	public void asignarProduccion(double produc)
	{
		produccion=produc;
	}
	public double calcularCostoTotal()
	{
		double rta=0;
		for (int i=0;i<servicios.size();i++)
		{
			Servicio iServicio=servicios.get(i);
			rta+=iServicio.calcularCostoTotalServicio();
		}
		return rta;
	}
	public double calcularRendimiento()
	{
		return produccion-(calcularCostoTotal()+lote.darCosteTierra());
	}
}
