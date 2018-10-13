/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.f3.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author f3
 */
public class Server{
    private ServerSocket serverSocket;
    private Socket clienSocket;
    private final Set<String> clientChatNameList;
    private final Set<ClientHandlerThread> clientList; 
    
    public Server(){
        clientChatNameList = new HashSet<String>();
        clientList = new HashSet<ClientHandlerThread>();
    }
    
    public void start(){
        try{
            serverSocket = new ServerSocket(4444); // buat server socket dan buka port
            System.out.println("Server ready, listening on port 4444.");
            
            while(true){
                clienSocket = serverSocket.accept(); // menerima client socket yang meminta koneksi ke serversocket
                System.out.println("New user connected");
                
                ClientHandlerThread client = new ClientHandlerThread(clienSocket, this); // buat class clienthandler untuk setiap user yang baru terkoneksi agar di handle oleh thread yang berbeda2
                clientList.add(client); // tambahkan clientthread client ke list
                
                client.start(); // jalankan thread
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void addClientChatName(String name){
        clientChatNameList.add(name);
    }
    
    public void deleteClientChatName(String name){
        clientChatNameList.remove(name);
    }
    
    public void deleteClient(ClientHandlerThread client){
        clientList.remove(client);
    }
    
    /**
     * Broadcast chat yang di terima dari satu client ke setiap client yang terkoneksi.
     * @param client
     * @param message
     * @throws IOException 
     */
    public void broadcast(ClientHandlerThread client, String message) throws IOException{
        for(ClientHandlerThread item : clientList){
            if(item != client)
                item.sendMessage(message);
        }
    }
    
    /**
     * Dapatkan nama client-client yang terkoneksi.
     * @return 
     */
    public Set<String> getChatNameList(){
        return clientChatNameList;
    }
    
    /**
     * Check apakah ada user yang terkoneksi di dalam list.
     * @return 
     */
    public boolean hasItemNamelist(){
        return !clientChatNameList.isEmpty();
    }
    
    public static void main(String[] args){
        new Server().start();
    }
}
