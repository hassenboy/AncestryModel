package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.function.Function;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Note;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import model.wrappers.NFamily;
import model.wrappers.NGedcom;
import model.wrappers.NPerson;

public class PersonalFile
{
    private final static String templateName = "latex_template.tex";
    
    public static PDFFile latexToPdf(NGedcom gedcom, NPerson person) 
    {
        File tmpDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "GEDCOM_editor");
        
        File templateDir = new File("res");
        File template = new File(templateDir.getAbsolutePath() + File.separator + templateName);
        
        if(!templateDir.exists())
        {
            // running from jar
            System.out.println("Running from jar");
            templateDir = new File(tmpDir.getAbsolutePath());
            template = new File(templateDir.getAbsolutePath() + File.separator + templateName);
            // Copy latex template to tmpdir
            try
            {
                ExportResource("/res/" + templateName, template.getAbsolutePath());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Running from IDE");
        }
        
        File invoice = new File(tmpDir.getAbsolutePath() + File.separator + "JLRConverter_output.tex");
//        () -> readPDFFile(null);
        try {
            JLRConverter converter = new JLRConverter(templateDir);

            converter.replace("name", person.getName());
            
            converter.replace("birthDate", person.getBirthDate());
            converter.replace("baptizedDate", person.getBaptismDate());
            converter.replace("father", person.getFather(gedcom).getName());
            converter.replace("mother", person.getMother(gedcom).getName());
            converter.replace("fatherRef", person.getFather(gedcom).getId());
            converter.replace("moterRef", person.getMother(gedcom).getId());
            converter.replace("moterRef", person.getGodParent());

            converter.replace("deathDate", person.getDeathDate());
            converter.replace("burialPlace", person.getBurialPlace());
            converter.replace("age", person.getAge());
            
            // Residence
            ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();
            for(EventFact residenceEvent : person.getResidences())
            {
                ArrayList<String> subservice = new ArrayList<String>();

                String place = residenceEvent.getPlace();
                String date = residenceEvent.getDate();
                
                String note = "";
                for(Note n : residenceEvent.getNotes())
                {
                    note = note + n.getValue();
                };
                
                
                subservice.add(place);
                subservice.add(date);
                subservice.add(note);
                subservice.add("");
                
                services.add(subservice);
            }
            converter.replace("residences", services);

            // Marriages
            services = new ArrayList<ArrayList<String>>();
            for(NFamily fam : person.getSpouseFamilies(gedcom))
            {
                ArrayList<String> subservice = new ArrayList<String>();

                NPerson partner = person.getPartner(fam, gedcom);
                String partnerName = partner == null ? "okänd" : partner.getName();
                String marriageDate = fam.getMarriageDate();

                subservice.add(partnerName);
                subservice.add(fam.getMarriageDate());
                
//              // Children
                ArrayList<ArrayList<String>>childrenServices = new ArrayList<ArrayList<String>>();
                for(NPerson child: fam.getChildren(gedcom))
                {
                    ArrayList<String> childSubservice = new ArrayList<String>();

                    String childName = child.getName();
                    String childrenBirthDate = child.getBirthDate();
                    
                    childSubservice.add(childName);
                    childSubservice.add(childrenBirthDate); 
                    
                    childrenServices.add(childSubservice);
                }
                converter.replace("marriagesChildren", childrenServices);

                
                services.add(subservice);
            }
            converter.replace("marriages", services);


            if (!converter.parse(template, invoice)) 
            {
                System.out.println(converter.getErrorMessage());
            }

            JLRGenerator pdfGen = new JLRGenerator();

            if (!pdfGen.generate(invoice, tmpDir, templateDir)) 
            {
                System.out.println(pdfGen.getErrorMessage());
            }

            return readPDFFile(pdfGen.getPDF());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    private static PDFFile readPDFFile(File pdfFile) throws IOException
    {
        //load a pdf from a byte buffer
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        return new PDFFile(buf);
    }

    public static void ExportResource(String resourceName, String outDest) throws Exception
    {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = PersonalFile.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
//            jarFolder = new File(PersonalFile.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
//            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            resStreamOut = new FileOutputStream(new File(outDest));
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        }
        catch (Exception ex) {
            throw ex;
        }
        finally 
        {
            if(stream != null)
            {
                stream.close();
            }
            if(resStreamOut != null)
            {
                resStreamOut.close();
            }
        }
    }

    public static PDFPage getPersonalFile(NGedcom gedcom, NPerson person)
    {
        return latexToPdf(gedcom, person).getPage(0);
    }
    
}
