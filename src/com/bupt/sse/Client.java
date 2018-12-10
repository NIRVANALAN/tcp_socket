package com.bupt.sse;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Client {
    public int port = 1122;
    public String host = "localhost";
    public final int length = 100;
    OutputStream outputStream = null;
    PrintWriter printWriter = null;
    InputStream inputStream = null;
    //        InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    Socket socket = null;

    public void single_thread() throws InterruptedException, IOException {
        Socket[] sockets = new Socket[length];
        for (int i = 0; i < length; i++) {
            sockets[i] = new Socket(host, port);
            System.out.println("Client " + i + " connect successfully");
        }
        // thread
        Thread.sleep(3000);
        for (int i = 0; i < sockets.length; i++) {
            sockets[i].close();
        }
    }

    public void client_wellworked() throws IOException {
//        client.single_thread();

        try {
            // init and bind
            socket = new Socket(host, port);
            // get outputStream
            outputStream = socket.getOutputStream(); // byte output steam
            // pack outputStream to print stream
            printWriter = new PrintWriter(outputStream);
            // write to outputStream
            printWriter.write("username:2016211930;password:123456");
            // flush
            printWriter.flush();
            // close outputStream
//            outputStream.close();
            socket.shutdownOutput();

            // get inputStream and read response info
            inputStream = socket.getInputStream();
            // use BufferRead
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String server_info_string = null;
            while ((server_info_string = bufferedReader.readLine()) != null) {
                System.out.printf("Response from server: " + server_info_string);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void client_calculate() throws IOException {
        int a = 1, b = 2;
        try {
            // init and bind
            socket = new Socket(host, port);
            // get outputStream
            outputStream = socket.getOutputStream(); // byte output steam
            // pack outputStream to print stream
            printWriter = new PrintWriter(outputStream);
            // write to outputStream
            // start calculation
            printWriter.write(a);
            printWriter.write(b);
            // flush
            printWriter.flush();
            // close outputStream
//            outputStream.close();
            socket.shutdownOutput();

            // get inputStream and read response info
            inputStream = socket.getInputStream();
            // use BufferRead
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String server_info_string = null;
            while ((server_info_string = bufferedReader.readLine()) != null) {
                System.out.print("Response from server: " + server_info_string);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // detor


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // close
        if (bufferedReader != null)
            bufferedReader.close();
        if (inputStream != null)
            inputStream.close();
        if (printWriter != null)
            printWriter.close();
        if (outputStream != null)
            outputStream.close();
        if (socket != null)
            socket.close();
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
