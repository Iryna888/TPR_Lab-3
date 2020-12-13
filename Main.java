public class Main {

    private static int[] N = {6, 7, 3, 4};
    private static char[][] A = {{'A', 'C', 'B', 'D'},
            {'B', 'C', 'A', 'D'},
            {'C', 'A', 'B', 'D'},
            {'C', 'A', 'D', 'B'}};

    private static final int[] numberOfVotes = {6, 7, 3, 4};
    private static final char[] candidates = {'A', 'B', 'C', 'D'};
    private static final char[][] orderingOfCandidates = {{'A', 'B', 'C', 'C'},
            {'C', 'C', 'A', 'A'},
            {'B', 'A', 'B', 'D'},
            {'D', 'D', 'D', 'B'}};

    private static int nA=0, nB=0, nC=0, nD=0;
    private static int sumN = 0;

    private static int[][] s = new int[A.length][A[0].length];

    static{
        for (int i : N) {
            sumN += i;
        }
    }

    public static void main (String[] args){

        for (int i : N) {
            System.out.print("\t" + i);
        }

        System.out.println();

        for (int i = 0; i < A.length; i++) {
            for (char[] chars : A) {
                System.out.print("\t" + chars[i]);
            }
            System.out.println();
        }

        System.out.println("\nПравило відносної більшості: ");
        relativeMajority1();
        System.out.println("\n\nПравило абсолютної більшості:" + "\n" +
                "Переміг кандидат: " + absoluteMajority() + "\n\n" +
                "Правило Борда:" + "\n" +
                "Переміг кандидат: " + borda() + "\n" +
                "nA = \" + nA + \" nB = \" + nB + \" nC = \" + nC + \" nD = \" + nD)" + "\n\n" +
                "Правило Кондорсе: \n" + kondorse() + "\n" +
                "Правило Копленда: \n" + koplenda() + "\n" +
                "Правило Сімпсона: \n" + simpson() + "\n");
    }

    private static void relativeMajority1() {
        int[] n = calculateEachSum();
        for (int i = 0; i < 4; i++) {
            System.out.println("n" + candidates[i] + " = " + n[i]);
        }
        char[] winners = findWinner(n);
        System.out.print("Переміг кандидат: ");
        for (char winner : winners) {
            System.out.print(winner + " ");
        }
    }

    private static int[] calculateEachSum() {
        int[] n = new int[candidates.length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n.length; j++) {
                if (Main.orderingOfCandidates[0][j] == candidates[i]) {
                    n[i] += numberOfVotes[j];
                }
            }
        }
        return n;
    }

    private static char[] findWinner(int[] arr) {
        int k = 0;
        int max = arr[0];

        for (int value : arr) {
            if (value > max) max = value;
        }
        for (int value : arr) {
            if (value == max)
                k++;
        }
        int j = 0;
        char[] winners = new char[k];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == max) {
                winners[j] = candidates[i];
                j++;
            }
        }

        return winners;
    }

    private static String simpson() {
        nA = nB =  nC = nD = Integer.MAX_VALUE;

        int[][] S = new int[A.length][A.length];

        for (int i = (int)'A'; i <= (int)'D'; i++) {
            for (int j = (int)'A'; j <= (int)'D'; j++) {
                if(i!=j){
                    S[i-(int)'A'][j-(int)'A'] = compare((char)i, (char)j);
                }
                else {
                    S[i-(int)'A'][j-(int)'A'] = -1;
                }
            }
        }

        for (int i = 0; i < S[0].length; i++) {
            if(S[0][i]<nA && S[0][i]>=0){
                nA = S[0][i];
            }
        }

        for (int i = 0; i < S[1].length; i++) {
            if(S[1][i]<nB && S[1][i]>=0){
                nB = S[1][i];
            }
        }

        for (int i = 0; i < S[2].length; i++) {
            if(S[2][i]<nC && S[2][i]>=0){
                nC = S[2][i];
            }
        }

        for (int i = 0; i < S[3].length; i++) {
            if(S[3][i]<nD && S[3][i]>=0){
                nD = S[3][i];
            }
        }

        System.out.println("S(A) = " + nA);
        System.out.println("S(B) = " + nB);
        System.out.println("S(C) = " + nC);
        System.out.println("S(D) = " + nD);

        return "Переміг кандидат: " + getMaxN();
    }


    private static int compare(char a, char b){

        int n = 0;

        for (int i = 0; i<N.length; i++){
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == a){
                    n += N[i];
                    break;
                }
                if (A[i][j] == b){
                    break;
                }
            }
        }

        return n;
    }

    private static String koplenda() {

        nA = nB =  nC = nD = 1;

        k('A');
        k('B');
        k('C');
        k('D');

        System.out.println("K(A) = " + nA);
        System.out.println("K(B) = " + nB);
        System.out.println("K(C) = " + nC);
        System.out.println("K(D) = " + nD);
        return "Переміг кандидат: " + getMaxN();
    }

    private static void k(char a) {
        if ('A'!=a && compare('A', a, false)){
            nA++;
        }else{
            nA--;
        }
        if ('B'!=a && compare('B', a, false)){
            nB++;
        }else{
            nB--;
        }
        if ('C'!=a && compare('C', a, false)){
            nC++;
        }else{
            nC--;
        }
        if ('D'!=a && compare('D', a, false)){
            nD++;
        }else{
            nD--;
        }
    }

    private static String kondorse() {

        nA = nB =  nC = nD = 0;

        if (compare('A', 'B', true))
            nA++;
        else nB++;

        if (compare('A', 'C', true))
            nA++;
        else nC++;

        if (compare('A', 'D', true))
            nA++;
        else nD++;

        if (compare('B', 'C', true))
            nB++;
        else nC++;

        if (compare('B', 'D', true))
            nB++;
        else nD++;

        if (compare('C', 'D', true))
            nC++;
        else nD++;

        System.out.println();

        if (nA == 3)
            return "Переміг кандидат: " + 'A';
        if (nB == 3)
            return "Переміг кандидат: " + 'B';
        if (nC == 3)
            return "Переміг кандидат: " + 'C';
        if (nD == 3)
            return "Переміг кандидат: " + 'D';

        return "Переможця за правилом Кондорсе не існує.";
    }

    private static boolean compare(char a, char b, boolean print) {
        int x=0, y=0;

        for (int i = 0; i<N.length; i++){
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == a){
                    x += N[i];
                    break;
                }
                if (A[i][j] == b){
                    y += N[i];
                    break;
                }
            }
        }

        if(print) {
            System.out.print(a + ":" + b + "=" + x + ":" + y + "\t");
        }

        return x>y;
    }

    private static char getMaxN() {
        if(nA>nB && nA>nC && nA>nD){
            return 'A';
        } else if (nB>nC && nB>nD){
            return 'B';
        }else if(nC>nD){
            return 'C';
        }else {
            return 'D';
        }
    }

    private static char absoluteMajority() {

        char[] round2 = new char[2];
        nA = nB =  nC = nD = 0;

        switch (round2[0] = relativeMajority()) {
            case 'A': {
                if(nA>(sumN/2+1)) {
                    System.out.print("набравши більше половини голосів ");
                    return 'A';
                }
                if (nB>nC && nB>nD){
                    round2[1] = 'B';
                }else if(nC>nD){
                    round2[1] = 'C';
                }else {
                    round2[1] = 'D';
                }
                break;
            }
            case 'B':{
                if(nB>(sumN/2+1)) {
                    System.out.print("набравши більше половини голосів ");
                    return 'B';
                }
                if (nA>nC && nA>nD){
                    round2[1] = 'A';
                }else if(nC>nD){
                    round2[1] = 'C';
                }else {
                    round2[1] = 'D';
                }
                break;
            }
            case 'C':{
                if(nC>(sumN/2+1)) {
                    System.out.print("набравши більше половини голосів ");
                    return 'C';
                }
                if (nA>nB && nA>nD){
                    round2[1] = 'A';
                }else if(nB>nD){
                    round2[1] = 'B';
                }else {
                    round2[1] = 'D';
                }
                break;
            }
            case 'D':{
                if(nD>(sumN/2+1)) {
                    System.out.print("набравши більше половини голосів ");
                    return 'D';
                }
                if (nA>nB && nA>nC){
                    round2[1] = 'A';
                }else if(nB>nC){
                    round2[1] = 'B';
                }else {
                    round2[1] = 'C';
                }
                break;
            }
        }

        System.out.println("У другий тур переходять кандидати: " + round2[0] + " і " + round2[1]);

        int[] round2N = {0, 0};

        for (int i = 0; i < N.length; i++){
            int j = 0;
            while (true) {
                if (A[i][j] == round2[0]) {
                    round2N[0] += N[i];
                    break;
                } else if (A[i][j] == round2[1]) {
                    round2N[1] += N[i];
                    break;
                } else {
                    j++;
                }
            }
        }

        System.out.printf("2 тур: n%C = %d n%C = %d\n", round2[0], round2N[0], round2[1], round2N[1]);

        return round2N[0] > round2N[1] ? round2[0] : round2[1];


    }


    private static char relativeMajority(){

        for (int i = 0; i < N.length; i++) {
            switch (A[i][0]) {
                case 'A': {
                    nA += N[i];
                    break;
                }
                case 'B': {
                    nB += N[i];
                    break;
                }
                case 'C': {
                    nC += N[i];
                    break;
                }
                case 'D': {
                    nD += N[i];
                    break;
                }
            }
        }

        return getMaxN();
    }

    private static char borda(){
        nA = nB =  nC = nD = 0;

        for (int i = 0; i < N.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                switch (A[i][j]){
                    case 'A':{
                        nA += N[i] * (A[i].length - j - 1);
                        break;
                    }
                    case 'B':{
                        nB += N[i] * (A[i].length - j - 1);
                        break;
                    }
                    case 'C': {
                        nC += N[i] * (A[i].length - j - 1);
                        break;
                    }
                    case 'D':{
                        nD += N[i] * (A[i].length - j - 1);
                        break;
                    }
                }
            }
        }

        return getMaxN();
    }
}
