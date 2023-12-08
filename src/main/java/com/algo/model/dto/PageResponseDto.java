package com.algo.model.dto;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model.dto
 * @since : 07.12.23
 */
public class PageResponseDto<T> {

  private final Page<T> pageable;
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

  public int getNumberSize() {
    return this.numberSize;
  }

  /**
   * 현재 Number 기준으로 시작 Number를 반환한다.
   * <p>
   * ex) Number = 3 일 경우 startNumber는 1
   * ex) Number = 13 일 경우 startNumber는 11
   * ex) Number = 23 일 경우  startNumber는 21
   * ex) Number = 123 경우 startNumber는 121
   */
  public int getStartNumber() {
    return ((pageable.getNumber() / 10) * 10) + 1;
  }

  /**
   * 현재 Number 기준으로 마지막 Number를 반환한다.
   * <p>
   * ex) Number = 3 일 경우 startNumber는 10
   * ex) Number = 13 일 경우 startNumber는 20
   * ex) Number = 23 일 경우 startNumber는 30
   * ex) Number = 123 경우 startNumber는 130
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

  public int getSize() {
    return pageable.getSize();
  }

  public int getNumberOfElements() {
    return pageable.getNumberOfElements();
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

  public long getTotalElements() {
    return pageable.getTotalElements();
  }

  public boolean isStartNumberPeriod() {
    return getNumber() >= 0 && getNumber() <= numberSize;
  }

  public boolean isEndNumberPeriod() {
    int lastNumber = pageable.getTotalPages();
    int endNumber = getEndNumber();
    return lastNumber == endNumber;
  }
}
