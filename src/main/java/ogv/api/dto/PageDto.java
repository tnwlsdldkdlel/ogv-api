package ogv.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogv.api.util.Values.SEARCH_TARGET;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDto {

	private Long page = 1L;
	private int size = 10;
	private Long startPage = 1L;
	private Long endPage = 0L;
	private Long amount = 0L; // 총게시물
	private Boolean prev;
	private Boolean next;
	private Long allPage; // 총 페이지
	private String type;
	private String search;
	private SEARCH_TARGET searchTarget;
	private String start;
	private String end;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Long getStartPage() {
		this.startPage = this.endPage - 9;

		return this.startPage = this.startPage < 0 ? 1 : this.startPage;
	}

	public void setStartPage(Long startPage) {
		this.startPage = startPage;
	}

	public Long getEndPage() {
		this.endPage = (long) (Math.ceil(this.page / 10) * 10); // 10
		
		if (getAllPage() > this.endPage) {
			this.endPage = getAllPage();
		}

		return this.endPage;
	}

	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Boolean getPrev() {
		return getStartPage() > 1;
	}

	public void setPrev(Boolean prev) {
		this.prev = prev;
	}

	public Boolean getNext() {
		return getEndPage() < getAllPage();
	}

	public void setNext(Boolean next) {
		this.next = next;
	}

	public Long getAllPage() {
		return allPage = (this.amount / this.size) + 1;
	}

	public void setAllPage(Long allPage) {
		this.allPage = allPage;
	}

}
