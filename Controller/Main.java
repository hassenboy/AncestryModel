package Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.xml.sax.SAXParseException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.wrappers.NGedcom;
import model.wrappers.NPerson;
import view.GedcomEditor;
import view.GedcomNavigator;

public class Main extends Application
{

    private final static File RES_DIR = new File("res");
    private final static File CSS_DIR = new File(RES_DIR.getPath() + File.separator + "css");
    
    private NGedcom gedcom;
    
    private BorderPane contentDisplayed;
    private GedcomNavigator navigator;
    private GedcomEditor editor;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        gedcom = loadGedcom();
        
        contentDisplayed = new BorderPane();
        
        navigator = new GedcomNavigator(gedcom);
        navigator.setOnChosenListener((p) -> showEditor(p));
        editor = new GedcomEditor(gedcom, gedcom.getPeople().get(0));
        editor.setOnCloseListener((p) -> showNavigator(p));

        contentDisplayed.setCenter(navigator);
        
        Scene scene = new Scene(contentDisplayed, 1200, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nya Anor");
        
        addStyleSheets(scene);

        primaryStage.show();

        editor.setDividerPosition(0.5);
        navigator.setDividerPosition(0.2);
    }
    
    private void addStyleSheets(Scene scene)
    {
        File[] cssFiles = CSS_DIR.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".css");
            }
        });
        for(File f : cssFiles)
        {
            System.out.println(f.getPath());
            scene.getStylesheets().add(f.getPath());
        }
    }
    
    public void showEditor(NPerson rootPerson)
    {
        editor.setRoot(rootPerson);
        contentDisplayed.setCenter(editor);
    }
    
    public void showNavigator(NPerson rootPerson)
    {
        navigator.setRoot(rootPerson);
        contentDisplayed.setCenter(navigator);
    }
    
    public NGedcom loadGedcom()
    {
        try
        {
            gedcom = new NGedcom(new File("/home/johannes/workspace/NyaAnor/convert_to_ged/gedcomfiles/Personakt_T24_wordexported.ged"));
        } catch (SAXParseException | IOException e)
        {
            // TBD Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        return gedcom;
    }
}
