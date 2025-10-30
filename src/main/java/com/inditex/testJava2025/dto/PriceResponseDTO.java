package com.inditex.testJava2025.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;




public class PriceResponseDTO {
	
		private int productId;
		private Integer brandId;
	    private int priceList;
	    private LocalDateTime startDate;
	    private LocalDateTime endDate;
	    private BigDecimal price;
	    
	    
	    public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public Integer getBrandId() {
			return brandId;
		}
		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}
		public int getPriceList() {
			return priceList;
		}
		public void setPriceList(int priceList) {
			this.priceList = priceList;
		}
		public LocalDateTime getStartDate() {
			return startDate;
		}
		public void setStartDate(LocalDateTime startDate) {
			this.startDate = startDate;
		}
		public LocalDateTime getEndDate() {
			return endDate;
		}
		public void setEndDate(LocalDateTime endDate) {
			this.endDate = endDate;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
	    
}
