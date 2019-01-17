package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Maquina implements Serializable, IInfo
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
	public String darNombreInfo()
	{
		String rta=darID()+darNombre();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Nombre"+":","Gastos"+":"
		,"Trabajos"+":","Dias de Trabajo"+":","Horometro"+":"
		};
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==6)
		{ 
			
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darNombre();
			//gastos
			Iterator<Servicio> iteSG=gastos.iterator();
			rta[2]=etiquetas[2]+"#";
			while(iteSG.hasNext())
			{
				Servicio iS=iteSG.next();
				rta[2]+=iS.darNombreInfo()+",";
				
			}
			
			//trabajos
			rta[3]=etiquetas[3]+"#";
			Iterator<Servicio> iteST=trabajos.iterator();
			while(iteST.hasNext())
			{
				Servicio iS=iteST.next();
				rta[2]+=iS.darNombreInfo()+",";
				
			}
			rta[4]=etiquetas[4]+"#"+darHorasTrabajo();
			rta[5]=etiquetas[5]+"#"+darHorometro();
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Maquina");
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
