package util;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class Re {
    BufferedReader br;
    StringTokenizer st = new StringTokenizer("");

    {
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "D:\\myCode\\tmp-project\\lr-analyze\\src\\main\\resources\\in.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String innerNextLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasNext() {
        while (!st.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            st = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String next() {
        hasNext();
        return st.nextToken();
    }

    public String nextLine() {
        st = new StringTokenizer("");
        return innerNextLine();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
}
