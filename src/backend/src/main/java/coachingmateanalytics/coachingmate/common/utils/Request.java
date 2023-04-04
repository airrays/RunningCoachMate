package coachingmateanalytics.coachingmate.common.utils;

public class Request<E> {
  private E model;
  private Paging paging;
  private String keyword;
  private String action;
  private String info;

  public E getModel() {
    return model;
  }

  public void setModel(E model) {
    this.model = model;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }
}
