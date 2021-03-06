/*
 Copyright  2002-2004 MySQL AB, 2008 Sun Microsystems

 This program is free software; you can redistribute it and/or modify
 it under the terms of version 2 of the GNU General Public License as 
 published by the Free Software Foundation.

 There are special exceptions to the terms and conditions of the GPL 
 as it is applied to this software. View the full text of the 
 exception in file EXCEPTIONS-CONNECTOR-J in the directory of this 
 software distribution.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 
 */
package com.mysql.jdbc.jdbc2.optional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Map;

import com.mysql.jdbc.SQLError;

/**
 * Base class for all wrapped instances created by LogicalHandle
 * 
 * @author Mark matthews
 * 
 * @version $Id$
 */
abstract class WrapperBase {
	protected MysqlPooledConnection pooledConnection;

	/**
	 * Fires connection error event if required, before re-throwing exception
	 * 
	 * @param sqlEx
	 *            the SQLException that has ocurred
	 * @throws SQLException
	 *             (rethrown)
	 */
	protected void checkAndFireConnectionError(SQLException sqlEx)
			throws SQLException {
		if (this.pooledConnection != null) {
			if (SQLError.SQL_STATE_COMMUNICATION_LINK_FAILURE.equals(sqlEx
					.getSQLState())) {
				this.pooledConnection.callConnectionEventListeners(
						MysqlPooledConnection.CONNECTION_ERROR_EVENT, sqlEx);
			}
		}

		throw sqlEx;
	}
	
	protected Map unwrappedInterfaces = null;

	protected class ConnectionErrorFiringInvocationHandler implements InvocationHandler {
		Object invokeOn = null;
		
		public ConnectionErrorFiringInvocationHandler(Object toInvokeOn) {
			invokeOn = toInvokeOn;
		}
		
		public Object invoke(Object proxy, Method method,
				Object[] args) throws Throwable {
			Object result = null;

			try {
				result = method.invoke(invokeOn, args);
				
				if (result != null) {
					result = proxyIfInterfaceIsJdbc(result, 
							result.getClass());
				}
			} catch (InvocationTargetException e) {
				if (e.getTargetException() instanceof SQLException) {
					checkAndFireConnectionError((SQLException) e
							.getTargetException());
				} else {
					throw e;
				}
			}

			return result;
		}
		
		/**
		 * Recursively checks for interfaces on the given object to determine
		 * if it implements a java.sql interface, and if so, proxies the 
		 * instance so that we can catch and fire SQL errors.
		 * @param toProxy
		 * @param clazz
		 * @return
		 */
		private Object proxyIfInterfaceIsJdbc(Object toProxy, Class clazz) {
			Class[] interfaces = clazz.getInterfaces();
			
			for (int i = 0; i < interfaces.length; i++) {
				String packageName = interfaces[i].getPackage().getName();
				
				if ("java.sql".equals(packageName) || 
						"javax.sql".equals(packageName)) {
					return Proxy.newProxyInstance(toProxy.getClass()
							.getClassLoader(), interfaces,
							new ConnectionErrorFiringInvocationHandler(toProxy));
				}
				
				return proxyIfInterfaceIsJdbc(toProxy, interfaces[i]);
			}
			
			return toProxy;
		}
	}
}