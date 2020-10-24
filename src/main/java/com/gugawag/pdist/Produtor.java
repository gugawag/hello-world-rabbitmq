package com.gugawag.pdist;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {

    public static void main(String[] args) throws Exception {
        // connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        String NOME_FILA = "FILA_OLA_PDIST_DURAVEL";

        try (
                // conexao
                Connection connection = connectionFactory.newConnection();
                // canal
                Channel canal = connection.createChannel()) {
            // criar fila
            //boolean durable, // a fila irá durar entre reinícios servidor
            //boolean exclusive, // se a fila é exclusiva
            //boolean autoDelete, // quando ninguém mais usar, será apagada
            boolean duravel = true;
            canal.queueDeclare(NOME_FILA, duravel, false, false, null);

            // enviar mensagem
            String mensagem = "Tudo bem com vcs?5";

            canal.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());
            System.out.println("Enviada mensagem: " + mensagem);
        }

    }
}
