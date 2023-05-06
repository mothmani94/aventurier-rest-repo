package com.aventurier.app.backend.business.enums;


/***
 * 
 * @author motmani
 *
 */
public enum CardinalDirectionsEnum {

	NORD("N"),
	SUD("S"),
	EST("E"),
	OUEST("O");

	String code;
	
	private CardinalDirectionsEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static CardinalDirectionsEnum getByCode(String code) {
		for (CardinalDirectionsEnum value : CardinalDirectionsEnum.values()) {
			if(value.getCode().equals(code)) {
				return value;
			}
		}
		return null;
	}
}
