package util;

import java.io.*;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class Wr {
    BufferedWriter bw;

    {
        try {
            bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    "D:\\myCode\\tmp-project\\lr-analyze\\src\\main\\resources\\out.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(Object o) {
        try {
            bw.write(o.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void println(Object o) {
        try {
            bw.write(o.toString());
            bw.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
