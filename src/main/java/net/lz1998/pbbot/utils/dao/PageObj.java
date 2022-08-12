package net.lz1998.pbbot.utils.dao;

public class PageObj {
  private int rows;
  private int page;
  private int total;

  public PageObj() {
  }

  public int getRows() {
    return this.rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotal() {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
