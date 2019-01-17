
package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Lote implements Serializable, IInfo
{
	private static final long serialVersionUID=200L;
	private String id;
	private String nombre;
	
	private String ubicacion;
	
	private double costeTierra;
	
	private double area;
	
	private Cultivo actual;
	
	private ArrayList<Cultivo> cultivos;
	
	public Lote(int nID,String nNombre,String nUbicacion,double nCoste,double nArea)
	{
		id="LO"+nID;
		nombre=nNombre;
		ubicacion=nUbicacion;
		costeTierra=nCoste;
		area=nArea;
		actual=null;
		cultivos=new ArrayList<Cultivo>();
	}
	public String darNombreInfo()
	{
		String rta=darID()+darNombre();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Nombre"+":","Ubicacion"+":"
		,"Coste de Tierra, Por Hectarea"+":","Area, Hectarea"+":","Cultivo Actual"+":"
		,"Total de Cultivos"
				+ ":"};
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==7)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darNombre();
			rta[2]=etiquetas[2]+"#"+darUbicacion();
			rta[3]=etiquetas[3]+"#"+darCosteTierra();
			rta[4]=etiquetas[4]+"#"+darArea();
			rta[5]=etiquetas[5]+"#"+darCultivoActual().darNombreInfo();
			rta[6]=etiquetas[6]+"#"+cultivos.size();
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Lote");
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
	public String darNombre()
	{
		return nombre;
		
	}

	public String darUbicacion()
	{
		return ubicacion;
	}
	public double darCosteTierra()
	{
		return costeTierra;
	}
	public double darArea()
	{
		return area;
	}
	public Cultivo darCultivoActual()
	{
		return actual;
	}
	public ArrayList<Cultivo> darCultivos()
	{
		return cultivos;
	}
	
	public void actualizarCultivoActual(Cultivo cultivo,LocalDate corta)
	{
		if(actual==null)
		{
			//System.out.println("Lote Class actual igual null");
			cultivo.asignarFechaCorta(corta);
			
			actual=cultivo;
			cultivos.add(cultivo);
			
		}
		else
		{
			actual.asignarFechaCorta(corta);
			actual.actualizarSiguiente(cultivo);
			cultivo.actualizarAnterior(actual);
			cultivos.add(actual);
			actual=cultivo;
		}
	}
	public void eliminarServicio(Servicio servicio)
	{
		boolean x=false;
		for (int i=cultivos.size()-1;i>-1&&!x;i--)
		{
			x=cultivos.get(i).eliminarServicio(servicio);
		}
		
	}
	public void eliminarCultivo(Cultivo cultivo)
	{
		if(cultivo.equals(actual))
		{
			Cultivo anterior=actual.darAnterior();
			if(anterior!=null)
			{
				anterior.actualizarSiguiente(null);
			}
			
			cultivos.remove(cultivos.size()-1);
			actual=null;
		}
		else
		{
			Cultivo anterior=cultivo.darAnterior();
			Cultivo siguiente=cultivo.darSiguiente();
			if(anterior!=null)
			{
				anterior.actualizarSiguiente(siguiente);
			}
			
			siguiente.actualizarAnterior(anterior);
			boolean x=true;
			for(int i=cultivos.size()-1;i>-1&&x;i--)
			{
				if(cultivos.get(i).equals(cultivo))
				{
					cultivos.remove(i);
					x=false; 
				}
			}
		}	
	}
	
	public boolean asignarProduccionLote(double produccion) 
	{
		boolean rta=false;
		if(actual!=null)
		{
			actual.asignarProduccion(produccion);
			rta=true;
		}
		return rta;
	}
	
}
