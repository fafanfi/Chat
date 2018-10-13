/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.f3.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author f3
 */
public class ClientReadThread extends Thread{
    private final BufferedReader reader;
    private final Socket socket;
    private final ClientChat client;
    
    public ClientReadThread(Socket socket, ClientChat ui) throws IOException{
        this.socket = socket;
        this.client = ui;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    /**
     * Jalankan thread.
     */
    public void run(){
        try{
            String serverMessage = reader.readLine(); // baca pesan awal dari server yang menginformasikan siapa saja yang terkoneksi
            client.printChat(serverMessage); // print pesan dari server ke jtextarea di class client
            
            client.printChat("Enter your name: ");
            
            while(true){
                serverMessage = reader.readLine(); // baca chat yang di broadcast oleh server (jika koneksi ke server tiba2 terputus method readline akan mengembalikan null)
                
                if(serverMessage == null){
                    socket.close(); // tutup socket
                    return;
                }
                
                client.printChat(serverMessage); // print chat ke jtextarea
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
