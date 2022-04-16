import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        ["0201","0101","0102","1212","2002"]
//"0202"
        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
        int result = solution.openLock(deadends, "0202");
//        String[] deadends = new String[]{"0001","0100","1000","9000","0900","0090","0009"};
//        int result = solution.openLock(deadends, "0010");
        System.out.println("Solution steps: " + result);
    }
}

class Solution2 {

    String target;

    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        this.target = target;

        char[] initial = {'0', '0', '0', '0'};

        State initialState = new State(initial, 0);

        Queue<State> queue = new LinkedList<>();
        queue.offer(initialState);

        while (!queue.isEmpty()) {
            State curState = queue.poll();
            if (curState.isTarget()) return curState.getMovesCount();
            if (deadSet.contains(curState.arrToString())) continue;
            deadSet.add(curState.arrToString());
        }

        return -1;
    }

    class State {
        private char[] arr = new char[4];
        private int movesCount;
        public State(char[] arr, int movesCount) {
            this.arr = arr;
            this.movesCount = movesCount;
        }

        public char[] getArr() {
            return arr;
        }

        public int getMovesCount() {
            return movesCount;
        }

        public boolean isTarget() {
            return target.charAt(0) == arr[0]
                    && target.charAt(1) == arr[1]
                    && target.charAt(2) == arr[2]
                    && target.charAt(3) == arr[3];
        }

        public String arrToString() {
            return String.copyValueOf(arr);
        }
    }
}


class Solution {

    String target;

    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>();
        deadSet.addAll(Arrays.asList(deadends));
        this.target = target;

        char[] initial = {'0','0','0','0'};

        return bfs(deadSet, initial, 0);
    }

    private int bfs(Set<String> deadSet, char[] cur, int count) {
        String curStr = String.copyValueOf(cur);
        if(deadSet.contains(curStr)) {
            return Integer.MAX_VALUE;
        }
        if(target.equals(curStr)) {
            return count;
        }
//        deadSet.add(curStr);
        count++;

        int[] bfsResults = new int[8];
        int resultCount = 0;
        for (int i = 1; i <= 4; i++) {
            bfsResults[resultCount] = bfs(deadSet, shift(cur, i),count);
            resultCount++;
            bfsResults[resultCount] = bfs(deadSet, shift(cur, -i),count);
            resultCount++;
        }

        int minResult = Integer.MAX_VALUE;
        for (int bfsResult : bfsResults) {
            if (bfsResult < minResult) minResult = bfsResult;
        }

        return minResult;

    }

    private String arrToString(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for(int i: arr) {
            builder.append(i);
        }
        return builder.toString();

    }

    private char[] shift(char[] arr, int num){ // num = +-1, +- 2, +-3, +- 4
        int sign = Integer.signum(num);
        char[] result = Arrays.copyOf(arr, arr.length);
        if(sign > 0) {
            result[Math.abs(num)-1] = increment(arr[Math.abs(num)-1]);
        } else {
            result[Math.abs(num)-1] = decrement(arr[Math.abs(num)-1]);
        }
        return result;
    }

    private char increment(char x) {
        if (x == '0') return '1';
        if (x == '1') return '2';
        if (x == '2') return '3';
        if (x == '3') return '4';
        if (x == '4') return '5';
        if (x == '5') return '6';
        if (x == '6') return '7';
        if (x == '7') return '8';
        if (x == '8') return '9';
        return '0';
    }

    private char decrement(char x) {
        if (x == '0') return '9';
        if (x == '1') return '0';
        if (x == '2') return '1';
        if (x == '3') return '2';
        if (x == '4') return '3';
        if (x == '5') return '4';
        if (x == '6') return '5';
        if (x == '7') return '6';
        if (x == '8') return '7';
        return '8';
    }

}
