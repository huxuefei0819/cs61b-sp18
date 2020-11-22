public class TriangleDrawer {
   public static void drawTriangle(int N) {
     int i = 1;
     while(i<=N){
       for(int j=0;j<i;j++){
        System.out.print("*");
       }
       System.out.println();
       i += 1;
       } 
   }
   
   public static void main(String[] args) {
      drawTriangle(10);
   }
}