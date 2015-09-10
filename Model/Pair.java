package Model;

import javax.xml.bind.annotation.*;

public class Pair<T1, T2> {

	@XmlElement(name="first")
	public T1 first;

	@XmlElement(name="second")
	public T2 second;
	
	public Pair()
	{
	}
	
	public Pair(T1 first, T2 second)
	{
		this.first = first;
		this.second = second;
	}
	
}
