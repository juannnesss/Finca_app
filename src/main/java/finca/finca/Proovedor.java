package finca.finca;

public class Proovedor 
{
	private static final long serialVersionUID=900L;
	private String id;
	private String nombre;
	
	private String nIT;
	
	public Proovedor(int nID,String nNombre,String nNIT) 
	{
		id="PR"+nID;
		nombre=nNombre;
		nIT=nNIT;
	}
	public String darID()
	{
		return id;
	}
	public String darNombre()
	{
		return nombre;
	}
	public String darNIT()
	{
		return nIT;
	}
	

}
