package Model;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import View.PersonSelectedListener;
import View.PropertyPanel;
import Wrappers.NGedcom;
import Wrappers.NPerson;

public class PersonEditorOld extends PropertyPanel
{
	
	private NGedcom gedcom;
	private NPerson person;
	
    private AutoCompleteBox<NPerson> fatherBox;
    private AutoCompleteBox<NPerson> motherBox;
    
	private EventEditor birthEventEditor;
	private EventEditorList residenceEventEditors = new EventEditorList();
	
    private PersonSelectedListener personSelectedListener;
	
	public PersonEditorOld(NGedcom gedcom)
	{
	    super();
	    this.gedcom = gedcom;

		setOpaque(true);
		setBackground(Color.WHITE);
		
        setBorder(BorderFactory.createTitledBorder("Personinformation"));
        GridBagConstraints gbc;
        
        fatherBox = new AutoCompleteBox<NPerson>(gedcom.getPeople(), (p) -> p.getName(), (p) -> setFather(p));
        
        motherBox = new AutoCompleteBox<NPerson>(gedcom.getPeople(), (p) -> p.getName(), (p) -> setMother(p));
        
        setPerson(person);
	}

    private void setFather(NPerson father)
    {
        person.setFather(father);   
    }
    
	private void setMother(NPerson mother)
    {
        person.setMother(mother);
    }

    public void setPerson(NPerson person)
	{
        this.person = person;

        this.removeAll();
        
        if(person != null)
        {
            addEditor(motherBox, "Mor");
            
            addEditor(fatherBox, "Far");
            
            birthEventEditor = new EventEditor();
            addEditor(birthEventEditor, "Födelse");
            
            residenceEventEditors = new EventEditorList();
            addEditor(residenceEventEditors, "Bosättningar");
            
//            applyChanges();	
    		birthEventEditor.setEvent(person.getBirthEvent());
    		residenceEventEditors.setEvents(person.getResidences());
    		
            fatherBox.setSelectedElement(person.getFather(gedcom));
            motherBox.setSelectedElement(person.getMother(gedcom));
    		
    		if(personSelectedListener != null)
    		{
                personSelectedListener.personSelected(person);
    		}
        }	
        
        this.revalidate();
        this.repaint();
	}
	
//	public void applyChanges()
//	{
//        birthEventEditor.applyChanges();
//        residenceEventEditors.applyChanges();
//	}
}
