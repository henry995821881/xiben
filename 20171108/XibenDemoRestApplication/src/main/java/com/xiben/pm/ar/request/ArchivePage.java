package com.xiben.pm.ar.request;

public class ArchivePage {

    private Integer curpageno;
    private Integer pagenum;
    private Integer pagesize;
    private Long totalsize;

    public Integer getCurpageno() {
        return curpageno;
    }

    public void setCurpageno(Integer curpageno) {
        this.curpageno = curpageno;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Long getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(Long totalsize) {
        this.totalsize = totalsize;
    }
}
