package com.gugawag.pdist;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumidor {
    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor ativo");

        String NOME_FILA = "FILA_OLA_PDIST_DURAVEL";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();
        boolean duravel = true;
        canal.queueDeclare(NOME_FILA, duravel, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            //Consumindo...
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi mensagem: " + mensagem);
            //ack
            canal.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        boolean autoAck = false;
        canal.basicConsume(NOME_FILA, autoAck, callback, consumertag -> {});
        System.out.println("Cheguei aqui");
    }
}
