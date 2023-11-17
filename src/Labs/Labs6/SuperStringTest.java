package Labs.Labs6;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Collections;

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}

class SuperString {
    private LinkedList<String> strings;
    private LinkedList<Integer> stack;

    public SuperString() {
        strings = new LinkedList<>();
        stack = new LinkedList<>();
    }

    public void append(String s) {
        strings.add(s);
        stack.add(1);
    }

    public void insert(String s) {
        strings.add(0, s);
        stack.add(-1);
    }

    public boolean contains(String s) {
        StringBuilder sb = new StringBuilder();
        strings.forEach(sb::append);

        return sb.toString().contains(s);
    }

    public void reverse() {
        strings.replaceAll(string -> new StringBuilder(string).reverse().toString());
        stack.replaceAll(integer -> -integer);
        Collections.reverse(strings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        strings.forEach(sb::append);

        return sb.toString();
    }

    public void removeLast(int k) {
        while (k-- != 0) {
            if (strings.isEmpty()) return;
            else {
                int integer = stack.getLast();

                if (integer == 1) strings.removeLast();
                else strings.removeFirst();

                stack.removeLast();
            }
        }
    }
}