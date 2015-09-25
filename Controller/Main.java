package Controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.SwingUtilities;

import org.xml.sax.SAXParseException;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PagePanel;

import Model.PersonalFile;
import View.PersonEditor;
import View.TreeEditor;
import Wrappers.NGedcom;
import Wrappers.NPerson;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{

    private PDFFile pdfFile;
    private PagePanel pdfPanel;
    private VBox leftPanel;
    private VBox personEditorPane;
    private SwingNode rightPanel;
    private Label filterLabel;
    
    private NGedcom gedcom;
    
    private List<PersonEditor> personEditors;

    private ComboBox<NPerson> personChooser;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
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
        
        leftPanel = new VBox();

        personChooser = new ComboBox<NPerson>();
        personChooser.setItems(FXCollections.observableArrayList(gedcom.getPeople()));
//        personChooser.setConverter(new PersonStringConverter(gedcom));
        personChooser.valueProperty().addListener(new ChangeListener<NPerson>()
        {
            @Override
            public void changed(ObservableValue<? extends NPerson> observable, NPerson oldValue, NPerson newValue)
            {
                personSelected(newValue);
            }
        });
        personChooser.setEditable(true);
        leftPanel.getChildren().add(personChooser);
        
        filterLabel = new Label();
        leftPanel.getChildren().add(filterLabel);
        
        personEditorPane = new VBox();
        personEditors = new ArrayList<PersonEditor>(gedcom.getPeople().size());
        for(NPerson p : gedcom.getPeople())
        {
            PersonEditor pe = new PersonEditor(p);
            pe.setDataChangedListener((arg) -> updatePdf(arg));
            personEditors.add(pe);
        }
        personEditors.sort(new Comparator<PersonEditor>()
        {
            @Override
            public int compare(PersonEditor o1, PersonEditor o2)
            {
                return o1.getPerson().getName().compareTo(o2.getPerson().getName());
            }
        });
        
        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setContent(personEditorPane);
        leftPanel.getChildren().add(sp);
        
        rightPanel = new SwingNode();
        
        pdfPanel = new PagePanel();
        pdfPanel.setOpaque(true);
        pdfPanel.setBackground(Color.WHITE);
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                rightPanel.setContent(pdfPanel);
            }
        });
        
        primaryStage.setTitle("Gedcom editor");

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(leftPanel);
        splitPane.getItems().add(rightPanel);
        
        Scene scene = new Scene(splitPane, 1200, 1000);
        scene.getStylesheets().add("res/css/caspian.css");
        scene.getStylesheets().add("res/css/gedcom_editor_stylesheet.css");
        primaryStage.setScene(scene);
        
//        personSelected(gedcom.getPeople().get(0));
        
        primaryStage.show();
        splitPane.setDividerPositions(0.5);
    }


    private void personSelected(NPerson person)
    {
        personEditorPane.getChildren().clear();
        personEditorPane.getChildren().add(new TreeEditor(person, (p) -> updatePdf(p)));
        updatePdf(person);
    }
    
//    private void filterList(String filter)
//    {
//        personEditorPane.getChildren().clear();
//        int hits = 0;
//        for(NPerson person : gedcom.getPeople())
//        {
//            if(person.getName().toLowerCase().contains(filter.toLowerCase()))
//            {
//                hits++;
//                personEditorPane.getChildren().add(new TreeEditor(person, (per) -> updatePdf(per)));
//            }
//        }
//        filterLabel.setText(hits + " träffar");
//    }
    
    private void updatePdf(NPerson person)
    {
        pdfPanel.showPage(PersonalFile.getPersonalFile(gedcom, person));
    }
}
