/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.resource.adapter.jms;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2016 Red Hat inc.
 */
public class GenericJmsContext implements JMSContext {

    private static final String ILLEGAL_METHOD = "This method is not applicable inside the application server. See the JEE spec, e.g. JEE 7 Section 6.7";

    private final JMSContext context;

    GenericJmsContext(JMSContext context) {
        this.context = context;
    }

    @Override
    public JMSContext createContext(int sessionMode) {
        throw new JMSRuntimeException(ILLEGAL_METHOD);
    }

    @Override
    public JMSProducer createProducer() {
        return context.createProducer();
    }

    @Override
    public String getClientID() {
        return context.getClientID();
    }

    @Override
    public void setClientID(String clientID) {
        throw new JMSRuntimeException(ILLEGAL_METHOD);
    }

    @Override
    public ConnectionMetaData getMetaData() {
        return context.getMetaData();
    }

    @Override
    public ExceptionListener getExceptionListener() {
        return context.getExceptionListener();
    }

    @Override
    public void setExceptionListener(ExceptionListener listener) {
        throw new JMSRuntimeException(ILLEGAL_METHOD);
    }

    @Override
    public void start() {
        context.start();
    }

    @Override
    public void stop() {
        throw new JMSRuntimeException(ILLEGAL_METHOD);
    }

    @Override
    public void setAutoStart(boolean autoStart) {
        context.setAutoStart(autoStart);
    }

    @Override
    public boolean getAutoStart() {
        return context.getAutoStart();
    }

    @Override
    public void close() {
        context.close();
    }

    @Override
    public BytesMessage createBytesMessage() {
        return context.createBytesMessage();
    }

    @Override
    public MapMessage createMapMessage() {
        return context.createMapMessage();
    }

    @Override
    public Message createMessage() {
        return context.createMessage();
    }

    @Override
    public ObjectMessage createObjectMessage() {
        return context.createObjectMessage();
    }

    @Override
    public ObjectMessage createObjectMessage(Serializable object) {
        return context.createObjectMessage(object);
    }

    @Override
    public StreamMessage createStreamMessage() {
        return context.createStreamMessage();
    }

    @Override
    public TextMessage createTextMessage() {
        return context.createTextMessage();
    }

    @Override
    public TextMessage createTextMessage(String text) {
        return context.createTextMessage(text);
    }

    @Override
    public boolean getTransacted() {
        return context.getTransacted();
    }

    @Override
    public int getSessionMode() {
        return context.getSessionMode();
    }

    @Override
    public void commit() {
        context.commit();
    }

    @Override
    public void rollback() {
        context.rollback();

    }

    @Override
    public void recover() {
        context.recover();
    }

    @Override
    public JMSConsumer createConsumer(Destination destination) {
        return context.createConsumer(destination);
    }

    @Override
    public JMSConsumer createConsumer(Destination destination, String messageSelector) {
        return context.createConsumer(destination, messageSelector);
    }

    @Override
    public JMSConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) {
        return context.createConsumer(destination, messageSelector, noLocal);
    }

    @Override
    public Queue createQueue(String queueName) {
        return context.createQueue(queueName);
    }

    @Override
    public Topic createTopic(String topicName) {
        return context.createTopic(topicName);
    }

    @Override
    public JMSConsumer createDurableConsumer(Topic topic, String name) {
        return context.createDurableConsumer(topic, name);
    }

    @Override
    public JMSConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) {
        return context.createDurableConsumer(topic, name, messageSelector, noLocal);
    }

    @Override
    public JMSConsumer createSharedDurableConsumer(Topic topic, String name) {
        return context.createSharedDurableConsumer(topic, name);
    }

    @Override
    public JMSConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) {
        return context.createSharedDurableConsumer(topic, name, messageSelector);
    }

    @Override
    public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) {
        return context.createSharedConsumer(topic, sharedSubscriptionName);
    }

    @Override
    public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) {
        return context.createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
    }

    @Override
    public QueueBrowser createBrowser(Queue queue) {
        return context.createBrowser(queue);
    }

    @Override
    public QueueBrowser createBrowser(Queue queue, String messageSelector) {
        return context.createBrowser(queue, messageSelector);
    }

    @Override
    public TemporaryQueue createTemporaryQueue() {
        return context.createTemporaryQueue();
    }

    @Override
    public TemporaryTopic createTemporaryTopic() {
        return context.createTemporaryTopic();
    }

    @Override
    public void unsubscribe(String name) {
        context.unsubscribe(name);
    }

    @Override
    public void acknowledge() {
        context.acknowledge();
    }
}
