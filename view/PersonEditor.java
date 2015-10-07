package view;

import java.util.function.Consumer;

import org.folg.gedcom.model.EventFact;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.wrappers.NPerson;

public class PersonEditor extends TitledPane
{
    private final static String DEFAULT_STYLE_CLASS = "person-editor";
    
    private final static double INDENTATION_WIDTH = 70;
    
    // TBD to css
    private final static String LABEL_STYLE = "";

    private NPerson person;

    private Stack rootStack;

    private Consumer<NPerson> dataChangedListener;
    
    public PersonEditor(NPerson person)
    {
        super(person.getName(), null);
        this.person = person;

        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setExpanded(false);
        
        this.focusedProperty().addListener(new ChangeListener<Boolean>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                fireChangeEvent();
            }
        });
        
        this.expandedProperty().addListener(new ChangeListener<Boolean>()
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
    
    public void setDataChangedListener(Consumer<NPerson> listener)
    {
        this.dataChangedListener = listener;
    }
    
    private void fireChangeEvent()
    {
        System.out.println("Person; " + person.getName());
        {
            dataChangedListener.accept(person);
        }
    }

    private void clearContent()
    {
        rootStack.getChildren().clear();
    }

    private void addContent()
    {
        rootStack = new Stack();
        
        PropertyEditor peName = PropertyEditor.createField( () -> person.getName(), (s) -> person.setName(s), changeListener);
        PropertyEditor peDate = PropertyEditor.createField( () -> person.getBirthDate(), (s) -> person.setBirthDate(s), changeListener);
        PropertyEditor pePlace = PropertyEditor.createField( () -> person.getBaptismDate(), (s) -> person.setBaptismDate(s), changeListener);

        rootStack.add("Namn", peName, 0);
        rootStack.add("Födelsedatum", peDate, 0);
        rootStack.add("Födelseplats", pePlace, 0);

        for(EventFact event : person.getEventsFacts())
        {
            rootStack.addEventEditor(event);
        }
        
        setContent(rootStack);
    }
    
    private static VBox createStack()
    {
        VBox stack = new VBox();
        return stack;
    }
    
    private static Label createLabel(String s)
    {

        Label label = new Label(s);

        label.setStyle(LABEL_STYLE);

        label.setMinWidth(INDENTATION_WIDTH*2);
        label.setMaxWidth(INDENTATION_WIDTH*2);

        return label;
    }

    public NPerson getPerson()
    {
        return person;
    }
    
    private class Row extends HBox
    {
        Row()
        {
            setFillHeight(true);
            setAlignment(Pos.BOTTOM_LEFT);
        }
        
        void add(Node node, int indentation)
        {
            Pane space = new Pane();
            space.setMaxWidth(INDENTATION_WIDTH * indentation);
            space.setMinWidth(INDENTATION_WIDTH * indentation);
            getChildren().add(space);
            getChildren().add(node);
        }
        
    }
    
    private class Stack extends VBox
    {
        Stack()
        {
            
        }
        
        public void add(String title, PropertyEditor propertyEditor, int indentation)
        {
            Row row = new Row();

            row.add(createLabel(title), indentation);
            
            row.add(propertyEditor.getNode(), indentation);

            add(row);
        }

        void add(Node node)
        {
            getChildren().add(node);
        }
        
        void addEventEditor(EventFact event)
        {
            TitledPane tp = new TitledPane();
            tp.setText(event.getDisplayType());
            Stack stack = new Stack();

            stack.add("Plats", PropertyEditor.createField(() -> event.getPlace(), (p) -> event.setPlace(p), changeListener), 1);
            stack.add("Datum", PropertyEditor.createField(() -> event.getDate(), (p) -> event.setDate(p), changeListener), 1);

            tp.setContent(stack);
            add(tp);
        }
    }

    ChangeListener<String> changeListener = new ChangeListener<String>()
    {
        
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            fireChangeEvent();            
        }
    };
}
