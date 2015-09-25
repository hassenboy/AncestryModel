package Wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.folg.gedcom.model.Change;
import org.folg.gedcom.model.ChildRef;
import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Family;
import org.folg.gedcom.model.LdsOrdinance;
import org.folg.gedcom.model.Media;
import org.folg.gedcom.model.MediaRef;
import org.folg.gedcom.model.Note;
import org.folg.gedcom.model.NoteRef;
import org.folg.gedcom.model.SourceCitation;
import org.folg.gedcom.model.SpouseRef;
import org.folg.gedcom.model.Visitor;

public class NFamily {
	
	private Family family;
	private NGedcom gedcom;
	
	public NFamily(Family f)
	{
		this.family = f;
	}
	
	//
	// =======================================Delegate methods ========================================================
	//
	
	public NPerson getHusband(NGedcom gedcom)
	{
		if(family.getHusbands(gedcom.getGedcom()).size() > 0)
		{
			return gedcom.getPerson(family.getHusbands(gedcom.getGedcom()).get(0).getId());
		}
		return null;
	}

	public NPerson getWife(NGedcom gedcom)
	{
		if(family.getWives(gedcom.getGedcom()).size() > 0)
		{
			return gedcom.getPerson((family.getWives(gedcom.getGedcom()).get(0).getId()));
		}
		return null;
	}

	public Map<String, Object> getExtensions() {
		return family.getExtensions();
	}

	public List<Media> getAllMedia(NGedcom gedcom) {
		return family.getAllMedia(gedcom.getGedcom());
	}

	public List<Note> getAllNotes(NGedcom gedcom) {
		return family.getAllNotes(gedcom.getGedcom());
	}

	public Object getExtension(String key) {
		return family.getExtension(key);
	}

	public List<EventFact> getEventsFacts() {
		return family.getEventsFacts();
	}

	public String getId() {
		return family.getId();
	}

	public void addSourceCitation(SourceCitation sourceCitation) {
		family.addSourceCitation(sourceCitation);
	}

	public void addEventFact(EventFact eventFact) {
		family.addEventFact(eventFact);
	}

	public List<MediaRef> getMediaRefs() {
		return family.getMediaRefs();
	}

	public List<NoteRef> getNoteRefs() {
		return family.getNoteRefs();
	}

	public List<LdsOrdinance> getLdsOrdinances() {
		return family.getLdsOrdinances();
	}

	public void addNoteRef(NoteRef noteRef) {
		family.addNoteRef(noteRef);
	}

	public void addMediaRef(MediaRef mediaRef) {
		family.addMediaRef(mediaRef);
	}

	public List<Note> getNotes() {
		return family.getNotes();
	}

	public List<NPerson> getHusbands(NGedcom gedcom) {
		return NPerson.toNPersonList(family.getHusbands(gedcom.getGedcom()), gedcom);
	}
	
	public List<NPerson> getWives(NGedcom gedcom) {
		return NPerson.toNPersonList(family.getWives(gedcom.getGedcom()), gedcom);
	}

	public List<Media> getMedia() {
		return family.getMedia();
	}

	public void addLdsOrdinance(LdsOrdinance ldsOrdinance) {
		family.addLdsOrdinance(ldsOrdinance);
	}

	public void addNote(Note note) {
		family.addNote(note);
	}

	public void addMedia(Media mediaObject) {
		family.addMedia(mediaObject);
	}

	public List<SpouseRef> getHusbandRefs() {
		return family.getHusbandRefs();
	}

	public void addReferenceNumber(String refn) {
		family.addReferenceNumber(refn);
	}

	public void addHusband(SpouseRef husband) {
		family.addHusband(husband);
	}

	public Change getChange() {
		return family.getChange();
	}

	public void addWife(SpouseRef wife) {
		family.addWife(wife);
	}

	public List<NPerson> getChildren(NGedcom gedcom) {
		return NPerson.toNPersonList(family.getChildren(gedcom.getGedcom()), gedcom);
	}

	public List<ChildRef> getChildRefs() {
		return family.getChildRefs();
	}

	public void addChild(ChildRef childRef) {
		family.addChild(childRef);
	}

	public void accept(Visitor visitor) {
		family.accept(visitor);
	}

	public List<SourceCitation> getSourceCitations() {
		return family.getSourceCitations();
	}

	public void setId(String id) {
		family.setId(id);
	}

	public void putExtension(String id, Object extension) {
		family.putExtension(id, extension);
	}

	public List<String> getReferenceNumbers() {
		return family.getReferenceNumbers();
	}

	public String getRin() {
		return family.getRin();
	}

	public void setRin(String rin) {
		family.setRin(rin);
	}

	public void setChange(Change chan) {
		family.setChange(chan);
	}

	public String getUid() {
		return family.getUid();
	}

	public void setUid(String uid) {
		family.setUid(uid);
	}

	public String getUidTag() {
		return family.getUidTag();
	}

	public void setUidTag(String uidTag) {
		family.setUidTag(uidTag);
	}

	public void visitContainedObjects(Visitor visitor) {
		family.visitContainedObjects(visitor);
	}

	public List<SpouseRef> getWifeRefs() {
		return family.getWifeRefs();
	}

	public int hashCode() {
		return family.hashCode();
	}

	public String toString() {
		return family.toString();
	}
	
	public boolean equals(Object obj) {
		return family.equals(obj);
	}
	
	public NPerson getPartner(NPerson p, NGedcom gedcom)
	{
		if(p.equals(getWife(gedcom)) && !p.equals(getHusband(gedcom)))
		{
			return getHusband(gedcom);
		}
		else if(!p.equals(getWife(gedcom)) && p.equals(getHusband(gedcom)))
		{
			return getWife(gedcom);
		}
		else
		{
			return null;
		}
	}
	
	private EventFact getEvent(String key)
	{
		for(EventFact ef : family.getEventsFacts())
		{
			if(ef.getDisplayType().equals(key))
			{
				return ef;
			}

		}
		return null;
	}
	
	private List<EventFact> getEvents(String key)
	{
		List<EventFact> retList = new ArrayList<EventFact>();
		for(EventFact ef : family.getEventsFacts())
		{
			if(ef.getDisplayType().equals(key))
			{
				retList.add(ef);
			}

		}
		return retList;
	}
	
	public String getMarriageDate()
	{
		EventFact evMarr = getEvent("Marriage");
		if(evMarr == null)
		{
			return null;
		}
		return evMarr.getDate();
	}

}
