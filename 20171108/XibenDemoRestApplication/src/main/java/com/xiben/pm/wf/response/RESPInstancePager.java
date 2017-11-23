package com.xiben.pm.wf.response;

import java.util.List;

public class RESPInstancePager {


    private List<RESPInstance> data;
    private PageObj page;

    public PageObj getPage() {
        return page;
    }

    public void setPage(PageObj page) {
        this.page = page;
    }

    public List<RESPInstance> getData() {
        return data;
    }

    public void setData(List<RESPInstance> data) {
        this.data = data;
    }

    public static class PageObj{
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

}
