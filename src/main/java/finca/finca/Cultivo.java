package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Cultivo implements Serializable, IInfo
{
	private static final long serialVersionUID=600L;
	private String[] serviciosIDS;
	private Finca finca;
	private String id;
	private String producto;
	private LocalDate siembraFecha;
	private LocalDate cortaFecha;
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
	public String darNombreInfo()
	{
		String rta=darID()+darLote().darNombreInfo()+"/"+darProducto();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Producto"+":","Fecha Siembra"+":"
		,"Fecha Corta"+":","Servicios"+":","Cultivo Anterior"+":",
		"Cultivo Siguiente"+":","Lote"
				+ ":","Produccion Dinero"
						+ ":","Produccion Peso"};
		
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==10)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darProducto();
			rta[2]=etiquetas[2]+"#"+darFechaSiembra();
			rta[3]=etiquetas[3]+"#"+darFechaCorta();
			rta[4]=etiquetas[4]+"#";
			Iterator<Servicio> iteS=servicios.iterator();
			while(iteS.hasNext())
			{
				Servicio iS=iteS.next();
				rta[4]+=iS.darNombreInfo()+",";			
			}
				
			rta[5]=etiquetas[5]+"#"+(anterior==null?"":anterior.darNombreInfo());
			rta[6]=etiquetas[6]+"#"+(siguiente==null?"":siguiente.darNombreInfo());
			rta[7]=etiquetas[7]+"#"+lote.darNombreInfo();
			rta[8]=etiquetas[8]+"#"+formatoDinero(darProduccion());
			rta[9]=etiquetas[9]+"#"+darProduccionInsumo();
			
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Cultivo");
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
