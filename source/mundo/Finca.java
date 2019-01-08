package mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import interfaz.EmpleadosVentana;





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
	
	private String archivoFincaProperties;
	
	private String archivoLotes;
	private String archivoEmpleados;
	private String archivoMaquinas;
	private String archivoInsumos;
	private String archivoProovedores;
	private String archivoServicios;
	private String archivoCompras;
	private String archivoCultivos;
	
	private LocalDate fechaUltimoCierreEmpleados;
	
	private LocalDate fechaUltimoCierreLotes;
	
	@SuppressWarnings("resource")
	public Finca(File fileRutaProperties,File fileRutaLotes,File fileRutaEmpleados,File fileRutaMaquinas,File fileRutaInsumos,File fileRutaProovedores
			,File fileRutaServicios,File fileRutaCompras,File fileRutaCultivos) throws Exception
	{
		
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
        
      //proovedores=finca.darProovedores();
        
      archivoProovedores=fileRutaProovedores.getAbsolutePath();
      cargarProovedoresCSV(archivoProovedores);
		
		
		
    //cultivosssssssss
      archivoCultivos=fileRutaCultivos.getAbsolutePath();
      cargarCultivosCSV(archivoCultivos);
      
      
      archivoServicios=fileRutaServicios.getAbsolutePath();
      cargarServiciosCSV(archivoServicios);
      
    //compras=finca.darCompras();
      archivoCompras=fileRutaCompras.getAbsolutePath();
      cargarComprasCSV(archivoCompras);
      
		//File file=new File(archivoFinca);
		
      archivoFincaProperties=fileRutaProperties.getAbsolutePath();
     System.out.println("preproper");
      Properties prope=validarProperties(fileRutaProperties);
      System.out.println(prope.getProperty("fechaUltimoCierreEmpleados(dd/MM/yyyy)"));
      
			
			String[] cierreEmple=prope.getProperty("fechaUltimoCierreEmpleados(dd/MM/yyyy)").split("[/]");
			String[] cierreLotes=prope.getProperty("fechaUltimoCierreLotes(dd/MM/yyyy)").split("[/]");
			
			System.out.println(cierreEmple[0]);
			System.out.println(cierreEmple[1]);
			LocalDate dateEmple=LocalDate.of(Integer.parseInt(cierreEmple[2]), Integer.parseInt(cierreEmple[1]), Integer.parseInt(cierreEmple[0]));
			LocalDate dateLot=LocalDate.of(Integer.parseInt(cierreLotes[2]), Integer.parseInt(cierreLotes[1]), Integer.parseInt(cierreLotes[0]));
			
			fechaUltimoCierreEmpleados=dateEmple;
			fechaUltimoCierreLotes=dateLot;
			
			
		
	}
	
	

	
	public Properties validarProperties(File valid) throws Exception
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
            throw new Exception( "Formato invalido ");
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
				System.out.println(linea[1]);
				
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Empleados");
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
			reader.close();
			
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Insumo");
		}
	}
	public void cargarProovedoresCSV(String rutaProovedoresCSV)
	{
		proovedores=new ArrayList<>();
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaProovedoresCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{
				nuevoProovedor(linea[1]);
			}
		}
		catch (Exception e) 
			{
				System.out.println("Error en el CSV Proovedor");
			}
	}
	public void cargarServiciosCSV(String rutaServiciosCSV)
	{
		servicios=new ArrayList<Servicio>();
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaServiciosCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			
			String[] linea=csvReader.readNext();
			
			while((linea=csvReader.readNext())!=null)
			{
				String[] date=linea[1].split("[/]");
			

				LocalDate fecha= LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
				
				
				
				String idLote=linea[3].substring(2);
								
				String idMaquina=linea[5].substring(2);
				String[] idMaquinaList=new String[1];
				idMaquinaList[0]=idMaquina;
				
				String idEmpleado=linea[6].substring(2);
				String[] idEmpleadoList=new String[1];
				idEmpleadoList[0]=idEmpleado;
				
				String[] infoInsumos=linea[7].split(":");
				String idyDosisInsumo=infoInsumos[0];
				
				String[] idInsumoList=new String[1];
				idInsumoList[0]=idyDosisInsumo;
				
				double nCostoXArea=Double.parseDouble(linea[8]);
				
				
				nuevoServicio(fecha, linea[2], buscarLoteID(Integer.parseInt(idLote)), idMaquinaList, idEmpleadoList, infoInsumos, nCostoXArea);			}
				
		}
		catch (Exception e) 
			{
				System.out.println("Error en el CSV Servicio");
			}
	}
	public void cargarComprasCSV(String rutaComprasCSV)
	{
		compras=new ArrayList<Compra>();
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaComprasCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{
				String[] date=linea[1].split("[/]");
				LocalDate fecha= LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
			
				Insumo[] insu=new Insumo[1];
				Insumo insumoUsado=buscarInsumoID(Integer.parseInt(linea[2].substring(2)));
				
				//nombre lote inicial o id lote inicial
				insu[0]=new Insumo(insumoUsado.darNombre(),Double.parseDouble(linea[3]), insumoUsado.darValorUnidad(), insumoUsado.darTipoMedida(), lotes.get(0).darNombre());
				
				String prove=linea[4];
				nuevaCompra(fecha, insu, prove);
			}
		}
		catch (Exception e) 
		{
			System.out.println("Error en el CSV Compras");
		}
	}
	public void cargarCultivosCSV(String rutaCultivosCSV)
	{
		try
		{
			Reader reader=Files.newBufferedReader(Paths.get(rutaCultivosCSV));
			CSVParser parser=new CSVParserBuilder().withSeparator('@').withIgnoreQuotations(true).build();
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();			//pasando por alto la primera fila
			
			//pasando por alto la primera fila
			
			String[] linea=csvReader.readNext();
			while((linea=csvReader.readNext())!=null)
			{
				String[] dateSiembra=linea[2].split("[/]");
				LocalDate fechaSiembra= LocalDate.of(Integer.parseInt(dateSiembra[2]), Integer.parseInt(dateSiembra[1]),Integer.parseInt(dateSiembra[0]));
			
				String[] dateCorta=linea[3].split("[/]");
				LocalDate fechaCorta= LocalDate.of(Integer.parseInt(dateCorta[2]), Integer.parseInt(dateCorta[1]),Integer.parseInt(dateCorta[0]));
			
				int index=Integer.parseInt(linea[7].substring(2));
				Cultivo cultivo=new Cultivo(linea[1],fechaSiembra, lotes.get(index));
				
				
				
				lotes.get(index).actualizarCultivoActual(cultivo, fechaCorta);
			}
		}
			catch (Exception e) 
			{
				System.out.println("Error en el CSV Cultivos");
			}
		
	}

		
	
	
	public void salvarFinca() throws Exception
{
	try
	{
		String rutaLotesFinal="./data/lotesFinal.csv";
		salvarLotesCSV(rutaLotesFinal);
		
		String rutaEmpleadosFinal="./data/empleadosFinal.csv";
		salvarEmpleadosCSV(rutaEmpleadosFinal);
		
		String rutaMaquinasFinal="./data/maquinasFinal.csv";
		salvarMaquinasCSV(rutaMaquinasFinal);
		
		String rutaInsumosFinal="./data/insumosFinal.csv";
		salvarInsumosCSV(rutaInsumosFinal);
		
		String rutaProovedoresFinal="./data/proovedoresFinal.csv";
		salvarProovedoresCSV(rutaProovedoresFinal);
		
		String rutaServiciosFinal="./data/serviciosFinal.csv";
		salvarServiciosCSV(rutaServiciosFinal);
		
		String rutaComprasFinal="./data/comprasFinal.csv";
		salvarComprasCSV(rutaComprasFinal);
		
		String rutaCultivoFinal="./data/cultivoFinal.csv";
		salvarCultivoCSV(rutaCultivoFinal);
		
		
		//PROPERTIES!!!!!!! 
		File f = new File("./data/FlorenciaProperties.properties");
        OutputStream out = new FileOutputStream( f );
        
		Properties props = new Properties();
		String fechaCierreEmpleados=fechaUltimoCierreEmpleados.getDayOfMonth()+"/"+fechaUltimoCierreEmpleados.getMonthValue()+"/"+fechaUltimoCierreEmpleados.getYear();
		String fechaCierreLotes=fechaUltimoCierreLotes.getDayOfMonth()+"/"+fechaUltimoCierreLotes.getMonthValue()+"/"+fechaUltimoCierreLotes.getYear();
        props.setProperty("fechaUltimoCierreEmpleados(dd/MM/yyyy)",fechaCierreEmpleados );
        props.setProperty("fechaUltimoCierreLotes(dd/MM/yyyy)", fechaCierreLotes);
        
        props.store(out, null);
		
		
	}
	catch (Exception e) 
	{
		throw new Exception("salvando error");
		// TODO: handle exception
	}
}
	public void salvarLotesCSV(String rutaLotesFinal) 
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaLotesFinal));
	
			@SuppressWarnings("resource")
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","UBICACION_2","COSTO_3","AREA_4"};
			csvWriter.writeNext(header);
			
			int index=0;
			for (Lote iLote : lotes) 
			{
				
				String[] iL={"LO"+index,iLote.darNombre(),iLote.darUbicacion(),Double.toString(iLote.darCosteTierra()),Double.toString(iLote.darArea())};
				csvWriter.writeNext(iL);
			
				index++;
			}
			
			csvWriter.close();
		}
		catch (Exception e) 
		{
			System.out.println("Error Salvando CSV Lotes");
		}
				
	}




	public void salvarEmpleadosCSV(String rutaEmpleadosCSV)
	{
	
		
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaEmpleadosCSV));
	
			@SuppressWarnings("resource")
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","CEDULA_2","FECHAINGRESO_3","EPS_4","ZAPATOS_5",
					"PANTALON_6","CAMISETA_7","SUELDO_8","HORAS_9"};
			csvWriter.writeNext(header);
			int index=0;
			for (Empleado iEmpleado : empleados) 
			{
				LocalDate fecha=iEmpleado.darFechaIngreso();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedString = fecha.format(formatter);
				
				
				
				String[] iE={"EM"+index,iEmpleado.darNombre(),iEmpleado.darCedula(),formattedString,
						iEmpleado.darEps(),iEmpleado.darTallas()[0],iEmpleado.darTallas()[1],iEmpleado.darTallas()[2],
								Double.toString(iEmpleado.darSalario()),Double.toString(iEmpleado.darDiasTrabajados())};
				csvWriter.writeNext(iE);
			
				index++;
			}
			
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Empleados");
		}
			
	
	
		
	}




	public void salvarMaquinasCSV(String rutaMaquinasFinal) {
		// TODO Auto-generated method stub
		
	}




	public void salvarInsumosCSV(String rutaInsumosFinal) {
		// TODO Auto-generated method stub
		
	}




	public void salvarProovedoresCSV(String rutaProovedoresFinal) {
		// TODO Auto-generated method stub
		
	}




	public void salvarServiciosCSV(String rutaServiciosFinal) {
		// TODO Auto-generated method stub
		
	}




	public void salvarComprasCSV(String rutaComprasFinal) {
		// TODO Auto-generated method stub
		
	}




	public void salvarCultivoCSV(String rutaCultivoFinal) {
		// TODO Auto-generated method stub
		
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
			String idIMaquina=nMaquinas[i];
			Maquina iMaquina=buscarMaquinaID(Integer.parseInt(idIMaquina));
			//System.out.println(iMaquina.darNombre());
			maquinasUsadas.add(iMaquina);
			
		}
		 
		//empleados
		ArrayList<Empleado> empleadosUsados=new ArrayList<>();
		for (int i = 0; i < nEmpleados.length; i++) 
		{
			String idIEmpleado=nEmpleados[i];
			Empleado iEmpleado=buscarEmpleadoID(Integer.parseInt(idIEmpleado));
			//System.out.println(iEmpleado.darNombre());
			empleadosUsados.add(iEmpleado);
			
		}
		
		ArrayList<Insumo> insumosUsados=new ArrayList<>();
		
		
		for (int i = 0; i < nInsumos.length; i++) 
		{
			String[] iS=nInsumos[i].split("[|]");
			
			
			String id=iS[0].substring(2);
			int index=Integer.parseInt(id);
			Insumo iInsumo=buscarInsumoID(index);
			
			double dosis=Double.parseDouble((iS[1]));
			
			//System.out.println(iInsumo.darNombre());
			
			double cantidad=dosis*areaLote;
			
			insumos.get(index).registrarCompra(-cantidad , lotes.get(0).darNombre());
			insumosUsados.add(new Insumo(iInsumo.darNombre(), cantidad, iInsumo.darValorUnidad(), iInsumo.darTipoMedida(),lotes.get(0).darNombre()));
			
			
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
	public Lote buscarLoteID(int ind)
	{
		return lotes.get(ind);
		
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
	public Maquina buscarMaquinaID(int ind)
	{
		return maquinas.get(ind);
		
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
	public Empleado buscarEmpleadoID(int ind)
	{
		return empleados.get(ind);
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
	public String buscarProovedorID(int ind)
	{
		return proovedores.get(ind);
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
	public Insumo buscarInsumoID(int ind)
	{
		return insumos.get(ind);
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
