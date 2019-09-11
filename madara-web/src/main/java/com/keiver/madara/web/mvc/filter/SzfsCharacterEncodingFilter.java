package com.keiver.madara.web.mvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keiver.madara.common.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 字符串编码拦截器
 * 
 * @author HuHui
 * @author 51
 * @version $Id: SzfsCharacterEncodingFilter.java, v 0.1 2018年1月2日 下午3:39:21 51 Exp $
 */
public class SzfsCharacterEncodingFilter extends CharacterEncodingFilter {

    private static final Logger logger               = LoggerFactory.getLogger(SzfsCharacterEncodingFilter.class);

    private static final String IGNORE_URL_SEPARATOR = ",";

    private String              ignoreUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();

        if (!isIgnore(url)) {
            super.doFilterInternal(request, response, filterChain);
        } else {
            LogUtil.info(logger, "igore encode url :{0}", url);
            filterChain.doFilter(request, response);
        }

    }

    public void setIgnoreUrls(String ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    /**
     * 是否忽略该URL
     * @param url 
     * @return true为忽略, false为不忽略
     */
    private boolean isIgnore(String url) {

        if (StringUtils.isBlank(url)) {
            return false;
        }

        if (StringUtils.isNotBlank(ignoreUrls)) {
            String[] ignoreUrlArray = ignoreUrls.split(IGNORE_URL_SEPARATOR);
            for (String urlItem : ignoreUrlArray) {
                if (url.indexOf(urlItem) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

}
