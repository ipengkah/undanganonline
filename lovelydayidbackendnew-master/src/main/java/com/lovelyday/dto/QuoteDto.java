package com.lovelyday.dto;

public class QuoteDto {
	private String quote;
	private String quoteGiver;

	
	public QuoteDto(String quote,String quoteGiver) {
		super();
		this.quote = quote;
		this.quoteGiver = quoteGiver;

	}
	
	@Override
	public String toString() {
		return "QuoteDto [ quote=" + quote + ", quoteGiver="
				+ quoteGiver + "]";
	}



	//GETTER SETTER
	
	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getQuoteGiver() {
		return quoteGiver;
	}

	public void setQuoteGiver(String quoteGiver) {
		this.quoteGiver = quoteGiver;
	}


}
