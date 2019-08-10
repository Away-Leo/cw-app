package com.cw.core.common.fastdfs;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/3.
 */
public interface FileManagerConfig extends Serializable {

    public static final String FILE_DEFAULT_WIDTH 	= "120";
    public static final String FILE_DEFAULT_HEIGHT 	= "120";
    public static final String FILE_DEFAULT_AUTHOR 	= "root";

    public static final String PROTOCOL = "https://";
    public static final String Domain = "www.66sudai.cn";
    public static final String SEPARATOR = "/";
    public static final String TRACKER_NGNIX_PORT 	= "";
    public static final String CLIENT_CONFIG_FILE   = "fdfs_client.conf";


}