package com.share1024.test.controller;

import com.share1024.test.service.ConnectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:08
 * \* Description:
 * \
 */
@Api(tags = "websocket连接")
@RestController
@RequestMapping("/ws")
public class WebsocketConnectController {

    @Autowired
    private ConnectService connectService;

    @ApiImplicitParams({@ApiImplicitParam(name = "wsUrl",value = "ws地址",required = true),
                        @ApiImplicitParam(name = "id",value = "连接标志id",required = true)})
    @ApiOperation(value = "建立单个ws连接")
    @PostMapping("connectUri")
    public ResponseEntity<String> connectUri(@RequestParam("id") int id,@RequestParam("wsUrl") String wsUrl){
        connectService.connect(id,wsUrl);
        return ResponseEntity.ok("ok");
    }

}