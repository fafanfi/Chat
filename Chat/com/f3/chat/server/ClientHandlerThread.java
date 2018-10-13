/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.f3.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Class untuk menghandle setiap user.
 * 
 * @author f3
 */
public class ClientHandlerThread extends Thread{
    private final Socket socket;
    private final Server server;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    
    public ClientHandlerThread(Socket socket, Server server) throws IOException{
        this.socket = socket;
        this.server = server;
        
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // handle inputstream dari socket menggunakan buffer
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // handle outputstream dari socket menggunakan buffer
    }
    
    /**
     * Jalankan thread.
     */
    public void run(){
        try{
            printUsers(); // kirim daftar user yang terkoneksi ke client
            
            String name = reader.readLine(); // di readline pertama ini menerima inputan nama chat dari user
            server.addClientChatName(name); // tambah nama ke list
            server.broadcast(this, "New user connected: [" + name + "]"); // broadcast pesan ke semua user yang terkoneksi
            
            String message;
            
            while(true){
                message = reader.readLine(); // di readline ini menerima chat dari user (jika user tiba2 menutup aplikasi method readline akan mengembalikan nilai null)
                
                if(message == null){
                    server.broadcast(this, "User [" + name + "]" + " leave chat.");
                    server.deleteClient(this); // delete class clienthandlerthread yang menangani user ini dari daftar list
                    server.deleteClientChatName(name); // delete nama user dari daftar list
                    
                    socket.close(); // tutup koneksi socket dari user ini
                    System.out.println("1 user disconnected");
                    
                    return;
                }
                
                server.broadcast(this, String.format("[%s]: %s", name, message));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Kirim pesan ke user yang di handle class thread ini.
     * @param message
     * @throws IOException 
     */
    public void sendMessage(String message) throws IOException{
        writer.write(message + '\n');
        writer.flush(); // flush buffer
    }
    
    private void printUsers() throws IOException{
        if(server.hasItemNamelist()) // cek apakah ada user yang terkoneksi di list
            writer.write("User connected: " + server.getChatNameList() + '\n');
        else
            writer.write("No user connected." + '\n');

        writer.flush(); // flush buffer
    }
}
