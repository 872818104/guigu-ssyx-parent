package com.crh.ssyx.sys.controller;


import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.sys.Region;
import com.crh.ssyx.sys.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/sys/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable("keyword") String keyword) {
        List<Region> regionList = regionService.findRegionByKeyword(keyword);
        return Result.ok(regionList);
    }




}

