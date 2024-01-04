package com.algo.others;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model.dto
 * @since : 07.12.23
 */
public class PageResponseDto<T> {

  /**
   * client 에서 넘어오는 페이지 넘버는 -1 을 해줘야 한다.
   * <p> ex) 화면에서 1 페이지를 클릭했을때 실제 넘어오는 page 값은 0 이다.
   * <p> Page 내부적으로 0 페이지가 첫번째 페이지라 인식하고 페이지네이션 계산 진행.
   */
  private final Page<T> pageable;

  /**
   * 한번에 보여지는 number의 size
   * <p> ex) numberSize = 10 일경우 하단의 number는 1부터 10 까지 출력 </p>
   * <p> 하단에 표시되는 number가 10개이면 실제 numberSize에 저장되는 값은 9 이다. </p>
   */
  private final int numberSize;

  public static <T> PageResponseDto<T> of(Page<T> pageable) {
    return new PageResponseDto<>(pageable);
  }

  public static <T> PageResponseDto<T> of(Page<T> pageable, int numberSize) {
    return new PageResponseDto<>(pageable, numberSize);
  }

  public PageResponseDto(Page<T> pageable) {
    this.pageable = pageable;
    numberSize = 10 - 1;
  }

  public PageResponseDto(Page<T> pageable, int numberSize) {
    this.pageable = pageable;
    this.numberSize = numberSize - 1;
  }

  /**
   * 현재 Number 기준으로 시작 Number를 반환한다.
   * <p>
   * ex) Number = 3 일 경우 startNumber는 1
   */
  public int getStartNumber() {
    return ((pageable.getNumber() / 10) * 10) + 1;
  }

  /**
   * 현재 Number 기준으로 마지막 Number를 반환한다.
   * <p>
   * ex) Number = 3 일 경우 startNumber는 10
   */
  public int getEndNumber() {
    return Math.min(pageable.getTotalPages(), getStartNumber() + numberSize);
  }

  /**
   * 현재 Number 반환
   */
  public int getNumber() {
    return pageable.getNumber();
  }

  public int getTotalPages() {
    return pageable.getTotalPages();
  }

  public List<T> getContent() {
    return pageable.getContent();
  }

  public boolean hasNext() {
    return pageable.hasNext();
  }

  public boolean hasPrevious() {
    return pageable.hasPrevious();
  }

  public boolean isStartNumberPeriod() {
    return getNumber() >= 0 && getNumber() <= numberSize;
  }

  public boolean isEndNumberPeriod() {
    int lastNumber = pageable.getTotalPages();
    int endNumber = getEndNumber();
    return lastNumber == endNumber;
  }

  public void createResults(Model model) {
    model.addAttribute("contents", this.getContent());
    model.addAttribute("totalPages", this.getTotalPages());
    model.addAttribute("isStartNumberPeriod", this.isStartNumberPeriod());
    model.addAttribute("isEndNumberPeriod", this.isEndNumberPeriod());
    model.addAttribute("startNumber", this.getStartNumber());
    model.addAttribute("endNumber", this.getEndNumber());
    model.addAttribute("hasPrevious", this.hasPrevious());
    model.addAttribute("hasNext", this.hasNext());
    model.addAttribute("currentPage", this.getNumber());
  }
}
