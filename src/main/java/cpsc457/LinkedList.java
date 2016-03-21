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
      if (this.size() == 0) { return(true); } //size == 0
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
    if (isEmpty() == true) {
      	//head = tail = t
       head = t_node;
       tail = t_node;
     }
     //Else add to the tail and move the tail to the end
    //tail.next = t    then		tail = t
     else {
       tail.next = t_node;
       tail = t_node;
     }
		//Do not forget to increment the size by 1 (if you have it as an attribute)
    return this;
    }

	//Gets a node's value at a specific index
    public T get(int index) {
			LinkedNode<T> curr = head;
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

	//	new MergeSort<T>(comp).sort(this); //Run this within the critical section (as discussed before)

		//It might not allow you to use this inside critical
			//Create a final pointer = this then use that pointer
    }

	//Sorts the link list in parallel (using multiple threads)
    private void par_sort(Comparator<T> comp) {
	//	new MergeSort<T>(comp).parallel_sort(this); //Run this within the critical section (as discussed before)
    }

	//Merge sort
    static class MergeSort<T> {

		//Variables (attributes)
			//ExecutorService
			//Depth limit

		//Comparison function
		final Comparator<T> comp;

		//Constructor
		private Comparator<T> MergeSort(Comparator<T> comp) {
			Comparator<T> new_comp;
			int comp_length = comp.length; //??
			int midpoint;

			// If the length is 2 then we do the comparison //
			if (comp_legnth == 2) {
				T first = comp[0];
				T second = comp[0];
				 	 if (first.compareTo(second) < 0){ //  if comp[0] is less than comp[1]
						 // then we swap them //
						 new_comp[0] = second;
						 new_comp[1] = first;
					 }
					return(new_comp);
			}
			// Otherwise we continue splitting the list
			else {
			  if (list_size % 2 == 0) { midpoint = list_size / 2; } // If the size is even then we simply divide the list evenly
				else { midpoint = (list_size / 2) + 1; } // Otherwise we must divide it unevenly

				for (int i = 0; i < midpoint; i++) { // Create the left side
					left_list.append(comp[i]);
				}

				for (int i = midpoint; i < comp.size(); i++) { // Create the right side
					right_list.append(comp[i]);
				}
				// Here we sort each side //
				left_list = MergeSort(left_list);
				right_list = MergeSort(right_list);

				// Now we merge the two sorted lists back together //
				new_comp = left_list;
				for (int i = midpoint; i < comp.size(); i++) {
					new_comp.append(right_list[i]);
				}

				return(new_comp);
			}
		}
		//#####################
		//# Sorting functions #
		//#####################
		//The next two functions will simply call the correct function
		//to merge sort the link list and then they will fix its
		//attributes (head and tail pointers)

		public void sort(LinkedList<T> list) {
			length = list.length();
			sortedList = MergeSort(list);
			this.clear(); // Clear the current linked list so we can put in the sorted one
			for (int i = 0; i < sortedList.length(); i++){
				this.append(sortedList[i]);
			}
		}

		public void parallel_sort(LinkedList<T> list) {
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
	}


}
