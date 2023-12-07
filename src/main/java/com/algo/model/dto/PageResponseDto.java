package com.algo.model.dto;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model.dto
 * @since : 07.12.23
 */
@RequiredArgsConstructor
public class PageResponseDto<T> {

  private final Page<T> pageable;

  public int getStartNumber() {
    return ((pageable.getNumber() / 10) * 10) + 1;
  }

  public int getEndNumber() {
    return Math.min(pageable.getTotalPages() - 1, getStartNumber() + 9);
  }

  public int getNumber() {
    return pageable.getNumber();
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

  public boolean isFirst() {
    return pageable.isFirst();
  }

  public boolean isLast() {
    return pageable.isLast();
  }

  public boolean hasNext() {
    return pageable.hasNext();
  }

  public boolean hasPrevious() {
    return pageable.hasPrevious();
  }
}
