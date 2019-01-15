package finca.finca;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cultivo implements Serializable
{
	private static final long serialVersionUID=600L;
	private Finca finca;
	private String id;
	private String producto;
	private LocalDate siembraFecha;
	private LocalDate cortaFecha;
	private String[] serviciosIDS;
	private ArrayList<Servicio> servicios;
	private Cultivo anterior;
	private Cultivo siguiente;
	private Lote lote;
	private double produccionDINERO;
	private Insumo produInsumo;

	public Cultivo(Finca mundo,int nID,String nProducto,LocalDate nPeriodo,Lote nLote,String[] serviciosID,double produccionInsumo)
	{
		finca=mundo;
		id="CU"+nID;
		producto=nProducto;
		siembraFecha=nPeriodo;
		cortaFecha=null;
		servicios=new ArrayList<Servicio>();
		anterior=null;
		siguiente=null;
		lote=nLote;
		produccionDINERO=0;
		serviciosIDS=serviciosID==null?new String[0]:serviciosID;
		if(produccionInsumo==-1)
		{
			Insumo insumo=finca.nuevoInsumo("PROD:"+lote.darNombre(), 0, 0, "Kg.");
			produInsumo=insumo;
			
		}
		else
		{
			produInsumo=finca.darInsumoPorIndex(finca.buscarInsumoIndex("PROD:"+lote.darNombre()));
		}
		
		
	}
	public void asignarIDS(String[] ids)
	{
		serviciosIDS=ids;
	}
	public String[] darIDS()
	{
		return serviciosIDS;
	}
	public String darID()
	{
		return id;
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
		return produccionDINERO;
	}
	public double darProduccionInsumo()
	{
		return produInsumo.darCantidadTotal();
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
		produccionDINERO=produc;
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
		return produccionDINERO-(calcularCostoTotal()+(lote.darCosteTierra()*lote.darArea()));
	}
	public String generarLineaServiciosCSV()
	{
		if(servicios.size()==0)
		{
			return "";
		}
		else if(servicios.size()==1)
		{
			return servicios.get(0).darID();
		}
		else {
			String linea="";
			int index=0;
			for(Servicio iServicio:servicios)
			{
				linea+=iServicio.darID();
				if(index<(servicios.size()-1))
				{
					linea+="#";
				}
				index++;
			}
			
			return linea;
		}
	}
	public void IDStoServicios()
	{
		
	}
	public void agregarServicioCSV(Servicio servicio) {
		// TODO Auto-generated method stub
		
	}
}
