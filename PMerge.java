//UT-EID=


import java.util.*;
import java.util.concurrent.*;


public class PMerge{

  /*public static void main(String[] args){
    int A[] = {0,3,5,7,11};
    int B[] = {0,3,5,11};
    int C[] = new int[9];

    parallelMerge(A,B,C,12);
    for(int i = 0;i < C.length; i++){
      System.out.println(C[i]);
    }

  }*/

  public static void parallelMerge(int[] A, int[] B, int[]C, int numThreads){
    // TODO: Implement your parallel merge function
    boolean[] cCheck = new boolean[C.length];
    for(int i = 0; i < cCheck.length; i++){
      cCheck[i]=false;
    }

    ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
    for(int i = 0; i < A.length; i++){
      threadPool.execute(new Merge(A,B,C,A[i],true,cCheck));
    }
    for(int i = 0; i < B.length; i++){
      threadPool.execute(new Merge(A,B,C,B[i],false,cCheck));
    }
    threadPool.shutdown();
    try{
      threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    catch (Exception e){
      e.printStackTrace();
    }


  }

  public static int binSearch(int a, int begin, int end, int[] arr){
    while(begin<end){
      int mid = (begin + end)/2;
      if(a <= arr[mid]){
        end = mid;
      }
      else{
        begin = mid+1;
      }
    }
    return end;

  }


  public static class Merge implements Runnable{

    public int[] A;
    public int[] B;
    public int[] C;
    public int num;
    public boolean isA;
    public boolean[] cCheck;

    public Merge(int[] A, int[] B, int[] C, int num, boolean isA, boolean[] cCheck){
      this.A = A;
      this.B = B;
      this.C = C;
      this.num = num;
      this.isA = isA;
      this.cCheck = cCheck;
    }

    public void run(){
      int index=0;
      if(isA){
        for(int i = 0; i < B.length; i++){
          if(num < B[i]) index++;
        }
        int cIndex = index + A.length - 1 - binSearch(num,0,A.length,A);
        if(cCheck[cIndex]){
          C[cIndex + 1] = num;
          cCheck[cIndex + 1] = true;
        }
        else{
          C[cIndex] = num;
          cCheck[cIndex] = true;
        }
      }
      else{
        for(int i = 0; i < A.length; i++){
          if(num < A[i]) index++;
        }
        int cIndex = index + B.length - 1 - binSearch(num,0,B.length,B);

        if(cCheck[cIndex]){
          C[cIndex + 1] = num;
          cCheck[cIndex + 1] = true;
        }

        else{
          C[cIndex] = num;
          cCheck[cIndex] = true;
        }
      }

    }

    }
  }



