package com.atguigu.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    // 当出现异常时，进行回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        // 读取Excel
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(dictMapper)).sheet().doRead();
        log.info("Excel导入成功!");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        //从数据库中获取excel数据
        List<Dict> dictList = baseMapper.selectList(null);
        // 将DictList收集为ExcelDictDTOList
        List<ExcelDictDTO> excelDictDTOList = new ArrayList<>();
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            // 属性拷贝
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        try {
            //先向Redis查询是否存在数据列表
            List<Dict> dictList = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if (dictList != null) {
                // 如果Redis中存在，则直接返回Redis中的数据列表
                log.info("从Redis中获取数据列表");
                return dictList;
            }
        } catch (Exception e) {
            log.error("Redis服务异常:" + ExceptionUtils.getStackTrace(e));
        }
        // 若Redis中不存在，则向数据库查询
        List<Dict> list = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        log.info("从数据库中获取数据列表");
        // 填充hasChildren属性
        List<Dict> dictList = list.stream().map(dict -> {
            // 判断当前节点是否有子节点，找到当前dict的下级有没有子节点
            boolean hasChildren = hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
            return dict;
        }).collect(Collectors.toList());

        try {
            // 查询完数据库，将数据列表放入Redis，过期时间为五分钟
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId, dictList, 5, TimeUnit.MINUTES);
            log.info("将数据列表放入Redis，过期时间为五分钟");
        } catch (Exception e) {
            log.error("Redis服务异常:" + ExceptionUtils.getStackTrace(e));
        }
        return dictList;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict dict = this.baseMapper.selectOne(
                new QueryWrapper<Dict>().eq("dict_code", dictCode));
        return this.listByParentId(dict.getId());
    }

    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
        Dict parentDict = baseMapper.selectOne(
                new LambdaQueryWrapper<Dict>()
                        .eq(Dict::getDictCode, dictCode));
        if (parentDict == null) {
            return "";
        }
        Dict dict = baseMapper.selectOne(
                new LambdaQueryWrapper<Dict>()
                        .eq(Dict::getParentId, parentDict.getId())
                        .eq(Dict::getValue, value));
        if (dict == null) {
            return "";
        }
        return dict.getName();
    }

    /**
     * @Description 辅助方法，判断当前id所在的节点下是否有子节点
     */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        Integer count = baseMapper.selectCount(dictQueryWrapper.eq("parent_id", id));
        return count > 0;
    }
}
