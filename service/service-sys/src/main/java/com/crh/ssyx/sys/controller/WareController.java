package com.crh.ssyx.sys.controller;


import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.sys.Ware;
import com.crh.ssyx.sys.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仓库
 */

@RestController
@RequestMapping("/admin/sys/ware")
public class WareController {
    @Autowired
    private WareService wareService;

    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> wareList = wareService.list();
        return Result.ok(wareList);
    }

}

