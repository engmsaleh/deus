package it.unipr.ce.dsg.deus.automator;


import it.unipr.ce.dsg.deus.automator.gui.SimulationSummaryFrame;
import it.unipr.ce.dsg.deus.core.Deus;
import it.unipr.ce.dsg.deus.schema.Automator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.JProgressBar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Runner implements Runnable{
	
	private Document document; 
	private Document doc;
	
	private JProgressBar simulationProgressBar = null;

	private String originalXML = "";
	private int numSim = 0;
	
	private int count = 0;
	

	private String originalXml = "";
	private String automatorXml = "";
	
	public Runner(String originalXml,String automatorXml){
		
		this.originalXml = originalXml;
		this.automatorXml = automatorXml;
	}
	
	/*public  void main(String[] args) throws DeusAutomatorException, JAXBException, SAXException, IOException{		
		
		if(args.length > 2)
		{
			throw new DeusAutomatorException("Troppi argomenti [args] inseriti");
		}
		
		if(args.length == 0)
		{
			throw new DeusAutomatorException("Nessun argomento [args] inserito");
		}
							
		
		//Creo n file XML per le n simulazioni con DEUS
		ArrayList<String> files = new ArrayList<String>();								
		
		if( args.length == 2 )
		{
		
		//Leggo il file xml automator e ricavo tutte le simulazioni da effettuare
		ArrayList<MyObjectSimulation> simulations = readXML(args[1]);	
			
		files = writeXML(simulations,args[0]);
				
		int numFile = 0;			
		
		ArrayList<String> logFile = new ArrayList<String>();
		//ArrayList<String> averageFile = new ArrayList<String>();
		
		for(int j = 0; j < simulations.size(); j++)
			for(int k = 0; k < simulations.get(j).getSimulationNumber(); k++ )
			{
				for(int i = 0; i < new Integer(simulations.get(j).getSimulationNumberSeed()); i++)
				{																								
					new Deus(files.get(numFile));
						
					File log = new File(simulations.get(j).getFileLog());								
					
					log.renameTo(new File(simulations.get(j).getSimulationName() + "-" + k +"-" + simulations.get(j).getEngine().get(j).getSeed().get(i)));										
					
					log.delete();
					
					logFile.add(simulations.get(j).getSimulationName() + "-" + k +"-" + simulations.get(j).getEngine().get(j).getSeed().get(i));
					
					numFile++;
				
				}				
								
			 //System.out.println("PRENDO LA LISTA E FACCIO MEDIA FILE AZZERO LA LISTA E LI METTO IN UNA NUOVA LISTA");			 			
			 
			 //TODO Controllare il nome del file
			 ResultAutomator resultAutomator = new ResultAutomator(logFile);
			 
			 String averageFileName = "";
			 String varFileName = "";
//			 
			 try {
					resultAutomator.readTotalResults();
					averageFileName = "./results//Average_" + simulations.get(j).getSimulationName()+"-"+k;
					varFileName = "./results//Var_" + simulations.get(j).getSimulationName()+"-"+k;
					resultAutomator.resultsAverage(averageFileName);
					resultAutomator.resultsVar(varFileName,averageFileName);
				} 
			 catch (IOException e) {
					e.printStackTrace();
				}
			 
			 logFile.removeAll(logFile);									
			 
			 for(int z = 0; z < simulations.get(j).getGnuplot().size(); z++)
				 {
				 // System.out.println("FACCIO GNUPLOT IN " + simulations.get(j).getGnuplot().get(z).getFileName() + " asse x : " + simulations.get(j).getGnuplot().get(z).getAxisX() + " asse y : " + simulations.get(j).getGnuplot().get(z).getAxisY() );				
				  writeGnuPlot( averageFileName, simulations.get(j).getGnuplot().get(z).getFileName()+k, simulations.get(j).getGnuplot().get(z).getAxisX(), simulations.get(j).getGnuplot().get(z).getAxisY());
				 }
			}			
		}
		
		else 
			new Deus(args[0]);
		
		for(int i = 0; i < files.size(); i++)
			new File(files.get(i)).delete();
		
	}
*/

	/**
	 * Funzione che si occupa di lanciare Deus
	 * @param originalXml, nome del file di base di Deus 
	 * @param automatorXml, nome del file che automatizza le simulazioni
	 * @throws ParserConfigurationException 
	 */
	public int start(String originalXml , String automatorXml) throws DeusAutomatorException, JAXBException, SAXException, IOException, ParserConfigurationException{
				
		DocumentBuilderFactory factory =
		      DocumentBuilderFactory.newInstance();
		
		 File f = new File(originalXml);
		 
	     DocumentBuilder builder = factory.newDocumentBuilder();
	      
	     doc = builder.parse(f);		      

		originalXML = originalXml;
		
		FileInputStream fis = new FileInputStream(originalXML);
		FileOutputStream fos = new FileOutputStream(originalXML+".temp");

		byte [] dati = new byte[fis.available()];						
					
		fis.read(dati);
		fos.write(dati);

		fis.close();
		fos.close();

//		SCRIVO IL FILE AUTOMETORLOGGER CHE POI SERVIR� PER LE MEDIE 
//		
//		AutomatorLogger a = new AutomatorLogger("logger");
//
//		ArrayList<LoggerObject> fileValue = new ArrayList<LoggerObject>();
//		
//		fileValue.add(new LoggerObject("Continuity Index", 50));
//		fileValue.add(new LoggerObject("Duplicate", 70));
//		
//		for(int vt = 10 ; vt < 100 ; vt = vt +10 )
//		 a.write(vt, fileValue);		
		
//		if(args.length > 2)
//		{
//			throw new DeusAutomatorException("Troppi argomenti [args] inseriti");
//		}
//		
//		if(args.length == 0)
//		{
//			throw new DeusAutomatorException("Nessun argomento [args] inserito");
//		}
//							
		
		//Creo n file XML per le n simulazioni con DEUS
		ArrayList<String> files = new ArrayList<String>();								
		
	//	if( args.length == 2 )
	//	{
		
		//Leggo il file xml automator e ricavo tutte le simulazioni da effettuare
		ArrayList<MyObjectSimulation> simulations = readXML(automatorXml);	
		
	//	ArrayList<MyObjectSimulation> simulations2 = a(automatorXml);
			
		
		//System.out.println(simulations.size());
		//Inserisce nella ArrayList files i nomi dei file .xml da lanciare
		files = writeXML(simulations,originalXml);
				
		int numFile = 0;					
		ArrayList<String> logFile = new ArrayList<String>();									
		
		boolean condition = false;
		
		//Lettura dal file contenente le info sulla simulazione da eseguire
		File simfile = new File("simulations");
		FileInputStream simfileInputStream = new FileInputStream(simfile);
		InputStreamReader isr = new InputStreamReader(simfileInputStream);
		BufferedReader br = new BufferedReader(isr);
		
		char[] cbuf = new char[simfileInputStream.available()];
		br.read(cbuf);
		String summary = new String(cbuf);
		
		//Eseguo la GUI per il sommario sulla simulazione
		SimulationSummaryFrame simulationsummary = new SimulationSummaryFrame();
		simulationsummary.getSimulationSummaryTextArea().setText(summary);
		simulationsummary.setVisible(true);
		
		while(!condition){
			
			if(simulationsummary.isClose() || !simulationsummary.isShowing() )
			{
				//Chiuso la GUI di riepilogo
				simulationsummary.dispose();
				
				return -1;
			}
			
			if(simulationsummary.isStart() || !simulationsummary.isShowing())
			{
				//Chiuso la GUI di riepilogo
				simulationsummary.dispose();
				condition = true;
			}
		}
		
		
		
		if( simulationProgressBar != null)
		{
			simulationProgressBar.setMaximum(files.size());
			simulationProgressBar.setMinimum(0);
		}
		// Lancia le n simulazioni con i rispettivi n file
		for(int j = 0; j < simulations.size(); j++)
		{
	//		System.out.println(simulations.get(j).getSimulationNumber());
			for(int k = 0; k < simulations.get(j).getSimulationNumber(); k++ )
			{
				for(int i = 0; i < new Integer(simulations.get(j).getSimulationNumberSeed()); i++)
				{																								
					new Deus(files.get(numFile));
						
					File log = new File(simulations.get(j).getFileLog());																		
					
					log.renameTo(new File(simulations.get(j).getSimulationName() + "-" + k +"-" + simulations.get(j).getEngine().get(j).getSeed().get(i)));																			
					
					log.delete();
					
					logFile.add(simulations.get(j).getSimulationName() + "-" + k +"-" + simulations.get(j).getEngine().get(j).getSeed().get(i));
					
					numFile++;
				
					simulationProgressBar.setValue(numFile);
				}															 						 			 			
				
				
			ResultAutomator resultAutomator = new ResultAutomator(logFile);
				 
			String averageFileName = "";
			String varFileName = "";

			//Calcola la media e la varianza ( con i vari seed ) dei dati presenti nei file di log
			 try {
					resultAutomator.readTotalResults();
					averageFileName = "./results//Average_" + simulations.get(j).getSimulationName()+"-"+k;
					varFileName = "./results//Var_" + simulations.get(j).getSimulationName()+"-"+k;
					resultAutomator.resultsAverage(averageFileName);
					resultAutomator.resultsVar(varFileName,averageFileName);
				} 
			 catch (IOException e) {
					e.printStackTrace();
				}
			 
			 for(int i = 0; i < logFile.size(); i++)
					new File(logFile.get(i)).delete();	
			 
			 logFile.clear();
			 
			 // Scrive i file gnuplot 
			 for(int z = 0; z < simulations.get(j).getGnuplot().size(); z++)
				 {			
				  writeGnuPlot( averageFileName, simulations.get(j).getGnuplot().get(z).getFileName()+k, simulations.get(j).getGnuplot().get(z).getAxisX(), simulations.get(j).getGnuplot().get(z).getAxisY());
				 }
			}			
		}
		
		if(files.size() == 0) 
		{
			new Deus(originalXml);		
		}
		//Rimuove tutti i file .xml utilizzati
		for(int i = 0; i < files.size(); i++)
			new File(files.get(i)).delete();
		
		if(new File(originalXML+".temp").exists() )
			new File(originalXML+".temp").delete();
		
		
		return 0;
	}
	
	
//	private  ArrayList<MyObjectSimulation> a(String path) throws DeusAutomatorException, JAXBException, SAXException{
//		
//		JAXBContext jc = JAXBContext.newInstance("it.unipr.ce.dsg.deus.schema.automator");
//		SchemaFactory schemaFactory = SchemaFactory
//				.newInstance("http://www.w3.org/2001/XMLSchema");
//		Schema schema = schemaFactory
//				.newSchema(new File("schema/automator/deusAutomator.xsd"));
//		
//		Unmarshaller unmarshaller = jc.createUnmarshaller();
//		unmarshaller.setSchema(schema);
//		unmarshaller.setEventHandler(new ValidationEventHandler() {
//
//			public boolean handleEvent(ValidationEvent ve) {
//				if (ve.getSeverity() == ValidationEvent.FATAL_ERROR
//						|| ve.getSeverity() == ValidationEvent.ERROR
//						|| ve.getSeverity() == ValidationEvent.WARNING) {
//					ValidationEventLocator locator = ve.getLocator();
//					System.out.println("Invalid configuration file: "
//							+ locator.getURL());
//					System.out.println("Error at column "
//							+ locator.getColumnNumber() + ", line "
//							+ locator.getLineNumber());
//					System.out.println("Error: " + ve.getMessage());
//					return false;
//				}
//				return true;
//			}
//
//		});
//				
//		unmarshaller.unmarshal(new File(path));				
//		
//		ArrayList<MyObjectSimulation> simulation = new ArrayList<MyObjectSimulation>(); 
//		
//		for(int i = 0 ; i < it.automator.gui.SimulationPanel.simulations.size(); i++)
//		{
//			ArrayList<MyObjectNode> nodes = new ArrayList<MyObjectNode>();
//			ArrayList<MyObjectProcess> processes = new ArrayList<MyObjectProcess>();			
//			MyObjectSimulation sim = new MyObjectSimulation();
//			MyObjectEngine myengine = new MyObjectEngine();
//						
//			//sim.setSimulationName(simulationName);
//			for( int j =0 ; j < it.automator.gui.SimulationPanel.simulations.get(i).size(); j++)
//			{				
//				//System.out.println(it.automator.gui.SimulationPanel.simulations.get(i).get(j));				
//				if(it.automator.gui.SimulationPanel.simulations.get(i).get(j).getClass().toString().equals("class it.automator.gui.Node"))
//					{
//					it.automator.gui.Node node = ((it.automator.gui.Node)(it.automator.gui.SimulationPanel.simulations.get(i).get(j)));
//					
//					if(node.getNodeParameterList().size() > 0 && node.getNodeResourceList().size() > 0 && node.getNodeResourceList().size()!=node.getNodeParameterList().size())
//						throw new DeusAutomatorException("Errore");
//					else
//					{
//						ArrayList<Double> value;
//						for(int k = 0; k < node.getNodeParameterList().size(); k++)							
//							{
//								value = calculateParameters(node.getNodeParameterList().get(k).getInitialValue().toString(),node.getNodeParameterList().get(k).getFinalValue().toString(),node.getNodeParameterList().get(k).getStepValue().toString());
//								for( int z = 0; z < value.size(); z++ )
//								{
//									MyObjectParam param = new MyObjectParam();								
//									
//									param.setObjectParam("paramName");
//									param.setObjectName(node.getNodeParameterList().get(k).getParamName());
//									param.setObjectValue(value.get(z));						 
//									 if(nodes.size()>z)						 					
//										 nodes.get(z).getObjectParam().add(param);
//									 						 
//									 else 
//									 {
//										 MyObjectNode mynode = new MyObjectNode(); 
//										 mynode.setObjectName(node.getNodeId());
//										 mynode.getObjectParam().add(param);						 
//										 nodes.add(mynode);
//			    					 }
//								}
//							}
//			
//						for(int k = 0; k < node.getNodeResourceList().size(); k++)							
//						{
//							value = calculateParameters(node.getNodeResourceList().get(k).getInitialValue().toString(),node.getNodeResourceList().get(k).getFinalValue().toString(),node.getNodeResourceList().get(k).getStepValue().toString());
//							
//							for( int z = 0; z < value.size(); z++ )
//							{
//							MyObjectResourceParam resourceParam = new MyObjectResourceParam();								
//							
//							resourceParam.setObjectParam("resourceParamName");		 
//
//							resourceParam.setObjectHandlerName(node.getNodeResourceList().get(k).getHandlerName());
//							resourceParam.setResParamValue(node.getNodeResourceList().get(k).getResParamValue());
//							
//							 resourceParam.setObjectValue(value.get(z));
//							 if(nodes.size()>z)						 						
//								 nodes.get(z).getObjectResourceParam().add(resourceParam);
//							 
//							 else 
//							 {
//								 MyObjectNode mynode = new MyObjectNode(); 
//								 mynode.setObjectName(node.getNodeId());							 
//								 mynode.getObjectResourceParam().add(resourceParam);						 
//								 nodes.add(mynode);
//	    					 }
//							}
//						}
//					}
//								
//					}
//							
//				
//				if(it.automator.gui.SimulationPanel.simulations.get(i).get(j).getClass().toString().equals("class it.automator.gui.ProcessParameter"))
//				{
//					it.automator.gui.ProcessParameter process = ((it.automator.gui.ProcessParameter)(it.automator.gui.SimulationPanel.simulations.get(i).get(j)));
//					ArrayList<Double> value;					
//					value = calculateParameters(process.getInitialValue().toString(),process.getFinalValue().toString(),process.getStepValue().toString());
//			
//					for(int k = 0; k < value.size(); k++ )
//					{						
//					MyObjectParam param = new MyObjectParam();								
//					
//					param.setObjectParam("paramName");
//
//					param.setObjectName(process.getParamName());
//					
//					param.setObjectValue(value.get(k));
//					
//					if(processes.size()>k)						 						
//						 processes.get(k).getObjectParam().add(param);
//				
//					else 
//					 {
//						 MyObjectProcess myprocess = new MyObjectProcess(); 
//						 myprocess.setObjectName(process.getProcessId());
//						 myprocess.getObjectParam().add(param);						 
//						 processes.add(myprocess);
//					 }
//					
//					}
//					
//					}									
//					
//				if(it.automator.gui.SimulationPanel.simulations.get(i).get(j).getClass().toString().equals("class it.automator.gui.EngineParameter"))
//					{			
//					it.automator.gui.EngineParameter engine = ((it.automator.gui.EngineParameter)(it.automator.gui.SimulationPanel.simulations.get(i).get(j)));										
//					myengine.getSeed().add(engine.getSeedValue());
//					}				
//			}
//			sim.setSimulationNumberSeed(String.valueOf(myengine.getSeed().size()));
//			sim.getProcess().add(processes);
//			sim.getEngine().add(myengine);
//			sim.getNode().add(nodes);
//			simulation.add(sim);
//		}
//		return simulation;
//	}
	
	/**
	 * Funzione che si occupa di scrivere i file per gnuplot
	 * @param sourceFile, nome del file da cui leggere i dati 
	 * @param destinationFile, nome del file in cui scrivere i risultati
	 * @param axisX
	 * @param axisY
	 */
	private void writeGnuPlot(String sourceFile, String destinationFile,String axisX, String axisY) {
		
		try {
			File f = new File(sourceFile);
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
								
			FileOutputStream fos = new FileOutputStream("./results//gnuplot//" + destinationFile);
			
			
			String linea=br.readLine();
			String app="";
			boolean assex = false;
			boolean assey = false;
			
			while(linea!=null) {					
			       if(linea.contains(axisX) && assey==false) 
			       {			    	   
			    	   assex = true;
			    	   String val = linea.substring(linea.indexOf('=') + 1, linea.length());
			    	   val = val + " ";
			    	   fos.write(val.getBytes());			    	   
			       }
			      
			       else if(linea.contains(axisY) && assex==true) 
			       {
			    	   assey = true;
			    	   String val = linea.substring(linea.indexOf('=') + 1, linea.length());
			    	   val = val + " ";
			    	   fos.write(val.getBytes());
			    	   fos.write("\n".getBytes());			    	   
			    	   assex=false;
			    	   assey=false;
			       }
			       
			       else if(linea.contains(axisY) && assex==false)
			       {			    	 
			    	   assey = true;
			    	   app = linea.substring(linea.indexOf('=') + 1, linea.length());
			    	   app = app + " ";				  
			       }
			       
			       else if(linea.contains(axisX) && assey==true){
			       			    	  
			    	   String val = linea.substring(linea.indexOf('=') + 1, linea.length());
			    	   val = val + " ";
			    	   fos.write(val.getBytes());
			    	   fos.write(app.getBytes());
			    	   fos.write("\n".getBytes());			    	   
			    	   assex=false;
			    	   assey=false;   
			       }
			       
			       linea=br.readLine();
			}	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

/**
 * Funzione che calcola i vari parametri delle simulazioni
 * @param initialValue, valore iniziale
 * @param finalValue, valore finale
 * @param stepValue, passo
 * @return ArrayList<> contenente tutti i valori compresi tra initialValue e finalValue
 */
	private  ArrayList<Float> calculateParameters(String initialValue, String finalValue, String stepValue) {						
		
		ArrayList<Float> parameters = new ArrayList<Float>();								
				
		if( Float.parseFloat(stepValue)!=0 && (Float.parseFloat(initialValue) < Float.parseFloat(finalValue)))			
			for(float i = Float.parseFloat(initialValue); i <= Float.parseFloat(finalValue) + 0.01; i = i + Float.parseFloat(stepValue) )
				{						
				parameters.add(i);
				}
		
		else if( Float.parseFloat(stepValue)!=0 && (Float.parseFloat(initialValue) > Float.parseFloat(finalValue)))			
			for(float i = Float.parseFloat(initialValue); i >= Float.parseFloat(finalValue); i = i - Float.parseFloat(stepValue) )
				{				
				parameters.add(i);
				}		
				
		return parameters;
	}
	
	/**
	 * Funzione che si occupa di leggere il file .xml per l'automazione delle simulazioni
	 * @param path, percorso del file da leggere
	 * @return un ArrayList<> contenete tutte le varie simulazioni da effettuare
	 * @throws DeusAutomatorException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 */
	private  ArrayList<MyObjectSimulation> readXML(String path) throws DeusAutomatorException, JAXBException, SAXException, IOException{
		
		
		JAXBContext jc = JAXBContext.newInstance("it.unipr.ce.dsg.deus.schema.automator");
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = schemaFactory
				.newSchema(new File("schema/automator/deusAutomator.xsd"));
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new ValidationEventHandler() {

			public boolean handleEvent(ValidationEvent ve) {
				if (ve.getSeverity() == ValidationEvent.FATAL_ERROR
						|| ve.getSeverity() == ValidationEvent.ERROR
						|| ve.getSeverity() == ValidationEvent.WARNING) {
					ValidationEventLocator locator = ve.getLocator();
					System.out.println("Invalid configuration file: "
							+ locator.getURL());
					System.out.println("Error at column "
							+ locator.getColumnNumber() + ", line "
							+ locator.getLineNumber());
					System.out.println("Error: " + ve.getMessage());
					return false;
				}
				return true;
			}

		});
				
		unmarshaller.unmarshal(new File(path));
				
		
		try {

			DocumentBuilderFactory factory =
			      DocumentBuilderFactory.newInstance();
			
			 File f = new File(path);
			 
		     DocumentBuilder builder = factory.newDocumentBuilder();
		      
		     document = builder.parse(f);		      
			
			document.getDocumentElement().normalize();
			
			//Elemento root
			NodeList simulationLst = document.getElementsByTagName("simulation");
			
			ArrayList<MyObjectSimulation> simulation = new ArrayList<MyObjectSimulation>(); 
			
			for (int w = 0; w < simulationLst.getLength(); w++) {						
				
				MyObjectSimulation sim = new MyObjectSimulation();				
				
				Node fstSimulation = simulationLst.item(w);

				if(fstSimulation.getAttributes().getNamedItem("simulationName").getNodeValue() == null || fstSimulation.getAttributes().getNamedItem("simulationNumberSeed").getNodeValue() == null)
					throw new DeusAutomatorException("Errore manca simulationNumberSeed e/o simulationName nel tag simulation");

				String resultFolder = null;
				String inputFolder = null;
				
				String simulationNumberSeed = fstSimulation.getAttributes().getNamedItem("simulationNumberSeed").getNodeValue();
				
				String simulationName = fstSimulation.getAttributes().getNamedItem("simulationName").getNodeValue();
				
				if(fstSimulation.getAttributes().getNamedItem("resultFolder") != null)
					resultFolder = fstSimulation.getAttributes().getNamedItem("resultFolder").getNodeValue();
				
				if(fstSimulation.getAttributes().getNamedItem("inputFolder") != null)
					inputFolder = fstSimulation.getAttributes().getNamedItem("inputFolder").getNodeValue();
				
								
				sim.setResultFolder(resultFolder);
				sim.setInputFolder(inputFolder);
				sim.setSimulationName(simulationName);
				sim.setSimulationNumberSeed(simulationNumberSeed);								
				
			NodeList nodeLst = document.getElementsByTagName("node");
									
			ArrayList<ArrayList<MyObjectNode>> nodes2 = new ArrayList<ArrayList<MyObjectNode>>();
			ArrayList<ArrayList<MyObjectProcess>> processes2 = new ArrayList<ArrayList<MyObjectProcess>>();
			
			// Cerco tutti i tag node
			for (int s = 0; s < nodeLst.getLength(); s++) {				
				
				Node fstNode = nodeLst.item(s);
				
				if(fstNode.getParentNode().equals(simulationLst.item(w))){
				
				ArrayList<MyObjectNode> nodes = new ArrayList<MyObjectNode>();	
					
				String messageType = fstNode.getAttributes().getNamedItem("id").getNodeValue();							

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("paramName");							
				
				//Ricavo tutti i parametri in ParamName di node
				for( int j = 0 ; j < fstNmElmntLst.getLength() ; j++)
				{										

					Element paramElement = (Element)fstNmElmntLst.item(j);

					String paramName  = ((Node) fstNmElmntLst.item(j)).getAttributes().getNamedItem("name").getNodeValue();															

					NodeList initialValue = paramElement.getElementsByTagName("initialValue");

					NodeList finalValue = paramElement.getElementsByTagName("finalValue");
	
					NodeList stepValue = paramElement.getElementsByTagName("stepValue");

					if(initialValue == null || finalValue == null || stepValue == null)
					{
					throw new DeusAutomatorException("Errore in initalValue , finalValue e stepValue in " + simulationName + " di Node " + messageType + " in " + paramName);	
					}
					
					ArrayList<Float> value = calculateParameters(initialValue.item(0).getTextContent(),finalValue.item(0).getTextContent(),stepValue.item(0).getTextContent());
					
					if(value.size() > 0 ) sim.setStep(value.size());
					
					if(Float.parseFloat(stepValue.item(0).getTextContent()) == 0.0)
					{
						MyObjectParam paramToWrite = new MyObjectParam();														
						paramToWrite.setObjectParam("paramName");
						paramToWrite.setObjectName(paramName);
						paramToWrite.setObjectValue(Float.parseFloat(initialValue.item(0).getTextContent()));						 						 
						 						 
						MyObjectNode nodeToWrite = new MyObjectNode(); 
						nodeToWrite.setObjectName(messageType);
						nodeToWrite.getObjectParam().add(paramToWrite);						 
						//System.out.println("SCRIVO SOLO IL NODO");
						writeXmlNodeParam(nodeToWrite);						
						
					}
					
					if(numSim == 0)
						numSim = value.size();
					
					if(numSim != value.size())
					{
					throw new DeusAutomatorException("Errore nel numero di step in "  + simulationName + " di Node " + messageType + " in " + paramName);	
					}
					
					for(int k = 0; k < value.size(); k++ )
						{					
						MyObjectParam param = new MyObjectParam();								
						
						param.setObjectParam("paramName");
						param.setObjectName(paramName);
						param.setObjectValue(value.get(k));						 
						 if(nodes.size()>k)						 					
							 nodes.get(k).getObjectParam().add(param);
						 						 
						 else 
						 {
							 MyObjectNode node = new MyObjectNode(); 
							 node.setObjectName(messageType);
							 node.getObjectParam().add(param);						 
							 nodes.add(node);
    					 }
						}							
				}
				
				
				NodeList paramName = fstElmnt.getElementsByTagName("resourceParamName");							

				//Ricavo tutti i parametri in resourceParamName di node 
				for( int j = 0 ; j < paramName.getLength() ; j++)
				{				
					
					Element paramElement = (Element)paramName.item(j);

					String handlerName  = ((Node) paramName.item(j)).getAttributes().getNamedItem("handlerName").getNodeValue();
					
					String resParamValueName  = ((Node) paramName.item(j)).getAttributes().getNamedItem("resParamValue").getNodeValue();

					NodeList initialValue = paramElement.getElementsByTagName("initialValue");

					NodeList finalValue = paramElement.getElementsByTagName("finalValue");
					
					NodeList stepValue = paramElement.getElementsByTagName("stepValue");
					
					if(initialValue == null || finalValue == null || stepValue == null)
					{
					throw new DeusAutomatorException("Errore in initalValue , finalValue e stepValue in " + simulationName + " di Node" + messageType + " in " + paramName );	
					}
					
					ArrayList<Float> value = calculateParameters(initialValue.item(0).getTextContent(),finalValue.item(0).getTextContent(),stepValue.item(0).getTextContent());										
					
					if(value.size() > 0 ) sim.setStep(value.size());
 					
					if(Float.parseFloat(stepValue.item(0).getTextContent()) == 0.0)
					{
						MyObjectResourceParam resourceParam = new MyObjectResourceParam();								
						
						resourceParam.setObjectParam("resourceParamName");		 

						resourceParam.setObjectHandlerName(handlerName);
						resourceParam.setResParamValue(resParamValueName);
						
						resourceParam.setObjectValue(Float.parseFloat(initialValue.item(0).getTextContent()));
						
						MyObjectNode nodeToWrite = new MyObjectNode(); 
						nodeToWrite.setObjectName(messageType);							 
						nodeToWrite.getObjectResourceParam().add(resourceParam);						 							 					
    											 
						//System.out.println("SCRIVO SOLO IL NODO");
						writeXmlNodeResource(nodeToWrite);
						
					}
					
					if(numSim == 0)
						numSim = value.size();
					
					if(numSim != value.size())
					{
					throw new DeusAutomatorException("Errore nel numero di step in " + simulationName + " di Node " + messageType + " in " + paramName);	
					}
					
					for(int k = 0; k < value.size(); k++ )
						{						
						MyObjectResourceParam resourceParam = new MyObjectResourceParam();								
						
						resourceParam.setObjectParam("resourceParamName");		 

						resourceParam.setObjectHandlerName(handlerName);
						resourceParam.setResParamValue(resParamValueName);
						
						 resourceParam.setObjectValue(value.get(k));
						 if(nodes.size()>k)						 						
							 nodes.get(k).getObjectResourceParam().add(resourceParam);
						 
						 else 
						 {
							 MyObjectNode node = new MyObjectNode(); 
							 node.setObjectName(messageType);							 
							 node.getObjectResourceParam().add(resourceParam);						 
							 nodes.add(node);
    					 }

						}
				}

					nodes2.add(nodes);
				}
						
			}	
			
			if(nodes2.size()>0)
			sim.setNode(nodes2);
			
			
			if(new Integer(sim.getNode().size())>0)
				sim.setSimulationNumber(new Integer(sim.getNode().get(0).size()));
			
			NodeList processLst = document.getElementsByTagName("process");						

			//Cerco tutti i tag process
			for (int s = 0; s < processLst.getLength(); s++) {
			
				Node fstNode = processLst.item(s);								
			
				if(fstNode.getParentNode().equals(simulationLst.item(w))){								
				
				ArrayList<MyObjectProcess> processes = new ArrayList<MyObjectProcess>();	
					
				String messageType = fstNode.getAttributes().getNamedItem("id").getNodeValue();

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("paramName");
								
				//Ricavo tutti i parametri in ParamName di process
				for( int j = 0 ; j < fstNmElmntLst.getLength() ; j++)
				{				
					
					Element paramElement = (Element)fstNmElmntLst.item(j);

					String paramName  = ((Node) fstNmElmntLst.item(j)).getAttributes().getNamedItem("name").getNodeValue();
					
					NodeList initialValue = paramElement.getElementsByTagName("initialValue");
					
					NodeList finalValue = paramElement.getElementsByTagName("finalValue");
					
					NodeList stepValue = paramElement.getElementsByTagName("stepValue");
					
					if(initialValue == null || finalValue == null || stepValue == null)
					{
					throw new DeusAutomatorException("Errore in initalValue , finalValue e stepValue in " + simulationName +  " di Process " + messageType + " in " + paramName);		
					}
					
					ArrayList<Float> value = calculateParameters(initialValue.item(0).getTextContent(),finalValue.item(0).getTextContent(),stepValue.item(0).getTextContent());
								
					if(value.size() > 0 ) sim.setStep(value.size());
					
					if(Float.parseFloat(stepValue.item(0).getTextContent()) == 0.0)
					{
						MyObjectParam param = new MyObjectParam();								
						
						param.setObjectParam("paramName");

						param.setObjectName(paramName);
						
						param.setObjectValue(Float.parseFloat(initialValue.item(0).getTextContent()));
											
						MyObjectProcess processToWrite = new MyObjectProcess(); 
						processToWrite.setObjectName(messageType);
						processToWrite.getObjectParam().add(param);						 												
			
						//System.out.println("SCRIVO SOLO IL PROCESSO");
						writeXmlProcess(processToWrite);
						
					}
					
					if(numSim == 0)
						numSim = value.size();
					
					if(numSim != value.size())
					{
					throw new DeusAutomatorException("Errore nel numero di step in " + simulationName + " di Process " + messageType + " in " + paramName);		
					}
					
					for(int k = 0; k < value.size(); k++ )
						{						
						MyObjectParam param = new MyObjectParam();								
						
						param.setObjectParam("paramName");

						param.setObjectName(paramName);
						
						param.setObjectValue(value.get(k));
						
						if(processes.size()>k)						 						
							 processes.get(k).getObjectParam().add(param);
					
						else 
						 {
							 MyObjectProcess process = new MyObjectProcess(); 
							 process.setObjectName(messageType);
							 process.getObjectParam().add(param);						 
							 processes.add(process);
						 }
						
						}
				
				}		
				if(processes.size()>0)
					processes2.add(processes);
				}			
				//if(a==false)
				
			}
			if(processes2.size()>0)
			sim.setProcess(processes2);
			
			numSim = 0;
			
			if(sim.getSimulationNumber() == 0)
				if(sim.getProcess().size()>0)
					
			sim.setSimulationNumber(new Integer(sim.getProcess().get(0).size()));
			
			NodeList engineLst = document.getElementsByTagName("engine");						

			//Cerco i tag engine
			for (int s = 0; s < engineLst.getLength(); s++) {

				MyObjectEngine engine = new MyObjectEngine();
				
				Node fstNode = engineLst.item(s);
				
				if(fstNode.getParentNode().equals(simulationLst.item(w))){

				String startVt = "";
				String endVt = "";
				String stepVt = "";
				
				ArrayList<Float> value = new ArrayList<Float>();
				boolean vt = true;
				
				if(fstNode.getAttributes().getNamedItem("startVT") != null )	
					startVt = fstNode.getAttributes().getNamedItem("startVT").getNodeValue();
				
				else vt = false;
				
				if(fstNode.getAttributes().getNamedItem("endVT") != null )	
					endVt = fstNode.getAttributes().getNamedItem("endVT").getNodeValue();
				 
				else vt = false;
				
				if(fstNode.getAttributes().getNamedItem("stepVT") != null )
					stepVt = fstNode.getAttributes().getNamedItem("stepVT").getNodeValue();
				
				else vt = false;
				
				if(vt)
					value = calculateParameters(startVt,endVt,stepVt);
				
				for(int k = 0; k < value.size(); k++ )
				{										 
				 engine.getVt().add(value.get(k));
				}
				
				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("seed");

				//Ricavo tutti i seedValue presenti in seed
				for( int j = 0 ; j < fstNmElmntLst.getLength() ; j++)
				{					
					
					Element paramElement = (Element)fstNmElmntLst.item(j);
								
					NodeList seedValue = paramElement.getElementsByTagName("seedValue");
					
					if(seedValue.getLength() > Integer.parseInt(sim.getSimulationNumberSeed()))
					{
					throw new DeusAutomatorException("Errore nel numero di seed inseriti, TROPPI rispetto a quelli indicati nel tag simulation in " + simulationName);	
					}
										
					if(seedValue.getLength() < Integer.parseInt(sim.getSimulationNumberSeed()))
					{
					throw new DeusAutomatorException("Errore nel numero di seed inseriti, POCHI rispetto a quelli indicati nel tag simulation in " + simulationName);
					}
					
					for( int o = 0 ; o < seedValue.getLength() ; o++)
					{						
						Node seedvalue = seedValue.item(o);						
						engine.getSeed().add(seedvalue.getTextContent());
					}									

				}
				
				}
				//Aggiunge Engine al simulation
				sim.getEngine().add(engine);	
			
			}			
			
			if(sim.getSimulationNumber()==0)
				sim.setSimulationNumber(1);			
			
			NodeList resultLogLst = document.getElementsByTagName("resultVT");						
			
			for (int i = 0; i < resultLogLst.getLength(); i++) {			
				
				Node fileLog = resultLogLst.item(i);
				
				if(fileLog.getParentNode().equals(simulationLst.item(w))){									
					
					sim.setFileLog(fileLog.getAttributes().getNamedItem("outputLogFile").getNodeValue());
				}			
		
			}
			
			
			NodeList GnuPlotLst = document.getElementsByTagName("resultXYFile");						

			for (int i = 0; i < GnuPlotLst.getLength(); i++) {			
				
				Node GnuPlotNode = GnuPlotLst.item(i);
				
				if(GnuPlotNode.getParentNode().equals(simulationLst.item(w))){
				
					MyObjectGnuplot gnuplot = new MyObjectGnuplot();									
					
					gnuplot.setFileName(GnuPlotNode.getAttributes().getNamedItem("fileName").getNodeValue()+"_"+"asseX"+GnuPlotNode.getAttributes().getNamedItem("axisX").getNodeValue()+"asseY"+GnuPlotNode.getAttributes().getNamedItem("axisY").getNodeValue()+"_"+sim.getSimulationName()+"-");
					
					gnuplot.setAxisX(GnuPlotNode.getAttributes().getNamedItem("axisX").getNodeValue());
					
					gnuplot.setAxisY(GnuPlotNode.getAttributes().getNamedItem("axisY").getNodeValue());
					
					sim.getGnuplot().add(gnuplot);
				}			
		
			}									
						
			simulation.add(sim);
			
			}						
			
			
		return simulation;
	}
	 catch (SAXException e) {

		e.printStackTrace();
	} catch (IOException e) {

		e.printStackTrace();
	} catch (ParserConfigurationException e) {

		e.printStackTrace();
	}
		return null;

}

	private  void writeXmlNodeParam(MyObjectNode nodeToWrite) throws IOException, ParserConfigurationException, SAXException {									

		FileOutputStream fos = new FileOutputStream(originalXML+".temp");
		
		NodeList node = doc.getElementsByTagName("aut:node");
		 for(int i = 0 ; i < node.getLength(); i++ ){		  
		    if(node.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(nodeToWrite.getObjectName()))
			{				    			    	
		    	for(int l=0; l<nodeToWrite.getObjectParam().size(); l++){			 		
		    		for(int m = 0 ; m < node.item(i).getChildNodes().getLength(); m++ )				    			
		    			for(int b = 0; b < node.item(i).getChildNodes().item(m).getChildNodes().getLength(); b++)
		    			  if(node.item(i).getChildNodes().item(m).getChildNodes().item(b).getNodeName().equals("aut:param"))				    			  				    				 				    				
		    				  if(node.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue().equals(nodeToWrite.getObjectParam().get(l).getObjectName()))				    			   				    					
		    				  {		    					  
		    					  node.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("value").setNodeValue(((Double)nodeToWrite.getObjectParam().get(l).getObjectValue()).toString());
		    				  }		    
		    	 }
			}
		 }
	
		 DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer;
		try {
			transformer = tf.newTransformer();
		
	      
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // System.out.println(writer.toString());
	
		 fos.write(writer.toString().getBytes());
	}


private  void writeXmlProcess(MyObjectProcess processToWrite) throws IOException, ParserConfigurationException, SAXException {		
	
		FileOutputStream fos = new FileOutputStream(originalXML+".temp");				
	
		NodeList process = doc.getElementsByTagName("aut:process");
		for(int i = 0 ; i < process.getLength(); i++ )
		{	
			 if(process.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(processToWrite.getObjectName()))
				{				
				 for(int l=0; l<processToWrite.getObjectParam().size(); l++)
					 for(int m = 0 ; m < process.item(i).getChildNodes().getLength(); m++ )
					 {
						 if(process.item(i).getChildNodes().item(m).getNodeName().equals("aut:params"))
							 for(int b = 0; b < process.item(i).getChildNodes().item(m).getChildNodes().getLength(); b++)
								 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getNodeName().equals("aut:param"))
								 {
									// System.out.println(process.item(i).getAttributes().getNamedItem("id").getNodeValue());
									 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue().equals(processToWrite.getObjectParam().get(l).getObjectName()))
										 {										 
										  process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("value").setNodeValue(((Double)processToWrite.getObjectParam().get(l).getObjectValue()).toString());
										 }
								 }
					 }
		
				}
		}

		DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer;
		try {
			transformer = tf.newTransformer();
		
	      
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // System.out.println(writer.toString());
	
		 fos.write(writer.toString().getBytes());

	}	
	
	
private  void writeXmlNodeResource(MyObjectNode nodeToWrite) throws IOException, ParserConfigurationException, SAXException {
	
	FileOutputStream fos = new FileOutputStream(originalXML+".temp");
	
		NodeList node = doc.getElementsByTagName("aut:node");
		 for(int i = 0 ; i < node.getLength(); i++ ){		  
		    if(node.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(nodeToWrite.getObjectName()))
			{				    			    			    					    
		    	for(int l=0; l<nodeToWrite.getObjectResourceParam().size(); l++){
		    		for(int m = 0 ; m < node.item(i).getChildNodes().getLength(); m++ ){				    		
		    			if(node.item(i).getChildNodes().item(m).getNodeName().equals("aut:resources"));
		    				for(int v = 0 ; v < node.item(i).getChildNodes().item(m).getChildNodes().getLength(); v++ )
		    					if( node.item(i).getChildNodes().item(m).getChildNodes().item(v).getNodeName().equals("aut:resource") 
		    							&& node.item(i).getChildNodes().item(m).getChildNodes().item(v).getAttributes().getNamedItem("handler").getNodeValue().equals(nodeToWrite.getObjectResourceParam().get(l).getObjectHandlerName())) 
				    				for(int z = 0 ; z < node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().getLength(); z++ )						    				
				    					if( node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getNodeName().equals("aut:params"))
					    				for(int x = 0 ; x < node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().getLength(); x++ )							    								    				
					    					if(node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getNodeName().equals("aut:param"))
					    					if(node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getAttributes().getNamedItem("name").getNodeValue().equals("amount"))							    													    					
					    						{					    						 
					    						 node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getAttributes().getNamedItem("value").setNodeValue(((Double)nodeToWrite.getObjectResourceParam().get(l).getObjectValue()).toString());
					    						}
					    						
					    				
		    		}
		    	}
			}
		 }
		   			  		
		 DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer;
		try {
			transformer = tf.newTransformer();
		
	      
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // System.out.println(writer.toString());
	
		 fos.write(writer.toString().getBytes());

	}

	/**
	 * Funzione che scrive i vari file .xml da utilizzare al momento del lancio di Deus
	 * @param simulation, le varie simulazioni da effettuare
	 * @param path, percorso del file .xml di base di Deus da modificare
	 * @return il nome dei file da lanciare
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 */
	private  ArrayList<String> writeXML(ArrayList<MyObjectSimulation> simulation, String path) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		
		ArrayList<String> xmlFile = new ArrayList<String>(); 
			
		try
		{								

			FileOutputStream simul = new FileOutputStream("simulations");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();										    
		    
		    doc.getDocumentElement().normalize();					    
		    		    
		    	
		for(int j = 0; j < simulation.size(); j++)
		{									 			

			int end = 0;
			
			File f = new File(path+".temp");						
			 
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(f);		      

			//System.out.println("SIM "+ j );
						
			
//			if(simulation.get(j).getNode().size()>0) 
//		try{
//				end = simulation.get(j).getNode().get(0).size();
//		}
//		catch(IndexOutOfBoundsException e){
//			e.printStackTrace();
//		}
//			//System.out.println("A " + end);
//			
//			if(simulation.get(j).getProcess().size()>0 && end==0) 
//				try{
//				end = simulation.get(j).getProcess().get(0).size();
//				}
//			catch(IndexOutOfBoundsException e){
//				e.printStackTrace();
//			}
//			
//			if(simulation.get(j).getNode().size()>j && end == 0)
//				end = simulation.get(j).getNode().get(j).size();
//			
//			if(simulation.get(j).getProcess().size()>j && end==0)
//				end = simulation.get(j).getProcess().get(j).size();

			end = simulation.get(j).getStep();
			
			if(simulation.get(j).getNode().size()>0 || simulation.get(j).getProcess().size()>0)							 
			for(int k = 0; k < end; k++){
				simul.write(("\n" + simulation.get(j).getSimulationName() + "-" + k + "\n").getBytes());
				for(int u = 0; u < simulation.get(j).getNode().size(); u++)
				{
				 NodeList node = doc.getElementsByTagName("aut:node");
				 for(int i = 0 ; i < node.getLength(); i++ ){
				  if(simulation.get(j).getNode().get(u).size()>0)
				    if(node.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(simulation.get(j).getNode().get(u).get(k).getObjectName()))
					{				    	
				    	simul.write(("Node : " + node.item(i).getAttributes().getNamedItem("id").getNodeValue() + "\n").getBytes());
				    	for(int l=0; l<simulation.get(j).getNode().get(u).get(k).getObjectParam().size(); l++){
					 		
				    		for(int m = 0 ; m < node.item(i).getChildNodes().getLength(); m++ )				    			
				    			for(int b = 0; b < node.item(i).getChildNodes().item(m).getChildNodes().getLength(); b++)
				    			  if(node.item(i).getChildNodes().item(m).getChildNodes().item(b).getNodeName().equals("aut:param"))				    			  				    				 				    				
				    				  if(node.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue().equals(simulation.get(j).getNode().get(u).get(k).getObjectParam().get(l).getObjectName()))				    			   				    					
				    				  {
				    					  simul.write(("Parameter : " + node.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue() + " ").getBytes());
				    					  simul.write((((Double)simulation.get(j).getNode().get(u).get(k).getObjectParam().get(l).getObjectValue()).toString() + "\n").getBytes());
				    					  node.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("value").setNodeValue(((Double)simulation.get(j).getNode().get(u).get(k).getObjectParam().get(l).getObjectValue()).toString());
				    				  }
				    
				    	 }
				    					    	
				    	for(int l=0; l<simulation.get(j).getNode().get(u).get(k).getObjectResourceParam().size(); l++){
				    		for(int m = 0 ; m < node.item(i).getChildNodes().getLength(); m++ ){				    		
				    			if(node.item(i).getChildNodes().item(m).getNodeName().equals("aut:resources"));
				    				for(int v = 0 ; v < node.item(i).getChildNodes().item(m).getChildNodes().getLength(); v++ )
				    					if( node.item(i).getChildNodes().item(m).getChildNodes().item(v).getNodeName().equals("aut:resource") 
				    							&& node.item(i).getChildNodes().item(m).getChildNodes().item(v).getAttributes().getNamedItem("handler").getNodeValue().equals(simulation.get(j).getNode().get(u).get(k).getObjectResourceParam().get(l).getObjectHandlerName())) 
						    				for(int z = 0 ; z < node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().getLength(); z++ )						    				
						    					if( node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getNodeName().equals("aut:params"))
							    				for(int x = 0 ; x < node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().getLength(); x++ )							    								    				
							    					if(node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getNodeName().equals("aut:param"))
							    					if(node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getAttributes().getNamedItem("name").getNodeValue().equals("amount"))							    													    					
							    						{
							    						 simul.write(("Resource Parameter : " + node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getAttributes().getNamedItem("name").getNodeValue() + " ").getBytes());
								    					 simul.write((((Double)simulation.get(j).getNode().get(u).get(k).getObjectResourceParam().get(l).getObjectValue()).toString() + "\n").getBytes());
							    						 node.item(i).getChildNodes().item(m).getChildNodes().item(v).getChildNodes().item(z).getChildNodes().item(x).getAttributes().getNamedItem("value").setNodeValue(((Double)simulation.get(j).getNode().get(u).get(k).getObjectResourceParam().get(l).getObjectValue()).toString());
							    						}
							    						
							    				
				    		}				
				    	}
				    }				  							
				  }
				} 
			
				if(simulation.get(j).getProcess().size()>0)
				{
					//simul.write(("\n" + simulation.get(j).getSimulationName() + "-" + k + "\n").getBytes());	
					for(int g=0; g < simulation.get(j).getProcess().size(); g++)
					{			
					NodeList process = doc.getElementsByTagName("aut:process");
					for(int i = 0 ; i < process.getLength(); i++ )
					{	
						if(simulation.get(j).getProcess().get(g).size()>0)
						 if(process.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(simulation.get(j).getProcess().get(g).get(k).getObjectName()))
							{
							 simul.write(("Process : " + process.item(i).getAttributes().getNamedItem("id").getNodeValue() + "\n").getBytes());
							 for(int l=0; l<simulation.get(j).getProcess().get(g).get(k).getObjectParam().size(); l++)
								 for(int m = 0 ; m < process.item(i).getChildNodes().getLength(); m++ )
								 {
									 if(process.item(i).getChildNodes().item(m).getNodeName().equals("aut:params"))
										 for(int b = 0; b < process.item(i).getChildNodes().item(m).getChildNodes().getLength(); b++)
											 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getNodeName().equals("aut:param"))
											 {
												// System.out.println(process.item(i).getAttributes().getNamedItem("id").getNodeValue());
												 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue().equals(simulation.get(j).getProcess().get(g).get(k).getObjectParam().get(l).getObjectName()))
													 {
													 simul.write(("Parameter : " + process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue() + " ").getBytes());
							    					  simul.write((((Double)simulation.get(j).getProcess().get(g).get(k).getObjectParam().get(l).getObjectValue()).toString() + "\n").getBytes());
													  process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("value").setNodeValue(((Double)simulation.get(j).getProcess().get(g).get(k).getObjectParam().get(l).getObjectValue()).toString());
													 }
											 }
								 }
					
							}
					}
					
					}
				}		
			
				 for(int seed = 0 ; seed < simulation.get(j).getEngine().get(j).getSeed().size(); seed++ )
						
					{					 					 								
						NodeList engine = doc.getElementsByTagName("aut:engine");
						for(int i = 0 ; i < engine.getLength(); i++ ){
							engine.item(i).getAttributes().getNamedItem("seed").setNodeValue(simulation.get(j).getEngine().get(j).getSeed().get(seed));
						}
						
					 DOMSource domSource = new DOMSource(doc);
				       StringWriter writer = new StringWriter();
				       StreamResult result = new StreamResult(writer);
				       TransformerFactory tf = TransformerFactory.newInstance();
				       Transformer transformer;
					try {
						transformer = tf.newTransformer();
					
				      
						transformer.transform(domSource, result);
					} catch (TransformerConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      
					
					// System.out.println(writer.toString());
					 String filename = "./xml/" + simulation.get(j).getSimulationName() + "_" + k + "_" + simulation.get(j).getEngine().get(j).getSeed().get(seed);
					 FileOutputStream file = new FileOutputStream(filename);
					 //System.out.println(filename);
					 
					 file.write(writer.toString().getBytes());
					 file.close();
				     xmlFile.add(filename);
				}
						
				 
			}					
			
			
			if(simulation.get(j).getProcess().size() < 1 && simulation.get(j).getNode().size() < 1)
			 for(int seed = 0 ; seed < simulation.get(j).getEngine().get(j).getSeed().size(); seed++ )					
				{					 					 											
					NodeList engine = doc.getElementsByTagName("aut:engine");
					for(int i = 0 ; i < engine.getLength(); i++ ){
						engine.item(i).getAttributes().getNamedItem("seed").setNodeValue(simulation.get(j).getEngine().get(j).getSeed().get(seed));
					}
					
				 DOMSource domSource = new DOMSource(doc);
			       StringWriter writer = new StringWriter();
			       StreamResult result = new StreamResult(writer);
			       TransformerFactory tf = TransformerFactory.newInstance();
			       Transformer transformer;
				try {
					transformer = tf.newTransformer();
				
			      
					transformer.transform(domSource, result);
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				 String filename = "./xml/" + simulation.get(j).getSimulationName() + "_0" + "_" + simulation.get(j).getEngine().get(j).getSeed().get(seed);
				 FileOutputStream file = new FileOutputStream(filename);							 
				 
				 file.write(writer.toString().getBytes());
			     xmlFile.add(filename);
			}
			//f.delete();
			}
			
					
			
	       
//		for(int j=0; j<simulation.size(); j++)
//		{			
//			if(simulation.get(j).getProcess().size()>0)
//			for(int k=0; k<simulation.get(j).getProcess().get(0).size(); k++){
//				simul.write(("\n" + simulation.get(j).getSimulationName() + "-" + k + "\n").getBytes());	
//				for(int u=0; u<simulation.get(j).getProcess().size(); u++)
//				{			
//				NodeList process = document.getElementsByTagName("aut:process");
//				for(int i = 0 ; i < process.getLength(); i++ ){										
//					 if(process.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(simulation.get(j).getProcess().get(u).get(k).getObjectName()))
//						{
//						 simul.write(("Process : " + process.item(i).getAttributes().getNamedItem("id").getNodeValue() + "\n").getBytes());
//						 for(int l=0; l<simulation.get(j).getProcess().get(u).get(k).getObjectParam().size(); l++)
//							 for(int m = 0 ; m < process.item(i).getChildNodes().getLength(); m++ ){
//								 if(process.item(i).getChildNodes().item(m).getNodeName().equals("aut:params"))
//									 for(int b = 0; b < process.item(i).getChildNodes().item(m).getChildNodes().getLength(); b++)
//										 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getNodeName().equals("aut:param"))
//										 {
//											// System.out.println(process.item(i).getAttributes().getNamedItem("id").getNodeValue());
//											 if(process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue().equals(simulation.get(j).getProcess().get(u).get(k).getObjectParam().get(l).getObjectName()))
//												 {
//												 simul.write(("Parameter : " + process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("name").getNodeValue() + " ").getBytes());
//						    					  simul.write((((Double)simulation.get(j).getProcess().get(u).get(k).getObjectParam().get(l).getObjectValue()).toString() + "\n").getBytes());
//												  process.item(i).getChildNodes().item(m).getChildNodes().item(b).getAttributes().getNamedItem("value").setNodeValue(((Double)simulation.get(j).getProcess().get(u).get(k).getObjectParam().get(l).getObjectValue()).toString());
//												 }
//										 }
//				}
//				
//						}
//				}
//				
//				 
//				}
//				
//				for(int seed = 0 ; seed < simulation.get(j).getEngine().get(j).getSeed().size(); seed++ )				
//				{
//					NodeList engine = document.getElementsByTagName("aut:engine");
//					for(int i = 0 ; i < engine.getLength(); i++ ){
//						engine.item(i).getAttributes().getNamedItem("seed").setNodeValue(simulation.get(j).getEngine().get(j).getSeed().get(seed));
//					}
//					
//				 DOMSource domSource = new DOMSource(document);
//			       StringWriter writer = new StringWriter();
//			       StreamResult result = new StreamResult(writer);
//			       TransformerFactory tf = TransformerFactory.newInstance();
//			       Transformer transformer;
//				try {
//					transformer = tf.newTransformer();
//				
//			      
//					transformer.transform(domSource, result);
//				} catch (TransformerConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				 catch (TransformerException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//			     String filename = "./xml/" + simulation.get(j).getSimulationName() + "_" + k + "_" + simulation.get(j).getEngine().get(j).getSeed().get(seed);
//				 FileOutputStream file = new FileOutputStream(filename);
//					
//				 file.write(writer.toString().getBytes());
//			      xmlFile.add(filename);
//				
//				}
//				
//			}
//		
//			if(simulation.get(j).getProcess().size()==0 && simulation.get(j).getNode().size()==0)
//			 for(int seed = 0 ; seed < simulation.get(j).getEngine().get(j).getSeed().size(); seed++ )
//					
//				{					 					 							
//					NodeList engine = document.getElementsByTagName("aut:engine");
//					for(int i = 0 ; i < engine.getLength(); i++ ){
//						engine.item(i).getAttributes().getNamedItem("seed").setNodeValue(simulation.get(j).getEngine().get(j).getSeed().get(seed));
//					}
//					
//				 DOMSource domSource = new DOMSource(document);
//			       StringWriter writer = new StringWriter();
//			       StreamResult result = new StreamResult(writer);
//			       TransformerFactory tf = TransformerFactory.newInstance();
//			       Transformer transformer;
//				try {
//					transformer = tf.newTransformer();
//				
//			      
//					transformer.transform(domSource, result);
//				} catch (TransformerConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				 catch (TransformerException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		
//				 String filename = "./xml/" + simulation.get(j).getSimulationName() + "_0" + "_" + simulation.get(j).getEngine().get(j).getSeed().get(seed);
//				 FileOutputStream file = new FileOutputStream(filename);
//			
//				 file.write(writer.toString().getBytes());
//			     xmlFile.add(filename);
//			}
//
//			
//		}	
		simul.close();
		return xmlFile;
		
	}
	 catch (IOException e) {

		e.printStackTrace();
	}
	
	return null;
}


	public  JProgressBar getSimulationProgressBar() {
		return simulationProgressBar;
	}


	public  void setSimulationProgressBar(JProgressBar simulationProgressBar) {
		this.simulationProgressBar = simulationProgressBar;
	}


	@Override
	public void run() {
		try {
			start(originalXml, automatorXml);
		} catch (DeusAutomatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}