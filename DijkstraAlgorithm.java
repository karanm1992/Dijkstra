import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DijkstraAlgorithm {

	public static Graph G = new Graph();
	
	public static void main(String[] args) {
		int vertexCount, edgeCount;
		Scanner scan = new Scanner(System.in);
		
		vertexCount = scan.nextInt();
		edgeCount = scan.nextInt();
		
//		System.out.println("v = " + vertexCount + " e = " + edgeCount);
	
		for(int i = 0; i < vertexCount; i++)
			G.V.add(new Vertex(i));
		
		for(int i = 0; i < edgeCount; i++)
			G.E.add(new Edge(G.V.get(scan.nextInt()),G.V.get(scan.nextInt()), scan.nextInt()));
		
		for(Edge e : G.E) {
			G.V.get(e.v1.id).adjList.add(e.v2);
		}
		
//		System.out.println(G.V);
//		System.out.println(G.E);
		
		Dijkstra(G.V.get(0));
		
		scan.close();
	}
	
	public static void Dijkstra(Vertex source) {
		
		int infinity = 0;
		
		for(Edge e : G.E)
			infinity += e.weight;
		
		for(Vertex v : G.V) {
			G.V.get(v.id).d = infinity+1;
			G.V.get(v.id).parent = null;
		}
		
		G.V.get(source.id).d = 0;
		
		
//		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(G.V.size()-1, comparator);
		PriorityQueue2 pq= new PriorityQueue2(G.V.size());
		
		for(Vertex u : G.V)
			pq.addVertex(u);
		
		
		while(pq.heapSize != 0) {
			Vertex u = pq.getLastVertex();
			for(Vertex v : u.adjList) {
				if(pq.contains(v) && (u.d + getWeight(u, v)) < v.d) {
					v.d = u.d + getWeight(u, v);
					v.parent = u;
					pq.decreaseKey(v);
					
					G.V.get(v.id).d = v.d;
				}
			}
		}
		
		System.out.println();
		
		System.out.print("Vertex     ");
		for(Vertex v : G.V)
			System.out.print(v + "\t\t");

		System.out.println();
		
		System.out.print("Distance   ");
		
		for(Vertex v : G.V) {
			if(v.d == infinity+1)
				System.out.print("INFINITY\t");
			else
				System.out.print(v.d + "\t\t");
		}
		
	}
	
	public static int getWeight(Vertex v1, Vertex v2) {
		for(Edge e : G.E) {
			if(e.v1.equals(v1) && e.v2.equals(v2))
				return e.weight;
		}
		return 0;
	}
}

class Vertex {
	public int id;
	List<Vertex> adjList = new ArrayList<Vertex>();
	int d;		//Shortest distance
	Vertex parent;
	
	public Vertex() {
		// TODO Auto-generated constructor stub
	}

	public Vertex(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.id == ((Vertex)obj).id)
			return true;
		
		return false;
	}

	
}

class Edge {
	Vertex v1;
	Vertex v2;
	int weight;
	
	public Edge() {
		// TODO Auto-generated constructor stub
	}

	public Edge(Vertex v1, Vertex v2, int weight) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", weight=" + weight + "]";
	}
	
}

class Graph {
	List<Vertex> V = new ArrayList<Vertex>();
	List<Edge> E = new ArrayList<Edge>();
}

class PriorityQueue2 {

	Vertex[] v;
	int heapSize;
	
	public PriorityQueue2(int size) {
		v = new Vertex[++size];
		heapSize = 0;
	}
	
	void addVertex (Vertex newVertex) {
		int position = ++heapSize;
		v[position] = newVertex;
		
		while(position != 1 && newVertex.d < v[position/2].d) {
			swap(position, position/2);
			position = position / 2;
		}
			
	}
	
	Vertex getLastVertex() {
		Vertex p = v[1];
		
		if(heapSize > 0) {
			v[1] = v[heapSize];
			heapSize--;
			remakeHeap(1);
		}
		
		return p;
	}
	
	Vertex peek() {
		return v[1];
	}

	boolean contains (Vertex check) {
		for(Vertex p : v) {
			if(p != null && p.equals(check))
				return true;
		}
		return false;
	}
	
	private void remakeHeap(int position) {
		int child;
		
		if(position*2 > heapSize)	
			child = position;
		else if(position*2+1 > heapSize) 
			child = position*2;
		else if(v[position*2].d < v[position*2+1].d)
			child = position*2;
		else
			child = position*2+1;
			
		if(v[position].d > v[child].d) {
			swap(position, child);
			remakeHeap(child);
		}	
}

	private void swap(int p1, int p2) {
		Vertex tempVertex = new Vertex();
		tempVertex = v[p1];
		v[p1] = v[p2];
		v[p2] = tempVertex;
		
	}
	
	void decreaseKey(Vertex ver) {
		for(Vertex v1 : v) {
			if(v1 != null && v1.equals(ver)) {
				v1.d = ver.d;
				break;
			}		
		}
		remakeHeap(1);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(v);
	}
	
	
}