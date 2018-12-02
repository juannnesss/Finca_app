package mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaz.InterfazFinca;

public class Finca implements Serializable
{
	private static final long serialVersionUID=100L;
	
	private ArrayList<Lote> lotes;
	
	private ArrayList<Maquina> maquinas;
	
	private ArrayList<Empleado> empleados;
	

	private ArrayList<Servicio> servicios;

	
	private ArrayList<Insumo> insumos;
	
	private String archivoFinca;
	
	public Finca(File fileRutaFinca) throws Exception
	{
		archivoFinca=fileRutaFinca.getAbsolutePath();
		//File file=new File(archivoFinca);
		if(fileRutaFinca.exists())
		{
			try
			{
				

				ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileRutaFinca));
				Finca finca=(Finca)ois.readObject();
				lotes=(ArrayList<Lote>)finca.darLotes();
				maquinas=(ArrayList<Maquina>)finca.darMaquinas();
				empleados=(ArrayList<Empleado>)finca.darEmpleados();
				//arreglo del cambio de tipo en la cantidad de los isumos, de int a double
				insumos=(ArrayList<Insumo>)finca.darInsumos();
				
				servicios=(ArrayList<Servicio>)finca.darServicios();
				
				
				
				
				ois.close();
				
				
			}
			catch (Exception e) 
			{
				throw new Exception("Error con la deserializacion");
			}
			
		}
		else
		{
			lotes=new ArrayList<Lote>();
			maquinas=new ArrayList<Maquina>();
			empleados=new ArrayList<Empleado>();
			servicios=new ArrayList<Servicio>();
			insumos=new ArrayList<Insumo>();
			
		}
	}
	public Finca(String nRuta,ArrayList<Lote> nLotes,ArrayList<Maquina> nMaquinas,ArrayList<Empleado> nEmpleados, ArrayList<Servicio> nServicios,ArrayList<Insumo> nInsumos)
	{
		lotes=nLotes;
		maquinas=nMaquinas;
		empleados=nEmpleados;
		servicios=nServicios;
		insumos=nInsumos;
		archivoFinca=nRuta;
	}
	public void salvarFinca() throws Exception
{
	try
	{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(archivoFinca));
	

		oos.writeObject(new Finca(archivoFinca, lotes, maquinas, empleados, servicios,insumos));
	

		oos.close();
	}
	catch (Exception e) 
	{
		throw new Exception("salvando error");
		// TODO: handle exception
	}
}

	public ArrayList<Servicio> darServicios() {
		// TODO Auto-generated method stub
		return servicios;
	}

	public ArrayList<Empleado> darEmpleados() {
		// TODO Auto-generated method stub
		return empleados;
	}

	public ArrayList<Maquina> darMaquinas() {
		// TODO Auto-generated method stub
		return maquinas;
	}

	public ArrayList<Lote> darLotes() {
		// TODO Auto-generated method stub
		return lotes;
	}
	public ArrayList<Insumo> darInsumos()
	{
		return insumos;
	}
	
	public ArrayList<Cultivo> darCultivosActuales()
	{
		ArrayList<Cultivo> resp=new ArrayList<>();
		for(int i=0;i<lotes.size();i++)
		{
			if(lotes.get(i).darCultivoActual()!=null)
			{
				resp.add(lotes.get(i).darCultivoActual());
			}
		}
		return resp;
	}
	public  Insumo darInsumoPorIndex(int index)
	{
		return insumos.get(index);
	}
	public void nuevoEmpleado(String nNombre, String nCedula)
	{
		if(existeEmpleado(nNombre)==false)
		{
			Empleado empleado=new Empleado(nNombre, nCedula, 0);
			empleados.add(empleado);
			
		}
		
	}
	public void nuevaMaquina(String nNombre,int nHoras)
	{
		if(existeMaquina(nNombre)==false)
		{
			Maquina maquina=new Maquina(nNombre,nHoras);
			maquinas.add(maquina);
		}
		
	}
	public void nuevoLote(String nNombre, String nUbicacion, double nCoste, double nArea)
	{
		if(existeLote(nNombre)==false)
		{
			Lote lote=new Lote(nNombre, nUbicacion, nCoste, nArea);
			lotes.add(lote);
		}
		
	}
	
	public void nuevoServicio(LocalDate nFecha, String nTipo, Lote lote, ArrayList<Maquina> nMaquinas, ArrayList<Empleado> nEmpleados,String[] nInsumos, double nCostoXArea)
	{
		double areaLote=lote.darArea();
		ArrayList<Insumo> usados=new ArrayList<>();
		for (int i = 0; i < nInsumos.length; i++) 
		{
			String[] iS=nInsumos[i].split("@");
			String nombre=iS[0];
			double dosis=Double.parseDouble((iS[1]));
			
			double cantidad=dosis*areaLote;
			System.out.println(buscarInsumoIndex(nombre));
			insumos.get(buscarInsumoIndex(nombre)).registrarCompra((-cantidad));
			usados.add(new Insumo(nombre, cantidad, insumos.get(buscarInsumoIndex(nombre)).darValorUnidad(), insumos.get(buscarInsumoIndex(nombre)).darTipoMedida()));
			
			
		}
		Servicio servicio=new Servicio(nFecha, nTipo, lote,lote.darCultivoActual(), nMaquinas, nEmpleados,usados, nCostoXArea);
		
		lote.darCultivoActual().agregarServicio(servicio);
		for(int m=0;m<nMaquinas.size();m++)
		{
			Maquina mMaq=nMaquinas.get(m);
			mMaq.agregarTrabajo(servicio);
		}
		for(int e=0;e<nEmpleados.size();e++)
		{
			Empleado eEmp=nEmpleados.get(e);
			eEmp.agregarServicio(servicio);
		}
		servicios.add(servicio);
	}
	
	public void nuevoCultivo(String nNombre, String nProducto, LocalDate nPeriodo, LocalDate corta)
	{
		
		Lote lote=buscarLoteNombre(nNombre);
		
		Cultivo cultivo=new Cultivo(nProducto, nPeriodo,lote);
		lote.actualizarCultivoActual(cultivo,corta);
	}
	public void nuevoInsumo(String nNombre,int nCantidad,double nValorUnidad,String nTipoMedida)
	{
		if(existeInsumo(nNombre)==false)
		{
			insumos.add(new Insumo(nNombre, nCantidad,nValorUnidad,nTipoMedida));
 
		}
	}
	
	public Lote buscarLoteNombre(String nombre)
	{
		Lote lote=null;
		for(int i=0;i<lotes.size();i++)
		{
			if(lotes.get(i).darNombre().equals(nombre))
			{
				lote=lotes.get(i);
			}
		}
		return lote;
	}
	
	public Maquina buscarMaquinaNombre(String nombre)
	{
		Maquina resp=null;
		for(int i=0;i<maquinas.size();i++)
		{
			if(maquinas.get(i).darNombre().equals(nombre))
			{
				resp=maquinas.get(i);
			}
		}
		return resp;
	}
	
	public Empleado buscarEmpleadoNombre(String nombre)
	{
		Empleado resp=null;
		for(int i=0;i<empleados.size();i++)
		{
			if(empleados.get(i).darNombre().equals(nombre))
			{
				resp=empleados.get(i);
			}
		}
		return resp;
	}
	
	public int buscarServicioOrdenAgregado(String tipo, Lote lote)
	{
		int rta=-1;
		boolean encontrado=false;
		for (int i=servicios.size()-1;i>-1&&encontrado==false;i--)
		{
			Servicio iServicio=servicios.get(i);
			if(iServicio.darCultivo().darLote().equals(lote))
			{
				if(iServicio.darTipo().equals(tipo))
				{
					rta=i;
				}
			}
		}
		return rta;
	}
	public int buscarInsumoIndex(String nNombre)
	{
		int rta=-1;
		boolean encontrado=false;
		for(int i=0;i<insumos.size()&&(!encontrado);i++)
		{
			if(insumos.get(i).darNombre().equals(nNombre))
			{
				
				rta=i;
				encontrado=true;
			}
		}
		return rta;
	}
	public boolean existeEmpleado(String nNombre)
	{
		
		return (buscarEmpleadoNombre(nNombre)!=null)?true:false;
	}
	public boolean existeMaquina(String nNombre)
	{
		
		return (buscarMaquinaNombre(nNombre)!=null)?true:false;
	}
	public boolean existeLote(String nNombre)
	{
		
		return (buscarLoteNombre(nNombre)!=null)?true:false;
	}
	public boolean existeInsumo(String nNombre)
	{
		
		return (buscarInsumoIndex(nNombre)!=-1)?true:false;
	}
	
	public void eliminarServicio(String nombreLote,String tipo)
	{
		Lote lote=buscarLoteNombre(nombreLote);
		int index=buscarServicioOrdenAgregado(tipo, lote);
		Servicio servicio=servicios.get(index);
		ArrayList<Maquina> maqui=servicio.darMaquinas();
		ArrayList<Empleado> emple=servicio.darEmpleados();
		ArrayList<Insumo> insu=servicio.darInsumos();
		lote.eliminarServicio(servicio);
		for(int i=0;i<maqui.size();i++)
		{
			Maquina iMaquina=maqui.get(i);
			buscarMaquinaNombre(iMaquina.darNombre()).eliminarTrabajo(servicio);
		}
		for(int i=0;i<emple.size();i++)
		{
			Empleado iEmpleado=emple.get(i);
			buscarEmpleadoNombre(iEmpleado.darNombre()).eliminarServicio(servicio);
					
		}
		for (int i = 0; i < insu.size(); i++) 
		{
			Insumo iInsu=insu.get(i);
			insumos.get(buscarInsumoIndex((iInsu.darNombre()))).registrarCompra(iInsu.darCantidad());
			
		}
		servicios.remove(index);
		
		
	}
	public void eliminarEmpleadoPorNombre(String nombre)
	{
		boolean x=true;
		for(int i=0;i<empleados.size()&&x;i++)
		{
			Empleado iEmpleado=empleados.get(i);
			if(iEmpleado.darNombre().equals(nombre))
			{
				empleados.remove(i);
				x=false;
			}
		}
	}
	public void eliminarMaquina(String nombre)
	{
		boolean x=true;
		for(int i=0;i<maquinas.size()&&x;i++)
		{
			Maquina iMaquina=maquinas.get(i);
			if(iMaquina.darNombre().equals(nombre))
			{
				maquinas.remove(i);
				x=false;
			}
		}
	}
	public void eliminarLote(String nombre)
	{
		boolean x=true;
		for(int i=0;i<lotes.size()&&x;i++)
		{
			Lote iLote=lotes.get(i);
			if(iLote.darNombre().equals(nombre))
			{
				lotes.remove(i);
				x=false;
			}
		}
	}
	public void eliminarInsumo(String nombre)
	{
		int index=buscarInsumoIndex(nombre);
		if(index!=-1)
		{
			insumos.remove(index);
		}
	}
	public Cultivo darCultivoPorLote(String producto, String nombreLote)
	{
		Lote lote=buscarLoteNombre(nombreLote);
		if(lote.darCultivoActual().darProducto().equals(producto))
		{
			return lote.darCultivoActual();
		}
		else
		{
			return null;
		}
	}
	public boolean asignarProduccion(String nomLote,double produccion)
	{
		boolean rta=false;
		Lote lote=buscarLoteNombre(nomLote);
		if(lote!=null)
		{
			lote.asignarProduccionLote(produccion);
			rta=true;
		}
		return rta;
	}
	
	
	
	
	
	

}
