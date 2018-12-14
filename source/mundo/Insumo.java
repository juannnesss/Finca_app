package mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class Insumo implements Serializable
{
	private static final long serialVersionUID=700L;
	
	private String nombre;
	
	private double cantidadTotal;
	private ArrayList<Object[]> distribucion;
	
	private double valorUnidad;
	
	private String tipoCantidadMedida;
	
	public Insumo(String nNombre,double nCantidad, double nValorUnidad, String nTipoMedida,String ubicacionInicial)
	{
		nombre=nNombre;
		cantidadTotal=nCantidad;
		valorUnidad=nValorUnidad;
		tipoCantidadMedida=nTipoMedida;
		//manejo de la distribucion del insumo, inicializando 
		distribucion=new ArrayList<>();
		Object[] ob=new Object[2];
		ob[0]=ubicacionInicial;
		ob[1]=cantidadTotal;
		distribucion.add(ob);
		
	}
	public String darNombre()
	{
		return nombre;
	}
	public double darCantidadTotal()
	{
		return cantidadTotal;
	
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
