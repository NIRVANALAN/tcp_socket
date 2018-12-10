package com.bupt.sse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 1122;
    private ServerSocket serverSocket;
    private Socket socket;

    public Server() throws Exception {
        // bind socket
        serverSocket = new ServerSocket(port, 10);
        socket = null;
        // backlog backlog : requested maximum length of the queue of incoming connections.
        // 即连接队列的请求长度
        System.out.println("Serversocket init, backlog is set to 10");
    }

    public void service_single_thread() {
        // listen
        while (true) {
            Socket socket = null;
            try {
                // accept
                socket = serverSocket.accept();
                System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void service_multi_threads() throws IOException, InterruptedException {
        int client_count = 0;
        System.out.println("server start, wait for client");
        while (true) {
            // listen
            socket = serverSocket.accept();
            // new thread when socket is connected, call ctor
            ServerThread serverThread = new ServerThread(socket);
            // start thread
            serverThread.start();
            client_count++;
//            serverThread.join();
            System.out.println("client(s) number now:"+client_count);
            // get InetAdd
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("Client address info: "+inetAddress.getHostAddress());
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
//        server.service_single_thread();
        server.service_multi_threads();
    }
}
