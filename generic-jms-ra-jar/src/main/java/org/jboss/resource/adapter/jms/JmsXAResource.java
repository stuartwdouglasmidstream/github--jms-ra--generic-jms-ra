/*
* JBoss, Home of Professional Open Source
* Copyright 2007, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.jboss.logging.Logger;

/**
 * JmsXAResource.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class JmsXAResource implements XAResource {
    
    private static final Logger log = Logger.getLogger(JmsXAResource.class);
    /**
     * The managed connection
     */
    private final JmsManagedConnection managedConnection;

    /**
     * The resource
     */
    private final XAResource xaResource;

    /**
     * Create a new JmsXAResource.
     *
     * @param managedConnection the managed connection
     * @param xaResource        the xa resource
     */
    public JmsXAResource(JmsManagedConnection managedConnection, XAResource xaResource) {
        this.managedConnection = managedConnection;
        this.xaResource = xaResource;
    }

    @Override
    public void start(Xid xid, int flags) throws XAException {
        managedConnection.lock();
        try {
            xaResource.start(xid, flags);
        } finally {
            managedConnection.unlock();
        }
    }

    @Override
    public void end(Xid xid, int flags) throws XAException {
        managedConnection.lock();
        try {
            xaResource.end(xid, flags);
        } finally {
            managedConnection.unlock();
        }
    }

    @Override
    public int prepare(Xid xid) throws XAException {
        managedConnection.lock();
        try {
            return xaResource.prepare(xid);
        } finally {
            managedConnection.unlock();
        }
    }

    @Override
    public void commit(Xid xid, boolean onePhase) throws XAException {
        managedConnection.lock();
        try {
            xaResource.commit(xid, onePhase);
        } finally {
            managedConnection.unlock();
        }
    }

    @Override
    public void rollback(Xid xid) throws XAException {
        managedConnection.lock();
        try {
            xaResource.rollback(xid);
        } finally {
            managedConnection.unlock();
        }
    }

    @Override
    public void forget(Xid xid) throws XAException {
        managedConnection.lock();
        try {
            xaResource.forget(xid);
        } finally {
            managedConnection.unlock();
        }
    }

    public XAResource getUnderlyingXAResource() {
        return xaResource;
    }

    @Override
    public boolean isSameRM(XAResource xaRes) throws XAException {
        XAResource currentRes = xaRes;
        if (currentRes instanceof JmsXAResource) {
            currentRes = ((JmsXAResource) currentRes).getUnderlyingXAResource();
        }
        return xaResource.isSameRM(currentRes);
    }

    @Override
    public Xid[] recover(int flag) throws XAException {
        if(xaResource == null) {
            log.warn("The underlying xaResource is NULL. We can't recover !!!!!");
        }
        return xaResource.recover(flag);
    }

    @Override
    public int getTransactionTimeout() throws XAException {
        return xaResource.getTransactionTimeout();
    }

    @Override
    public boolean setTransactionTimeout(int seconds) throws XAException {
        return xaResource.setTransactionTimeout(seconds);
    }


}
