package org.openedit.entermedia.smartjog;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	
	
	
	
	public static Test suite()
	{
	
		String rootPath = System.getProperty( "oe.root.path" );
		if ( rootPath == null )
		{
			System.setProperty("oe.root.path", "base");
		}
		TestSuite suite = new TestSuite( "Test for entermedia" );
		
		suite.addTestSuite( SmartJogTest.class );
		
		return suite;
	}
}
