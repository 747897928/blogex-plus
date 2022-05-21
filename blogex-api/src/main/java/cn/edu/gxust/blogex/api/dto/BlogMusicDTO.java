package cn.edu.gxust.blogex.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BlogMusicDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @NotBlank(message = "音乐名不能为空")
    @ApiModelProperty(value = "音乐名")
    private String musicName;

    @ApiModelProperty(value = "专辑作者")
    private String musicArtist;

    @URL(message = "不是一个正确的url")
    @ApiModelProperty(value = "音乐播放链接")
    private String musicUrl;

    @ApiModelProperty(value = "专辑图片链接")
    private String musicCover;

    @ApiModelProperty(value = "歌词lrc")
    private String musicLrc;

    @Max(message = "priority不能大于100", value = 100L)
    @Min(message = "priority不能小于于1", value = 1L)
    @ApiModelProperty(value = "优先级，用于排序", name = "priority", example = "1")
    private Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getMusicLrc() {
        return musicLrc;
    }

    public void setMusicLrc(String musicLrc) {
        this.musicLrc = musicLrc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMusicCover() {
        return musicCover;
    }

    public void setMusicCover(String musicCover) {
        this.musicCover = musicCover;
    }

    @Override
    public String toString() {
        return "BlogMusicDTO{" +
                "id=" + id +
                ", musicName='" + musicName + '\'' +
                ", musicArtist='" + musicArtist + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", musicCover='" + musicCover + '\'' +
                ", musicLrc='" + musicLrc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

