package cn.edu.gxust.blogex.dao.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:09:24
 */
@TableName(value = "t_blog_music")
public class BlogMusicPO extends Model<BlogMusicPO> implements Serializable {

    private static final long serialVersionUID = -21900583811079160L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 音乐名
     */
    private String musicName;

    /**
     * 专辑作者
     */
    private String musicArtist;

    /**
     * 音乐播放链接
     */
    private String musicUrl;

    /**
     * 歌词lrc
     */
    private String musicLrc;

    /**
     * 专辑图片链接
     */
    private String musicCover;

    /**
     * 优先级，用于排序
     */
    private Integer priority;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(update = "now()", fill = FieldFill.UPDATE)
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
        return "BlogMusicPO{" +
                "id=" + id +
                ", musicName='" + musicName + '\'' +
                ", musicArtist='" + musicArtist + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", musicLrc='" + musicLrc + '\'' +
                ", musicCover='" + musicCover + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

