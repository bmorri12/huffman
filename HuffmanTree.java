package huffmantree;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HuffmanTree {

	HuffmanNode root;
	
	/*This constructor sets this.root to huff. Before
calling this constructor, we make a BinaryHeap of HuffmanNodes where each node has its left &
right pointers set appropriately via the HuffmanNode constructor with node parameters above.
Once the final HuffmanNode (containing all the others) is removed from the heap, we make that
into a HuffmanTree object by calling this constructor.*/
	public HuffmanTree(HuffmanNode huff)
	{
		this.root = huff;
	}
	
/*This calls printLegend(root, ""), which calls private
void printLegend(HuffmanNode t, String s), a recursive method that works as
follows: If (t.letter.length() > 1) i.e., t contains multiple characters, then t is NOT a
leaf node, so we recursively call printLegend() on its left child using string s + "0", and
recurse on t’'s right child using string s + "1". If t.letter is a single character, then t is a leaf
node, and we print out (t.letter+"="+s);*/
	
	public void printLegend()
	{
		this.printLegend(this.root, "");
	}
	
	private void printLegend(HuffmanNode t, String s)
	{
		if( t.letter.length() > 1 )
		{
			this.printLegend( t.left, s + "0");
			this.printLegend( t.right, s + "1" );
		}
		else
		{
			System.out.print( t.letter + "->" + s );
		}
	}
	/*takes a String for the
legend containing our input (letter & frequency data). The letters and frequencies are all one line
with spaces as separators. (we may assume each separator is a single space).*/

	public static BinaryHeap fileToHeap(String filename)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader( filename ));
			
			String line = br.readLine();			
			br.close();
			
			String[] key = line.split(" ");
			HuffmanNode[] huffs = new HuffmanNode[ key.length / 2];
			int i = 0;
			int j = 0;
			while( i < key.length )
			{
				huffs[ j ] = new HuffmanNode( key[i], key[i + 1]);

				
				i = i + 2;
				j++;
			}
			
			BinaryHeap heap = new BinaryHeap( huffs );
			return heap;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
/*We run the
Huffman algorithm here. When we have only one element left in the heap, we remove it, and
create a new HuffmanTree object with root set to the removed object.*/
	
	public static HuffmanTree createFromHeap(BinaryHeap b)
	{
		try {
			while( b.getSize() > 1 )
			{
				HuffmanNode huff1 = (HuffmanNode) b.deleteMin();
				HuffmanNode huff2 = (HuffmanNode) b.deleteMin();
				HuffmanNode huff = new HuffmanNode( huff1, huff2 );
				b.insert( huff );
			}
			HuffmanTree tree = new HuffmanTree( (HuffmanNode) b.deleteMin() );
			return tree;
		} catch (UnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	
/*	this is the main method that takes  a command-line argument for the input file name
 * calls legendToHeap() on the legend
	string and returns a BinaryHeap (bheap, for example). We then call bheap.printHeap() on
	the heap. Next, we call createFromHeap(bheap) on the heap to run our Huffman algorithm
	which returns a HuffmanTree, called, here, htree. Finally, we call htree.printLegend() on
	this HuffmanTree object to print the binary encodings for each of the letters in our input file.
	*/
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryHeap bheap = HuffmanTree.fileToHeap("huff2.txt");
		bheap.printHeap();
		HuffmanTree htree = HuffmanTree.createFromHeap( bheap );
		htree.printLegend();
	}

}