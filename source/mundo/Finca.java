package mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;





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
	
	private String archivoLotes;
	private String archivoEmpleados;
	private String archivoMaquinas;
	private String archivoInsumos;
	
	private LocalDate fechaUltimoCierreEmpleados;
	
	private LocalDate fechaUltimoCierreLotes;
	
	@SuppressWarnings("resource")
	public Finca(File fileRutaFinca,File fileRutaLotes,File fileRutaEmpleados,File fileRutaMaquinas,File fileRutaInsumos) throws Exception
	{
		archivoFinca=fileRutaFinca.getAbsolutePath();
		
		//File file=new File(archivoFinca);
		if(fileRutaFinca.exists())
		{
			try
			{
				

				ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileRutaFinca));
				Finca finca=(Finca)ois.readObject();
				
				
				System.out.println("LotesCSV");
				
				//Properties infoLotes=validarProperties(fileRutaLotes, "Lotes");
				archivoLotes=fileRutaLotes.getAbsolutePath();
		        cargarLotesCSV(archivoLotes);
		        
				
				
				
				System.out.println("MaquinasCSV");
				
				//Properties infoMaquinas=validarProperties(fileRutaMaquinas, "Maquinas");
				archivoMaquinas=fileRutaMaquinas.getAbsolutePath();
		        cargarMaquinasCSV(archivoMaquinas);
		        
				
			
		        
				System.out.println("EmpleadosCSV");
				//Properties infoEmpleados=validarProperties(fileRutaEmpleados, "Empleados");
				archivoEmpleados=fileRutaEmpleados.getAbsolutePath();
				cargarEmpleadosCSV(archivoEmpleados);
		        
				
				
				System.out.println("InsumosCSV");
				
		        //Properties infoInsumos=validarProperties(fileRutaInsumos, "Insumos");
		        archivoInsumos=fileRutaInsumos.getAbsolutePath();
		        cargarInsumosCSV(archivoInsumos);
		        
				
				
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
	public Properties validarProperties(File valid,String queTipo) throws Exception
	{
		Properties info=new Properties();
		
        FileInputStream in = new FileInputStream( valid );
        
        try
        {
          info.load(in);
          
          
            in.close( );
            
        }
        catch( Exception e )
        {
            throw new Exception( "Formato invalido " +queTipo);
        }
        return info;
        
        
	}
	public void cargarLotesCSV(String rutaLotesCSV)
	{
		lotes=new ArrayList<Lote>();
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaLotesCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{

				nuevoLote(linea[1], linea[2], Double.parseDouble(linea[3]), Double.parseDouble(linea[4]));
				
				
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Lotes");
		}
	}
	public void cargarLotes(Properties infoLotes) 
	{
		lotes=new ArrayList<Lote>();
		String total=infoLotes.getProperty("total");
		
		int intTotal=Integer.parseInt(total);
		System.out.println(intTotal);
		
		for(int i=1;i<=intTotal;i++)
		{
			
			String loteInfo=infoLotes.getProperty("lote."+i);
			
			String[] dataLote=loteInfo.split("[@]");
			
			
			for(int j=0;j<dataLote.length;j++)
			{
				nuevoLote(dataLote[0], dataLote[1], Double.parseDouble(dataLote[2]), Double.parseDouble(dataLote[3]));
			}
		}
			
	}
	public void cargarEmpleadosCSV(String rutaEmpleadosCSV)
	{
		empleados=new ArrayList<Empleado>();

		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaEmpleadosCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{

				String[] date=linea[3].split("[/]");
				
				LocalDate fecha= LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
				nuevoEmpleado( linea[1], linea[2],fecha, linea[4], linea[5], linea[6],linea[7], Double.parseDouble(linea[8]));
				
				
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Empleados");
		}
	}
	public void cargarEmpleados(Properties infoEmpleados) 
	{
		empleados=new ArrayList<Empleado>();
		String total=infoEmpleados.getProperty("total");
		
		int intTotal=Integer.parseInt(total);
		System.out.println(intTotal);
		for(int i=1;i<=intTotal;i++)
		{
			
			String empleadoInfo=infoEmpleados.getProperty("empleado."+i);
			
			String[] dataEmpleado=empleadoInfo.split("[@]");
			
			
			for(int j=0;j<dataEmpleado.length;j++)
			{
				String[] date=dataEmpleado[2].split("[/]");
				System.out.println(dataEmpleado[3]);
				LocalDate fecha= LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
				nuevoEmpleado(dataEmpleado[0], dataEmpleado[1], fecha, dataEmpleado[3], dataEmpleado[4], dataEmpleado[5], dataEmpleado[6], Double.parseDouble(dataEmpleado[7]));
				
			}
		}
			
	}
	public void cargarMaquinasCSV(String rutaMaquinasCSV)
	{
		maquinas=new ArrayList<Maquina>();
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaMaquinasCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{
				nuevaMaquina(linea[1], Integer.parseInt(linea[2]));
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Maquinas");
		}
	}
	public void cargarMaquinas(Properties infoMaquinas)
	{
		maquinas=new ArrayList<Maquina>();
		String total=infoMaquinas.getProperty("total");
		
		int intTotal=Integer.parseInt(total);
		
		for(int i=1;i<=intTotal;i++)
		{
			
			String maquinaInfo=infoMaquinas.getProperty("maquina."+i);
			
			String[] dataMaquina=maquinaInfo.split("[@]");
			
			
			for(int j=0;j<dataMaquina.length;j++)
			{
				
				nuevaMaquina(dataMaquina[0],Integer.parseInt(dataMaquina[1]));
				
			}
		}
			
	}
	public void cargarInsumosCSV(String rutaInsumosCSV)
	{
		insumos=new ArrayList<Insumo>();

		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaInsumosCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{

				nuevoInsumo(linea[1], Double.parseDouble(linea[2]), Double.parseDouble(linea[3]), linea[4]);
				
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Insumo");
		}
	}
	public void cargarInsumos(Properties infoInsumos)
	{
		insumos=new ArrayList<Insumo>();
		String total=infoInsumos.getProperty("total");
		
		int intTotal=Integer.parseInt(total);
		
		for(int i=1;i<=intTotal;i++)
		{
			
			String insumoInfo=infoInsumos.getProperty("insumo."+i);
			
			String[] dataInsumo=insumoInfo.split("[@]");
			
			
			for(int j=0;j<dataInsumo.length;j++)
			{
				nuevoInsumo(dataInsumo[0], Double.parseDouble(dataInsumo[1]), Double.parseDouble(dataInsumo[2]), dataInsumo[3]);
				
				
			}
		}
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
	
	public void nuevoServicio(LocalDate nFecha, String nTipo, Lote lote, String[] nMaquinas, String[] nEmpleados,String[] nInsumos, double nCostoXArea)
	{
		double areaLote=lote.darArea();
		//maquinas
		ArrayList<Maquina> maquinasUsadas=new ArrayList<>();
		for (int i = 0; i < nMaquinas.length; i++) 
		{
			String iMaquina=nMaquinas[i];
			maquinasUsadas.add(buscarMaquinaNombre(iMaquina));
			
		}
		 
		//empleados
		ArrayList<Empleado> empleadosUsados=new ArrayList<>();
		for (int i = 0; i < nEmpleados.length; i++) 
		{
			String iEmpleado=nEmpleados[i];
			empleadosUsados.add(buscarEmpleadoNombre(iEmpleado));
			
		}
		
		ArrayList<Insumo> insumosUsados=new ArrayList<>();
		for (int i = 0; i < nInsumos.length; i++) 
		{
			String[] iS=nInsumos[i].split("@");
			String nombre=iS[0];
			double dosis=Double.parseDouble((iS[1]));
			
			double cantidad=dosis*areaLote;
			System.out.println(buscarInsumoIndex(nombre));
			insumos.get(buscarInsumoIndex(nombre)).registrarCompra(-cantidad , lotes.get(0).darNombre());
			insumosUsados.add(new Insumo(nombre, cantidad, insumos.get(buscarInsumoIndex(nombre)).darValorUnidad(), insumos.get(buscarInsumoIndex(nombre)).darTipoMedida(),lotes.get(0).darNombre()));
			
			
		}
		Servicio servicio=new Servicio(nFecha, nTipo, lote,lote.darCultivoActual(), maquinasUsadas, empleadosUsados,insumosUsados, nCostoXArea);
		
		lote.darCultivoActual().agregarServicio(servicio);
		for(int m=0;m<maquinasUsadas.size();m++)
		{
			Maquina mMaq=maquinasUsadas.get(m);
			mMaq.agregarTrabajo(servicio);
		}
		for(int e=0;e<empleadosUsados.size();e++)
		{
			Empleado eEmp=empleadosUsados.get(e);
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
	public void nuevoInsumo(String nNombre,double nCantidad,double nValorUnidad,String nTipoMedida)
	{
		//se asume que la ubicacion es la florencia
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
	public ArrayList<String[]> liquidarEmpleados(LocalDate fechaCierre)
	{
		ArrayList<String[]> rta=new ArrayList<>();
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
				linea[1]="1.El total de horas es:"+totalTrabajado+";";
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
				linea[1]="2.El total de horas es:"+totalTrabajado+";";
				rta.add(linea);
				
			}
		}
		fechaUltimoCierreEmpleados=fechaCierre;
		//se retonra un arraylist donde cada index es una List[] donde List[0]=Nombre EMpleado
		//List[1]= mensaje total horas
		return rta;
	}
	public void agregarGastoMaquina(String maquinaNombre,Servicio servi, double horometro)
	{
		buscarMaquinaNombre(maquinaNombre).agregarGasto(servi, horometro);
	}
	
	//version larga, muchas simplificaciones se pueden hacer!!!!
	//TODO
	public Object[] generarReporteCierre()
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
		Object[] rta=new Object[2];
		rta[0]=proovedorReporte;
		rta[1]=proovedorDeudaReporte;
		return rta;
	}
	
	
	
	
	

}
