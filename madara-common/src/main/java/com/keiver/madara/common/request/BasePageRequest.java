package com.keiver.madara.common.request;


import com.keiver.madara.common.base.BaseRequest;

/**
 * 基础分页类
 * @author prd-fuy
 * @version $Id: BasePageRequest.java, v 0.1 2018年1月16日 下午5:11:40 prd-fuy Exp $
 */
public abstract class BasePageRequest extends BaseRequest {

    /**  */
    private static final long serialVersionUID = 3515108529325911948L;

    /** 查询页码 */
    protected int             pageNum;

    /** 每页查询结果数 */
    protected int             pageSize;

    /** 排序字段名*/
    protected String          sidx;

    /** 排序方式*/
    protected String          order;

    /**
     * Getter method for property <tt>pageNum</tt>.
     * 
     * @return property value of pageNum
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Setter method for property <tt>pageNum</tt>.
     * 
     * @param pageNum value to be assigned to property pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     * 
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     * 
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
