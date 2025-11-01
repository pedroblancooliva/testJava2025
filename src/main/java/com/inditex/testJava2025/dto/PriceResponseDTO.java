package com.inditex.testJava2025.dto;

import java.math.BigDecimal;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class PriceResponseDTO {



	@Schema(description = "Identificador del producto", example = "35455")
	private Long productId;

	@Schema(description = "Identificador de la marca/cadena", example = "1")
	private Long brandId;

	@Schema(description = "Identificador de la tarifa aplicada", example = "1")
	private Integer priceList;

	@Schema(description = "Precio aplicable", example = "35.50")
	private BigDecimal price;

	@Schema(description = "Código de moneda", example = "EUR")
	private String currency;

	
	public PriceResponseDTO() {
		super();
	}
	
	public PriceResponseDTO(Long productId, Long brandId, Integer priceList, BigDecimal price, String currency) {
		super();
		this.productId = productId;
		this.brandId = brandId;
		this.priceList = priceList;
		this.price = price;
		this.currency = currency;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brandId, currency, price, priceList, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceResponseDTO other = (PriceResponseDTO) obj;
		return Objects.equals(brandId, other.brandId) && Objects.equals(currency, other.currency)
				&& Objects.equals(price, other.price) && Objects.equals(priceList, other.priceList)
				&& Objects.equals(productId, other.productId);
	}

	@Override
	public String toString() {
		return "PriceResponseDTO [productId=" + productId + ", brandId=" + brandId + ", priceList=" + priceList
				+ ", price=" + price + ", currency=" + currency + "]";
	}

}
