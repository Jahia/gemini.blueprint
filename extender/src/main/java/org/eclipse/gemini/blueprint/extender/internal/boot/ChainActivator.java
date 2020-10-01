/******************************************************************************
 * Copyright (c) 2006, 2010 VMware Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html and the Apache License v2.0
 * is available at http://www.opensource.org/licenses/apache2.0.php.
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *   VMware Inc.
 *****************************************************************************/

package org.eclipse.gemini.blueprint.extender.internal.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.eclipse.gemini.blueprint.extender.internal.activator.ContextLoaderListener;
import org.springframework.util.ClassUtils;

/**
 * Bundle activator that simply the lifecycle callbacks to other activators.
 *
 * @author Costin Leau
 *
 */
public class ChainActivator implements BundleActivator {

	protected final Log log = LogFactory.getLog(getClass());

	private static final boolean BLUEPRINT_AVAILABLE =
			ClassUtils.isPresent("org.osgi.service.blueprint.container.BlueprintContainer", ChainActivator.class
					.getClassLoader());

	private final BundleActivator[] CHAIN;

	public ChainActivator() {
		log.info("Blueprint Container functionality of Gemini is not enabled");
		CHAIN = new BundleActivator[] { new ContextLoaderListener() };
	}

	public void start(BundleContext context) throws Exception {
		for (int i = 0; i < CHAIN.length; i++) {
			CHAIN[i].start(context);
		}
	}

	public void stop(BundleContext context) throws Exception {
		for (int i = CHAIN.length - 1; i >= 0; i--) {
			CHAIN[i].stop(context);
		}
	}
}
