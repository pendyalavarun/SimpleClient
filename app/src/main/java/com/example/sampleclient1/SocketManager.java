package com.example.sampleclient1;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketManager {
    public static BufferedReader bufferedReader = null;
    public static boolean serverrunning = false;
    public static PrintStream printStream;
    public static Socket socket = null;

    public static void setSocket(Socket socket) {
        SocketManager.socket = socket;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setPrintStream(PrintStream ps) {
        printStream = ps;
    }

    public static PrintStream getPrintStream() {
        return printStream;
    }

    public static void setBufferedReader(BufferedReader in) {
        bufferedReader = in;
    }

    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
