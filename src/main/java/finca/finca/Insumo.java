package finca.finca;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Insumo implements Serializable, IInfo
{
	private static final long serialVersionUID=700L;
	private String id;
	private String nombre;
	
	private double cantidadTotal;
	private ArrayList<Object[]> distribucion;
	
	private double valorUnidad;
	
	private String tipoCantidadMedida;
	
	public Insumo(int nID,String nNombre,double nCantidad, double nValorUnidad, String nTipoMedida,String ubicacionInicial)
	{
		id="IN"+nID;
		nombre=nNombre;
		cantidadTotal=nCantidad;
		valorUnidad=nValorUnidad;
		tipoCantidadMedida=nTipoMedida;
		//manejo de la distribucion del insumo, inicializando 
		distribucion=new ArrayList<Object[]>();
		Object[] ob=new Object[2];
		ob[0]=ubicacionInicial;
		ob[1]=cantidadTotal;
		distribucion.add(ob);
		
	}
	public String darNombreInfo()
	{
		String rta=darID()+darNombre();
		return rta;
	}
	public String[] darEtiquetas()
	{
		
		String [] rta={"ID"+":","Nombre"+":","Cantidad Total"+":"
		,"Distribucion"+":","Valor Unidad"+":","Tipo Medida"+":"
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
			rta[2]=etiquetas[2]+"#"+darCantidadTotal();
			rta[3]=etiquetas[3]+"#"+darUbicacion();
			rta[4]=etiquetas[4]+"#"+formatoDinero(darValorUnidad());
			rta[5]=etiquetas[5]+"#"+darTipoMedida();
			
			return rta;
		}
		else 
		{
			System.out.println("Numero de Etiquetas No es igual Insumo");
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
	public double darCantidadTotal()
	{
		return cantidadTotal;
	
	}
	public String darUbicacion()
	{
		return "";
	}
	public String darTipoMedida()
	{
		return tipoCantidadMedida;
	}
	public void registrarCompra(double nuevo,String ubicacion)
	{
		cantidadTotal+=nuevo;
		double cantiUbicacion=Double.parseDouble((distribucion.get(buscarUbicacion(ubicacion))[1]).toString());
		distribucion.get(buscarUbicacion(ubicacion))[1]=cantiUbicacion+nuevo;
	}
	public double darValorUnidad() 
	{
		return valorUnidad;
	}
	public int buscarUbicacion(String ubicacion)
	{
		int rta=-1;
		for (int i = 0; i < distribucion.size()&&rta==-1; i++) 
		{
			Object[] iOb=distribucion.get(i);
			if(iOb[0].toString().equals(ubicacion))
			{
				rta=i;
			}
			
		}
		return rta;
	}
	public void nuevaUbicacion(String ubica)
	{
		Object[] ob=new Object[2];
		ob[0]=ubica;
		ob[1]=0;
		distribucion.add(ob);
	}

}
