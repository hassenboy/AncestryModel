package view;

import java.util.List;
import java.util.function.Consumer;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Note;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.wrappers.NPerson;

public class PersonTreeEditor extends TreeView<String>
{

    private Tree<String> tree;
    private TreeItem<String> rootNode;

    private Consumer<NPerson> onChangeListener;
    private NPerson rootPerson;
    private ChangeListener<Boolean> focusedListener;
    
    public PersonTreeEditor(NPerson rootPerson)
    {
        setRootPerson(rootPerson);
    }


    public void setOnChangeListener(Consumer<NPerson> onChangeListener)
    {
        this.onChangeListener = onChangeListener;

        if(focusedListener != null)
        {
            focusedProperty().removeListener(focusedListener);
        }
        
        if(onChangeListener != null)
        {
            focusedListener = new ChangeListener<Boolean>()
            {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    onChangeListener.accept(rootPerson);
                }
            };
            focusedProperty().addListener(focusedListener);
        }
        else
        {
            throw new NullPointerException();
        }
    }
    
    private void addPerson()
    {
        
    }

    private void initContent()
    {
        clearContent();
        PropertyEditor nameEditor = PropertyEditor.createField(() -> rootPerson.getName(), (p) -> rootPerson.setName(p), changeListener);
        rootNode = new TreeItem<String>("Namn", nameEditor.getNode());
        rootNode.setExpanded(false);
        setRoot(rootNode);

        rootNode.expandedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if(!oldValue && newValue)
                {
                    initContent();
                }
                else if(!newValue)
                {
                    clearContent();
                }
            }
        });
        // Name
        
        // Events
        for(EventFact event : rootPerson.getEventsFacts())
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
        getChildren().clear();
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
            onChangeListener.accept(rootPerson);
        }
    };

    public NPerson getRootPerson()
    {
        return rootPerson;
    }

    public void setRootPerson(NPerson rootPerson)
    {
        this.rootPerson = rootPerson;
        initContent();
    }

}
