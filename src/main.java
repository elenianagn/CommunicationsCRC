import java.util.*;


class CRC {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //int n ;

        System.out.println("Κ");
        int n= scan.nextInt();
        int[] K=new int[n];
       // creationofBinary(n);
        K[n]= Integer.parseInt(creationofBinary(n));
        //DIVISOR
        System.out.println("\nP");
        int P=scan.nextInt();
        int[] PDivisor=new int[P];

        int[] remainderCRC = divide(K,PDivisor);
        for(int i=0;i<remainderCRC.length-1;i++) {
            System.out.print(remainderCRC[i]);
        }
        System.out.println("\nΟ κώδικας CRC είναι:");
        for (int j:K) System.out.print(j);
        for(int i=0 ; i < remainderCRC.length-1 ; i++) System.out.print(remainderCRC[i]);
        System.out.println();
        //CHECK THIS AGAIN
        int[] sent_data=new int[K.length+remainderCRC.length-1];
        System.out.println("Enter the data to be sent:");
        for(int i=0 ; i < sent_data.length ; i++) {
            System.out.println("Enter bit number "+(sent_data.length-i)+":");
            sent_data[i] = scan.nextInt();
        }
        receive(sent_data, PDivisor);
         //UNTIL HERE
    }
    //Οι πρώτες δύο μέθοδοι δημιουργούν ένα τυχαίο binary αριθμό άναλογα τον αριθμό ψηφίων που δίνει ο χρήστης
    //Πιο συγκεκριμένα η μέθοδος creatingRandom δημιουργεί binary και το creationofBinary δημιουργεί το string binary
    static int creatingRandom(){
        return (1+(int)(Math.random()*100))%2;
    }
    static String creationofBinary(int n){
        String S="";
        for(int i=0;i<n;i++){
            int Ran=creatingRandom();
            S=S+String.valueOf(Ran);
        }
        return S;
    }
    //Οι επόμενες δύο μέθοδοι αναλαμβάνουν την επεξεργασία του binary string για τον υπολογισμό του CRC (FCS)
    //Ώς πρότυπο υπολογισμού χρησιμοποιήτε ο αριθμός P (divisor)
    static int[] divide(int[] old,int[] divisor) {
        int[] remainder;
        int i;
        int[] P=new int[old.length+divisor.length];
        System.arraycopy(old,0,P,0,old.length);
        remainder=new int[divisor.length];
        System.arraycopy(P,0,remainder,0,divisor.length);
        for(i=0;i<old.length;i++){
            if(remainder[0]==1){
                for(int j=1;j<divisor.length;j++) remainder[j-1]=exor(remainder[j],divisor[j]);
            }
            else{
                for(int j=1;j<divisor.length;j++)remainder[j-1]=exor(remainder[j],0);
            }
            remainder[divisor.length-1]=P[i+divisor.length];
        }
        return remainder;
    }
    static int exor(int a,int b){
        if(a==b) return 0;
        return 1;
    }
    //Έλεγχος του CRC
    static void receive(int[] data,int[] divisor){
        int[] remainder=divide(data,divisor);
        for (int j:remainder){
            if(j!=0){
                System.out.println("There is an error in received data...");
                return;
            }
        }
        System.out.println("Data was received without any error.");
    }
}
