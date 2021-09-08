import java.util.*;


class CRC {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        //int n ;

        System.out.println("Κ");
        int n= scan.nextInt();
        int K[]=new int[n];
       // creationofBinary(n);
        K[n]= Integer.parseInt(creationofBinary(n));
        //DIVISOR
        System.out.println("\nP");
        int P=scan.nextInt();
        int PDivisor[]=new int[P];

        int remainderCRC[] = divide(K,PDivisor);
        for(int i=0 ; i < remainderCRC.length-1 ; i++) {
            System.out.print(remainderCRC[i]);
        }
        System.out.println("\nΟ κώδικας CRC είναι:");
        for(int i=0 ; i < K.length ; i++)System.out.print(K[i]);
        for(int i=0 ; i < remainderCRC.length-1 ; i++) System.out.print(remainderCRC[i]);
        System.out.println();
        //CHECK THIS AGAIN
        int sent_data[] = new int[K.length + remainderCRC.length - 1];
        System.out.println("Enter the data to be sent:");
        for(int i=0 ; i < sent_data.length ; i++) {
            System.out.println("Enter bit number "+(sent_data.length-i)+":");
            sent_data[i] = scan.nextInt();
        }


        receive(sent_data, PDivisor);
         //UNTIL HERE
    }
    //first part of the project
    static int creatingRandom(){
        int num =(1+(int)(Math.random()*100))%2;
        return num;
    }
    static String creationofBinary(int n){
        String S="";
        for(int i=0;i<n;i++){
            int Ran=creatingRandom();
            S=S+String.valueOf(Ran);
        }
        return S;
    }
    //end of first part
    //second start
    static int[] divide(int old[], int divisor[]) {
        int remainder[],i;
        int data[]=new int[old.length+divisor.length];
        System.arraycopy(old,0,data,0,old.length);
        // Remainder array stores the remainder
        remainder=new int[divisor.length];
        // Initially, remainder's bits will be set to the data bits
        System.arraycopy(data,0,remainder,0,divisor.length);
        // Loop runs for same number of times as number of bits of data
        // This loop will continuously exor the bits of the remainder and
        // divisor
        for(i=0 ; i < old.length ; i++) {
            if(remainder[0] == 1) {
                // We have to exor the remainder bits with divisor bits
                for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], divisor[j]);
                }
            }
            else {
                // We have to exor the remainder bits with 0
                for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], 0);
                }
            }
            // The last bit of the remainder will be taken from the data
            // This is the 'carry' taken from the dividend after every step
            // of division
            remainder[divisor.length-1]=data[i+divisor.length];
        }
        return remainder;
    }

    static int exor(int a, int b) {
        // This simple function returns the exor of two bits
        if(a == b) {
            return 0;
        }
        return 1;
    }

    static void receive(int data[], int divisor[]) {
        // This is the receiver method
        // It accepts the data and divisor (although the receiver already has
        // the divisor value stored, with no need for the sender to resend it)
        int remainder[] = divide(data, divisor);
        // Division is done
        for(int i=0 ; i < remainder.length ; i++) {
            if(remainder[i] != 0) {
                // If remainder is not zero then there is an error
                System.out.println("There is an error in received data...");
                return;
            }
        }
        //Otherwise there is no error in the received  data
        System.out.println("Data was received without any error.");
    }
}
//0