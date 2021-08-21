package com.zhangying.oem1688.custom.scroll;

public interface PageDecorationLastJudge {
    /**
     * Is the last row in one page
     *
     * @param position
     * @return
     */
    boolean isLastRow(int position);
 
    /**
     * Is the last Colum in one row;
     *
     * @param position
     * @return
     */
    boolean isLastColumn(int position);
 
    boolean isPageLast(int position);
}