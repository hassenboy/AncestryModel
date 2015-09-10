package Model;

public class BinaryTree<T> 
{
	private T content;
	private BinaryTree<T> first;
	private BinaryTree<T> second;

	// Constructor
	public BinaryTree()
	{
	}
	
	private void insert(T newContent, ComparisonRule<T> comparisonRule)
	{
		if(content == null)
		{
			content = newContent;
			return;
		}
		
		// if 'first' should be called recursively
		if(comparisonRule.compare(first.content, second.content))
		{
			first.insert(newContent,comparisonRule);
		}
		else
		{
			second.insert(newContent,comparisonRule);
		}
	}
}
