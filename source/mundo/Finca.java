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

import javax.imageio.stream.IIOByteBuffer;

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
	
	private ArrayList<Proovedor> proovedores;
	
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
	private int ultimoIndexCultivo;
	
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
      /*
      for(Lote iLote:lotes)
      {
    	 for( Cultivo iCul:iLote.darCultivos())
    	 {
    		 if(iCul.darIDS().length>0)
    		 {
    			 for(String iID:iCul.darIDS())
    			 {
    				 iCul.agregarServicio(buscarServicioID(Integer.parseInt(iID.substring(2))));
    			 }
    		 }
    	 }

      }
      */
		//File file=new File(archivoFinca);
		
      archivoFincaProperties=fileRutaProperties.getAbsolutePath();
     System.out.println("preProperties");
      Properties prope=validarProperties(fileRutaProperties);
      
      
			
			String[] cierreEmple=prope.getProperty("fechaUltimoCierreEmpleados(dd/MM/yyyy)").split("[/]");
			String[] cierreLotes=prope.getProperty("fechaUltimoCierreLotes(dd/MM/yyyy)").split("[/]");
			String indexCultivo=prope.getProperty("indexCultivo");
			ultimoIndexCultivo=Integer.parseInt(indexCultivo);
			
			
			LocalDate dateEmple=LocalDate.of(Integer.parseInt(cierreEmple[2]), Integer.parseInt(cierreEmple[1]), Integer.parseInt(cierreEmple[0]));
			LocalDate dateLot=LocalDate.of(Integer.parseInt(cierreLotes[2]), Integer.parseInt(cierreLotes[1]), Integer.parseInt(cierreLotes[0]));
			
			fechaUltimoCierreEmpleados=dateEmple;
			fechaUltimoCierreLotes=dateLot;
			
		System.out.println("postProperties");	
		
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
				nuevoEmpleado( linea[1], linea[2],fecha, linea[4], linea[5], linea[6],linea[7], Double.parseDouble(linea[8]),Double.parseDouble(linea[9]));
				
				
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
				nuevoProovedor(linea[1],linea[2]);
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
				System.out.println(linea[0]);
				String[] date=linea[1].split("[/]");
			

				LocalDate fecha= LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]));
				
				
				
				String idLote=linea[3].substring(2);
								
				String[] idsMaquinas=linea[5].equals("")?new String[0]:linea[5].split("[#]");;
				
						
				String[] idsEmpleados=linea[6].equals("")?new String[0]:linea[6].split("[#]");
				
				
				
				
				String[] idInsumosCantidad=linea[7].equals("")?new String[0]:linea[7].split("[#]");
				
				
		
				
				
				double nCostoXArea=Double.parseDouble(linea[8]);
				//System.out.println(nCostoXArea);
				
				
				nuevoServicioCSV(fecha, linea[2], buscarLoteID(Integer.parseInt(idLote)), idsMaquinas,
						idsEmpleados, idInsumosCantidad, nCostoXArea);			}
				
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
			
				String[] insumosString=linea[2].split("[#]");
				String[] insumosCantidades=linea[3].split("[#]");
				
				Insumo[] insu=new Insumo[insumosString.length];
				for(int i=0;i<insu.length;i++)
				{
					Insumo insumoUsado=buscarInsumoID(Integer.parseInt(insumosString[i].substring(2)));
					Double cantidad=Double.parseDouble(insumosCantidades[i]);
					insu[i]=new Insumo(Integer.parseInt(insumoUsado.darID().substring(2)), insumoUsado.darNombre(),cantidad , insumoUsado.darValorUnidad(), insumoUsado.darTipoMedida(), insumoUsado.darUbicacion());
				}
				
				
				//nombre lote inicial o id lote inicial
				
				String prove=linea[4];
				Proovedor proovedor=buscarProovedorID(Integer.parseInt(prove.substring(2)));
				nuevaCompraCSV(fecha, insu, proovedor);
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
				LocalDate fechaCorta=null;
				if(dateCorta.length>1)
				{
					fechaCorta= LocalDate.of(Integer.parseInt(dateCorta[2]), Integer.parseInt(dateCorta[1]),Integer.parseInt(dateCorta[0]));
					
				}
				
				String[] servicios=linea[4].split("[#]");
				
				
				
				
				int indexLote=Integer.parseInt(linea[7].substring(2));
				Cultivo cultivo=nuevoCultivoCSV(linea[0], linea[7], linea[1], fechaSiembra, fechaCorta, servicios);
				
				//lotes.get(indexLote).actualizarCultivoActual(cultivo, fechaCorta);
				
				boolean idCultivoAnterior=linea[5].length()>1?asignarAnteriorCultivo(cultivo, Integer.parseInt(linea[5].substring(2))):false;
				
				boolean idCultivoSiguiente=linea[6].length()>1?asignarSiguienteCultivo(cultivo, Integer.parseInt(linea[6].substring(2))):false;
				
				
				
				boolean produccion=(linea[8].length()>0)?lotes.get(indexLote).asignarProduccionLote(Double.parseDouble(linea[8])):false;
				
				
				
				
				
				
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
		System.out.print("Mquinas Salvar");
		salvarMaquinasCSV(rutaMaquinasFinal);
		
		String rutaInsumosFinal="./data/insumosFinal.csv";
		salvarInsumosCSV(rutaInsumosFinal);
		
		String rutaProovedoresFinal="./data/proovedoresFinal.csv";
		salvarProovedoresCSV(rutaProovedoresFinal);
		
		String rutaServiciosFinal="./data/serviciosFinal.csv";
		salvarServiciosCSV(rutaServiciosFinal);
		System.out.print("Servicios Salvar");
		
		String rutaComprasFinal="./data/comprasFinal.csv";
		salvarComprasCSV(rutaComprasFinal);
		
		String rutaCultivoFinal="./data/cultivosFinal.csv";
		salvarCultivoCSV(rutaCultivoFinal);
		System.out.print("Cultivo Salvar");
		
		//PROPERTIES!!!!!!! 
		File f = new File("./data/FlorenciaProperties.properties");
        OutputStream out = new FileOutputStream( f );
        
		Properties props = new Properties();
		String fechaCierreEmpleados=fechaUltimoCierreEmpleados.getDayOfMonth()+"/"+fechaUltimoCierreEmpleados.getMonthValue()+"/"+fechaUltimoCierreEmpleados.getYear();
		String fechaCierreLotes=fechaUltimoCierreLotes.getDayOfMonth()+"/"+fechaUltimoCierreLotes.getMonthValue()+"/"+fechaUltimoCierreLotes.getYear();
        props.setProperty("fechaUltimoCierreEmpleados(dd/MM/yyyy)",fechaCierreEmpleados );
        props.setProperty("fechaUltimoCierreLotes(dd/MM/yyyy)", fechaCierreLotes);
        props.setProperty("indexCultivo", Integer.toString(ultimoIndexCultivo));
        
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
	
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","CEDULA_2","FECHAINGRESO_3","EPS_4","ZAPATOS_5",
					"PANTALON_6","CAMISETA_7","SUELDO_8","HORAS_9"};
			csvWriter.writeNext(header);
			
			for (Empleado iEmpleado : empleados) 
			{
				LocalDate fecha=iEmpleado.darFechaIngreso();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedString = fecha.format(formatter);
				
				
				
				String[] iE={iEmpleado.darID(),iEmpleado.darNombre(),iEmpleado.darCedula(),formattedString,
						iEmpleado.darEps(),iEmpleado.darTallas()[0],iEmpleado.darTallas()[1],iEmpleado.darTallas()[2],
								Double.toString(iEmpleado.darSalario()),Double.toString(iEmpleado.darDiasTrabajados())};
				csvWriter.writeNext(iE);
			
				
			}
			
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Empleados");
		}
			
		}




	public void salvarMaquinasCSV(String rutaMaquinasFinal) 
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaMaquinasFinal));
	
			
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","DIAS_2"};
			System.out.println(header.length);
			csvWriter.writeNext(header);
			
			for(Maquina iMaquina:maquinas)
			{
				
			
				String[] iM={iMaquina.darID(),iMaquina.darNombre(),Integer.toString(iMaquina.darHorasTrabajo())};
				System.out.println(iM.length);
				csvWriter.writeNext(iM);
				
			}
			csvWriter.close();
			
			
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Maquinas");
		}
		
	}




	public void salvarInsumosCSV(String rutaInsumosFinal) {
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaInsumosFinal));
	
			
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","CANTIDAD_2","VALOR_UNIDAD_3","TIPO_MEDIDA_4","UBICACION_INICIAL_5"};
			csvWriter.writeNext(header);
			
			for(Insumo iInsumo:insumos)
			{
			
				String[] iI={iInsumo.darID(),iInsumo.darNombre(),Double.toString(iInsumo.darCantidadTotal()),Double.toString(iInsumo.darValorUnidad()),iInsumo.darTipoMedida(),iInsumo.darUbicacion()};
				csvWriter.writeNext(iI);

			}
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Insumos");
		}
		
		
	}




	public void salvarProovedoresCSV(String rutaProovedoresFinal) 
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaProovedoresFinal));
	
			
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","NOMBRE_1","NIT_2"};
			csvWriter.writeNext(header);
			
			for(Proovedor iProovedor:proovedores)
			{
			
				String[] iP={iProovedor.darID(),iProovedor.darNombre(),iProovedor.darNIT()};
				csvWriter.writeNext(iP);
				
			}
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Proovedores");
		}
		
		
	}




	public void salvarServiciosCSV(String rutaServiciosFinal)
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaServiciosFinal));
	
			
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","FECHA_1","TIPO_2","LOTE_3","CULTIVO_4","MAQUINAS_5","EMPLEADOS_6","INSUMOS|DOSIS_7","COSTO_8"};
			csvWriter.writeNext(header);
			
			for(Servicio iServicio:servicios)
			{
				LocalDate fecha=iServicio.darFecha();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedString = fecha.format(formatter);
			
			
				String maquinasLinea=iServicio.generarLineaMaquinasCSV();
				String empleadosLinea=iServicio.generarLineaEmpleadosCSV();
				String insumosDosisLinea=iServicio.generarLineaInsumosDosisCSV() ;
				
					
				
				String[] iS={iServicio.darID(),formattedString,iServicio.darTipo(),iServicio.darLote().darID(),iServicio.darCultivo().darID(),maquinasLinea,empleadosLinea,insumosDosisLinea,Double.toString(iServicio.darCostoXArea()) };
				csvWriter.writeNext(iS);
				
			}
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Servicios");
		}
		
	}




	public void salvarComprasCSV(String rutaComprasFinal) {
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaComprasFinal));
	
			
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","FECHA_1","INSUMOS_2","CANTIDAD_3","PROOVEDOR_4","TOTAL_5"};
			csvWriter.writeNext(header);
			
			for(Compra iCompra:compras)
			{
				String insumosLinea=iCompra.generarInsumosLineaCSV();
				String cantidadLinea=iCompra.generarCantidadLineaCSV();
				LocalDate fecha=iCompra.darFecha();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedString = fecha.format(formatter);
			
				String[] iC={iCompra.darID(),formattedString,insumosLinea,cantidadLinea,iCompra.darProovedor().darID(),Double.toString(iCompra.darTotalCompra())};
				csvWriter.writeNext(iC);
				System.out.println(iC[0]);
			}
			csvWriter.close();
		}
		catch (Exception e) {
			System.out.println("Error Salvando CSV Compras");
		}
				
		
	}




	public void salvarCultivoCSV(String rutaCultivoFinal) 
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(rutaCultivoFinal));
	
		
			CSVWriter csvWriter = new CSVWriter(writer,
	                '@',
	                CSVWriter.NO_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String[] header={"ID_0","PRODUCTO_1","SIEMBRA_FECHA_2","CORTA_FECHA_3","SERVICIOS_4","CULTIVO_ANTERIOR_5","CULTIVO_SIGUIENTE_6","LOTE_7","PRODUCCION_8"};
			csvWriter.writeNext(header);
			for(Lote iLote:lotes)
			{
				ArrayList<Cultivo> cultivos=iLote.darCultivos();
				for(Cultivo iCultivo:cultivos)
				{
					LocalDate fechaS=iCultivo.darFechaSiembra();
					DateTimeFormatter formatterS = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					String formattedStringS = fechaS.format(formatterS);
				
					LocalDate fechaC=iCultivo.darFechaCorta();
					String formattedStringC="";
					if(fechaC!=null)
					{
						DateTimeFormatter formatterC = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						formattedStringC = fechaC.format(formatterC);
						
						
					}
					
					String[] iC={iCultivo.darID(),iCultivo.darProducto(),
							formattedStringS,formattedStringC,iCultivo.generarLineaServiciosCSV(),
							((iCultivo.darAnterior()!=null)?iCultivo.darAnterior().darID():new String("")),
							((iCultivo.darSiguiente()!=null)?iCultivo.darSiguiente().darID():new String("")),iCultivo.darLote().darID(),Double.toString(iCultivo.darProduccion())};
					csvWriter.writeNext(iC);
				}
			}
			csvWriter.close();
			
		}
		catch (Exception e) 
		{
			System.out.println("Error Salvando CSV Cultivo");
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
	public ArrayList<Proovedor> darProovedores()
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
			,String pantalon,String camiseta,double nSalario,double horas)
	{
		if(existeEmpleado(nNombre)==false)
		{
			Empleado empleado=new Empleado(empleados.size(),nNombre, nCedula, nFecha,nEps, zapatos
					 ,pantalon,camiseta, nSalario,horas);
			empleados.add(empleado);
			
		}
		
	}
	
	
	public void nuevaMaquina(String nNombre,int nHoras)
	{
		if(existeMaquina(nNombre)==false)
		{
			Maquina maquina=new Maquina(maquinas.size(),nNombre,nHoras);
			maquinas.add(maquina);
		}
		
	}
	public void nuevoLote(String nNombre, String nUbicacion, double nCoste, double nArea)
	{
		if(existeLote(nNombre)==false)
		{
			Lote lote=new Lote(lotes.size(),nNombre, nUbicacion, nCoste, nArea);
			lotes.add(lote);
		}
		
	}
	public void nuevoServicioCSV(LocalDate nFecha, String nTipo, Lote lote, String[] nMaquinas, String[] nEmpleados,String[] nInsumos, double nCostoXArea)
	{
		double areaLote=lote.darArea();
		
		//maquinas
		ArrayList<Maquina> maquinasUsadas=new ArrayList<>();
		System.out.println(nMaquinas.length);
		for (int i = 0; i < nMaquinas.length; i++) 
		{
			//System.out.println(nMaquinas[i]);
			String idIMaquina=nMaquinas[i].substring(2);
			Maquina iMaquina=buscarMaquinaID(Integer.parseInt(idIMaquina));
			
			maquinasUsadas.add(iMaquina);
			
		}
		 
		//empleados
		System.out.println(nEmpleados.length);
		ArrayList<Empleado> empleadosUsados=new ArrayList<>();
		for (int i = 0; i < nEmpleados.length; i++) 
		{
			String idIEmpleado=nEmpleados[i].substring(2);
			Empleado iEmpleado=buscarEmpleadoID(Integer.parseInt(idIEmpleado));
			
			empleadosUsados.add(iEmpleado);
			
		}
		
		ArrayList<Insumo> insumosUsados=new ArrayList<>();
		
		System.out.println(nInsumos.length);
		for (int i = 0; i < nInsumos.length; i++) 
		{
			System.out.println(nInsumos[i]);
			String[] iS=nInsumos[i].split("[|]");
			
			
			String id=iS[0].substring(2);
			int index=Integer.parseInt(id);
			Insumo iInsumo=buscarInsumoID(index);
			
			double dosis=Double.parseDouble((iS[1]));
			
		
			
			double cantidad=dosis*areaLote;
			
			//insumos.get(index).registrarCompra(-cantidad , lotes.get(0).darNombre());
			insumosUsados.add(new Insumo(Integer.parseInt(iInsumo.darID().substring(2)),iInsumo.darNombre(), cantidad, iInsumo.darValorUnidad(), iInsumo.darTipoMedida(),lotes.get(0).darNombre()));
			
			
		}
		Servicio servicio=new Servicio(servicios.size(),nFecha, nTipo, lote,lote.darCultivoActual(), maquinasUsadas, empleadosUsados,insumosUsados, nCostoXArea);
		lote.darCultivoActual().agregarServicio(servicio);
		for(int m=0;m<maquinasUsadas.size();m++)
		{
			Maquina mMaq=maquinasUsadas.get(m);
			mMaq.agregarTrabajoCSV(servicio);
		}
		for(int e=0;e<empleadosUsados.size();e++)
		{
			Empleado eEmp=empleadosUsados.get(e);
			eEmp.agregarServicioCSV(servicio);
		}
		servicios.add(servicio);
	}
	public void nuevoServicio(LocalDate nFecha, String nTipo, Lote lote, String[] nMaquinas, String[] nEmpleados,String[] nInsumos, double nCostoXArea)
	{
		double areaLote=lote.darArea();
		//maquinas
		ArrayList<Maquina> maquinasUsadas=new ArrayList<>();
		for (int i = 0; i < nMaquinas.length; i++) 
		{
			System.out.println(nMaquinas[i]);
			String idIMaquina=nMaquinas[i];
			Maquina iMaquina=buscarMaquinaID(Integer.parseInt(idIMaquina));
			System.out.println(iMaquina.darNombre());
			maquinasUsadas.add(iMaquina);
			
		}
		 
		//empleados
		ArrayList<Empleado> empleadosUsados=new ArrayList<>();
		for (int i = 0; i < nEmpleados.length; i++) 
		{
			String idIEmpleado=nEmpleados[i];
			Empleado iEmpleado=buscarEmpleadoID(Integer.parseInt(idIEmpleado));
			System.out.println(iEmpleado.darNombre());
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
			
			System.out.println(iInsumo.darNombre());
			
			double cantidad=dosis*areaLote;
			
			insumos.get(index).registrarCompra(-cantidad , lotes.get(0).darNombre());
			insumosUsados.add(new Insumo(Integer.parseInt(iInsumo.darID().substring(2)),iInsumo.darNombre(), cantidad, iInsumo.darValorUnidad(), iInsumo.darTipoMedida(),lotes.get(0).darNombre()));
			
			
		}
		Servicio servicio=new Servicio(servicios.size(),nFecha, nTipo, lote,lote.darCultivoActual(), maquinasUsadas, empleadosUsados,insumosUsados, nCostoXArea);
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
		
		Cultivo cultivo=new Cultivo((ultimoIndexCultivo+1),nProducto, nPeriodo,lote,null);
		lote.actualizarCultivoActual(cultivo,corta);
		ultimoIndexCultivo++;
	}
	public Cultivo nuevoCultivoCSV(String IDCultivo,String IDLote, String nProducto, LocalDate nPeriodo, LocalDate corta,String[] serviciosID)
	{
		
		Lote lote=buscarLoteID(Integer.parseInt(IDLote.substring(2)));
		
		Cultivo cultivo=new Cultivo(Integer.parseInt(IDCultivo.substring(2)),nProducto, nPeriodo,lote,serviciosID);
		lote.actualizarCultivoActual(cultivo,corta);
		return cultivo;
		
	}
	public void nuevoInsumo(String nNombre,double nCantidad,double nValorUnidad,String nTipoMedida)
	{
		//se asume que la ubicacion es la florencia
		if(existeInsumo(nNombre)==false)
		{
			Insumo insu=new Insumo(insumos.size(),nNombre, nCantidad,nValorUnidad,nTipoMedida,lotes.get(0).darNombre());
			insumos.add(insu);
 
		}
	}
	public boolean nuevoProovedor(String nNombre ,String nNIT) 
	{
		boolean rta=false;
		if(buscarProovedorIndex(nNombre)==-1)
		{
			Proovedor pro=new Proovedor(proovedores.size(),nNombre, nNIT);
			proovedores.add(pro);
			rta=true;
		}
		return rta;
	}
	public void nuevaCompra(LocalDate date,Insumo[] insu,Proovedor prove)
	{
		//se agregan las cantidades que indica la compra
		for (int i = 0; i < insu.length; i++) 
		{
			Insumo iIns=insu[i];
			// se asume que la ubicacion es La Florencia28
			insumos.get(buscarInsumoIndex(iIns.darNombre())).registrarCompra(iIns.darCantidadTotal(), lotes.get(0).darNombre());
			
		}
		Compra compra=new Compra(compras.size(),date, insu, prove);
		
		compras.add(compra);
		
	}
	public void nuevaCompraCSV(LocalDate date,Insumo[] insu,Proovedor prove)
	{
		//se agregan las cantidades que indica la compra
		
		Compra compra=new Compra(compras.size(),date, insu, prove);
		
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
	public Servicio buscarServicioID(int ind)
	{
		System.out.println(servicios.size());
		return servicios.get(ind);
	}
	public Cultivo buscarCultivoID(int ind)
	{
		Cultivo rta=null;
		for(Lote iLote:lotes)
		{
			for(Cultivo iCul:iLote.darCultivos())
			{
				if(iCul.darID().equals("CU"+ind))
				{
					rta=iCul;
					return iCul;
				}
			}
		}
		return rta;
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
	public Proovedor buscarProovedorID(int ind)
	{
		return proovedores.get(ind);
	}
	public int buscarProovedorIndex(String nNombre)
	{
		int rta=-1;
		
		for(int i=0;i<proovedores.size();i++)
		{
			String iPro=proovedores.get(i).darNombre();
			if(iPro.equals(nNombre))
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
	public boolean asignarServiciosCultivoCSV(String[] servicios,Cultivo cultivo)
	{
		cultivo.asignarIDS(servicios);
		return true;
		
	}
	public boolean asignarAnteriorCultivo(Cultivo cultivo,int indCultiAnterior)
	{
		cultivo.actualizarAnterior(buscarCultivoID(indCultiAnterior));
		return true;
	}
	public boolean asignarSiguienteCultivo(Cultivo cultivo,int indCultiSiguiente)
	{
		cultivo.actualizarSiguiente(buscarCultivoID(indCultiSiguiente));
		return true;
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
				String iProovedor=iCompra.darProovedor().darNombre();
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
				String iProovedor=iCompra.darProovedor().darNombre();
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
