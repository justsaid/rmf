package org.eclipse.rmf.reqif10.pror.report.oda.impl.test;

import java.util.Iterator;

import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.Specification;

public class SpecificationIterator implements Iterator<SpecHierarchy> {

	private Specification specification;
	private int cursor;
	private int max;
	
	public SpecificationIterator(Specification specification) {
		this.specification = specification;
		cursor = 0;
		Iterator it = specification.getChildren().listIterator();
		
	}
	@Override
	public boolean hasNext() {
		
		return false;
	}

	@Override
	public SpecHierarchy next() {
		
		return null;
	}

	@Override
	public void remove() {
		
	}



}
