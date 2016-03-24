import java.util.Arrays;

/**
 * @author mohith zacharias maratt
 * Hash table with linear probing (open-addressing)
 * V holds the type for value
 * Keys are assumed to be of type int in this implementation. A key cannot have the value below 0.
 * An expansion could be to use Array resizing when the hash table is about to be full. When N (number of elements)-
 *  -reaches close to M (prime number, size of hashtable) , we can use array resizing.
 *
 */
public class HashTableWithLinearProbing<V> 
{
	private final int M = 7; //fixed size of the hash table
	private V values[]; //holds the values
	private int keys[]; //holds the keys
	private int N; 	//number of elements in the hashtable
	
	
	/**
	 * constructor to initialize keys
	 */
	@SuppressWarnings("unchecked")
	public HashTableWithLinearProbing() 
	{
		values = (V[])new Object[M];
		keys = new int[M];
		java.util.Arrays.fill(keys, -1); //initiall fill the keys with -1
	}
	
	
	/**
	 * Returns the hash of the key
	 * @param key is key of the hashtable entered by the user
	 */
	private int hash(int key)
	{
		return (key % M);
	}
	
	
	/**
	 * @return true if the hashtable is empty
	 */
	public boolean isEmpty()
	{
		return (N==0);
	}
	
	
	/**
	 * @return the size of the hashtable (number of elements in the table)
	 */
	public int size()
	{
		return N;
	}
	
	/**
	 * Insert key - value pair into the hashtable
	 * @param key is the key of the hashtable into which the user wants to associate his value with
	 * @param value is the value the user wants to store
	 */
	public void put(int key, V value)
	{
		if(value == null  || key < 0)
		{
			System.err.println("Keys cannot be -ve or values cannot be null. Use delete() to delete keys-values.");
			return;
		}
		
		if(N==M)
		{
			System.err.println("The hashtable is full!");
			return;
		}
		
		int index = hash(key);
		
		while(keys[index]!=-1)
		{
			if(keys[index]==key) //update value
			{
				values[index] = value;
				return;
			}
			else
			{
				index = (index + 1) % M;
			}
		}
		keys[index] = key;
		values[index] = value;
		N++;
	}
	
	/**
	 * function to get a value for a particular key
	 * @param key is the key of the hashtable from which the user wants to get his value
	 * @return the value associated with the key entered by the user. Returns null if key is absent.
	 */
	public V get(int key)
	{
		int count = 0;	//keeps track of the number keys searched
		int index = hash(key);
		
		if(index < 0)
		{
			System.err.println("keys cannot be -1");
			return null;
		}

		while(keys[index] != key)
		{
			index = (index + 1) % M;
			count++;
			
			if(keys[index]==-1) 
			{
				System.err.println("The key "+key+" was not found in the hashtable");
				return null;
			}
			
			if(count > M)
			{
				return null;
			}
		}
		return values[index];
	}
	
	
	/**
	 * the contains tells if a key is present or not
	 * @param key that is required to be searched for
	 * @return true if a key is present
	 */
	public boolean contains(int key)
	{
		if(get(key)==null)return false;
		else return true;
	}
	
	
	/**
	 * function searches and deletes the key-value pair
	 * @param key that has to be deleted
	 * @return the value that is deleted, returns null if key is not found
	 */
	public V delete(int key)
	{
		if(key < 0)
		{
			System.err.println("Keys cannot be less than 0");
			return null;
		}
		if(!contains(key)){
			return null;
		}
		
		int index = hash(key);
		
		while(keys[index]!=key)
		{
			index = (index + 1)%M;
		}
			
		V value = values[index]; 
		keys[index] = -1;
		values[index] = null;
		N--;
			
		index = (index + 1)%M;
		while(keys[index]!=-1) //move elements one slot up until an empty slot is encountered
		{
			if(keys[index]!=key) //stop shifting elements if you find a key in its right location
				return value;
			
			int keyTemp = keys[index];
			V valueTemp = values[index];
			keys[index] = -1;
			values[index] = null;
			N--;
			put(keyTemp, valueTemp);
			index = (index + 1) % M;
		}
		return value;
	}
	
	
	/**
	 * unit testing
	 * @param args
	 */
	public static void main(String[] args) 
	{
		HashTableWithLinearProbing<String> ht = new HashTableWithLinearProbing<>();
		System.out.println("\n______putting______\n"); //some testing
		ht.put(7,"7");
		ht.put(3,"3");
		ht.put(0,"0");
		ht.put(11, "11");
		ht.put(7,"7");
		ht.put(3,"33");
		ht.put(0,"0");
		ht.put(11, "11");
		ht.put(1, null);
		ht.put(-1, "negative");
		ht.put(1, "1");
		
		System.out.println("\n______getting______\n");
		System.out.println(ht.get(1));
		System.out.println(ht.get(3));
		System.out.println(ht.get(4));
		System.out.println(ht.get(11));
		System.out.println(ht.get(0));
		
		System.out.println("\n______deleting______\n");
		System.out.println("ht.delete(7) : "+ht.delete(7));
		System.out.println("ht.delete(7) : "+ht.delete(7));
		System.out.println("ht.delete(0) : "+ ht.delete(0));
		System.out.println("ht.delete(-1) : "+ht.delete(-1));

		System.out.println("done");
	}

}
