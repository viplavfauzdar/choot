package co.viplove.choot.controller;

import java.io.*;
import java.net.*;

public class PortForwarder {
    public static void main(String[] args) {
        //if (args.length < 3) {
            System.out.println("Usage: java PortForwarder <sourcePort> <targetHost> <targetPort>");
       //     return;
        //}

        int sourcePort = 443;//Integer.parseInt(args[0]);
        String targetHost = "portal.gp.homedepot.com"; //args[1];
        int targetPort = 443;//Integer.parseInt(args[2]);

        System.out.println("Starting port forwarder...");
        System.out.println("Listening on port " + sourcePort + ", forwarding to " + targetHost + ":" + targetPort);

        try (ServerSocket serverSocket = new ServerSocket(sourcePort)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket, targetHost, targetPort)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, String targetHost, int targetPort) {
        try (Socket targetSocket = new Socket(targetHost, targetPort);
             InputStream clientInput = clientSocket.getInputStream();
             OutputStream clientOutput = clientSocket.getOutputStream();
             InputStream targetInput = targetSocket.getInputStream();
             OutputStream targetOutput = targetSocket.getOutputStream()) {

            Thread forwardClientToTarget = new Thread(() -> forwardData(clientInput, targetOutput));
            Thread forwardTargetToClient = new Thread(() -> forwardData(targetInput, clientOutput));

            forwardClientToTarget.start();
            forwardTargetToClient.start();

            forwardClientToTarget.join();
            forwardTargetToClient.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void forwardData(InputStream input, OutputStream output) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        try {
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                output.flush();
            }
        } catch (IOException e) {
            // Connection closed
        }
    }
}

