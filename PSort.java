//UT-EID= rp32526


import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import java.util.*;
import java.util.concurrent.*;

public class PSort{

    static Object sync = new Object();

    /*public static void main(String[] args){
        int[] A = {1,5,7,9,16, 1000,18,12,11,19,26,23,31,4,20,194,674, 534,54, 87,785,666,27};
        parallelSort(A, 0, A.length);
        for(int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }
    }*/

    public static void parallelSort(int[] A, int begin, int end){
        // TODO: Implement your parallel sort function
        ForkJoinPool fjp = new ForkJoinPool(4);
        QuickSort q = new QuickSort(A, begin, end);
        fjp.invoke(q);

    }

    public static void insertionSort(int[] A, int begin, int end){
           synchronized (sync) {
               int curVal = A[begin];
               for (int i = begin + 1; i < end; i++) {
                   curVal = A[i];
                   for (int j = i - 1; j >= begin; j--) {
                       if (A[j] > curVal) {
                           A[j + 1] = A[j];
                           A[j] = curVal;
                       }
                   }

               }
           }
    }

    public static class QuickSort extends RecursiveAction{
        public static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
        static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        int[] A;
        int begin;
        int end;

        public QuickSort(int[] A, int begin, int end){
            this.A= A;
            this.begin= begin;
            this.end= end;
        }
        @Override
        public void compute(){

            if(end - begin < 16){
                insertionSort(A, begin, end);
            }
            else{

                int pivot = end - 1;
                int left = 0;
                int right = pivot - 1;
                while(left < right){
                    while(A[left] < A[pivot] && left < right) left++;
                    while(A[right] > A[pivot] && left < right) right--;

                    int temp = A[left];
                    A[left] = A[right];
                    A[right] = temp;
                }

                int temp = A[left];
                A[left] = A[pivot];
                A[pivot] = temp;

                List<QuickSort> threads = new ArrayList<QuickSort>();

                QuickSort t1 = new QuickSort(A, begin, left);
                QuickSort t2 = new QuickSort(A, left + 1, end);

                threads.add(t1);
                threads.add(t2);

                for(QuickSort q : threads){
                    q.fork();
                }






            }
        }
    }

}

