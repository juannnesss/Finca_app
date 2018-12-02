package mundo;

import java.io.Serializable;

public class Insumo implements Serializable
{
	private static final long serialVersionUID=700L;
	
	private String nombre;
	
	private double cantidad;
	
	private double valorUnidad;
	
	private String tipoCantidadMedida;
	
	public Insumo(String nNombre,double nCantidad, double nValorUnidad, String nTipoMedida)
	{
		nombre=nNombre;
		cantidad=nCantidad;
		valorUnidad=nValorUnidad;
		tipoCantidadMedida=nTipoMedida;
		
	}
	public String darNombre()
	{
		return nombre;
	}
	public double darCantidad()
	{
		return cantidad;
	
	}
	public String darTipoMedida()
	{
		return tipoCantidadMedida;
	}
	public void registrarCompra(double nuevo)
	{
		cantidad+=nuevo;
	}
	public double darValorUnidad() 
	{
		return valorUnidad;
	}
	

}
