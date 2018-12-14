package mundo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;



public class Finca implements Serializable
{
	private static final long serialVersionUID=100L;
	
	private ArrayList<Lote> lotes;
	
	private ArrayList<Maquina> maquinas;
	
	private ArrayList<Empleado> empleados;
	

	private ArrayList<Servicio> servicios;

	
	private ArrayList<Insumo> insumos;
	
	private ArrayList<String> proovedores;
	
	private ArrayList<Compra> compras;
	
	private String archivoFinca;
	
	private LocalDate fechaUltimoCierreEmpleados;
	
	private LocalDate fechaUltimoCierreLotes;
	
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
				fechaUltimoCierreEmpleados=finca.darFechaUltimoCierreEmpleados();
				fechaUltimoCierreLotes=finca.darFechaUltimoCierreLotes();
				proovedores=finca.darProovedores();
				compras=finca.darCompras();
				
				
				
				
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
			//se crea el lote La Florencia, sede y ubicacion principal
			Lote florencia28=new Lote("Florencia28", "Finca La Florencia", 0, 28);
			lotes.add(florencia28);
			maquinas=new ArrayList<Maquina>();
			empleados=new ArrayList<Empleado>();
			servicios=new ArrayList<Servicio>();
			insumos=new ArrayList<Insumo>();
			fechaUltimoCierreEmpleados=null;
			fechaUltimoCierreLotes=null;
			proovedores=new ArrayList<String>();
			compras= new ArrayList<Compra>();
			
		}
	}
	
	

	public Finca(String nRuta,ArrayList<Lote> nLotes,ArrayList<Maquina> nMaquinas,ArrayList<Empleado> nEmpleados,
			ArrayList<Servicio> nServicios,ArrayList<Insumo> nInsumos,ArrayList<String> nProovedores,ArrayList<Compra> nCompras,LocalDate nFechaUltimoCierreEmpleados,LocalDate nFechaUltimoCierreLotes)
	{
		lotes=nLotes;
		maquinas=nMaquinas;
		empleados=nEmpleados;
		servicios=nServicios;
		insumos=nInsumos;
		archivoFinca=nRuta;
		fechaUltimoCierreEmpleados=nFechaUltimoCierreEmpleados;
		fechaUltimoCierreLotes=nFechaUltimoCierreLotes;
		proovedores=nProovedores;
		compras=nCompras;
	}
	public void salvarFinca() throws Exception
{
	try
	{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(archivoFinca));
	

		oos.writeObject(new Finca(archivoFinca, lotes, maquinas, empleados, servicios,insumos,proovedores,compras,fechaUltimoCierreEmpleados,fechaUltimoCierreLotes));
	

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
	public ArrayList<String> darProovedores()
	{
		return proovedores;
	}
	public ArrayList<Compra> darCompras() 
	{
		
		return compras;
	}
	public LocalDate darFechaUltimoCierreEmpleados() 
	{
		// TODO Auto-generated method stub
		return fechaUltimoCierreEmpleados;
	}
	public LocalDate darFechaUltimoCierreLotes()
	{
		return fechaUltimoCierreLotes;
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
	public void nuevoEmpleado(String nNombre, String nCedula,LocalDate nFecha,String nEps,String zapatos
			,String pantalon,String camiseta,double nSalario)
	{
		if(existeEmpleado(nNombre)==false)
		{
			Empleado empleado=new Empleado(nNombre, nCedula, nFecha,nEps, zapatos
					 ,pantalon,camiseta, nSalario,0);
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
			insumos.get(buscarInsumoIndex(nombre)).registrarCompra(-cantidad , lotes.get(0).darNombre());
			usados.add(new Insumo(nombre, cantidad, insumos.get(buscarInsumoIndex(nombre)).darValorUnidad(), insumos.get(buscarInsumoIndex(nombre)).darTipoMedida(),lotes.get(0).darNombre()));
			
			
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
			insumos.add(new Insumo(nNombre, nCantidad,nValorUnidad,nTipoMedida,lotes.get(0).darNombre()));
 
		}
	}
	public boolean nuevoProovedor(String proovedor) 
	{
		boolean rta=false;
		if(buscarProovedorIndex(proovedor)==-1)
		{
			proovedores.add(proovedor);
			rta=true;
		}
		return rta;
	}
	public void nuevaCompra(LocalDate date,Insumo[] insu,String prove)
	{
		//se agregan las cantidades que indica la compra
		for (int i = 0; i < insu.length; i++) 
		{
			Insumo iIns=insu[i];
			// se asume que la ubicacion es La Florencia28
			insumos.get(buscarInsumoIndex(iIns.darNombre())).registrarCompra(iIns.darCantidadTotal(), lotes.get(0).darNombre());
			
		}
		Compra compra=new Compra(date, insu, prove);
		
		compras.add(compra);
		
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
	public int buscarProovedorIndex(String proveedor)
	{
		int rta=-1;
		
		for(int i=0;i<proovedores.size();i++)
		{
			String iPro=proovedores.get(i);
			if(iPro.equals(proveedor))
			{
				rta=i;
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
			//cuando los insumos que sea borrados tengan otras localizaciones 
			insumos.get(buscarInsumoIndex((iInsu.darNombre()))).registrarCompra(iInsu.darCantidadTotal(),lotes.get(0).darNombre());
			
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
	public ArrayList liquidarEmpleados(LocalDate fechaCierre)
	{
		ArrayList rta=new ArrayList<>();
		if(fechaUltimoCierreEmpleados==null)
		{
			for (int i = 0; i < empleados.size(); i++) 
			{
				Empleado iEmple=empleados.get(i);
				ArrayList<Servicio> iServ=iEmple.darServiciosPrestados();
				String[] linea=new String[2];
				linea[0]=iEmple.darNombre();
				double totalTrabajado=0;
				for(int s=0;s<iServ.size();i++)
				{
					//codigo para sumar las horas
					totalTrabajado+=1;
				}
				linea[1]=totalTrabajado+"";
				rta.add(linea);
				
			}
			
		}
		else
		{
			for (int i = 0; i < empleados.size(); i++) 
			{
				Empleado iEmple=empleados.get(i);
				ArrayList<Servicio> iServ=iEmple.darServiciosPrestados();
				String[] linea=new String[2];
				linea[0]=iEmple.darNombre();
				double totalTrabajado=0;
				boolean seLLegoAFecha=false;
				for(int s=iServ.size()-1;s>-1&&!(seLLegoAFecha);i--)
				{
					Servicio ise=iServ.get(s);
					if(ise.darFecha().isAfter(fechaUltimoCierreEmpleados))
					{
						totalTrabajado+=1;
					}
					else
					{
						seLLegoAFecha=true;
					}
				}
				linea[1]="El total de horas es:"+totalTrabajado+";";
				rta.add(linea);
				
			}
		}
		fechaUltimoCierreEmpleados=fechaCierre;
		return rta;
	}
	public void agregarGastoMaquina(String maquinaNombre,Servicio servi, double horometro)
	{
		buscarMaquinaNombre(maquinaNombre).agregarGasto(servi, horometro);
	}
	
	//version larga, muchas simplificaciones se pueden hacer!!!!
	//TODO
	public void generarReporteCierre()
	{
		// Que clase de reporte se requiere de los insumos al cerrar
		ArrayList<Insumo> reporteInsumos= new ArrayList<>();
		ArrayList<String> proovedorReporte=new ArrayList<>();
		ArrayList<Double> proovedorDeudaReporte=new ArrayList<>();
		
		if(darFechaUltimoCierreLotes()==null)
		{
			
			
			
			for (int i = compras.size()-1; i >-1; i--) 
			{
				Compra iCompra=compras.get(i);
				LocalDate iDate=iCompra.darFecha();
				Insumo[] iInsumos=iCompra.darInsumos();
				String iProovedor=iCompra.darProovedor();
				double iCostoCompra=iCompra.darTotalCompra();
				if(proovedorReporte.size()==0)
				{
					proovedorReporte.add(iProovedor);
					proovedorDeudaReporte.add(iCostoCompra);
				}
				else
				{
					Iterator<String> iteraProveedorReporte=proovedorReporte.iterator();
					//Index para manejar el prooovedor actual y boolean para encontrarlo 
					int index=0;
					boolean encontrado=false;
					//System.out.println("revisar iterador de un arraylist (compra) vacio");
					while(iteraProveedorReporte.hasNext()&&!(encontrado))
					{
						String proovedorRepor=iteraProveedorReporte.next();
						if(proovedorRepor.equals(iProovedor))
						{
							double deudaAnterior=proovedorDeudaReporte.get(index);
							proovedorDeudaReporte.set(index, deudaAnterior+=iCostoCompra);
							encontrado=true;
							
						}
					    index+=1;
					    System.out.println(index);
					}
					
					//nuevo proovedor 
					System.out.println("Index de nuevo Proovedor= "+index+", .size()= "+proovedorReporte.size());
					if(!encontrado)
					{
						proovedorReporte.add(iProovedor);
						proovedorDeudaReporte.add(iCostoCompra);
					}
					
				}
				
				
				
				
			}
			//revisar que el tamano de los arraylist son iguales
			System.out.println("Tamaño de Proove: "+proovedorReporte.size()+", Tamaño de doubles: "+proovedorDeudaReporte.size());
		}
		else
		{
			//el otro caso, cuando ya se habia hecho un cierre antesç
			
			
			boolean llegamosFecha=false;
			for (int i = compras.size()-1; i >-1&&!llegamosFecha; i--) 
			{
				Compra iCompra=compras.get(i);
				LocalDate iDate=iCompra.darFecha();
				Insumo[] iInsumos=iCompra.darInsumos();
				String iProovedor=iCompra.darProovedor();
				double iCostoCompra=iCompra.darTotalCompra();
				if(iDate.isBefore(darFechaUltimoCierreLotes()))
				{
					llegamosFecha=true;
				}
				else
				{
					if(proovedorReporte.size()==0)
					{
						proovedorReporte.add(iProovedor);
						proovedorDeudaReporte.add(iCostoCompra);
					}
					else
					{
						Iterator<String> iteraProveedorReporte=proovedorReporte.iterator();
						//Index para manejar el prooovedor actual y boolean para encontrarlo 
						int index=0;
						boolean encontrado=false;
						//System.out.println("revisar iterador de un arraylist (compra) vacio");
						while(iteraProveedorReporte.hasNext()&&!(encontrado))
						{
							String proovedorRepor=iteraProveedorReporte.next();
							if(proovedorRepor.equals(iProovedor))
							{
								double deudaAnterior=proovedorDeudaReporte.get(index);
								proovedorDeudaReporte.set(index, deudaAnterior+=iCostoCompra);
								encontrado=true;
								
							}
						    index+=1;
						    System.out.println(index);
						}
						
						//nuevo proovedor 
						System.out.println("CIERRE 2. Index de nuevo Proovedor= "+index+", .size()= "+proovedorReporte.size());
						if(!encontrado)
						{
							proovedorReporte.add(iProovedor);
							proovedorDeudaReporte.add(iCostoCompra);
						}
					}
					
				}
			}
		}
		
	}
	
	
	
	
	

}
