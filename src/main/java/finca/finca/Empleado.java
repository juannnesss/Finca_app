package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Empleado implements Serializable, IInfo
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
	public String darNombreInfo()
	{
		String rta=darID()+darNombre();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Nombre"+":","Cedula"+":"
		,"Fecha Ingreso"+":","Eps"+":","Tallas(Zapatos, Pantalon, Camisa)"+":",
		"Salario"+":","Servicios"
				+ ":","Dias Trabajados"
						+ ":"};
		
		
		return rta;
	}
	public String[] darInfo()
	{
		String[] etiquetas=darEtiquetas();
		String[] rta=new String[etiquetas.length];
		if(etiquetas.length==9)
		{ 
			//System.out.println(etiquetas[0]);
			rta[0]=etiquetas[0]+"#"+darID();
			rta[1]=etiquetas[1]+"#"+darNombre();
			rta[2]=etiquetas[2]+"#"+darCedula();
			//System.out.println(etiquetas[2]);
			rta[3]=etiquetas[3]+"#"+darFechaIngreso();
			rta[4]=etiquetas[4]+"#"+darEps();
			//System.out.println(etiquetas[4]);
			rta[5]=etiquetas[5]+"#"+darTallas()[0]+","+darTallas()[1]+","+darTallas()[2];
			//System.out.println(etiquetas[5]);
			rta[6]=etiquetas[6]+"#"+formatoDinero(darSalario());
			//System.out.println(etiquetas[6]);
			rta[7]=etiquetas[7]+"#";
			Iterator<Servicio> iteS=serviciosPrestados.iterator();
			while(iteS.hasNext())
			{
				Servicio iS=iteS.next();
				rta[7]+=iS.darNombreInfo()+",";
			}
			rta[8]=etiquetas[8]+"#"+darDiasTrabajados();
			
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Empleado");
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
