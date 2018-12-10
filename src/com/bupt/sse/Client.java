package com.bupt.sse;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Client {
    public int port = 1122;
    public String host = "localhost";
    public final int length = 100;

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

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
//        client.single_thread();
        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Socket socket = null;
        try {
            // init and bind
            socket = new Socket(client.host, client.port);
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
        } finally {
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
    }
}
