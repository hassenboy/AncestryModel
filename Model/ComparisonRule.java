package Model;

public interface ComparisonRule<T> {

	public boolean compare(T first, T second);
	
}
