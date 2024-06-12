package com.example.integracao.repository.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginacaoDTO implements Serializable {
	private static final long serialVersionUID = 1l;

	@JsonProperty("last_page")
	private Boolean lastPage;
	@JsonProperty("total_elements")
	private Long totalElements;
	@JsonProperty("total_pages")
	private Integer totalPages;
	@JsonProperty("page_size")
	private Integer pageSize;
	@JsonProperty("page_number")
	private Integer pageNumber;
}
