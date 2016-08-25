package ld.maven.ldspringjpahib.model.subobjects;

import java.io.Serializable;

public class Decoration implements Serializable{
	
	private Long stripeNumber;
	private String stripeLength;
	private Long starNumber;
	private String starSize;

	public Long getStripeNumber() {
		return stripeNumber;
	}
	public void setStripeNumber(Long stripeNumber) {
		this.stripeNumber = stripeNumber;
	}
	public String getStripeLength() {
		return stripeLength;
	}
	public void setStripeLength(String stripeLength) {
		this.stripeLength = stripeLength;
	}
	public Long getStarNumber() {
		return starNumber;
	}
	public void setStarNumber(Long starNumber) {
		this.starNumber = starNumber;
	}
	public String getStarSize() {
		return starSize;
	}
	public void setStarSize(String starSize) {
		this.starSize = starSize;
	}
	
	@Override
	public String toString() {
		return this.stripeNumber
				+" , "+this.stripeLength
				+" , "+this.starNumber
				+" , "+this.starSize
				;
	}
	

}
