package com.bupt.sse;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket = null;
    int[] Fibonacci = new int[2];
    boolean login = false;
    int index = 0;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
//        super.run();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try {
            // read input from socket
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            // get outputStream to client
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            String client_info_string = null;
            while ((client_info_string = bufferedReader.readLine()) != null) {
                if (login == false && client_info_string.equals("username:2016211930;password:123456")) {
                    login = true;
                    System.out.println("Receive login request from client: " + client_info_string);
                } else {
                    // calculate sum
                    Fibonacci[index] = Integer.parseInt(client_info_string);
                    index = 1 - index;
                    if (index == 0) {
                        // string to int
                        printWriter.write(String.valueOf(Fibonacci[0] + Fibonacci[1]));
                    }
                }
            }
            // close
            socket.shutdownInput();
            // response the client when the first connection is confirmed
            printWriter.write("Request received. You are Logged in. Calculation will start next");
            // flush
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (printWriter != null)
                    printWriter.close();
                if (outputStream != null)
                    outputStream.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
