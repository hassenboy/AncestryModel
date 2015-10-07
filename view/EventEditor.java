package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Note;

public class EventEditor extends PropertyPanel {

	private EventFact event;

	private PropertyEditorOld placeEditor;
	private PropertyEditorOld dateEditor;
	private List<PropertyEditorOld> noteAreas = new ArrayList<PropertyEditorOld>();
//	private JTextField noteField;
	
	public EventEditor(EventFact event)
	{
	    this();
		setEvent(event);
	}
	
	public EventEditor() 
	{
	    super();
	    
		setOpaque(false);
	}

	public void setEvent(EventFact event)
	{
		this.event = event;

		this.removeAll();
		noteAreas.clear();
		
		setEnabled(event != null);
		if(event != null)
		{
	        
	        placeEditor = new PropertyEditorOld(() -> event.getPlace(), (s) -> event.setPlace(s));
	        addEditor(placeEditor, "Plats");

            dateEditor = new PropertyEditorOld(() -> event.getDate(), (s) -> event.setDate(s));
            addEditor(dateEditor, "Datum");

	        int i = 1;
	        for(Note note : event.getNotes())
	        {
	            JTextField a = new JTextField();
//	            a.setBorder(BorderFactory.createTitledBorder("Not " + i));
	            PropertyEditorOld editor = new PropertyEditorOld(() -> note.getValue(), (s) -> note.setValue(s));
//	            editor.setBorder(BorderFactory.createTitledBorder("Not " + i));
	            noteAreas.add(editor);
	            i++;
	        }
	        // TBD
//	        addEditors(noteAreas, "Noteringar");
	        
			this.revalidate();
			this.repaint();
		}
	}
	
	public EventFact getEvent()
	{
	    return event;
	}

//	public void applyChanges() {
//	    if(event != null)
//	    {
//    		event.setPlace(placeField.getText());
//    		event.setDate(dateField.getText());
//    		
//    		int i = 0;
//    		for(Note note : event.getNotes())
//    		{
//    			note.setValue(noteAreas.get(i).getText());
//    			i++;
//    		}
//	    }
//	}
	
}
