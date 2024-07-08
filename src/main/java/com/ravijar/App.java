package com.ravijar;

import com.ravijar.handler.CommandHandler;

public class App {

    public static void main(String[] args) {

        CommandHandler commandHandler = new CommandHandler();

        if (args.length > 0 && "create-app".equals(args[0])) {
            commandHandler.createReactApp();
        } else {
            System.out.println("Invalid command. Use 'create-app' to create a React application.");
        }
    }

}
