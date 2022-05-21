package cn.edu.gxust.blogex.upload.service;

import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.Live2dPO;
import cn.edu.gxust.blogex.upload.dto.Live2dDTO;
import cn.edu.gxust.blogex.upload.query.Live2dPageQuery;
import cn.edu.gxust.blogex.upload.vo.Live2dVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2022/3/29 10:05
 */
public interface Live2dService extends IService<Live2dPO> {

    /**
     * 上传live2d压缩包，解压后解析文件，填充默认值后返回给前端
     */
    Live2dVO uploadLive2d(FilePart filePart);

    /**
     * 解压live2d压缩包，返回解压后的文件夹
     */
    File uncompressLive2d(File destCompressFile) throws IOException;

    /**
     * 新增
     */
    int insertLive2d(Live2dDTO live2dDTO);

    /**
     * 更新
     */
    int updateLive2d(Live2dDTO live2dDTO);

    /**
     * 通过id查询
     */
    Live2dVO getLive2dById(Integer id);

    /**
     * 删除live2d
     */
    int deleteLive2d(Integer id);

    /**
     * 查询全部
     */
    List<Live2dVO> listLive2d();

    /**
     * 查询是否存在，如果存在就返回
     *
     * @throws cn.edu.gxust.blogex.common.exception.Live2dNotFoundException if not exist
     */
    Live2dPO checkExist(Integer live2dId);

    /**
     * 条件查询
     */
    Pagination<Live2dVO> listPage(Live2dPageQuery query);

    /**
     * 随机获取10条live2d列表，这里不包括下架的模型，即showMark=1的模型会被查出
     */
    List<Live2dVO> getRandomTenList();

    /**
     * 下载live2d
     *
     * @param id       live2dId
     * @param response 流式响应
     */
    Mono<Void> downloadLive2d(Integer id, ServerHttpResponse response);

    /**
     * 获取live2d的词库
     */
    String getModelMessage();

    /**
     * 编辑live2d词库
     */
    void editModelMessage(Object o);

    /**
     * 备份live2d，返回下载地址
     */
    String backupLive2d();
}
