package View;

import java.util.List;
import java.util.function.Consumer;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Note;

import Model.Tree;
import Wrappers.NPerson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeEditor extends TreeView<String>
{

    private Tree<String> tree;
    private TreeItem<String> rootNode;

    private Consumer<NPerson> onChange;
    private NPerson person;
    
    public TreeEditor(NPerson person, Consumer<NPerson> onChange)
    {
        this.person = person;
        this.onChange = onChange;
        
        PropertyEditor peRoot = PropertyEditor.createField(() -> person.getName(), (p) -> person.setName(p), changeListener);
        rootNode = new TreeItem<String>("Namn", peRoot.getNode());
        rootNode.setExpanded(false);
        this.setRoot(rootNode);
        addDummy();

        focusedProperty().addListener(new ChangeListener<Boolean>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                onChange.accept(person);
            }
        });
        
        rootNode.expandedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if(!oldValue && newValue)
                {
                    addContent();
                }
                else if(!newValue)
                {
                    clearContent();
                }
            }
        });
    }

    private void addContent()
    {
        clearContent();
        for(EventFact event : person.getEventsFacts())
        {
            TreeItem<String> eventNode = new TreeItem<String>("Event av typen " + event.getDisplayType());
            rootNode.getChildren().add(eventNode);
            PropertyEditor peDate = PropertyEditor.createField(() -> event.getDate(), (p) -> event.setDate(p), changeListener);
            PropertyEditor pePlace = PropertyEditor.createField(() -> event.getPlace(), (p) -> event.setPlace(p), changeListener);
            eventNode.getChildren().add(new TreeItem<String>("Datum", peDate.getNode()));
            eventNode.getChildren().add(new TreeItem<String>("Plats", pePlace.getNode()));
            
            addNoteNodes(event.getNotes(), eventNode);
        }
    }
    
    private void clearContent()
    {
        rootNode.getChildren().clear();
        addDummy();
    }
    
    private void addDummy()
    {
        rootNode.getChildren().add(new TreeItem<String>());
    }
    
    private void addNoteNodes(List<Note> notes, TreeItem<String> parentNode)
    {
        // Noteringar
        TreeItem<String> noteNode = new TreeItem<String>("Noteringar");
        parentNode.getChildren().add(noteNode);
        for(Note note : notes)
        {
            PropertyEditor peNote = PropertyEditor.createArea(() -> note.getValue(), (p) -> note.setValue(p), changeListener);
            noteNode.getChildren().add(new TreeItem<String>("Notering", peNote.getNode()));
        }
    }
    
    private ChangeListener<String> changeListener = new ChangeListener<String>()
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            onChange.accept(person);
        }
    };

}
