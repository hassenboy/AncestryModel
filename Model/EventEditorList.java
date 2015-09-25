package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.folg.gedcom.model.EventFact;

public class EventEditorList extends JPanel {

	private List<EventEditor> eventEditors = new ArrayList<EventEditor>();
	
	public EventEditorList() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addEmptyEvent();
		setOpaque(false);
	}
	
	public void clear()
	{
		eventEditors.clear();
		removeAll();
		addEmptyEvent();
	}

	public void addEvent(EventFact event)
	{
		eventEditors.get(eventEditors.size() - 1).setEvent(event);
		addEmptyEvent();
	}
	
	private void addEmptyEvent()
	{
		EventEditor eventEditor = new EventEditor();
		eventEditors.add(eventEditor);
		add(eventEditor);
	}

	public void addEvents(List<EventFact> residences) 
	    {
		for(EventFact e : residences)
		{
			addEvent(e);
		}
	}

	public void setEvents(List<EventFact> residences) {
		clear();
		addEvents(residences);
	}

//	public void applyChanges() {
//		for(EventEditor e : eventEditors)
//		{
//			e.applyChanges();
//		}
//	}
	
}
