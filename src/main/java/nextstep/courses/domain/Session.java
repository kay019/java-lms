package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.users.domain.NsUser;

public class Session {

  private Long id;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private SessionImageInfo sessionImageInfo;

  private boolean isFree;

  private int maxCapacity;

  private int fee;

  private SessionStatus status;

  private List<NsUser> users = new ArrayList<>();


  public Session(LocalDateTime startDate, LocalDateTime endDate,
      SessionImageInfo sessionImageInfo, boolean isFree, int fee, int maxCapacity, SessionStatus status) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.sessionImageInfo = sessionImageInfo;
    this.isFree = isFree;
    this.fee = fee;
    this.maxCapacity = maxCapacity;
    this.status = status;
    validate();
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public SessionImageInfo getSessionImageInfo() {
    return sessionImageInfo;
  }

  public boolean isFree() {
    return isFree;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public int getFee() {
    return fee;
  }

  public SessionStatus getStatus() {
    return status;
  }

  public List<NsUser> getUsers() {
    return users;
  }


  private void validate() {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("시작일은 종료일보다 빠르거나 같아야 합니다.");
    }
  }

  public void enroll(NsUser userId, long amount) {
    if (status != SessionStatus.RECRUITING) {
      throw new IllegalStateException("강의 상태가 모집중일 때만 수강 신청이 가능합니다.");
    }

    if (!isFree) {
      if (users.size() >= maxCapacity) {
        throw new IllegalStateException("최대 수강 인원을 초과할 수 없습니다.");
      }

      if (amount != fee) {
        throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
      }
    }

    users.add(userId);
  }
}
