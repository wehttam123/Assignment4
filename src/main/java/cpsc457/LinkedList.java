package cpsc457;

import cpsc457.doNOTmodify.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedList<T> implements Iterable<T> {

	//####################
	//# Static Functions #
	//####################

	//We do not want the testers to have the freedom of specifying the comparison function
	//Thus, we will create wrappers for them that they can use and inside these wrappers
	//we will have the comparison function predefined
		//These two static wrappers, will simply call the sort method in the list passed as parameter,
		//and they pass the comparison function as well

	public static <T extends Comparable<T>> void par_sort(LinkedList<T> list) {
		list.par_sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public static <T extends Comparable<T>> void sort(LinkedList<T> list){
        list.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

	//############
	//# LinkList #
	//############

	//Variables (attributes)
		private LinkedNode<T> head; //Head
		private LinkedNode<T> tail;//Tail
		//Size (not required)
		//Critical Section

	//Constructor
    public LinkedList() {
		//Set head and tail to null
      head = null;
      tail = null;
		//Set size to zero
		//Create new instance for the critical section
    }

	//Returns the size of the list
    public int size() {
        int size = 0;
        LinkedNode<T> curr = head;
        while (curr != null) { // Iterates through the list until we reach a null node
          size++;
          curr = curr.next;
        }
        return (size);

         //either iterate through all the list and count
					//or create an attribute that stores the size and changes
					//every time we add or remove a node
    }

	//Checks if the list is empty
	public boolean isEmpty() {
      if (size() == 0) { return(true); } //size == 0
      else { return(false); }
    }

	//Deletes all the nodes in the list
	public void clear() {
		head = null;
		tail = null;
		//just set the head and tail to null (the garbage collector takes care of the rest)
			//cpp developers: be careful, you have to destroy them first

		//What if the merge sort is running now in a thread
			//I should not be able to delete the nodes (and vice versa)
			//Thus run this and everything else in a critical section
    }

	//Adds a new node to the list at the end (tail)
    public LinkedList<T> append(T t) {
    LinkedNode<T> t_node = new LinkedNode(t);

		//Check if it is empty
	  if ((isEmpty() == true) && (t != null)) {
      	//head = tail = t
       head = t_node;
       tail = head;
			 head.next = null;
     }
     //Else add to the tail and move the tail to the end
    //tail.next = t    then		tail = t
     else {
       tail.next = t_node;
			 tail = tail.next;
       //tail = t_node;
     }
		//Do not forget to increment the size by 1 (if you have it as an attribute)
    return this;
    }

	//Gets a node's value at a specific index
    public T get(int index) {
			LinkedNode<T> curr = head;
			if(curr == null){
				return(null);
			}
			for (int i = 0; i < size(); i++){
				if (i == index){
					return(curr.data);
				}
				else { curr = curr.next; }
			}
			return(null);
		//Iterate through the list
			//Create a new pointer that starts at the head
			//Keeps moving forward (pt = pt.next) for index times
			//then return that object

		//Make sure not to exceed the size of the list (else return null)
    }

	@Override
    public Iterator<T> iterator() {
		return null;
    }

	//The next two functions, are being called by the static functions at the top of this page
	//These functions are just wrappers to prevent the static function from deciding which
	//sorting algorithm should it use.
	//This function will decide which sorting algorithm it should use
	//(we only have merge sort in this assignment)

	//Sorts the link list in serial
    private void sort(Comparator<T> comp) {

		new MergeSort<T>(comp).sort(this); //Run this within the critical section (as discussed before)

		//It might not allow you to use this inside critical
			//Create a final pointer = this then use that pointer
    }

	//Sorts the link list in parallel (using multiple threads)
    private void par_sort(Comparator<T> comp) {
		new MergeSort<T>(comp).parallel_sort(this); //Run this within the critical section (as discussed before)
    }

	//Merge sort
    static class MergeSort<T> {

		//Variables (attributes)
			//ExecutorService
			//Depth limit

		//Comparison function
		final Comparator<T> comp;

		//Constructor
		private MergeSort(Comparator<T> comp) {
			this.comp = comp;
		}
		//#####################
		//# Sorting functions #
		//#####################
		//The next two functions will simply call the correct function
		//to merge sort the link list and then they will fix its
		//attributes (head and tail pointers)

		public void sort(LinkedList<T> list) {

			int midpoint;
			int result;
			LinkedList<T> left_list = new LinkedList<T>();
			LinkedList<T> right_list = new LinkedList<T>();

	//		long s = System.currentTimeMillis();
			if(list.size() == 0) {

			}

			else if(list.size() == 1) {
			}
				//return (list);
			else {
				if (list.size() % 2 == 0) { midpoint = list.size() / 2; } // If the size is even then we simply divide the list evenly
				else { midpoint = (list.size() / 2) + 1; } // Otherwise we must divide it unevenly

				for (int i = 0; i < midpoint; i++) { // Create the left side
					left_list.append(list.get(i));
				}


				for (int i = midpoint; i < list.size(); i++) { // Create the right side
					right_list.append(list.get(i));
				}

			//	long sd = System.currentTimeMillis();


				// Here we sort each side //
				sort(left_list);
				sort(right_list);

			//	long ed = System.currentTimeMillis();
				//System.out.println("recurs" + (ed-sd));

				int list_size = list.size();

				list.clear();



		//		long sl = System.currentTimeMillis();


				for (int i = 0; i < list_size; i++){

			//		long sg = System.nanoTime();

					T leftPointer = left_list.get(0);
					T rightPointer = right_list.get(0);

				//	long eg = System.nanoTime();
				//	System.out.println("gets" + (eg-sg));

				//	long sf = System.nanoTime();
				 // compare //
				 if ((leftPointer == null) || (rightPointer == null)){ // If one of the lists is empty we can't compare the heads so we simply make result 0
					 result = 0;
				 }
				 else { result = comp.compare(leftPointer,rightPointer); } // Otherwise we compare the heads of the two lists

			//	 long ef = System.nanoTime();
				// System.out.println("if" + (ef-sf));

			//	 long ssf = System.nanoTime();
				 if (left_list.size() == 0) { // If the left list is empty we take the right lists head
					 list.append(rightPointer);
					 right_list.head = right_list.head.next;
				 }
				 else if (right_list.size() == 0) { // If the right list is empty we take the left lists head
					// System.out.println("Working?");
					 list.append(leftPointer);
					 left_list.head = left_list.head.next;
				 }
				 else if (result <= 0) { // If the left lists head was smaller we take the left lists head
					 list.append(leftPointer);
					 left_list.head = left_list.head.next;
				 }

				 else if (result > 0) { // Otherwise we take the right lists head
					 list.append(rightPointer);
					 right_list.head = right_list.head.next;
				 }
			//	 long esf = System.nanoTime();
				// System.out.println("sif" + (esf-ssf));
				}

		//		long el = System.currentTimeMillis();
			//	System.out.println("loop" + (el-sl));
		}
	//	long e = System.currentTimeMillis();
	//	System.out.println("efjwep" + (e-s));
	}

		public void parallel_sort(LinkedList<T> list) {
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			int midpoint;
			int result;
			LinkedList<T> left_list = new LinkedList<T>();
			LinkedList<T> right_list = new LinkedList<T>();


			if(list.size() == 1) {
			}
				//return (list);
			else {
				if (list.size() % 2 == 0) { midpoint = list.size() / 2; } // If the size is even then we simply divide the list evenly
				else { midpoint = (list.size() / 2) + 1; } // Otherwise we must divide it unevenly

				for (int i = 0; i < midpoint; i++) { // Create the left side
					left_list.append(list.get(i));
				}

				for (int i = midpoint; i < list.size(); i++) { // Create the right side
					right_list.append(list.get(i));
				}


			executorService.execute(new Runnable() {
			    public void run() {
			        sort(left_list);
							//right_list.sort();
			    }
			});



			executorService.execute(new Runnable() {
			    public void run() {
			        //left_list.sort();
							sort(right_list);
			    }
			});



			int list_size = list.size();
			list.clear();

			for (int i = 0; i < list_size; i++){
				T leftPointer = left_list.get(0);
				T rightPointer = right_list.get(0);

			 // compare //
			 if ((leftPointer == null) || (rightPointer == null)){ // If one of the lists is empty we can't compare the heads so we simply make result 0
				 result = 0;
			 }
			 else { result = comp.compare(leftPointer,rightPointer); } // Otherwise we compare the heads of the two lists

			 if (left_list.size() == 0) { // If the left list is empty we take the right lists head
				 list.append(rightPointer);
				 right_list.head = right_list.head.next;
			 }
			 else if (right_list.size() == 0) { // If the right list is empty we take the left lists head
				// System.out.println("Working?");
				 list.append(leftPointer);
				 left_list.head = left_list.head.next;
			 }
			 else if (result <= 0) { // If the left lists head was smaller we take the left lists head
				 list.append(leftPointer);
				 left_list.head = left_list.head.next;
			 }

			 else if (result > 0) { // Otherwise we take the right lists head
				 list.append(rightPointer);
				 right_list.head = right_list.head.next;
			 }
			}
	}

executorService.shutdown();
	}
}
}
		//#########
		//# Steps #
		//#########

		//The main merge sort function (parrallel_msort and msort)
			//Split the list to two parts
			//Merge sort each part
			//Merge the two sorted parts together

		//Splitting function
			//Run two pointers and find the middle of the a specific list
			//Create two new lists (and break the link between them)
			//It should return pair (the two new lists)

		//Merging function
			//1- Keep comparing the head of the two link lists
			//2- Move the smallest node to the new merged link list
			//3- Move the head on the list that lost this node

			//4- Once one of the two lists is done, append the rest of the
			//	 second list to the tail of the new merged link list
