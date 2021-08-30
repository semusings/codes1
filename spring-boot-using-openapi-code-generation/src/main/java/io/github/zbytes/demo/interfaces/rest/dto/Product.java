package io.github.zbytes.demo.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Product
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-11-29T11:22:35.830+05:45[Asia/Kathmandu]")

public class Product  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("productId")
  private String productId;

  @JsonProperty("productName")
  private String productName;

  public Product productId(String productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Product identifier
   * @return productId
  */
  @ApiModelProperty(example = "X0Q01", value = "Product identifier")

@Size(min=5,max=5) 
  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Product productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Product name
   * @return productName
  */
  @ApiModelProperty(example = "Apple", value = "Product name")

@Size(min=4) 
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.productId, product.productId) &&
        Objects.equals(this.productName, product.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, productName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

