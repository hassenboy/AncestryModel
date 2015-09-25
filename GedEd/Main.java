package GedEd;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.tools.xjc.model.Model;

import Model.EditorFrame;
import Wrappers.NGedcom;

public class Main {

    // TBR
    private static File debugFile = new File("/home/johannes/workspace/NyaAnor/convert_to_ged/gedcomfiles/Personakt_T24_wordexported.ged");

    public static boolean DEBUG_MODE = false;
    
	public static void mainTemp(String[] args) {
//
//	    View view = new View();
//	    
//	    model.addObserver(view);
//	    
//	    Controller controller = new Controller();
//	    controller.addModel(model);
//	    controller.addView(view);
//	    
//	    controller.initModel(start_value);
//	    
//	    view.addController(controller);
	    
//	    File gedcomFile;
//	    if(DEBUG_MODE)
//	    {
//	        gedcom = NGedcom.loadGedcom(debugFile);	        
//	    }
//	    else
//	    {
//	        if(args.length != 1)
//	        {
//	            JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")));
//	            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//	            jfc.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
//	            int result = jfc.showOpenDialog(null);
//	            if(result == JFileChooser.OPEN_DIALOG)
//	            {
//	                
//	            }
//	        }
//	        else
//	        {
//	            gedcom = NGedcom.loadGedcom(new File(args[0]));
//	        }
//	    }
//		
//	    if(gedcomFile != null)
//	    {
//    	    NGedcom gedcom = NGedcom.loadGedcom(gedcomFile);
//    		new EditorFrame(gedcom);
//	    }
//	    else
//	    {
//	        JOptionPane.showConfirmDialog(null, "No valid GEDCOM file could be found.", "ERROR", JOptionPane., messageType, icon)
//	    }
//	    }
//		try {

// 			JAXBContext jaxbContext = JAXBContext.newInstance(Pair.class);
						// output pretty printed
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			jaxbMarshaller.marshal(modelData, dataFile);
//
//			//
//			// jc = JAXBContext.newInstance(ModelData.class);
//			//
//			// Unmarshaller unmarshaller = jc.createUnmarshaller();
//			// ModelData tests = (ModelData) unmarshaller.unmarshal(dataFile);
//			//
//			// Marshaller marshaller = jc.createMarshaller();
//			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			// marshaller.marshal(tests, System.out);
//			
//			latexToPdf(thomas);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
	}
	
	private void assertNotNull(Object o)
	{
		if(o == null)
		{
			throw new NullPointerException("Assert null");
		}
	}

	private static String nullToEmptyStr(String str) {
		if(str == null)
		{
			return "";
		}
		return str;
	}

}










//List<NPerson> persons = gedcom.getPeople();
//NPerson person = persons.get(2); // Arnold Lindgren
//for(NPerson p : gedcom.getPeople())
//{
//    System.out.println();
//    System.out.println(p.getName());
//    if(p.getFather(gedcom) != null)
//        System.out.println("    far: " + p.getFather(gedcom).getName());
//    if(p.getMother(gedcom) != null)
//        System.out.println("    mor: " + p.getMother(gedcom).getName());
//    if(p.getSiblings(gedcom).size() > 1)
//    {
//        System.out.println("    syskon:");
//        for(NPerson s : p.getSiblings(gedcom))
//        {
//            System.out.println("        " + s.getName());
//        }
//    }
//    for(NFamily f : p.getSpouseFamilies(gedcom))
//    {
////      System.out.println("barn med " + f.getHusband(gedcom).getName());
//        if(p.getPartner(f, gedcom) != null)
//            System.out.println("    barn med " + p.getPartner(f, gedcom).getName() + ":");
//        else
//            System.out.println("     barn med okänd");
//                
//        for(NPerson c : f.getChildren(gedcom))
//        {
//            System.out.println("        " + c.getName());
//        }
//    }
//}
