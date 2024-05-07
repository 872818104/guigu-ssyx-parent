package com.crh.ssyx.search.api;

import com.crh.ssyx.model.search.SkuEs;
import com.crh.ssyx.search.service.SkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-12 16:09
 **/
@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {
    @Autowired
    private SkuService skuService;

    @ApiOperation(value = "获取爆品商品")
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }
}
