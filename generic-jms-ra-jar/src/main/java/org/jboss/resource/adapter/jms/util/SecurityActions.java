/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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
package org.jboss.resource.adapter.jms.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Security Actions.
 *
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2014 Red Hat inc.
 */
public class SecurityActions {

    public static ClassLoader getThreadContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        }
    }

    /**
     * Set the context classloader.
     *
     * @param cl classloader
     */
    public static void setThreadContextClassLoader(final ClassLoader cl) {
        if (System.getSecurityManager() == null) {
            Thread.currentThread().setContextClassLoader(cl);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    Thread.currentThread().setContextClassLoader(cl);

                    return null;
                }
            });
        }
    }

    /**
     * Get a classLoader
     *
     * @param clazz The class name
     * @return the ClassLoader
     */
    static ClassLoader getClassLoader(final Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            @Override
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        });
    }

    static Method getMethod(final Class<?> clazz, final String method, Class<?>... parameters) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getMethod(method, parameters);
        }
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
                @Override
                public Method run() throws NoSuchMethodException {
                    return clazz.getMethod(method, parameters);
                }
            });
        } catch (PrivilegedActionException ex) {
            throw (NoSuchMethodException) ex.getException();
        }
    }

    static Field getDeclaredField(final Class<?> clazz, final String name) throws NoSuchFieldException {
        if (System.getSecurityManager() == null) {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Field>() {
                @Override
                public Field run() throws NoSuchFieldException {
                    Field field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    return field;
                }
            });
        } catch (PrivilegedActionException ex) {
            throw (NoSuchFieldException) ex.getException();
        }
    }
}
