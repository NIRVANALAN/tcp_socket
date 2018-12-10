package com.bupt.sse;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket = null;

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
            String client_info_string = null;
            while ((client_info_string = bufferedReader.readLine()) != null) {
                System.out.println("Receive info from client: " + client_info_string);
            }
            // close
            socket.shutdownInput();
            // get outputStream to client
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            // response the client
            printWriter.write("Request received. You are registered");
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
