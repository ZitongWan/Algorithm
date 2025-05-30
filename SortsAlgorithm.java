import java.util.Arrays;
import java.util.Scanner;

public class SortsAlgorithm {
    public static void main(String[] args) {
        new Menu().start();
    }
}
class Menu{
    Scanner scanner = new Scanner(System.in);
    int numArrays;  // Several one-dimensional arrays
    int arraySize;  // The length of a one-dimensional array
    int[][] randomArrays;  // Target two-dimensional array
    public void fillArraysSize(){
        // Add some logic to prevent exiting due to input exceptions
        // Define the number of arrays
        do{
            System.out.print("Enter number of arrays (must be >= 5): ");
            numArrays = scanner.nextInt();
            if (numArrays < 5) System.out.println("Please enter it again");
        } while (numArrays < 5);
        randomArrays = new int[numArrays][];  // Initialize array length
        // Fill in the specifications of the array
        for (int i = 0; i < numArrays; i++){
            do{
                System.out.println("Enter size for Array " + (i + 1) + ": (must be < 10)");
                arraySize = scanner.nextInt();
                if (arraySize >= 10 || arraySize <= 0) System.out.println("Please enter it again");
            } while (arraySize >= 10 || arraySize <= 0);
            randomArrays[i] = new int[arraySize];
        }
    }
    public void fillArraysNumber(){
        // Randomly fill data in an array
        for (int[] i : randomArrays){
            for (int j = 0; j < i.length; j++){
                i[j] = (int)(Math.random() * (100));
            }
        }
    }
    public int[] flattenArray(int[][] randomArrays){
        // Transforming a two-dimensional array into a one-dimensional one, i.e. flattening the array
        int length = 0;
        for (int[] ros : randomArrays){
            length += ros.length;
        }
        // Create a merged one-dimensional array
        int[] monologue = new int[length];
        // Add elements from a two-dimensional array to a one-dimensional array
        int index = 0;
        for (int[] row : randomArrays){
            for (int i : row){
                monologue[index] = i;
                index++;
            }
        }
        return monologue;
    }
    public void start(){
        this.fillArraysSize();
        this.fillArraysNumber();
        sortAlgorithm ros = new sortAlgorithm();
        int count = 0;
        int[] finalArray = flattenArray(randomArrays);  // The final one-dimensional array
        while (count < numArrays){
            System.out.println("Random Array " + (count + 1) + ": " + Arrays.toString(randomArrays[count]));

            System.out.println("\nSorting Algorithm Menu:");
            System.out.println("1. Bucket Sort");
            System.out.println("2. Insertion Sort");
            System.out.println("3. Merge Sort");
            System.out.println("4. Quick Sort");
            System.out.println("5. Selection Sort");
            System.out.println("6. Radix Sort");
            System.out.println("7. Heap Sort");
            System.out.println("8. Shell Sort");
            System.out.println("9. Exit");

            System.out.print("Choose a sorting algorithm (1-6): ");
            int choice = scanner.nextInt();
            if (choice == 9){
                break;
            }
            switch (choice){
                case 1 -> {
                    ros.BucketSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 2 -> {
                    ros.insertSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 3 -> {
                    ros.mergeSort(randomArrays[count],0,randomArrays[count].length - 1,new int[randomArrays[count].length]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 4 -> {
                    ros.quickSort(randomArrays[count],0,randomArrays[count].length - 1);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 5 -> {
                    ros.selectionSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 6 -> {
                    ros.radixSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 7 -> {
                    ros.HeapSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                case 8 -> {
                    ros.shellSort(randomArrays[count]);
                    System.out.println("Sorted Array" + (count + 1) + ": " + Arrays.toString(randomArrays[count]));
                    count++;
                }
                default -> System.out.println("Invalid choice. Please choose a valid option.");
            }
            System.out.println();
        }
        System.out.println("Merged Array : " + Arrays.toString(finalArray));
        ros.quickSort(finalArray,0,finalArray.length - 1);
        System.out.println("Sorted-Merged Array : " + Arrays.toString(finalArray));
        System.out.println("bye~");
    }

}
class sortAlgorithm{
    public void insertSort(int[] lst){
        // Insertion Sort
        int insertValue;
        int insertIndex;
        for (int i = 1; i < lst.length; i++){
            insertValue = lst[i];  // Inserted Value
            insertIndex = i - 1;  // Insert the previous index of the value
            while (insertIndex >= 0 && insertValue < lst[insertIndex]){
                // Sort from small to large
                lst[insertIndex + 1] = lst[insertIndex];
                insertIndex--;
            }
            if (insertIndex + 1 != i){
                lst[insertIndex + 1] = insertValue;
            }
        }
    }
    public void shellSort(int[] lst){
        // Improved Shell sorting
        for (int gap = lst.length / 2; gap > 0; gap /= 2){
            for (int i = gap; i < lst.length; i++){
                // Integrated insertion algorithm
                int j = i;
                int temp = lst[j];
                if (lst[j] < lst[j - gap]){
                    while (j - gap >= 0 && temp < lst[j - gap]){
                        // Preventing cross-border and exchange conditions
                        lst[j] = lst[j - gap];
                        j -= gap;
                    }
                    lst[j] = temp;
                }
            }
        }
    }
    public void quickSort(int[] lst, int left, int right){  // Incoming list, first and last indexes
        // Quick Sort
        int l = left;  // Left subscript
        int r = right;  // Right subscript
        int pivot = lst[(left + right) / 2];  // Middle value
        int temp;  // Temporary variables
        while (l < r){
            while (lst[l] < pivot){  // Exit the loop when a number to the left of the pivot is found that is greater than the pivot
                l++;
            }
            while (lst[r] > pivot){  // Exit when a number to the right of a pivot is found that is less than the pivot
                r--;
            }
            if (l == r){  // Exit conditions
                break;
            }
            // Swap values when exiting a while loop
            temp = lst[l];
            lst[l] = lst[r];
            lst[r] = temp;
            if (lst[l] == pivot){  // If reach the middle on the left, directly move the right side--
                r--;
            }
            if (lst[r] == pivot){  // If reach the middle on the right, directly move the left side++
                l++;
            }
        }
        if (l == r){
            l++;
            r--;
        }
        if (left < r){  // left recursive
            quickSort(lst,left,r);
        }
        if (right > l){  // right-recursive
            quickSort(lst,l,right);
        }
    }
    public void mergeSort(int[] lst, int left, int right, int[] temp){
        // Both left and right are indexes, with left and right indexes    mergeSort
        if (left < right){
            int mid = (left + right) / 2;
            mergeSort(lst,left,mid,temp);  // left recursive
            mergeSort(lst,mid + 1,right,temp);  // right-recursive
            merge(lst,left,mid,right,temp);
        }
    }
    public void merge(int[] lst, int left, int mid, int right, int[] temp){
        // Mid is the intermediate index
        int i = left;  // The first index in order on the left
        int j = mid + 1;  // The first index in order on the right
        int t = 0;  // Pointer to temp array

        // Merge data from both sides
        while (i <= mid && j <= right){
            if (lst[i] < lst[j]){
                // If the data on the left is smaller
                temp[t] = lst[i];
                i++;
            } else{
                // If the data on the right is smaller
                temp[t] = lst[j];
                j++;
            }
            t++;
        }

        // Fill in data
        while (i <= mid){
            // If the data on the left is not fully filled in
            temp[t] = lst[i];
            i++;
            t++;
        }
        while (j <= right){
            // If the data on the right is not fully filled in
            temp[t] = lst[j];
            j++;
            t++;
        }

        // Copying data
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right){
            lst[tempLeft] = temp[t];
            tempLeft++;
            t++;
        }
    }
    public void selectionSort(int[] lst){
        // Choosing sorting is better than bubble sorting
        int minIndex;
        int min;
        for (int i = 0; i < lst.length - 1; i++){
            minIndex = i;
            min = lst[i];
            for (int j = 1 + i; j < lst.length; j++){
                if (min > lst[j]){
                    // If the assumed number is not the smallest
                    minIndex = j;  // Update Index
                    min = lst[j];  // Update minimum value
                }
            }
            // Swap the position of the minimum value
            if (minIndex != i){
                lst[minIndex] = lst[i];  // Update the value of the target index position to the starting value
                lst[i] = min;  // Update the starting value to the target value
            }
        }
    }
    public void radixSort(int[] lst){
        // radixSort
        // Find the maximum value of the lst array first
        int max = lst[0];
        int len = lst.length;
        int i = 0;
        while (i != len){
            if (max < lst[i]){
                max = lst[i];
            }
            i++;
        }
        // The number of digits to obtain the maximum value
        int maxlength = String.valueOf(max).length();

        // Sort by cardinality, starting with individual digits, and then following in sequence
        int[][] bucket = new int[10][len];  // 10 is a container of 0-9, len is used to prevent crossing boundaries, and cardinality sorting is a classic way of exchanging space for time
        int[] counts = new int[10];  // Record the number of elements in each container

        for (int j = 0,n = 1; j < maxlength; j++,n *= 10){
            // J is the number of cycles, n is the number of digits, digits, tens, hundreds
            for (int value : lst){
                int element = value / n % 10;
                bucket[element][counts[element]] = value;  // Place the corresponding number of digits in the container
                counts[element]++;  // Transfer the values inside the container++
            }
            // Update the data in the previous LST array
            int index = 0;
            for (int x = 0; x < counts.length; x++){
                if (counts[x] != 0){  // If there is data in the corresponding number of digits
                    for (int y = 0; y < counts[x]; y++){  // Update data into an array
                        lst[index] = bucket[x][y];
                        index++;
                    }
                }
                counts[x] = 0;
            }
        }
    }
    public void adjustHeap(int[] lst, int i, int length){
        // Form a large top heap
        int temp = lst[i];  // temp stores the value as a temporary variable
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1){
            if (k + 1 < length && lst[k] < lst[k + 1]) k++;  // If the value on the left is less than the value on the right
            if (temp < lst[k]){  // If the maximum value of a child node is greater than the value of the parent node
                lst[i] = lst[k];
                i = k;
            } else{
                break;  // If the maximum value of the child node is less than the parent node, there is no need to compare and simply exit
            }
        }
        lst[i] = temp;
    }
    public void HeapSort(int[] lst){
        int temp;  // Temporary variable for numerical exchange
        for (int i = lst.length / 2 - 1; i >= 0; i--){
            adjustHeap(lst,i,lst.length);  // The final manifestation is that the arr is a large top heap
        }
        for (int j = lst.length - 1; j > 0; j--){
            // j is the last element in the sequence
            temp = lst[j];
            lst[j] = lst[0];
            lst[0] = temp;
            adjustHeap(lst,0,j);  // Continue adjusting the top stack
        }
    }
    public void BucketSort(int[] lst){
        // Using Heap sorting to achieve bucket sorting
        int[] counts = new int[10];  // 10 is a container from 0 to 9
        int[][] bucket = new int[10][lst.length];
        for (int[] ros : bucket){
            Arrays.fill(ros,-1);  // Fill the array with -1
        }
        for (int i : lst){
            int element = i / 10;  // Put it into the corresponding bucket
            bucket[element][counts[element]] = i;
            counts[element]++;
        }
        int index = 0;
        for (int[] ros : bucket){
            if (!isEmptyArray(ros)){
                insertSort(ros);
//                quickSort(ros,0,ros.length - 1);
                for (int j : ros){
                    if (j != -1){
                        lst[index] = j;
                        index++;
                    }
                }
            }
        }
    }
    public boolean isEmptyArray(int[] lst){
        // Determine whether the array is empty
        for (int i : lst){
            if (i != -1) return false;
        }
        return true;
    }
}