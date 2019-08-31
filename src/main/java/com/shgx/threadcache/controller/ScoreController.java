package com.shgx.threadcache.controller;

import com.shgx.threadcache.model.ApiResponse;
import com.shgx.threadcache.model.ScoreVO;
import com.shgx.threadcache.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import static com.shgx.threadcache.model.HeadConstants.TOKEN;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
@RestController
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Value("${token}")
    private String token;

    /**
     * 查询成绩1
     * @param tokenHead
     * @param uid
     * @param scoreId
     * @return
     */
    @RequestMapping(path = "/query1/{uid}/{scoreId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<ScoreVO> queryScoreOne(@RequestHeader(TOKEN) String tokenHead,
                                           @PathVariable("uid") String uid,
                                           @PathVariable("scoreId") String scoreId){
        if(!tokenHead.equals(token)||uid==null||scoreId==null){
            return new ApiResponse<ScoreVO>().fail(new ScoreVO());
        }
        ScoreVO scoreVO = scoreService.queryScoreOne(uid, scoreId);
        return new ApiResponse<ScoreVO>().success(scoreVO);
    }

    /**
     * 查询成绩2
     * @param tokenHead
     * @param uid
     * @param scoreId
     * @return
     */
    @RequestMapping(path = "/query2/{uid}/{scoreId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<ScoreVO> queryScoreTwo(@RequestHeader(TOKEN) String tokenHead,
                                           @PathVariable("uid") String uid,
                                           @PathVariable("scoreId") String scoreId){
        if(!tokenHead.equals(token)||uid==null||scoreId==null){
            return new ApiResponse<ScoreVO>().fail(new ScoreVO());
        }
        ScoreVO scoreVO = scoreService.queryScoreTwo(uid, scoreId);
        return new ApiResponse<ScoreVO>().success(scoreVO);
    }
}
