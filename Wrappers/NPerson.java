package Wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.folg.gedcom.model.Address;
import org.folg.gedcom.model.Association;
import org.folg.gedcom.model.Change;
import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Family;
import org.folg.gedcom.model.LdsOrdinance;
import org.folg.gedcom.model.Media;
import org.folg.gedcom.model.MediaRef;
import org.folg.gedcom.model.Name;
import org.folg.gedcom.model.Note;
import org.folg.gedcom.model.NoteRef;
import org.folg.gedcom.model.ParentFamilyRef;
import org.folg.gedcom.model.Person;
import org.folg.gedcom.model.SourceCitation;
import org.folg.gedcom.model.SpouseFamilyRef;
import org.folg.gedcom.model.Visitor;



public class NPerson {

    public enum EventTag {
        Marriage("Marriage"), 
        Birth("Birth"), 
        Burial("Burial"), 
        Residence("Residence"), 
        Occupation("Occupation"), 
        Death("Death"), 
        Sex("Sex"),
        Christening("Christening"),
        Baptism("Baptism"),
        Divorce("Divorce");
        
        private final String strTag;
        EventTag(String s)
        {
            this.strTag = s;
        }
        
        @Override
        public String toString()
        {
            return strTag;
        }
    }
    
	private Person person;
	
	public NPerson(Person per)
	{
		this.person = per;
	}
	
	//
	// =======================================Delegate methods ========================================================
	//
	
	public Map<String, Object> getExtensions() {
		return person.getExtensions();
	}

	public List<SourceCitation> getSourceCitations() {
		return person.getSourceCitations();
	}

	public Object getExtension(String key) {
		return person.getExtension(key);
	}

	public List<EventFact> getEventsFacts() {
		return person.getEventsFacts();
	}

	public List<MediaRef> getMediaRefs() {
		return person.getMediaRefs();
	}

	public List<NoteRef> getNoteRefs() {
		return person.getNoteRefs();
	}

	public void putExtension(String id, Object extension) {
		person.putExtension(id, extension);
	}

	public String getId() {
		return person.getId();
	}

	public List<LdsOrdinance> getLdsOrdinances() {
		return person.getLdsOrdinances();
	}

	public List<Name> getNames() {
		return person.getNames();
	}

	public List<Note> getNotes() {
		return person.getNotes();
	}

	public List<Media> getMedia() {
		return person.getMedia();
	}

	public List<String> getReferenceNumbers() {
		return person.getReferenceNumbers();
	}

	public List<NFamily> getParentFamilies(NGedcom gedcom) 
	{
		List<NFamily> parentFamilies = new ArrayList<NFamily>();
		for(Family f : person.getParentFamilies(gedcom.getGedcom()))
		{
			parentFamilies.add(gedcom.getFamily(f.getId()));
		}
		return parentFamilies;
	}

	public String getRin() {
		return person.getRin();
	}

	public Change getChange() {
		return person.getChange();
	}

	public List<ParentFamilyRef> getParentFamilyRefs() {
		return person.getParentFamilyRefs();
	}

	public String getUid() {
		return person.getUid();
	}

	public String getUidTag() {
		return person.getUidTag();
	}

	public List<SpouseFamilyRef> getSpouseFamilyRefs() {
		return person.getSpouseFamilyRefs();
	}

	public List<Association> getAssociations() {
		return person.getAssociations();
	}

	public String getDescendantInterestSubmitterRef() {
		return person.getDescendantInterestSubmitterRef();
	}

	public String getRecordFileNumber() {
		return person.getRecordFileNumber();
	}

	public String getPhone() {
		return person.getPhone();
	}

	public String getFax() {
		return person.getFax();
	}

	public String getEmail() {
		return person.getEmail();
	}

	public String getEmailTag() {
		return person.getEmailTag();
	}

	public String getWww() {
		return person.getWww();
	}

	public String getWwwTag() {
		return person.getWwwTag();
	}

	public int hashCode() {
		return Integer.parseInt(person.getId().substring(1));
	}

	public void setId(String id) {
		person.setId(id);
	}

	public void setRin(String rin) {
		person.setRin(rin);
	}

	public void setChange(Change chan) {
		person.setChange(chan);
	}

	public void setUid(String uid) {
		person.setUid(uid);
	}

	public void setUidTag(String uidTag) {
		person.setUidTag(uidTag);
	}

	public void visitContainedObjects(Visitor visitor) {
		person.visitContainedObjects(visitor);
	}

	public void setAncestorInterestSubmitterRef(String anci) {
		person.setAncestorInterestSubmitterRef(anci);
	}

	public void setDescendantInterestSubmitterRef(String desi) {
		person.setDescendantInterestSubmitterRef(desi);
	}

	public void setRecordFileNumber(String rfn) {
		person.setRecordFileNumber(rfn);
	}

	public void setAddress(Address addr) {
		person.setAddress(addr);
	}

	public void setPhone(String phon) {
		person.setPhone(phon);
	}

	public void setFax(String fax) {
		person.setFax(fax);
	}

	public void setEmail(String email) {
		person.setEmail(email);
	}

	public void setEmailTag(String emailTag) {
		person.setEmailTag(emailTag);
	}

	public void setWww(String www) {
		person.setWww(www);
	}

	public void setWwwTag(String wwwTag) {
		person.setWwwTag(wwwTag);
	}

	public String toString() {
		return person.toString();
	}

	public List<Media> getAllMedia(NGedcom gedcom) {
		return person.getAllMedia(gedcom.getGedcom());
	}

	public List<Note> getAllNotes(NGedcom gedcom) {
		return person.getAllNotes(gedcom.getGedcom());
	}

	public String getAncestorInterestSubmitterRef() {
		return person.getAncestorInterestSubmitterRef();
	}

	public void addSourceCitation(SourceCitation sourceCitation) {
		person.addSourceCitation(sourceCitation);
	}

	public void addNoteRef(NoteRef noteRef) {
		person.addNoteRef(noteRef);
	}

	public void addMediaRef(MediaRef mediaRef) {
		person.addMediaRef(mediaRef);
	}

	public void addName(Name name) {
		person.addName(name);
	}

	public void addNote(Note note) {
		person.addNote(note);
	}

	public void addReferenceNumber(String refn) {
		person.addReferenceNumber(refn);
	}

	public void addParentFamilyRef(ParentFamilyRef parentFamilyRef) {
		person.addParentFamilyRef(parentFamilyRef);
	}

	public void addSpouseFamilyRef(SpouseFamilyRef spouseFamilyRef) {
		person.addSpouseFamilyRef(spouseFamilyRef);
	}

	public boolean equals(Object obj) {
//		return person.equals(obj);
		if(obj instanceof NPerson)
		{
			return ((NPerson)obj).getId().equals(this.getId()); // TBD improve
		}
		else
		{
			return false;
		}
	}

	public Address getAddress() {
		return person.getAddress();
	}

	public void addEventFact(EventFact eventFact) {
		person.addEventFact(eventFact);
	}

	public void addLdsOrdinance(LdsOrdinance ldsOrdinance) {
		person.addLdsOrdinance(ldsOrdinance);
	}

	public void addMedia(Media mediaObject) {
		person.addMedia(mediaObject);
	}

	public void addAssociation(Association asso) {
		person.addAssociation(asso);
	}

	public void accept(Visitor visitor) {
		person.accept(visitor);
	}

	public String getName()
	{
		if(person.getNames().size() > 0)
		{
			String name = person.getNames().get(0).getDisplayValue();
			return name.replaceAll("/", ""); // TBD
		}
		else
			return null;
	}

	private EventFact getEvent(EventTag ev)
	{
		for(EventFact ef : person.getEventsFacts())
		{
			if(ef.getDisplayType().equals(ev.toString()))
			{
				return ef;
			}

		}
		return null;
	}
	
	private List<EventFact> getEvents(EventTag ev)
	{
		List<EventFact> retList = new ArrayList<EventFact>();
		for(EventFact ef : person.getEventsFacts())
		{
			if(ef.getDisplayType().equals(ev.toString()))
			{
				retList.add(ef);
			}

		}
		return retList;
	}
	
	public EventFact getBirthEvent()
	{
		return getEvent(EventTag.Birth);
	}

	public String getBirthDate() {
        EventFact ev = getBirthEvent();
        String s = null;
        if(ev != null)
            s = ev.getDate();
        return s == null ? "" : s;
	}
	
	public String getBirthPlace() 
	{
        EventFact ev = getBirthEvent();
        String s = null;
        if(ev != null)
            s = ev.getPlace();
        return s == null ? "" : s;
	}
	
	public NPerson getFather(NGedcom gedcom) 
	{
		if(getParentFamilies(gedcom).size() > 0)
		{
			return getParentFamilies(gedcom).get(0).getHusband(gedcom);
		}
		return null;
	}

	public NPerson getMother(NGedcom gedcom)
	{
		if(getParentFamilies(gedcom).size() > 0)
		{
			return getParentFamilies(gedcom).get(0).getWife(gedcom);
		}
		return null;
	}
	
	public static List<NPerson> toNPersonList(List<Person> list, NGedcom gedcom)
	{
		List<NPerson> retList = new ArrayList<NPerson>(list.size());
		for(Person p : list)
		{
			retList.add(gedcom.getPerson(p.getId()));
		}
		return retList;
	}
	
	public List<EventFact> getResidences()
	{
		return getEvents(EventTag.Residence);
	}
	
	public List<NPerson> getPartners(NGedcom gedcom)
	{
		List<NPerson> partners = new ArrayList<NPerson>();
		for(NFamily p : getSpouseFamilies(gedcom))
		{
			NPerson partner = p.getHusband(gedcom).equals(this) ? p.getWife(gedcom) : p.getHusband(gedcom);
			partners.add(partner);
		}
		return partners;
	}
	
	public List<NFamily> getSpouseFamilies(NGedcom gedcom) 
	{
		List<NFamily> spouseFamily = new ArrayList<NFamily>();
		for(Family f : person.getSpouseFamilies(gedcom.getGedcom()))
		{
			spouseFamily.add(gedcom.getFamily(f.getId()));
		}
		return spouseFamily;
	}
	
	public NPerson getPartner(NFamily family, NGedcom gedcom)
	{
		return family.getPartner(this, gedcom);
	}
	
	public List<NPerson> getOffspring(NGedcom gedcom)
	{
		List<NPerson> offsprings = new ArrayList<NPerson>();
		for(NFamily f : getSpouseFamilies(gedcom))
		{
			for(NPerson p : f.getChildren(gedcom))
			{
				if(!offsprings.contains(p))
				{
					offsprings.add(p);
				}
			}
		}
		return offsprings;
	}
	
	public List<NPerson> getSiblings(NGedcom gedcom)
	{
		List<NPerson> siblings = new ArrayList<NPerson>();
		if(getFather(gedcom) != null)
		{
			for(NPerson fc : getFather(gedcom).getOffspring(gedcom))
			{
				if(!siblings.contains(fc))
				{
					siblings.add(fc);
//					System.out.println("added "  + fc.getId() + ", " + fc.getName());
				}
			}
		}
		if(getMother(gedcom) != null)
		{
			for(NPerson mc : getMother(gedcom).getOffspring(gedcom))
			{
				if(!siblings.contains(mc))
				{
					siblings.add(mc);
//					System.out.println("added " + mc.getId() + ", " + mc.getName());
				}
			}
		}
		siblings.remove(this);
		return siblings;
	}

    public void setFather(NPerson father)
    {
        // TBD
        System.out.println("setFather() not yet implemented");
    }

    public void setMother(NPerson mother)
    {
        // TBD
        System.out.println("setMother() not yet implemented");
    }

    public String getBaptismDate()
    {
        EventFact ev = getBaptismEvent();
        String s = null;
        if(ev != null)
            s = ev.getDate();
        return s == null ? "" : s;
    }

    public NPerson getGodParent()
    {
        // TODO Auto-generated method stub
        return this;
    }

    public String getDeathDate()
    {
        EventFact ev = getDeathEvent();
        String s = null;
        if(ev != null)
            s = ev.getDate();
        return s == null ? "" : s;
    }
    
    public String getBurialPlace()
    {
        EventFact ev = getBurialEvent();
        String s = null;
        if(ev != null)
            s = ev.getPlace();
        return s == null ? "" : s;
    }

    private EventFact getDeathEvent()
    {
        return getEvent(EventTag.Death);
    }
    
    public EventFact getBurialEvent()
    {
        return getEvent(EventTag.Burial);
    }

    public EventFact getBaptismEvent()
    {
        return getEvent(EventTag.Baptism);
    }

    public String getAge()
    {
        return "ålder ej beräknad...";
    }
    
    public void setName(String newName)
    {
        if(person.getNames().size() > 0)
        {
            Name name = new Name();
            person.addName(name);
        }
        person.getNames().get(0).setValue(newName);
    }

    public void setBirthDate(String birthDate) {
        getBirthEvent().setDate(birthDate);
    }

    public void setBaptismDate(String s)
    {
        getBaptismEvent().setDate(s);
    }
    
}
