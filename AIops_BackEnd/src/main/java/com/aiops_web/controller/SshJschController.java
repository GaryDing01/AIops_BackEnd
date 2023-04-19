// package com.aiops_web.controller;

// import javax.annotation.Resource;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;

// import com.aiops_web.service.neo4j.SshJschService;
// import com.aiops_web.std.ResponseStd;

// @RestController(value = "SshJsch")
// @RequestMapping("/ssh")
// public class SshJschController {

//     @Resource
//     private SshJschService sshJschService;

//     @RequestMapping(value = "/test", method = RequestMethod.GET)
//     public ResponseStd<String> test() {
//         sshJschService.test();
//         return new ResponseStd<>("info", 200, "success", "返回相关ssh获取信息!");
//     }
// }
