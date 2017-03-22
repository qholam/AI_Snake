/**
	 * Node which holds info on location of cell on grid.
	 */
	public class Node implements Comparable{
		SNode cell;
		int gcost;
		int hcost;
		int tcost;
		Node parent;
		
		public Node(SNode cell){
			this.cell = cell;
			gcost = 0;
			hcost = 0;
			tcost = 0;
			parent = null;
		}
		
		public Node(SNode cell, int gcost, int hcost, Node parent){
			this.cell = cell;
			this.gcost = gcost;
			this.hcost = hcost;
			this.tcost = gcost + hcost;
			this.parent = parent;
		}
		
		@Override
		public int compareTo(Object arg0) {
			Node other = (Node) arg0;
			
			return this.tcost - other.tcost;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cell == null) ? 0 : cell.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			Node other = (Node) obj;
			if(!this.cell.equals(other.cell)){
				return false;
			}
			if(this.cell.equals(other.cell))
				return true;
			
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			if (cell == null) {
				if (other.cell != null)
					return false;
			} else if (!cell.equals(other.cell))
				return false;
			return true;
		}
	}