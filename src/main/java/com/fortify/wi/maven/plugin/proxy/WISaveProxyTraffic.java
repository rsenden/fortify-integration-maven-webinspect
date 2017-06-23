/*******************************************************************************
 * (c) Copyright 2017 Hewlett Packard Enterprise Development LP
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the Software"),
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.wi.maven.plugin.proxy;

import java.nio.file.CopyOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.fortify.wi.maven.plugin.AbstractWIMojo;

/**
 * Mojo for saving WebInspect proxy traffic to local disk
 * 
 * @author Ruud Senden
 *
 */
@Mojo(name = "wiSaveProxyTraffic", defaultPhase = LifecyclePhase.NONE, requiresProject = false)
public class WISaveProxyTraffic extends AbstractWIMojo {
	/**
	 * The instance id of the proxy, as specified or generated when creating the proxy instance,
	 * for which the traffic needs to be saved.
	 */
	@Parameter(property = "com.fortify.webinspect.proxy.instanceId", required = false)
	protected String instanceId;
	
	/**
	 * Extension of savefile, choose between webmacro, tsf or xml.
	 * Defaults to xml.
	 */
	@Parameter(property = "com.fortify.webinspect.proxy.traffic.extension", required = true, defaultValue = "xml")
	private String extension;
	
	@Parameter(property = "com.fortify.webinspect.proxy.outputFile", required = true)
	private String outputFile;
	
	@Parameter(property = "com.fortify.webinspect.proxy.replaceExistingOutputFile", required = false, defaultValue = "true")
	private boolean replaceExistingOutputFile;
	

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		CopyOption[] copyOptions = new CopyOption[]{};
		if ( replaceExistingOutputFile ) {
			copyOptions = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
		}
		getWebInspectConnection().saveProxyTraffic(instanceId, extension, Paths.get(outputFile), copyOptions);
	}
}